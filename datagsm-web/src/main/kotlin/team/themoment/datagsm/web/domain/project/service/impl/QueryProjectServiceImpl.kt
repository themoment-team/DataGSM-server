package team.themoment.datagsm.web.domain.project.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.datagsm.common.domain.project.dto.request.QueryProjectReqDto
import team.themoment.datagsm.common.domain.project.repository.ProjectJpaRepository
import team.themoment.datagsm.shared.domain.club.dto.ClubSummaryDto
import team.themoment.datagsm.shared.domain.project.dto.ProjectListResDto
import team.themoment.datagsm.shared.domain.project.dto.ProjectResDto
import team.themoment.datagsm.shared.domain.student.dto.ParticipantInfoDto
import team.themoment.datagsm.web.domain.project.service.QueryProjectService

@Service
class QueryProjectServiceImpl(
    private val projectJpaRepository: ProjectJpaRepository,
) : QueryProjectService {
    @Transactional(readOnly = true)
    override fun execute(queryReq: QueryProjectReqDto): ProjectListResDto {
        val projectPage =
            projectJpaRepository.searchProjectWithPaging(
                id = queryReq.projectId,
                name = queryReq.projectName,
                clubId = queryReq.clubId,
                status = queryReq.status,
                pageable = PageRequest.of(queryReq.page, queryReq.size),
                sortBy = queryReq.sortBy,
                sortDirection = queryReq.sortDirection,
            )

        return ProjectListResDto(
            totalPages = projectPage.totalPages,
            totalElements = projectPage.totalElements,
            projects =
                projectPage.content.map { project ->
                    ProjectResDto(
                        id = project.id!!,
                        name = project.name,
                        description = project.description,
                        startYear = project.startYear,
                        endYear = project.endYear,
                        status = project.status,
                        club = project.club?.let { ClubSummaryDto(id = it.id!!, name = it.name, type = it.type) },
                        participants =
                            project.participants.map { student ->
                                ParticipantInfoDto(
                                    id = student.id!!,
                                    name = student.name,
                                    email = student.email,
                                    studentNumber = student.studentNumber?.fullStudentNumber,
                                    major = student.major,
                                    sex = student.sex,
                                )
                            },
                    )
                },
        )
    }
}
