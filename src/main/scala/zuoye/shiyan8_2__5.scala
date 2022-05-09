package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan8_2__5 {
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
        
        val usersID_movie2116_rating = ratings.map(x=>(x._1,(x._2,x._3))).filter(x=>x._2._1=="2116")
        val usersID_age = users.map(x=>(x._1,x._3))
        //usersID_movie2116_rating.foreach(println(_))
        
        val usersId_movie2116_rating_age = usersID_movie2116_rating.join(usersID_age)
        //usersId_movie2116_rating_age.foreach(println(_))
        val age_rating = usersId_movie2116_rating_age.map(x=>(x._2._2,x._2._1._2.toInt)).groupByKey()
        //age_rating.foreach(println(_))
        val age_ratingAvg = age_rating.map(x=>(x._1,(x._2.sum.toDouble/x._2.size).formatted("%.2f")))
        age_ratingAvg.foreach(println(_))
    }
}
