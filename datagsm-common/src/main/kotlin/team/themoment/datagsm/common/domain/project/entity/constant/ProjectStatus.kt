package team.themoment.datagsm.common.domain.project.entity.constant

import team.themoment.datagsm.ksp.annotation.KmpExport

@KmpExport
enum class ProjectStatus(
    val value: String,
) {
    ACTIVE("운영 중"),
    ENDED("종료"),
}
