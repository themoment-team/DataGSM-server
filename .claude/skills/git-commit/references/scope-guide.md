# Commit Scope Selection Guide

## Priority Rule

**Domain name > Module name**

Always use a domain name. Only fall back to a module name when the change is genuinely cross-cutting (affects multiple domains or the entire module).

## Discover Scope at Runtime

Do not use a hardcoded list. Determine scope from:

1. `git diff --name-only` — look at changed file paths
2. Directory structure — infer domain/module from path segments (e.g., `src/auth/`, `packages/user/`, `services/payment/`)
3. Project-specific conventions in `CLAUDE.md` or `.claude/rules/`

## Module / Cross-cutting Names (Secondary)

| Scope    | When to use                              |
|----------|------------------------------------------|
| `global` | Affects multiple modules or the whole project |
| `ci/cd`  | Build / deployment pipelines             |
| (module) | Changes scoped to one module but multiple domains |

## Examples

| Wrong | Correct | Reason |
|-------|---------|--------|
| `fix(module): 로그인 버그 수정` | `fix(auth): 로그인 버그 수정` | auth is the domain |
| `update(common): 유저 엔티티 수정` | `update(user): 엔티티 필드 추가` | user entity belongs to user domain |
| `add(module): 결제 필터 추가` | `add(payment): 결제 필터 추가` | payment feature is payment domain |

## Correct Cross-cutting Usage

```
refactor(global): 공통 예외 처리 로직 개선
update(ci/cd): GitHub Actions 워크플로우 최적화
```