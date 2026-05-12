package team.themoment.datagsm.openapi.domain.student.service

import team.themoment.datagsm.common.domain.student.dto.request.CreateStudentReqDto
import team.themoment.datagsm.shared.domain.student.dto.StudentResDto

interface CreateStudentService {
    fun execute(reqDto: CreateStudentReqDto): StudentResDto
}
