package org.bigbluebutton.core.api.json

import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ SendWbAnnotationReqInMsg2x }
import org.bigbluebutton.core.domain._
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsArray, JsBoolean, JsFalse, JsNumber, JsObject, JsString, JsTrue, JsValue, JsonFormat, SerializationException }

trait AnyValTypeProtocol {
  this: DefaultJsonProtocol =>

  implicit val inMessageHeaderFormat = jsonFormat4(InMessageHeader)

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

  //implicit val whiteboardId = jsonFormat1(WhiteboardId)
  implicit object WhiteboardIdFormat extends JsonFormat[WhiteboardId] {
    def write(obj: WhiteboardId): JsValue = JsString(obj.value)

    def read(json: JsValue): WhiteboardId = json match {
      case JsString(str) => WhiteboardId(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val annotationType = jsonFormat1(AnnotationType)
  implicit object AnnotationTypeFormat extends JsonFormat[AnnotationType] {
    def write(obj: AnnotationType): JsValue = JsString(obj.value)

    def read(json: JsValue): AnnotationType = json match {
      case JsString(str) => AnnotationType(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val annotationTextContent = jsonFormat1(AnnotationTextContent)
  implicit object AnnotationTextContentFormat extends JsonFormat[AnnotationTextContent] {
    def write(obj: AnnotationTextContent): JsValue = JsString(obj.value)

    def read(json: JsValue): AnnotationTextContent = json match {
      case JsString(str) => AnnotationTextContent(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val annotationTextBoxHeight = jsonFormat1(AnnotationTextBoxHeight)
  implicit object AnnotationTextBoxHeightFormat extends JsonFormat[AnnotationTextBoxHeight] {
    def write(obj: AnnotationTextBoxHeight): JsValue = JsString(obj.value)

    def read(json: JsValue): AnnotationTextBoxHeight = json match {
      case JsString(num) => AnnotationTextBoxHeight(num)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val annotationTextFontColor = jsonFormat1(AnnotationTextFontColor)
  implicit object AnnotationTextFontColorFormat extends JsonFormat[AnnotationTextFontColor] {
    def write(obj: AnnotationTextFontColor): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationTextFontColor = json match {
      case JsNumber(num) => AnnotationTextFontColor(num.toInt)
      case _ => throw new DeserializationException("Int expected")
    }
  }

  //implicit val annotationTextThickness = jsonFormat1(AnnotationTextThickness)
  implicit object AnnotationTextThicknessFormat extends JsonFormat[AnnotationTextThickness] {
    def write(obj: AnnotationTextThickness): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationTextThickness = json match {
      case JsNumber(num) => AnnotationTextThickness(num.toInt)
      case _ => throw new DeserializationException("Int expected")
    }
  }

  implicit val TextAnnotationFormat = jsonFormat3(TextAnnotation)
  implicit val ShapeAnnotationFormat = jsonFormat1(ShapeAnnotation)

  implicit object WhiteboardProperties2xFormat extends JsonFormat[WhiteboardProperties2x] {
    def write(x: WhiteboardProperties2x): JsValue = {
      if ("text".equalsIgnoreCase(x.annotationType.value)) {
        new JsObject(Map[String, JsValue](
          "whiteboardId" -> JsString(x.whiteboardId.value),
          "annotationType" -> JsString(x.annotationType.value),
          "annotation" -> TextAnnotationFormat.write(x.annotation.asInstanceOf[TextAnnotation]).toJson))
      } else {
        new JsObject(Map[String, JsValue](
          "whiteboardId" -> JsString(x.whiteboardId.value),
          "annotationType" -> JsString(x.annotationType.value),
          "annotation" -> ShapeAnnotationFormat.write(x.annotation.asInstanceOf[ShapeAnnotation]).toJson))
      }
    }

    def read(json: JsValue): WhiteboardProperties2x = {
      // println("\n~~1:" + json)
      import spray.json._
      json.asJsObject.getFields("whiteboardId", "annotationType", "annotation") match {
        case Seq(JsString(whiteboardId), JsString(annotationType), JsObject(annotation)) =>
          if ("text".equalsIgnoreCase(annotationType)) {
            // println("\n~~1: text--" + annotation.toJson)
            WhiteboardProperties2x(WhiteboardId(whiteboardId), AnnotationType(annotationType),
              TextAnnotationFormat.read(annotation.toJson))
          } else {
            // println("\n~~1: shape--" + annotation.toJson)
            WhiteboardProperties2x(WhiteboardId(whiteboardId), AnnotationType(annotationType),
              ShapeAnnotationFormat.read(annotation.toJson))
          }
        case _ => throw DeserializationException("WhiteboardProperties2x expected")
      }
    }
  }

}
