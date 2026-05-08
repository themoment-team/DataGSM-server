package team.themoment.datagsm.common.domain.webhook.dto.payload

import com.fasterxml.jackson.annotation.JsonProperty

data class ClubUpdatedData(
    @field:JsonProperty("club_id")
    val clubId: Long,
    @field:JsonProperty("name")
    val name: String,
    @field:JsonProperty("type")
    val type: String,
)
