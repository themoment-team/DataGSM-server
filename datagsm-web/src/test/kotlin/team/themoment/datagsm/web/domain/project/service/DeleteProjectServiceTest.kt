package team.themoment.datagsm.web.domain.project.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import team.themoment.datagsm.common.domain.club.entity.ClubJpaEntity
import team.themoment.datagsm.common.domain.club.entity.constant.ClubType
import team.themoment.datagsm.common.domain.project.entity.ProjectJpaEntity
import team.themoment.datagsm.common.domain.project.repository.ProjectJpaRepository
import team.themoment.datagsm.common.domain.webhook.service.WebhookPublisher
import team.themoment.datagsm.web.domain.project.service.impl.DeleteProjectServiceImpl
import team.themoment.sdk.exception.ExpectedException
import java.util.Optional

class DeleteProjectServiceTest :
    DescribeSpec({

        val mockProjectRepository = mockk<ProjectJpaRepository>()
        val webhookPublisher = mockk<WebhookPublisher>()

        val deleteProjectService = DeleteProjectServiceImpl(mockProjectRepository, webhookPublisher)

        beforeEach {
            justRun { webhookPublisher.dispatch(any(), any()) }
        }

        afterEach {
            clearAllMocks()
        }

        describe("DeleteProjectService 클래스의") {
            describe("execute 메서드는") {

                context("존재하는 프로젝트 ID로 삭제 요청할 때") {
                    val projectId = 1L

                    val ownerClub =
                        ClubJpaEntity().apply {
                            id = 1L
                            name = "SW개발동아리"
                            type = ClubType.MAJOR_CLUB
                        }

                    val existingProject =
                        ProjectJpaEntity().apply {
                            this.id = projectId
                            name = "DataGSM 프로젝트"
                            description = "학교 데이터를 제공하는 API 서비스"
                            this.club = ownerClub
                        }

                    beforeEach {
                        every { mockProjectRepository.findById(projectId) } returns Optional.of(existingProject)
                        every { mockProjectRepository.delete(existingProject) } returns Unit
                    }

                    it("프로젝트가 성공적으로 삭제되어야 한다") {
                        deleteProjectService.execute(projectId)

                        verify(exactly = 1) { mockProjectRepository.findById(projectId) }
                        verify(exactly = 1) { mockProjectRepository.delete(existingProject) }
                    }
                }

                context("존재하지 않는 프로젝트 ID로 삭제 요청할 때") {
                    val projectId = 999L

                    beforeEach {
                        every { mockProjectRepository.findById(projectId) } returns Optional.empty()
                    }

                    it("ExpectedException이 발생해야 한다") {
                        val exception =
                            shouldThrow<ExpectedException> {
                                deleteProjectService.execute(projectId)
                            }

                        exception.message shouldBe "프로젝트를 찾을 수 없습니다."

                        verify(exactly = 1) { mockProjectRepository.findById(projectId) }
                        verify(exactly = 0) { mockProjectRepository.delete(any()) }
                    }
                }

                context("다양한 타입의 동아리 프로젝트를 삭제할 때") {
                    val projectId = 2L

                    val autonomousClub =
                        ClubJpaEntity().apply {
                            id = 2L
                            name = "자율동아리"
                            type = ClubType.AUTONOMOUS_CLUB
                        }

                    val autonomousProject =
                        ProjectJpaEntity().apply {
                            this.id = projectId
                            name = "자율동아리 프로젝트"
                            description = "자율 프로젝트"
                            club = autonomousClub
                        }

                    beforeEach {
                        every { mockProjectRepository.findById(projectId) } returns Optional.of(autonomousProject)
                        every { mockProjectRepository.delete(autonomousProject) } returns Unit
                    }

                    it("자율동아리 프로젝트도 정상적으로 삭제되어야 한다") {
                        deleteProjectService.execute(projectId)

                        verify(exactly = 1) { mockProjectRepository.findById(projectId) }
                        verify(exactly = 1) { mockProjectRepository.delete(autonomousProject) }
                    }
                }
            }
        }
    })
