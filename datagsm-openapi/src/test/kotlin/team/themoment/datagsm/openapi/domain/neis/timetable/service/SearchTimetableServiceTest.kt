package team.themoment.datagsm.openapi.domain.neis.timetable.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.toKotlinLocalDate
import team.themoment.datagsm.common.domain.neis.dto.timetable.request.QueryTimetableReqDto
import team.themoment.datagsm.common.domain.neis.timetable.entity.TimetableRedisEntity
import team.themoment.datagsm.common.domain.neis.timetable.repository.TimetableRedisRepository
import team.themoment.datagsm.openapi.domain.neis.timetable.service.impl.SearchTimetableServiceImpl
import java.time.LocalDate

class SearchTimetableServiceTest :
    DescribeSpec({

        val mockTimetableRepository = mockk<TimetableRedisRepository>()
        val searchTimetableService = SearchTimetableServiceImpl(mockTimetableRepository)

        afterEach {
            clearAllMocks()
        }

        describe("SearchTimetableService 클래스의") {
            describe("execute 메서드는") {

                context("특정 날짜로 검색할 때") {
                    val targetDate = LocalDate.of(2025, 4, 1)
                    val timetable1 =
                        TimetableRedisEntity(
                            id = "7380292_20250401_1_1_1",
                            schoolCode = "7380292",
                            schoolName = "광주소프트웨어마이스터고등학교",
                            officeCode = "F10",
                            officeName = "광주광역시교육청",
                            date = targetDate,
                            academicYear = "2025",
                            semester = "1",
                            grade = 1,
                            classNum = 1,
                            period = 1,
                            subject = "국어",
                        )
                    val timetable2 =
                        TimetableRedisEntity(
                            id = "7380292_20250401_1_1_2",
                            schoolCode = "7380292",
                            schoolName = "광주소프트웨어마이스터고등학교",
                            officeCode = "F10",
                            officeName = "광주광역시교육청",
                            date = targetDate,
                            academicYear = "2025",
                            semester = "1",
                            grade = 1,
                            classNum = 1,
                            period = 2,
                            subject = "수학",
                        )

                    beforeEach {
                        every {
                            mockTimetableRepository.findByGradeAndClassNumAndDate(1, 1, targetDate)
                        } returns listOf(timetable1, timetable2)
                    }

                    it("해당 날짜의 시간표 정보를 반환해야 한다") {
                        val result =
                            searchTimetableService.execute(
                                QueryTimetableReqDto(grade = 1, classNum = 1, date = targetDate),
                            )

                        result.timetables.size shouldBe 2
                        result.timetables[0].timetableId shouldBe "7380292_20250401_1_1_1"
                        result.timetables[0].grade shouldBe 1
                        result.timetables[0].classNum shouldBe 1
                        result.timetables[0].period shouldBe 1
                        result.timetables[0].subject shouldBe "국어"
                        result.timetables[1].timetableId shouldBe "7380292_20250401_1_1_2"
                        result.timetables[1].period shouldBe 2

                        verify(exactly = 1) { mockTimetableRepository.findByGradeAndClassNumAndDate(1, 1, targetDate) }
                    }
                }

                context("날짜 범위로 검색할 때") {
                    val fromDate = LocalDate.of(2025, 4, 1)
                    val toDate = LocalDate.of(2025, 4, 2)
                    val timetable1 =
                        TimetableRedisEntity(
                            id = "7380292_20250401_1_1_1",
                            schoolCode = "7380292",
                            schoolName = "광주소프트웨어마이스터고등학교",
                            officeCode = "F10",
                            officeName = "광주광역시교육청",
                            date = fromDate,
                            academicYear = "2025",
                            semester = "1",
                            grade = 1,
                            classNum = 1,
                            period = 1,
                            subject = "국어",
                        )
                    val timetable2 =
                        TimetableRedisEntity(
                            id = "7380292_20250402_1_1_1",
                            schoolCode = "7380292",
                            schoolName = "광주소프트웨어마이스터고등학교",
                            officeCode = "F10",
                            officeName = "광주광역시교육청",
                            date = toDate,
                            academicYear = "2025",
                            semester = "1",
                            grade = 1,
                            classNum = 1,
                            period = 1,
                            subject = "영어",
                        )

                    beforeEach {
                        every {
                            mockTimetableRepository.findByGradeAndClassNumAndDateBetween(1, 1, fromDate, toDate)
                        } returns listOf(timetable1, timetable2)
                    }

                    it("날짜 범위 내의 시간표 정보를 반환해야 한다") {
                        val result =
                            searchTimetableService.execute(
                                QueryTimetableReqDto(grade = 1, classNum = 1, fromDate = fromDate, toDate = toDate),
                            )

                        result.timetables.size shouldBe 2
                        result.timetables[0].timetableDate shouldBe fromDate.toKotlinLocalDate()
                        result.timetables[1].timetableDate shouldBe toDate.toKotlinLocalDate()

                        verify(exactly = 1) {
                            mockTimetableRepository.findByGradeAndClassNumAndDateBetween(1, 1, fromDate, toDate)
                        }
                    }
                }

                context("fromDate만 지정하여 검색할 때") {

                    val fromDate = LocalDate.of(2025, 4, 1)
                    val timetable =
                        TimetableRedisEntity(
                            id = "7380292_20250401_1_1_1",
                            schoolCode = "7380292",
                            schoolName = "광주소프트웨어마이스터고등학교",
                            officeCode = "F10",
                            officeName = "광주광역시교육청",
                            date = fromDate,
                            academicYear = "2025",
                            semester = "1",
                            grade = 1,
                            classNum = 1,
                            period = 1,
                            subject = "국어",
                        )

                    beforeEach {
                        every {
                            mockTimetableRepository.findByGradeAndClassNumAndDateGreaterThanEqual(1, 1, fromDate)
                        } returns listOf(timetable)
                    }

                    it("fromDate 이후의 시간표 정보를 반환해야 한다") {
                        val result =
                            searchTimetableService.execute(
                                QueryTimetableReqDto(grade = 1, classNum = 1, fromDate = fromDate),
                            )

                        result.timetables.size shouldBe 1
                        result.timetables[0].timetableDate shouldBe fromDate.toKotlinLocalDate()

                        verify(exactly = 1) {
                            mockTimetableRepository.findByGradeAndClassNumAndDateGreaterThanEqual(1, 1, fromDate)
                        }
                    }
                }

                context("toDate만 지정하여 검색할 때") {
                    val toDate = LocalDate.of(2025, 4, 30)
                    val timetable =
                        TimetableRedisEntity(
                            id = "7380292_20250430_1_1_1",
                            schoolCode = "7380292",
                            schoolName = "광주소프트웨어마이스터고등학교",
                            officeCode = "F10",
                            officeName = "광주광역시교육청",
                            date = toDate,
                            academicYear = "2025",
                            semester = "1",
                            grade = 1,
                            classNum = 1,
                            period = 1,
                            subject = "국어",
                        )

                    beforeEach {
                        every {
                            mockTimetableRepository.findByGradeAndClassNumAndDateLessThanEqual(1, 1, toDate)
                        } returns listOf(timetable)
                    }

                    it("toDate 이전의 시간표 정보를 반환해야 한다") {
                        val result =
                            searchTimetableService.execute(
                                QueryTimetableReqDto(grade = 1, classNum = 1, toDate = toDate),
                            )

                        result.timetables.size shouldBe 1
                        result.timetables[0].timetableDate shouldBe toDate.toKotlinLocalDate()

                        verify(exactly = 1) {
                            mockTimetableRepository.findByGradeAndClassNumAndDateLessThanEqual(1, 1, toDate)
                        }
                    }
                }

                context("검색 결과가 없을 때") {
                    val targetDate = LocalDate.of(2025, 4, 1)

                    beforeEach {
                        every {
                            mockTimetableRepository.findByGradeAndClassNumAndDate(1, 1, targetDate)
                        } returns emptyList()
                    }

                    it("빈 목록을 반환해야 한다") {
                        val result =
                            searchTimetableService.execute(
                                QueryTimetableReqDto(grade = 1, classNum = 1, date = targetDate),
                            )

                        result.timetables.size shouldBe 0

                        verify(exactly = 1) { mockTimetableRepository.findByGradeAndClassNumAndDate(1, 1, targetDate) }
                    }
                }
            }
        }
    })
