package team.themoment.datagsm.openapi.domain.club.service

import team.themoment.datagsm.common.domain.club.dto.request.ClubReqDto
import team.themoment.datagsm.shared.domain.club.dto.ClubResDto

interface CreateClubService {
    fun execute(clubReqDto: ClubReqDto): ClubResDto
}
