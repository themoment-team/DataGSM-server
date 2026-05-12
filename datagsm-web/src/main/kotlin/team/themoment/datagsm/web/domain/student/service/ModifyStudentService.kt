package team.themoment.datagsm.web.domain.student.service

import team.themoment.datagsm.common.domain.student.dto.request.UpdateStudentReqDto
import team.themoment.datagsm.shared.domain.student.dto.StudentResDto

interface ModifyStudentService {
    fun execute(
        studentId: Long,
        reqDto: UpdateStudentReqDto,
    ): StudentResDto
}
