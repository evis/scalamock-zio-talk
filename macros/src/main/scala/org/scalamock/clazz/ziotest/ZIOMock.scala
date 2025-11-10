package org.scalamock.clazz.ziotest

import org.scalamock.clazz.{Mock, MockImpl}
import org.scalamock.context.MockContext
import zio.{ULayer, ZLayer}

import scala.quoted.*

@scala.annotation.experimental
trait ZIOMock:

  import scala.language.implicitConversions

  inline def zioMock[T](implicit mockContext: MockContext): ULayer[T] =
    ${ ZIOMockImpl.zioMock[T]('mockContext) }

@scala.annotation.experimental
private[ziotest] object ZIOMockImpl extends Mock:
  def zioMock[T: Type](mockContext: Expr[MockContext])(using Quotes): Expr[ULayer[T]] =
    '{ ZLayer.succeed(${MockImpl.mock[T](mockContext)}) }
