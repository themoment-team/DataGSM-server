package team.themoment.datagsm.openapi.domain.student.service

import team.themoment.datagsm.common.domain.student.dto.request.QueryStudentReqDto
import team.themoment.datagsm.shared.domain.student.dto.StudentListResDto

interface QueryStudentService {
    fun execute(queryReq: QueryStudentReqDto): StudentListResDto
}
