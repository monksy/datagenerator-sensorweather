package com.mrmonksy.datageneratorsensorweather

import java.util.Calendar

import scala.util.Random


/**
  * @author ${user.name}
  */
object App {


  def main(args: Array[String]) {
    if (args.length != 3) {
      System.err.println(
        """
          |Must have 3 arguements:\n
          | - Ids (comma seperated numbers)
          | - Every (int) seconds
          | - Days - Int: The days to run this.
        """.stripMargin)
    }

    val ids = args(0).split(",")
    val seconds = args(1).toInt
    val days = args(2).toInt
    val totalSecondSectionsInDay = (24 * 60 * 60) / seconds

    val rand = new Random()
    val scalarForIds = ids.map(a => (a, rand.nextDouble() * 0.4 + 0.8)).toMap

    println("checkintime,id,temperature")

    val curDayTime = Calendar.getInstance()
    curDayTime.set(Calendar.SECOND, 0)

    for (curDay <- 1 to days) {
      val dayTolerences = dailyDeviation(ids)
      val curInstanceDay = curDayTime.clone().asInstanceOf[Calendar]
      curInstanceDay.add(Calendar.DAY_OF_YEAR, curDay)

      for (curId <- ids) {
        val idTime = curInstanceDay.clone().asInstanceOf[Calendar]

        val curDayVariation = cos(50 * scalarForIds(curId), idTime.get(Calendar.DAY_OF_YEAR) / 365.0, Math.PI, 50)
        for (secondOfDay <- 0 to totalSecondSectionsInDay) {
          idTime.add(Calendar.SECOND, seconds)
          val intraDayVariance = cos(dayTolerences(curId), secondOfDay / totalSecondSectionsInDay.toDouble, Math.PI, 0)
          val temperature = intraDayVariance + curDayVariation
          println(f"${idTime.getTimeInMillis},$curId,$temperature%1.2f")
        }
      }
    }
  }

  def cos(scalar: Double, angularFraction: Double, angleOffSet: Double, offSet: Double): Double = {
    scalar * Math.cos(angularFraction * 2 * Math.PI + angleOffSet) + offSet
  }

  def dailyDeviation(ids: Array[String]): Map[String, Double] = {
    val random = new Random()
    ids.map(i => (i -> random.nextDouble() * 10)).toMap
  }

}
