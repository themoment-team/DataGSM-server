package team.themoment.datagsm.openapi.domain.neis.timetable.service

import team.themoment.datagsm.common.domain.neis.dto.timetable.request.QueryTimetableReqDto
import team.themoment.datagsm.shared.domain.neis.timetable.dto.TimetableResDto

interface SearchTimetableService {
    fun execute(queryReq: QueryTimetableReqDto): TimetableResDto
}
