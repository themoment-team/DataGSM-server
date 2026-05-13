@AGENTS.md

## Claude Code Specific

- 코드 변경 전 관련 도메인의 `.claude/rules/` 파일이 자동 로드되었는지 확인하세요. 누락된 규칙은 추측하지 말고 해당 파일을 직접 읽으세요.
- 컨벤션 위반이 발생했을 때 단순히 수정만 하지 말고, 그 규칙이 `.claude/rules/`에 명문화되어 있는지 확인하고 빠져 있다면 추가를 제안하세요.
- 커밋·PR 전에는 `commit` 스킬을 통해 `./gradlew ktlintFormat`이 적용된 상태인지 확인하세요.
- 코드 리뷰가 필요할 때는 `code-review` 스킬을, 보안 점검이 필요할 때는 `security-checklist` 스킬을 우선 호출하세요.