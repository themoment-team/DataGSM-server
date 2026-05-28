package team.themoment.datagsm.common.domain.webhook.validator

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class WebhookUrlValidatorTest :
    DescribeSpec({
        describe("WebhookUrlValidator의") {
            describe("isPrivateOrLocalUrl 메서드는") {
                context("유효한 외부 URL이 주어질 때") {
                    it("false를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("https://example.com/hook") shouldBe false
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://example.com/hook") shouldBe false
                    }
                }

                context("localhost URL이 주어질 때") {
                    it("true를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://localhost/hook") shouldBe true
                        WebhookUrlValidator.isPrivateOrLocalUrl("https://localhost:8080/hook") shouldBe true
                    }
                }

                context("루프백 IP(127.x.x.x)가 주어질 때") {
                    it("true를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://127.0.0.1/hook") shouldBe true
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://127.0.0.1:8080/hook") shouldBe true
                    }
                }

                context("10.x.x.x 대역 IP가 주어질 때") {
                    it("true를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://10.0.0.1/hook") shouldBe true
                    }
                }

                context("172.16.x.x 대역 IP가 주어질 때") {
                    it("true를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://172.16.0.1/hook") shouldBe true
                    }
                }

                context("192.168.x.x 대역 IP가 주어질 때") {
                    it("true를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://192.168.1.1/hook") shouldBe true
                    }
                }

                context("링크-로컬 IP(169.254.x.x)가 주어질 때") {
                    it("true를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://169.254.169.254/hook") shouldBe true
                    }
                }

                context("IPv6 루프백(::1)이 주어질 때") {
                    it("true를 반환해야 한다") {
                        WebhookUrlValidator.isPrivateOrLocalUrl("http://[::1]/hook") shouldBe true
                    }
                }
            }
        }
    })
