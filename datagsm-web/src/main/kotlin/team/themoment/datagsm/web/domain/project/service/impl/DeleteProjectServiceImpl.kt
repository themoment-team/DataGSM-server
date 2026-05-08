package team.themoment.datagsm.web.domain.project.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.datagsm.common.domain.project.repository.ProjectJpaRepository
import team.themoment.datagsm.common.domain.webhook.dto.payload.ProjectDeletedData
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent
import team.themoment.datagsm.common.domain.webhook.service.WebhookDispatchService
import team.themoment.datagsm.web.domain.project.service.DeleteProjectService
import team.themoment.sdk.exception.ExpectedException

@Service
class DeleteProjectServiceImpl(
    private val projectJpaRepository: ProjectJpaRepository,
    private val webhookDispatchService: WebhookDispatchService,
) : DeleteProjectService {
    @Transactional
    override fun execute(projectId: Long) {
        val project =
            projectJpaRepository
                .findById(projectId)
                .orElseThrow { ExpectedException("프로젝트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }
        projectJpaRepository.delete(project)

        webhookDispatchService.dispatch(
            WebhookEvent.PROJECT_DELETED,
            ProjectDeletedData(projectId = project.id!!, name = project.name),
        )
    }
}
