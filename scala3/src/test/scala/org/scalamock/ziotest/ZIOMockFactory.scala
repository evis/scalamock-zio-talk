package org.scalamock.ziotest

import org.scalamock.MockFactoryBase
import org.scalamock.context.CallLog
import org.scalamock.handlers.UnorderedHandlers
import zio.ZIO

trait ZIOMockFactory extends MockFactoryBase:

  protected def initializeExpectations() =
    val initialHandlers = new UnorderedHandlers
    callLog = new CallLog

    expectationContext = initialHandlers
    currentExpectationContext = initialHandlers

  private def clearExpectations() =
    // to forbid setting expectations after verification is done
    callLog = null
    expectationContext = null
    currentExpectationContext = null

  protected def verifyExpectations() =
    callLog foreach expectationContext.verify _

    val oldCallLog = callLog
    val oldExpectationContext = expectationContext

    clearExpectations()

    if (!oldExpectationContext.isSatisfied)
      reportUnsatisfiedExpectation(oldCallLog, oldExpectationContext)
