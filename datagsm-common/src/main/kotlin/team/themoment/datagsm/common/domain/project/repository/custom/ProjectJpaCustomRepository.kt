package team.themoment.datagsm.common.domain.project.repository.custom

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import team.themoment.datagsm.common.domain.project.entity.ProjectJpaEntity
import team.themoment.datagsm.common.global.constant.SortDirection
import team.themoment.datagsm.shared.domain.project.constant.ProjectSortBy
import team.themoment.datagsm.shared.domain.project.constant.ProjectStatus

interface ProjectJpaCustomRepository {
    fun searchProjectWithPaging(
        id: Long?,
        name: String?,
        clubId: Long?,
        status: ProjectStatus?,
        pageable: Pageable,
        sortBy: ProjectSortBy?,
        sortDirection: SortDirection,
    ): Page<ProjectJpaEntity>
}
