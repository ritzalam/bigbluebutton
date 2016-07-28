package org.bigbluebutton.core2x.api

import java.util.concurrent.TimeUnit

object TimestampGenerator {

  def generateTimestamp(): Long = {
    TimeUnit.NANOSECONDS.toMillis(System.nanoTime())
  }

  def getCurrentTime(): Long = {
    System.currentTimeMillis();
  }
}