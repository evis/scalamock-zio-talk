import org.scalamock.clazz.ziotest.DebugMock
import zio.*
import zio.test.*

import scala.annotation.experimental

@experimental
object ApiService11DebugConcreteMockSpec extends ScalamockZIOSpec with DebugMock:

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
      // run `sbt scala3/test:compile` to see debug logs
      ZLayer.succeed(debugMock[UserService])
    )
  }
