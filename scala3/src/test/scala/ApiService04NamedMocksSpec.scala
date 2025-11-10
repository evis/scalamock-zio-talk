import org.scalamock.MockFactoryBase
import zio.*
import zio.test.*

object ApiService04NamedMocksSpec extends ZIOSpecDefault with MockFactoryBase:

  override def spec: Spec[TestEnvironment, Any] = {
    suite("ApiServiceSpec")(
      test("return greeting"): // GREEN! 🟢
        for
          _ <- ZIO.serviceWith[UserService]:
            _.getUserName.expects(4).returns(ZIO.succeed("Agent Smith"))
          result <- ZIO.serviceWithZIO[ApiService](_.getGreeting(4))
        yield assertTrue(result == "Hello, Agent Smith!")
      .provide(
        ZLayer.derive[ApiService],
        ZLayer.succeed(mock[UserService]("greeting"))
      ),
      test("return greeting 2"): // BOOM! 💥
        for
          _ <- ZIO.serviceWith[UserService]:
            _.getUserName.expects(4).returns(ZIO.succeed("Agent Smith"))
          result <- ZIO.serviceWithZIO[ApiService](_.getGreeting(4))
        yield assertTrue(result == "Hello, Agent Smith!")
      .provide(
        ZLayer.derive[ApiService],
        ZLayer.succeed(mock[UserService]("greeting 2"))
      )
    )
  }

  override type ExpectationException = Exception

  override protected def newExpectationException(
      message: String,
      methodName: Option[Symbol]
  ) = new Exception(message)
