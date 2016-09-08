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

  //implicit val fromColor = jsonFormat1(Color)
  implicit object ColorFormat extends JsonFormat[Color] {
    def write(obj: Color): JsValue = JsString(obj.value)

    def read(json: JsValue): Color = json match {
      case JsString(str) => Color(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val fromTime = jsonFormat1(FromTime)
  implicit object FromTimeFormat extends JsonFormat[FromTime] {
    def write(obj: FromTime): JsValue = JsString(obj.value)

    def read(json: JsValue): FromTime = json match {
      case JsString(str) => FromTime(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val chatType = jsonFormat1(ChatType)
  implicit object ChatTypeFormat extends JsonFormat[ChatType] {
    def write(obj: ChatType): JsValue = JsString(obj.value)

    def read(json: JsValue): ChatType = json match {
      case JsString(str) => ChatType(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val intUserId = jsonFormat1(IntUserId)
  implicit object IntUserIdFormat extends JsonFormat[IntUserId] {
    def write(obj: IntUserId): JsValue = JsString(obj.value)

    def read(json: JsValue): IntUserId = json match {
      case JsString(str) => IntUserId(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val chatMessageText = jsonFormat1(ChatMessageText)
  implicit object ChatMessageTextFormat extends JsonFormat[ChatMessageText] {
    def write(obj: ChatMessageText): JsValue = JsString(obj.value)

    def read(json: JsValue): ChatMessageText = json match {
      case JsString(str) => ChatMessageText(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val username = jsonFormat1(Username)
  implicit object UsernameFormat extends JsonFormat[Username] {
    def write(obj: Username): JsValue = JsString(obj.value)

    def read(json: JsValue): Username = json match {
      case JsString(str) => Username(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val timeZoneOffset = jsonFormat1(TimeZoneOffset)
  implicit object TimeZoneOffsetFormat extends JsonFormat[TimeZoneOffset] {
    def write(obj: TimeZoneOffset): JsValue = JsString(obj.value)

    def read(json: JsValue): TimeZoneOffset = json match {
      case JsString(str) => TimeZoneOffset(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

}
