# Spring Security SpEL 파싱 오류 분석 및 해결

## 개요

프로덕션 환경(`datagsm-oauth-userinfo` 모듈)에서 `/userinfo` 엔드포인트 호출 시
`IllegalArgumentException: Failed to evaluate expression` 오류가 발생하는 장애를 분석하고 해결했습니다.

---

## 문제 상황

`/userinfo` 엔드포인트는 OAuth2 Access Token의 scope를 검증하기 위해
`@PreAuthorize` 어노테이션에 Spring SpEL 표현식을 사용하고 있었습니다.

```kotlin
@PreAuthorize("hasAuthority('SCOPE_' + @oauthJwtVerificationEnvironment.datagsmApplicationId + ':self_read')")
fun getUserInfo(): AccountInfoResDto = queryUserInfoService.execute()
```

이 표현식은 로컬 환경에서는 문제가 없어 보였지만,
프로덕션에서 매 요청마다 아래 스택 트레이스와 함께 실패했습니다.

```
IllegalArgumentException: Failed to evaluate expression
  'hasAuthority('SCOPE_' + @oauthJwtVerificationEnvironment.datagsmApplicationId + ':self_read')'
at ExpressionUtils.java:68 (evaluate)
at PreAuthorizeAuthorizationManager.authorize(...)
```

---

## 원인 분석

Spring SpEL 파서는 표현식을 토큰 단위로 분석합니다.
문제는 세 번째 피연산자인 `':self_read'` 에 있었습니다.

SpEL 파서는 콜론(`:`)을 **삼항 연산자(`? A : B`)의 구분자**로 인식합니다.
따라서 `':self_read'` 를 문자열 리터럴로 파싱하지 못하고
삼항 연산자의 일부로 해석하여 파싱 오류가 발생했습니다.

### 과거 사례와의 비교

같은 프로젝트의 PR #34에서도 동일한 원인으로 `SpelParseException`이 발생한 적이 있었습니다.
당시 해결 방식은 scope 값을 작은따옴표로 감싸는 것이었습니다.

```kotlin
// PR #34 해결 방식
@PreAuthorize("@scopeChecker.hasScope(authentication, '{scope}')")
```

그러나 이번 케이스는 구조가 달랐습니다.

| | PR #34 | 이번 케이스 |
|---|---|---|
| 구조 | 어노테이션 파라미터를 단순 치환 | bean 프로퍼티 + 문자열을 `+`로 동적 연결 |
| 해결 가능 여부 | 작은따옴표로 감싸면 해결 | `+` 연결 표현식 전체의 파싱 문제가 남아 동일 방법 적용 불가 |

PR #34 방식을 그대로 적용해도 `+` 로 연결되는 복합 표현식 구조에서는
파서가 전체 표현식을 올바르게 해석하지 못하는 문제가 남기 때문에
SpEL 자체를 사용하지 않는 방향으로 해결했습니다.

---

## 해결 방법

`@PreAuthorize`를 제거하고, `SecurityConfig`의 `authorizeHttpRequests`에서
Kotlin 문자열 템플릿으로 authority를 조합하여 처리했습니다.

```kotlin
// SecurityConfig.kt
val selfReadAuthority = OAuthScope.authorityOf(
    oauthJwtVerificationEnvironment.datagsmApplicationId,
    "self_read"
)

http.authorizeHttpRequests {
    it.requestMatchers("/userinfo").hasAuthority(selfReadAuthority)
      .anyRequest().permitAll()
}
```

또한 권한 문자열 조합 로직이 `SecurityConfig`에 직접 노출되는 문제를 해결하기 위해
`OAuthScope.companion`에 `authorityOf` 헬퍼 메서드를 추가하여 중앙화했습니다.

```kotlin
// OAuthScope.kt
companion object {
    fun authorityOf(applicationId: String, scopeName: String): String =
        "SCOPE_$applicationId:$scopeName"
}
```

---

## 결과

- 프로덕션 장애 해결: `/userinfo` 엔드포인트 정상화
- SpEL 의존 제거로 동일 유형의 파싱 오류 재발 방지
- 권한 문자열 생성 로직을 `OAuthScope`에 집중시켜 유지보수성 향상