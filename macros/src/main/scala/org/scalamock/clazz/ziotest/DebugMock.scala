package org.scalamock.clazz.ziotest

import org.scalamock.clazz.MockImpl
import org.scalamock.context.MockContext

import scala.quoted.*

@scala.annotation.experimental
trait DebugMock:
  import scala.language.implicitConversions

  inline def debugMock[T](implicit mockContext: MockContext): T =
    ${DebugMockImpl.debugMock[T]('mockContext)}

@scala.annotation.experimental
private[ziotest] object DebugMockImpl:
  def debugMock[T: Type](mockContext: Expr[MockContext])
                        (using quotes: Quotes): Expr[T] =
    val result = MockImpl.mock[T](mockContext)
    println(s"// Генерируем мок для типа: ${Type.show[T]}")
    println(s"// Сгенерированный код:")
    println(s"${result.show}")
    result
