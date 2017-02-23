import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.bigbluebutton.common.JsonUtil
import org.bigbluebutton.common.message.{CreateMeetingRequestMessage2x, CreateMeetingRequestMessageBody, Header}


val originalMap = Map("a" -> List(1,2), "b" -> List(3,4,5), "c" -> List())
val json = JsonUtil.toJson(originalMap)
// json: String = {"a":[1,2],"b":[3,4,5],"c":[]}
val map = JsonUtil.toMap[Seq[Int]](json)
// map: Map[String,Seq[Int]] = Map(a -> List(1, 2), b -> List(3, 4, 5), c -> List())


/*
 * Unmarshalling to a specific type of Map
 */
val mutableSymbolMap = JsonUtil.fromJson[collection.mutable.Map[Symbol,Seq[Int]]](json)
// mutableSymbolMap: scala.collection.mutable.Map[Symbol,Seq[Int]] = Map('b -> List(3, 4, 5), 'a -> List(1, 2), 'c -> List())

/*
 * (Un)marshalling nested case classes
 */
case class Person(name: String, age: Int)
case class Group(name: String, persons: Seq[Person], leader: Person)

val jeroen = Person("Jeroen", 26)
val martin = Person("Martin", 54)

val originalGroup = Group("Scala ppl", Seq(jeroen,martin), martin)
// originalGroup: Group = Group(Scala ppl,List(Person(Jeroen,26), Person(Martin,54)),Person(Martin,54))

val groupJson = JsonUtil.toJson(originalGroup)
// groupJson: String = {"name":"Scala ppl","persons":[{"name":"Jeroen","age":26},{"name":"Martin","age":54}],"leader":{"name":"Martin","age":54}}

val group = JsonUtil.fromJson[Group](groupJson)
// group: Group = Group(Scala ppl,List(Person(Jeroen,26), Person(Martin,54)),Person(Martin,54))


