import org.apache.spark.{SparkConf, SparkContext}

object T {
    def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "F:\\Development_Environment")
        val conf = new SparkConf().setAppName("MLlib").setMaster("local[*]")
        val sc = new SparkContext(conf)
        
        val data = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\car\\part-00000.txt").map(x=>x.split("("))
        data.foreach(println(_))
    }
}
