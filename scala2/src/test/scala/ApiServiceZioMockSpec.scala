import zio._
import zio.test._
import zio.mock._

@mockable[UserService]
object UserServiceMock

object ApiServiceZioMockSpec extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment, Any] =
    suite("ApiServiceZioMockSpec")(
      test("return greeting")(
        for {
          result <- ZIO.serviceWithZIO[ApiService](_.getGreeting(4))
        } yield assertTrue(result == "Hello, Agent Smith!")
      ).provide(
        ZLayer.derive[ApiService],
        UserServiceMock.GetUserName(
          assertion = Assertion.equalTo(4),
          result = Expectation.value("Agent Smith")
        )
      )
    )
}
