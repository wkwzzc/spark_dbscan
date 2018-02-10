package org.alitouka.spark.dbscan.util.debug


private [dbscan] class Clock /*extends Logging*/ {
  val startTime = System.currentTimeMillis()

  def logTimeSinceStart (): Unit = {
    logTimeSinceStart("Test")
  }

  def logTimeSinceStart (message: String) = {
    val currentTime = System.currentTimeMillis()
    val timeSinceStart = (currentTime - startTime) / 1000.0

   // logInfo (s"$message took $timeSinceStart seconds")
  }
}
