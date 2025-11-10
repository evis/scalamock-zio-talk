import org.scalamock.MockFactoryBase
import zio.*
import zio.test.*

object ApiService01MockOutsideLayerSpec extends ZIOSpecDefault with MockFactoryBase:

  override def spec: Spec[TestEnvironment, Any] = {
    val m = mock[UserService]
    m.getUserName.expects(4).returns(ZIO.succeed("Agent Smith"))
    suite("ApiServiceSpec")(
      test("return greeting"): // GREEN! 🟢
        for result <- ZIO.serviceWithZIO[ApiService](_.getGreeting(4))
        yield assertTrue(result == "Hello, Agent Smith!")
      .provide(ZLayer.derive[ApiService], ZLayer.succeed(m)),
    )
  }

  override type ExpectationException = Exception

  override protected def newExpectationException(
      message: String,
      methodName: Option[Symbol]
  ) = new Exception(message)
