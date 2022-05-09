package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan7_3 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setMaster("local")
        conf.setAppName("MySpark")
        val sc = new SparkContext(conf)
        //创建RDD
        val s = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\正文数据\\第3章\\data\\student.txt")
        val b = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\正文数据\\第3章\\data\\result_bigdata.txt")
        val m = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\正文数据\\第3章\\data\\result_math.txt")
        //读取ID，姓名，成绩
        val student = s.map { x =>
            val line = x.split("\t"); (line(0), line(1))
        }
        val bigdata = b.map { x =>
            val line = x.split("\t"); (line(0), line(2).toInt)
        }
        //println(bigdata.collect().mkString("Array(", ", ", ")"))
        val math = m.map { x =>
            val line = x.split("\t"); (line(0), line(2).toInt)
        }
        //用join将三个RDD连接
        val join_scores = student.join(bigdata).join(math)
        //println(join_scores.collect().mkString("Array(", ", ", ")"))
        //将join的RDD展平
        val scores = join_scores.map(x => (x._1, x._2._1._1, x._2._1._2, x._2._2))
        //println(scores.collect().mkString("Array(", ", ", ")"))
        //计算平均成绩
        val avg_score = scores.map(x => (x._1, x._2, (x._3 + x._4).toDouble / 2))
        println(avg_score.collect().mkString("Array(", ", ", ")"))
    }
}
