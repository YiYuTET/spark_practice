package zuoye

class Person{
    var name: String="张三"
    var sex:Char='男'
    var age:Int=18
    def set(name:String,sex:Char,age:Int): Unit ={
        this.name=name
        this.sex=sex
        this.age=age
        print("设置成功!\n")
    }
    def get(): Unit ={
        print(name,sex,age)
    }
}

class Student extends Person{
    var school:String="实验中学"
    var studentID:String="0001"
    override def get(): Unit = {
        print(name,sex,age,school,studentID)
    }
}

object shiyan2_1{
    def main(args: Array[String]): Unit = {
        val student1=new Student()
        student1.set("张三",'男',18)
        student1.get()
    }
}
