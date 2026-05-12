package team.themoment.datagsm.web.domain.project.service

import team.themoment.datagsm.common.domain.project.dto.request.ProjectReqDto
import team.themoment.datagsm.shared.domain.project.dto.ProjectResDto

interface CreateProjectService {
    fun execute(projectReqDto: ProjectReqDto): ProjectResDto
}
