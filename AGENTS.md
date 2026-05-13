**한국어로 응답하고 작업해주세요 (Please respond and work in Korean).**

## Project Overview

DataGSM은 광주소프트웨어마이스터고등학교의 학교 정보(학생, 동아리, 급식, 시간표 등)를 제공하는 Spring Boot REST API 서버입니다. Google OAuth2(JWT) 인증과 API Key 기반 외부 공개 API를 함께 제공합니다.

## Tech Stack

- **Language / Framework**: Kotlin 2.3.10, Spring Boot 4.0.3, Spring Security, Spring Data JPA
- **Build**: Gradle (Java 25 toolchain), multi-module
- **Database**: MySQL (main), Redis (cache, session)
- **Query / Integration**: QueryDSL, OpenFeign
- **Serialization**: Jackson 3.0
- **Testing**: Kotest (`DescribeSpec`) + MockK + JUnit 5

## Project Structure

```
datagsm-server/
├── datagsm-common/              # 공유 Entity/DTO/Repository/Config, Health API (실행 모듈 아님)
├── datagsm-oauth-authorization/ # OAuth2 인증, 계정 라이프사이클(회원가입, 비밀번호 재설정)
├── datagsm-oauth-userinfo/      # OAuth2 UserInfo 엔드포인트 (외부 클라이언트용)
├── datagsm-openapi/             # 외부 공개 API (API Key 인증): student, club, project, webhook, neis
└── datagsm-web/                 # 웹 서비스 API: account, auth, application, client, student, club, project, utility (Excel 처리 포함)
```

각 모듈은 도메인별로 `controller/ → service/ → repository/` + `entity/`, `dto/` 구조를 따릅니다.

**Key Paths**

- `/v1/health` 엔드포인트는 `datagsm-common`의 `HealthController`가 제공하며 모든 실행 모듈에 공유됩니다.
- 공통 Entity: `datagsm-common/src/main/kotlin/team/themoment/datagsm/common/domain/`
- 공통 예외 핸들러: `datagsm-common/src/main/kotlin/team/themoment/datagsm/common/global/common/error/`
- API 응답: 성공 응답은 DTO를 직접 반환합니다. 예외 응답은 `GlobalExceptionHandler`가 `CommonApiResponse`로 래핑합니다.

## Commands

- Build: `./gradlew build`
- Test: `./gradlew test`
- Format (commit 전 필수): `./gradlew ktlintFormat`
- Run a module: `./gradlew :<module>:bootRun` (실행 가능 모듈: `datagsm-oauth-authorization`, `datagsm-oauth-userinfo`, `datagsm-openapi`, `datagsm-web`)

## Core Rules (요약)

상세 규칙은 `.claude/rules/`에 정의되어 있습니다. 아래는 매 세션 적용되는 최소 규칙입니다.

- **계층**: Controller → Service → Repository 패턴을 유지하세요. Service 인터페이스(`*Service`)와 구현체(`*ServiceImpl`)를 분리하세요.
- **DI**: 항상 생성자 주입을 사용하세요. `@Autowired` 필드 주입은 금지입니다.
- **불변성**: `val`을 우선 사용하세요. `var`는 재할당이 반드시 필요한 경우(루프 누적, Logback 주입 등)에만 사용하세요.
- **Null 안전성**: `!!`를 사용하지 말고 `?.`, `?:`, `requireNotNull`을 사용하세요.
- **트랜잭션**: `@Transactional`은 **메서드 레벨에만** 붙이세요. 읽기는 `readOnly = true`, 쓰기는 기본 `@Transactional`을 사용하세요.
- **JPA**: N+1을 피하기 위해 Fetch Join 또는 `@EntityGraph`를 사용하세요.
- **테스트**: Kotest `DescribeSpec` + MockK + Given-When-Then 구조를 사용하세요. 테스트 이름은 한국어로 작성하세요 (`describe("클래스명 클래스의")`).
- **주석**: 로직이 자명하지 않은 경우에만 작성하세요. 과도한 주석을 추가하지 마세요.

## Detailed Rules (`.claude/rules/`)

해당 파일을 작업할 때 자동 로드됩니다.

- `kotlin-style.md` — `val/var`, 생성자 주입, null 안전성
- `dto-annotations.md` — Jackson/Swagger의 `@field:` vs `@param:` 규칙
- `api-conventions.md` — `@RequestParam` vs `@ModelAttribute`, DTO 명명, `@Transactional` 배치
- `logging.md` — 영어 only, SLF4J `{}` 플레이스홀더, 콜론 구분자 금지
- `exception.md` — `ExpectedException` 사용 규칙과 메시지 포맷
- `commit-conventions.md` — 커밋 `type(scope): 설명` 규칙 (scope는 도메인명)

## Notes

- 파일 변경을 제안할 때는 `.gitignore`를 항상 확인하세요.
- 코드를 분석할 때는 다중 모듈 구조와 모듈 간 의존을 고려하세요 (`datagsm-common`은 모든 실행 모듈의 base).