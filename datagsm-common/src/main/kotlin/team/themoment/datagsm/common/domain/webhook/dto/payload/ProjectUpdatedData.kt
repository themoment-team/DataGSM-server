package team.themoment.datagsm.common.domain.webhook.dto.payload

import com.fasterxml.jackson.annotation.JsonProperty

data class ProjectUpdatedData(
    @field:JsonProperty("project_id")
    val projectId: Long,
    @field:JsonProperty("name")
    val name: String,
)
