package zuoye

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}


object shiyan9_1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
        val sc = new SparkContext(conf)
        val spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()
        import spark.implicits._
        
        // RDD->DataFrame
        val rdd = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\people.dat").map(x => x.split("::")).map(x => (x(0), x(1), x(2), x(3), x(4)))
        //people.foreach(println(_))
        val people: DataFrame = rdd.toDF("id", "sex", "age", "col4", "col5")
//        people.printSchema()
//        people.show(5)
//        people.head(3).foreach(println(_))
        //people.collect().foreach(println(_))
        val ageCount = people.groupBy("age").count()
        ageCount.collect().foreach(println(_))
        
        
        //csv->DataFrame
//        val people = spark.read.csv("F:\\IDEA_scala_workspace\\Maven\\Data\\people.csv")
//        people.printSchema()
//        people.show(5)
//        people.head(3).foreach(println(_))
//        people.collect().foreach(println(_))
//        val ageCount = people.groupBy("_c2").count()
//        ageCount.collect().foreach(println(_))
        
        //MySql数据库->DataFrame
//        val people = spark.read
//            .format("jdbc")
//            .option("url", "jdbc:mysql://192.168.17.129/test_db")
//            .option("dbtable", "people")
//            .option("user", "root")
//            .option("password", "971192zyw")
//            .load()
//        people.printSchema()
//        people.show(5)
//        people.head(3).foreach(println(_))
//        people.collect().foreach(println(_))
        
        
        //hive->创建Dataframe
        
    }
}
