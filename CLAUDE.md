@AGENTS.md

## Claude Code Specific

- 컨벤션이 불확실할 때는 추측하지 말고 `.claude/rules/`의 해당 규칙 파일을 직접 읽으세요.
- 컨벤션 위반이 발생했을 때 단순히 수정만 하지 말고, 그 규칙이 `.claude/rules/`에 명문화되어 있는지 확인하고 빠져 있다면 추가를 제안하세요.
- 커밋·PR 전에는 `./gradlew ktlintFormat`을 실행해 포맷을 맞추세요.