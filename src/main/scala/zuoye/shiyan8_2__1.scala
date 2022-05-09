package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan8_2__1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setMaster("local")
        conf.setAppName("ShiYan8")
        val sc = new SparkContext(conf)
        val movie_data = sc.textFile("F:\\Office文档\\Word文档\\Spark_scala\\Dataset\\ml-1m\\movies.dat")
        val ratings_data = sc.textFile("F:\\Office文档\\Word文档\\Spark_scala\\Dataset\\ml-1m\\ratings.dat")
        val users_data = sc.textFile("F:\\Office文档\\Word文档\\Spark_scala\\Dataset\\ml-1m\\users.dat")
        val movie = movie_data.map(x => x.split("::")).map(x => (x(0), x(1), x(2)))
        val ratings = ratings_data.map(x => x.split("::")).map(x => (x(0), x(1), x(2), x(3)))
        val users = users_data.map(x => x.split("::")).map(x => (x(0), x(1), x(2), x(3), x(4)))
        
        val movie_ID = ratings.map(x=>(x._2, 1))
        val movie_count = movie_ID.reduceByKey((x1, x2)=>x1+x2).sortBy(_._2, ascending = false)
        val movie_name = movie.map(x=>(x._1, x._2))
        val result = movie_count.join(movie_name).sortBy(x=>x._2._1, ascending = false).map(x=>(x._2._2, x._2._1))
        result.take(10).foreach(println(_))
    }
    
}
