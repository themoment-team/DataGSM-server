# Comment Rules

Write comments only when the logic is not self-evident. Do NOT add excessive comments.

## When to write

- Non-obvious business rules or domain constraints
- Workarounds for specific bugs or framework quirks
- Hidden invariants that a future reader could not infer from the code

## When NOT to write

- Restating what well-named identifiers already convey
- Narrating step-by-step what the code does
- Marking referenced tickets, callers, or the current task (those belong in the commit/PR description)

```kotlin
// WRONG — restates the obvious
// Find student by id
val student = studentRepository.findById(id).orElseThrow { ... }

// CORRECT — surfaces a non-obvious constraint
// NEIS API returns 0 instead of null for "no allergy", so treat 0 as absent.
val allergy = response.allergyCode.takeIf { it != 0 }
```
