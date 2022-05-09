package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan8_1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setMaster("local")
        conf.setAppName("ShiYan8")
        val sc = new SparkContext(conf)
        val content_data = sc.textFile("F:\\学习\\Spark\\书籍资源\\Spark大数据技术与应用-实训数据\\第4章\\jc_content_viewlog.txt")
        
        val data = content_data.map(x => x.split(",")).map(x => (x(0), x(1), x(2), x(3), x(4), x(5)))
        //data.foreach(println(_))
        val userCount = data.map(x => x._4).distinct().count()
        println(s"用户数：$userCount")
        
        val contentCount = data.map(x=>x._2).distinct().count()
        println(s"网页个数：$contentCount")
        
        val month = data.map(x=>x._6).map(x=>x.split(" ")).map(x=>x(0)).map(x=>x.split("-")).map(x=>(x(1), 1))
        //month.foreach(println(_))
        val monthCount = month.groupByKey().map(x=>(x._1, x._2.sum))
        monthCount.foreach(println("每月访问量：", _))
    }
}
