package team.themoment.datagsm.openapi.domain.neis.schedule.service

import team.themoment.datagsm.common.domain.neis.dto.schedule.request.QueryScheduleReqDto
import team.themoment.datagsm.shared.domain.neis.schedule.dto.ScheduleResDto

interface SearchScheduleService {
    fun execute(reqDto: QueryScheduleReqDto): ScheduleResDto
}
