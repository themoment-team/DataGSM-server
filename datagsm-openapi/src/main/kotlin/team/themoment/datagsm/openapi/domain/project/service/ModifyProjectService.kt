package team.themoment.datagsm.openapi.domain.project.service

import team.themoment.datagsm.common.domain.project.dto.request.ProjectReqDto
import team.themoment.datagsm.shared.domain.project.dto.ProjectResDto

interface ModifyProjectService {
    fun execute(
        projectId: Long,
        reqDto: ProjectReqDto,
    ): ProjectResDto
}
