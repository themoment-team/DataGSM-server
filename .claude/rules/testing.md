# Testing Rules

## Framework

Use **Kotest `DescribeSpec` + MockK + JUnit 5** with the **Given-When-Then** structure.

## Naming

Test names are written in Korean. Use `describe("클래스명 클래스의")` for the top-level scope.

```kotlin
class StudentServiceImplTest : DescribeSpec({
    describe("StudentServiceImpl 클래스의") {
        context("execute 메서드를 호출할 때") {
            it("학생을 조회한다") {
                // Given
                val id = 1L
                every { studentRepository.findById(id) } returns Optional.of(student)

                // When
                val result = service.execute(id)

                // Then
                result.id shouldBe id
            }
        }
    }
})
```

## Structure

- **Given**: prepare data, configure mocks (`every { } returns ...`)
- **When**: invoke the system under test
- **Then**: assert results (`shouldBe`, `shouldThrow<T>`, `verify { }`)
