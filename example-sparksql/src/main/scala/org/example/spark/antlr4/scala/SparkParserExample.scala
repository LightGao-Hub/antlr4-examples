package org.example.spark.antlr4.scala

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.{FunctionIdentifier, TableIdentifier}
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.parser.ParserInterface
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.types.{DataType, StructType}


class SparkParserExample(spark: SparkSession, parser: ParserInterface) extends ParserInterface {

  override def parsePlan(sqlText: String): LogicalPlan =
    parser.parsePlan(sqlText)

  override def parseExpression(sqlText: String): Expression =
    parser.parseExpression(sqlText)

  override def parseTableIdentifier(sqlText: String): TableIdentifier =
    parser.parseTableIdentifier(sqlText)

  override def parseFunctionIdentifier(sqlText: String): FunctionIdentifier =
    parser.parseFunctionIdentifier(sqlText)

  override def parseTableSchema(sqlText: String): StructType =
    parser.parseTableSchema(sqlText)

  override def parseDataType(sqlText: String): DataType =
    parser.parseDataType(sqlText)
}
