package org.bigbluebutton.core.api.json

import org.bigbluebutton.core.domain._
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsBoolean, JsString, JsValue, JsonFormat }

trait AnyValTypeProtocol {
  this: DefaultJsonProtocol =>

  implicit object RecordedFormat extends JsonFormat[Recorded] {
    def write(obj: Recorded): JsValue = JsBoolean(obj.value)

    def read(json: JsValue): Recorded = json match {
      case JsBoolean(str) => Recorded(str)
      case _ => throw new DeserializationException("Boolean expected")
    }
  }

  //implicit val intMeetingIdFormat = jsonFormat1(IntMeetingId)
  implicit object IntMeetingIdFormat extends JsonFormat[IntMeetingId] {
    def write(obj: IntMeetingId): JsValue = JsString(obj.value)

    def read(json: JsValue): IntMeetingId = json match {
      case JsString(str) => IntMeetingId(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val extMeetingIdFormat = jsonFormat1(ExtMeetingId)
  implicit object ExtMeetingIdFormat extends JsonFormat[ExtMeetingId] {
    def write(obj: ExtMeetingId): JsValue = JsString(obj.value)

    def read(json: JsValue): ExtMeetingId = json match {
      case JsString(str) => ExtMeetingId(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val nameFormat = jsonFormat1(Name)
  implicit object NameFormat extends JsonFormat[Name] {
    def write(obj: Name): JsValue = JsString(obj.value)

    def read(json: JsValue): Name = json match {
      case JsString(str) => Name(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val voiceConfFormat = jsonFormat1(VoiceConf)
  implicit object VoiceConfFormat extends JsonFormat[VoiceConf] {
    def write(obj: VoiceConf): JsValue = JsString(obj.value)

    def read(json: JsValue): VoiceConf = json match {
      case JsString(str) => VoiceConf(str)
      case _ => throw new DeserializationException("String expected")
    }
  }
}
