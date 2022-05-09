package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan7_1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setAppName("MySpark")
        conf.setMaster("local")
        val sc = new SparkContext(conf)
        //读取文件，创建RDD
        val test_data = sc.textFile("F:\\IDEA_scala_workspace\\Maven\\Data\\实例数据\\第3章\\test.txt")
        //取出每行第4列
        val test = test_data.map { x =>
            val line = x.split(",");
            line(3)
        }
        //过滤
        val man = test.filter(_ == "男")
        //计数
        println(s"男性用户为：${man.count()}")
    }
}
