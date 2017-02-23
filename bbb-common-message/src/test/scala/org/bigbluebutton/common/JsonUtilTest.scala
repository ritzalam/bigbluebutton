package org.bigbluebutton.common

import scala.util.Success
import scala.util.Failure
import org.scalatest._

case class Person(name: String, age: Int)
case class Group(name: String, persons: Seq[Person], leader: Person)

class JsonUtilTest extends UnitSpec {

  it should "unmarshall a simple map" in {
    /*
     * (Un)marshalling nested case classes
     */


    val jeroen = Person("Jeroen", 26)
    val martin = Person("Martin", 54)

    val originalGroup = Group("Scala ppl", Seq(jeroen,martin), martin)
    // originalGroup: Group = Group(Scala ppl,List(Person(Jeroen,26), Person(Martin,54)),Person(Martin,54))

    val groupJson = JsonUtil.toJson(originalGroup)
    // groupJson: String = {"name":"Scala ppl","persons":[{"name":"Jeroen","age":26},{"name":"Martin","age":54}],"leader":{"name":"Martin","age":54}}

    val group = JsonUtil.fromJson[Group](groupJson)
    // group: Group = Group(Scala ppl,List(Person(Jeroen,26), Person(Martin,54)),Person(Martin,54))
  }
}
