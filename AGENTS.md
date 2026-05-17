**한국어로 응답하고 작업해주세요 (Please respond and work in Korean).**

## Project Overview

DataGSM is a Spring Boot REST API server that exposes Gwangju Software Meister High School data (students, clubs, meals, timetable, etc.). It provides Google OAuth2 (JWT) authentication for internal clients and API-Key authentication for external public APIs.

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
├── datagsm-common/              # Shared Entity/DTO/Repository/Config, Health API (library module)
├── datagsm-oauth-authorization/ # OAuth2 authentication, account lifecycle (signup, password reset)
├── datagsm-oauth-userinfo/      # OAuth2 UserInfo endpoint (for external clients)
├── datagsm-openapi/             # External public API (API-Key auth)
│                                #   Domains: student, club, project, webhook, neis
└── datagsm-web/                 # Web service API (includes Excel processing)
                                 #   Domains: account, auth, application, client, student, club, project, utility
```

Each module follows the `controller/ → service/ → repository/` layering with `entity/` and `dto/` packages per domain.

**Key Paths**

- The `/v1/health` endpoint is served by `HealthController` in `datagsm-common` and is shared across all runnable modules.
- Shared entities: `datagsm-common/src/main/kotlin/team/themoment/datagsm/common/domain/`
- Global exception handling: `datagsm-common/src/main/kotlin/team/themoment/datagsm/common/global/common/error/`
- API response: controllers return DTOs directly — `the-sdk`'s `ResponseBodyAdvice` automatically wraps them in `CommonApiResponse`. Use `CommonApiResponse<Nothing>` explicitly only when no data is returned (e.g. delete operations). Exception responses are wrapped by `GlobalExceptionHandler`.

## Runnable Modules

Modules launched via `./gradlew :<module>:bootRun`: `datagsm-oauth-authorization`, `datagsm-oauth-userinfo`, `datagsm-openapi`, `datagsm-web`. (`datagsm-common` is a library module and is not run directly.)

## Detailed Rules (`.claude/rules/`)

The following rule files are auto-loaded when working on related code:

- `kotlin-style.md` — `val/var`, constructor injection, null safety
- `dto-annotations.md` — Jackson/Swagger `@field:` vs `@param:` targets
- `api-conventions.md` — `@RequestParam` vs `@ModelAttribute`, DTO naming, `@Transactional` placement
- `logging.md` — English only, SLF4J `{}` placeholder, no colon separators
- `exception.md` — `ExpectedException` usage and message format
- `testing.md` — Kotest `DescribeSpec` + MockK + Given-When-Then structure
- `comments.md` — when to write comments (only for non-obvious logic)
- `commit-conventions.md` — commit `type(scope): description` rules (scope = domain name)

## Notes

- Always check `.gitignore` before proposing file changes.
- When analyzing code, consider the multi-module structure and inter-module dependencies (`datagsm-common` is the base for every runnable module).
