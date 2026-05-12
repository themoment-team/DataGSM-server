package team.themoment.datagsm.web.domain.club.service

import team.themoment.datagsm.common.domain.club.dto.request.QueryClubReqDto
import team.themoment.datagsm.shared.domain.club.dto.ClubListResDto

interface QueryClubService {
    fun execute(queryReq: QueryClubReqDto): ClubListResDto
}
