import org.scalamock.MockFactoryBase
import zio.*
import zio.test.*
import zio.test.TestAspect.sequential

object ApiService06NotVerifiedSpec extends ZIOSpecDefault with MockFactoryBase:

  override def spec: Spec[TestEnvironment, Any] = {
    suite("ApiServiceSpec")(
      test("return greeting"): // GREEN! 🟢
        for
          _ <- ZIO.serviceWith[UserService]:
            _.getUserName.expects(4).returns(ZIO.succeed("Agent Smith"))
          result <- ZIO.serviceWithZIO[ApiService](_.getGreeting(4))
        yield assertTrue(result == "Hello, Agent Smith!")
      ,
      test("return greeting 2"): // GREEN! 🟢
        for
          _ <- ZIO.serviceWith[UserService]:
            _.getUserName.expects(4).returns(ZIO.succeed("Agent Smith"))
          result <- ZIO.serviceWithZIO[ApiService](_.getGreeting(4))
        yield assertTrue(result == "Hello, Agent Smith!")
      ,
      test("should fail"): // GREEN! 💥
        for
          _ <- ZIO.serviceWith[UserService]:
            _.getUserName.expects(4).returns(ZIO.succeed("Agent Smith"))
        yield assertCompletes
    ).provide(
      ZLayer.derive[ApiService],
      ZLayer.succeed(mock[UserService])
    ) @@ sequential
  }

  override type ExpectationException = Exception

  override protected def newExpectationException(
      message: String,
      methodName: Option[Symbol]
  ) = new Exception(message)
