package team.themoment.datagsm.web.domain.student.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.datagsm.common.domain.club.repository.ClubJpaRepository
import team.themoment.datagsm.common.domain.student.repository.StudentJpaRepository
import team.themoment.datagsm.common.domain.webhook.dto.payload.StudentGraduatedData
import team.themoment.datagsm.common.domain.webhook.entity.constant.WebhookEvent
import team.themoment.datagsm.common.domain.webhook.service.WebhookPublisher
import team.themoment.datagsm.shared.domain.student.constant.StudentRole
import team.themoment.datagsm.web.domain.student.service.GraduateStudentService
import team.themoment.sdk.exception.ExpectedException

@Service
class GraduateStudentServiceImpl(
    private val studentJpaRepository: StudentJpaRepository,
    private val clubJpaRepository: ClubJpaRepository,
    private val webhookPublisher: WebhookPublisher,
) : GraduateStudentService {
    @Transactional
    override fun execute(studentId: Long) {
        val student =
            studentJpaRepository.findByIdOrNull(studentId)
                ?: throw ExpectedException("학생을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        clubJpaRepository.findAllByLeader(student).forEach { it.leader = null }

        student.role = StudentRole.GRADUATE
        student.major = null
        student.specialty = null
        student.studentNumber = null
        student.dormitoryRoomNumber = null
        student.majorClub = null
        student.autonomousClub = null

        webhookPublisher.dispatch(
            WebhookEvent.STUDENT_GRADUATED,
            StudentGraduatedData(studentId = student.id!!, name = student.name, email = student.email),
        )
    }
}
