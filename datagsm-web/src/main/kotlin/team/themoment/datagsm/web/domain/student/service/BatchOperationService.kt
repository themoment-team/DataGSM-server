package team.themoment.datagsm.web.domain.student.service

import team.themoment.datagsm.common.domain.student.dto.request.BatchOperationReqDto
import team.themoment.datagsm.shared.domain.student.dto.GraduateStudentResDto

interface BatchOperationService {
    fun execute(reqDto: BatchOperationReqDto): GraduateStudentResDto
}
