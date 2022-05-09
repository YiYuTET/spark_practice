import org.apache.spark.{SparkConf, SparkContext}

object test {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("mySpark")
        conf.setMaster("local")
        val sc = new SparkContext(conf)
        
        val b = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\正文数据\\第3章\\data\\result_bigdata.txt")
        val m = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\正文数据\\第3章\\data\\result_math.txt")
        
        val bigdata = b.map { x =>
            val line = x.split("\t");
            (line(0), line(2).toInt)
        }
        //println(bigdata.collect().mkString("Array(", ", ", ")"))
        
        val math = m.map { x =>
            val line = x.split("\t");
            (line(0), line(2).toInt)
        }
        //println(math.collect().mkString("Array(", ", ", ")"))
        
        /*Task3
        val all_score = math.union(bigdata).reduceByKey((x,y)=>x+y)
        println(all_score.collect().mkString("Array(", ", ", ")"))*/
        
        //Task4
        val scores = bigdata.union(math)
        //println(scores.collect().mkString("Array(", ", ", ")"))
        val cb_scores = scores.combineByKey(
            score => (score, 1),
            (acc: (Int, Int), score) => (acc._1 + score, acc._2 + 1),
            (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
        //println(cb_scores.collect().mkString("Array(", ", ", ")"))
        val avg_scores = cb_scores.map(x => (x._1, x._2._1.toDouble / x._2._2))
        println(avg_scores.collect().mkString("Array(", ", ", ")"))
    }
}
