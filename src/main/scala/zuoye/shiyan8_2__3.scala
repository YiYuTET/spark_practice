package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan8_2__3 {
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
        
        val users_sex = users.map(x => (x._1, x._2))
        val users_ID = ratings.map(x => (x._1, x._2))
        val movie_name = movie.map(x => (x._1, x._2))
        
        // 用户ID，电影ID，性别
        val usersID_movieID_usersSex = users_ID.join(users_sex)
        //usersID_movieID_usersSex.foreach(println(_))
        
        val movieId_1_man = usersID_movieID_usersSex.filter(x => x._2._2 == "M").map(x => (x._2._1, (1, x._2._2)))
        val movieId_1_woman = usersID_movieID_usersSex.filter(x => x._2._2 == "F").map(x => (x._2._1, (1, x._2._2)))
        val movieId_Count_man = movieId_1_man.reduceByKey((x1, x2) => (x1._1 + x2._1, x1._2))
        val movieId_Count_woman = movieId_1_woman.reduceByKey((x1, x2) => (x1._1 + x2._1, x1._2))
        // 电影ID，次数，性别
        val movieId_top10_man = sc.parallelize(movieId_Count_man.sortBy(x => x._2._1, false).take(10))
        val movieId_top10_woman = sc.parallelize(movieId_Count_woman.sortBy(x => x._2._1, false).take(10))
        
        val movieIdName_man = movieId_top10_man.join(movie_name)
        val movieIdName_woman = movieId_top10_woman.join(movie_name)
        
        val result_man = movieIdName_man.sortBy(x => x._2._1._1).map(x => (x._2._1._2, x._2._2))
        val result_woman = movieIdName_woman.sortBy(x => x._2._1._1).map(x => (x._2._1._2, x._2._2))
        result_man.foreach(println(_))
        result_woman.foreach(println(_))
    }
}
