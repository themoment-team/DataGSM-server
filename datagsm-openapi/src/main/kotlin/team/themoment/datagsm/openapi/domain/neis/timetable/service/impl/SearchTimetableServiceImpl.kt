package team.themoment.datagsm.openapi.domain.neis.timetable.service.impl

import kotlinx.datetime.toKotlinLocalDate
import org.springframework.stereotype.Service
import team.themoment.datagsm.common.domain.neis.dto.timetable.request.QueryTimetableReqDto
import team.themoment.datagsm.common.domain.neis.timetable.entity.TimetableRedisEntity
import team.themoment.datagsm.common.domain.neis.timetable.repository.TimetableRedisRepository
import team.themoment.datagsm.openapi.domain.neis.timetable.service.SearchTimetableService
import team.themoment.datagsm.shared.domain.neis.timetable.dto.TimetableInfoResDto
import team.themoment.datagsm.shared.domain.neis.timetable.dto.TimetableResDto

@Service
class SearchTimetableServiceImpl(
    private val timetableRedisRepository: TimetableRedisRepository,
) : SearchTimetableService {
    override fun execute(queryReq: QueryTimetableReqDto): TimetableResDto {
        val grade = queryReq.grade
        val classNum = queryReq.classNum
        val date = queryReq.date
        val fromDate = queryReq.fromDate
        val toDate = queryReq.toDate

        val timetables =
            when {
                date != null ->
                    timetableRedisRepository.findByGradeAndClassNumAndDate(grade, classNum, date)
                fromDate != null && toDate != null ->
                    timetableRedisRepository.findByGradeAndClassNumAndDateBetween(grade, classNum, fromDate, toDate)
                fromDate != null ->
                    timetableRedisRepository.findByGradeAndClassNumAndDateGreaterThanEqual(grade, classNum, fromDate)
                toDate != null ->
                    timetableRedisRepository.findByGradeAndClassNumAndDateLessThanEqual(grade, classNum, toDate)
                else -> emptyList()
            }

        return TimetableResDto(timetables = timetables.map { it.toInfoResDto() })
    }

    private fun TimetableRedisEntity.toInfoResDto(): TimetableInfoResDto =
        TimetableInfoResDto(
            timetableId = id,
            schoolCode = schoolCode,
            schoolName = schoolName,
            officeCode = officeCode,
            officeName = officeName,
            timetableDate = date.toKotlinLocalDate(),
            academicYear = academicYear,
            semester = semester,
            grade = grade,
            classNum = classNum,
            period = period,
            subject = subject,
        )
}
