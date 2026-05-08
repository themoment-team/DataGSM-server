# Webhook 기능 설계 결정 (이슈 #293)

## 결론

**Webhook은 API Key 인증 경로에서만 지원한다. OAuth(Client Credentials) 경로는 지원하지 않는다.**

---

## 배경: 두 인증 체계가 왜 충돌하는가

이슈에서 언급된 "인증 수단별 스코프 호환성 문제"의 원인은 두 인증 체계의 scope 형식이 근본적으로 다르기 때문이다.

### API Key scope

`ApiKeyScope` enum에 하드코딩된 고정 문자열이다.

```
student:read, student:write, student:*
club:read, club:write, club:*
project:read, project:write, project:*
neis:read, neis:*
```

`ApiKey` 엔티티의 `scopes` 컬럼(JSON)에 위 문자열들의 집합으로 저장된다.

### OAuth scope

`ApplicationJpaEntity` + `OAuthScopeJpaEntity` 구조로, 앱 개발자가 자유롭게 정의한다.

```
OAuthScopeJpaEntity.scopeName = "student_info"  (자유 문자열)
applicationId = "gsm-main"
→ 최종 scope 문자열: "gsm-main:student_info"
```

JWT의 `scopes` claim에 `"applicationId:scopeName"` 형식으로 저장된다.

### 형식 충돌

| | API Key | OAuth |
|---|---|---|
| scope 형식 | `student:read` | `gsm-main:student_info` |
| 앞부분 의미 | 도메인 | applicationId |
| 뒷부분 의미 | 권한 레벨 | 자유 문자열 |

`ScopeValidator.hasScope()`는 문자열 일치로 검증하기 때문에, `webhook:write`를 API Key 쪽에 추가하더라도 OAuth 토큰에서는 `"gsm-main:webhook:write"` 형식이 되어 매칭이 불가능하다.

---

## OAuth를 지원하지 않는 근거

현재 `datagsm-oauth-userinfo` 모듈이 제공하는 엔드포인트는 `/userinfo` 하나다.

```kotlin
// QueryUserInfoServiceImpl.kt
val account = currentUserProvider.getCurrentAccount()  // sub(email)로 계정 조회
```

이 API는 **"지금 로그인한 GSM 구성원이 누구인지"** 를 반환하는 소셜 로그인 용도다.

Client Credentials 토큰의 `sub`은 사용자 email이 아닌 `clientId`다. 즉 Client Credentials로 `/userinfo`를 호출하면 계정을 찾지 못하고 404가 발생한다. **현재 OAuth 모듈 전체가 사용자가 존재하는 Authorization Code 흐름만 상정하고 있으며, 서버 간 통신(Client Credentials)으로 실제 호출할 수 있는 API가 없다.**

OAuth Client Credentials 경로에서 Webhook을 등록할 수 있게 해도, 그 토큰으로 호출할 수 있는 API 자체가 없으므로 의미가 없다.

---

## 구현 방향

`ApiKeyScope` enum에 `WEBHOOK_WRITE` 추가 후 API Key 경로에서만 Webhook CRUD를 허용한다.

```kotlin
// ApiKeyScope.kt에 추가
WEBHOOK_WRITE("webhook:write", "Webhook 등록/수정/삭제", AccountRole.USER)
```

Webhook 엔드포인트에는 기존 패턴과 동일하게 `@RequireScope` 어노테이션을 적용한다.

```kotlin
@RequireScope(ApiKeyScope.WEBHOOK_WRITE)
@PostMapping("/v1/webhooks")
fun createWebhook(...) { ... }
```

---

## 프론트엔드 연동 흐름

Webhook은 외부 서버가 GSM 이벤트를 수신하기 위한 기능이다. 프론트엔드는 **Webhook을 직접 사용하지 않고**, 사용자가 Webhook을 등록·관리할 수 있도록 UI를 제공하는 역할이다.

### 전제: API Key 발급

Webhook 등록 API는 `webhook:write` scope가 포함된 API Key로만 호출할 수 있다. 기존 API Key 발급 UI에서 `webhook:write` scope를 선택 가능하도록 항목을 추가해야 한다.

```
기존 scope 선택 항목:
  [x] student:read    학생 데이터 조회
  [x] club:read       동아리 데이터 조회
  ...

추가될 항목:
  [ ] webhook:write   Webhook 등록/수정/삭제
```

### Webhook 관리 UI 흐름

```
1. 사용자가 webhook:write scope를 포함한 API Key 발급
   (기존 API Key 발급 흐름과 동일)

2. 사용자가 Webhook 등록 화면에서 입력:
   - 수신할 URL (외부 서버 주소)
   - 구독할 이벤트 선택
     [ ] student.graduated   학생 졸업
     [ ] student.withdrawn   학생 자퇴
     [ ] club.created        동아리 생성
     [ ] club.updated        동아리 수정
     [ ] club.deleted        동아리 삭제
     ... (이슈 참고)

3. 등록 성공 시 secret 1회 노출
   → 사용자에게 "이 값은 다시 확인할 수 없습니다" 안내 필요
   → 외부 서버에서 수신한 요청의 X-DataGSM-Signature 헤더 검증에 사용

4. 이후 CRUD는 동일한 API Key로 호출
```

### 보안 헤더 안내 (외부 서버 개발자용 문서에 포함할 내용)

GSM 서버가 Webhook을 전송할 때 아래 헤더를 함께 보낸다. 외부 서버는 이 값을 검증해야 위조 요청을 걸러낼 수 있다.

```
X-DataGSM-Signature: sha256=<HMAC-SHA256(secret, payload)>
```

---

## 미결 사항 (이슈 체크리스트 참고)

- [ ] 구독 이벤트 목록 최종 선별
- [ ] Webhook URL 유효성 검증 여부 (등록 시 ping 체크 등)
- [ ] 재시도 정책 확정 (이슈 초안: 최대 3회, 지수 백오프 1s → 10s → 60s)

---

## 디스코드 공유용 요약

**[이슈 #293] Webhook 인증 설계 결정**

결론: **Webhook은 API Key 경로에서만 지원, OAuth는 미지원**

**OAuth를 배제한 이유 두 가지**

첫째, scope 형식이 달라 통합 검증이 불가능합니다.
API Key는 `student:read` (enum 고정값), OAuth는 `gsm-main:student_info` (앱ID + 자유문자열) 형식이라 `ScopeValidator`에서 문자열 일치 비교 자체가 안 됩니다.

둘째, OAuth 서버 간 통신(Client Credentials)으로 호출할 수 있는 API가 현재 없습니다.
OAuth 모듈이 제공하는 건 `/userinfo` 하나뿐인데, 이건 "로그인한 사용자가 누구인지" 반환하는 소셜 로그인 전용입니다. Client Credentials 토큰의 `sub`은 사용자 email이 아닌 clientId라서 `/userinfo`를 호출하면 404가 납니다. Webhook 권한을 줘도 쓸 곳이 없는 상황입니다.

**구현 방향**
`ApiKeyScope`에 `WEBHOOK_WRITE("webhook:write")` 추가, 기존 `@RequireScope` 패턴 그대로 적용

**프론트 변경 필요 사항**
1. API Key 발급 UI에 `webhook:write` scope 선택 항목 추가
2. Webhook 등록 화면 구성 (여러 수신 URL + 구독 이벤트 선택)
3. 등록 후 secret **1회 노출** 안내 ("이 값은 다시 확인할 수 없습니다")
