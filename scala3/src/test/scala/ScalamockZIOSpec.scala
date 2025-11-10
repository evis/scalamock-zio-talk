import org.scalamock.clazz.ziotest.{DebugMock, ZIOMock}
import org.scalamock.ziotest.ZIOMockFactory
import zio.{Chunk, Tag, ULayer, ZIO, ZLayer}
import zio.test.TestAspect.{after, before, sequential}
import zio.test.ZIOSpecDefault

import scala.annotation.experimental

@experimental
trait ScalamockZIOSpec extends ZIOSpecDefault with ZIOMockFactory with ZIOMock:
  override def aspects =
    super.aspects ++
      Chunk(
        sequential,
        before(ZIO.succeed(initializeExpectations())),
        after(ZIO.attempt(verifyExpectations()).orDie))

  override type ExpectationException = Exception

  override protected def newExpectationException(message: String, methodName: Option[Symbol]): ExpectationException =
    new Exception(message)
