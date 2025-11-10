import org.scalamock.ziotest.ZIOMockFactory
import zio.*
import zio.test.*
import zio.test.TestAspect.{after, before, sequential}

object ApiService07VerifiedSpec extends ZIOSpecDefault with ZIOMockFactory:

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
      test("should fail"): // RED! 🔴 As expected
        for
          _ <- ZIO.serviceWith[UserService]:
            _.getUserName.expects(4).returns(ZIO.succeed("Agent Smith"))
        yield assertCompletes
    ).provide(
      ZLayer.derive[ApiService],
      ZLayer.succeed(mock[UserService])
    ) @@ sequential
      @@ before(ZIO.succeed(initializeExpectations()))
      @@ after(ZIO.attempt(verifyExpectations()).orDie)
  }

  override type ExpectationException = Exception

  override protected def newExpectationException(
      message: String,
      methodName: Option[Symbol]
  ) = new Exception(message)
