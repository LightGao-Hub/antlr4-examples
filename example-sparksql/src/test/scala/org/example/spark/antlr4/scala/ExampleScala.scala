package org.example.spark.antlr4.scala

import org.apache.spark.sql.SparkSession
import org.junit.Test

class ExampleScala {

  @Test
  def demo(): Unit = {
    // TODO 创建SparkSQL的运行环境
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local[2]")
      .getOrCreate()

    import spark.implicits._

    Seq(Person("Jack", 12), Person("James", 21), Person("Mac", 30)).toDS().createTempView("person")

    val df = spark.sql("SELECT * FROM PERSON WHERE AGE > 18")

    df.show()
    df.explain(true) // 打印执行计划

  }

  @Test
  def demoParser(): Unit = {
    // TODO 创建SparkSQL的运行环境
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local[2]")
      .withExtensions( e => e.injectParser((session, parser) => new SparkParserExample(session, parser)))
      .getOrCreate()

    import spark.implicits._

    Seq(Person("Jack", 12), Person("James", 21), Person("Mac", 30)).toDS().createTempView("person")

    val df = spark.sql("SELECT * FROM PERSON WHERE AGE > 18")

    df.show()
    df.explain(true) // 打印执行计划

  }


}

case class Person(name: String, age: Int)

