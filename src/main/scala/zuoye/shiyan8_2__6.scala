package zuoye

import org.apache.spark.{SparkConf, SparkContext}

object shiyan8_2__6 {
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
        
        val usersID_movie_rating = ratings.map(x => (x._1, (x._2, x._3)))
        val usersID_sex = users.map(x => (x._1, x._2))
        val movie_rating = ratings.map(x => (x._2, x._3))
        val movieIdName = movie.map(x => (x._1, x._2))
        val usersID_movie_rating_woman = usersID_movie_rating.join(usersID_sex).filter(x => x._2._2 == "F")
        //usersID_movie_rating_woman.foreach(println(_))
        val usersID_movie_1_rating = usersID_movie_rating_woman.map(x => (x._1, (x._2._1._1, 1, x._2._1._2)))
        //usersID_movie_1_rating.foreach(println(_))
        val usersID_movie_count_rating = usersID_movie_1_rating.reduceByKey((x1, x2) => (x1._1, x1._2 + x2._2, x1._3))
        //usersID_movie_count_rating.foreach(println(_))
        val countTopID = sc.parallelize(usersID_movie_count_rating.sortBy(x => x._2._2, false).take(1)).map(x => (x._1, 1))
        //countTopID.foreach(println(_))
        val topID_movieID_rating = usersID_movie_rating.join(countTopID)
        //topID_movieID_rating.foreach(println(_))
        val top10_movieId = sc.parallelize(topID_movieID_rating.sortBy(x => x._2._1._2, false).take(10)).map(x => (x._2._1._1, 1))
        //top10_movieId.foreach(println(_))
        val top10_movieId_rating = top10_movieId.join(movie_rating).map(x => (x._1, x._2._2.toInt))
        //top10_movieId_rating.foreach(println(_))
        val top10_movieId_ratingAvg = top10_movieId_rating.groupByKey().map(x => (x._1, (x._2.sum.toDouble / x._2.size).formatted("%.2f")))
        //top10_movieId_ratingAvg.foreach(println(_))
        val top10_movieName_ratingAvg = top10_movieId_ratingAvg.join(movieIdName).map(x=>(x._2._2, x._2._1))
        top10_movieName_ratingAvg.foreach(println(_))
    }
}
