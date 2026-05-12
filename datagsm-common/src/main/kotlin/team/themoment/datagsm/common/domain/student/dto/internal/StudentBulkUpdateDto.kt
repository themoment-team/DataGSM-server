package team.themoment.datagsm.common.domain.student.dto.internal

import team.themoment.datagsm.common.domain.club.entity.ClubJpaEntity
import team.themoment.datagsm.shared.domain.student.constant.Major
import team.themoment.datagsm.shared.domain.student.constant.Sex
import team.themoment.datagsm.shared.domain.student.constant.StudentRole

data class StudentBulkUpdateDto(
    val id: Long,
    val name: String,
    val major: Major?,
    val majorClub: ClubJpaEntity?,
    val autonomousClub: ClubJpaEntity?,
    val dormitoryRoomNumber: Int?,
    val role: StudentRole,
    val sex: Sex,
)
