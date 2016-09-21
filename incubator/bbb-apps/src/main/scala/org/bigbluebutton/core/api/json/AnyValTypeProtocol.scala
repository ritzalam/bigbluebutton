package org.bigbluebutton.core.api.json

import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ SendWbAnnotationReqInMsg2x, SendWbAnnotationReqInMsgBody }
import org.bigbluebutton.core.apps.whiteboard.{ Annotation, ShapeAnnotation, TextAnnotation, WhiteboardProperties2x }
import org.bigbluebutton.core.domain._
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsArray, JsBoolean, JsFalse, JsNumber, JsObject, JsString, JsTrue, JsValue, JsonFormat, SerializationException }

trait AnyValTypeProtocol {
  this: DefaultJsonProtocol =>

  implicit val InMessageHeaderFormat = jsonFormat4(InMessageHeader)

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

  //implicit val annotationTextBoxWidth = jsonFormat1(AnnotationTextBoxWidth)
  //  implicit object AnnotationTextBoxWidthFormat extends JsonFormat[AnnotationTextBoxWidth] {
  //    def write(obj: AnnotationTextBoxWidth): JsValue = JsNumber(obj.value)
  //
  //    def read(json: JsValue): AnnotationTextBoxWidth = json match {
  //      case JsNumber(num) => AnnotationTextBoxWidth(num.doubleValue())
  //      case _ => throw new DeserializationException("Double expected")
  //    }
  //  }

  implicit val TextAnnotationFormat = jsonFormat3(TextAnnotation)
  implicit val ShapeAnnotationFormat = jsonFormat1(ShapeAnnotation)

  implicit object AnnotationFormat extends JsonFormat[Annotation] {
    // def write(x: Annotation): JsValue = {
    def write(x: Annotation) = x match {
      case n: TextAnnotation =>
        //JsObject(Map[String, JsValue](
        //"text" -> JsString(n.text),
        //"fontColor" -> JsNumber(n.fontColor),
        //"thickness" -> JsNumber(n.thickness)))
        TextAnnotationFormat.write(n)
      case s: ShapeAnnotation =>
        //JsObject(Map[String, JsValue]("color" -> JsNumber(s.color)))
        ShapeAnnotationFormat.write(s)

    }
    // def read(value: JsObject) = value.asInstanceOf[Annotation]

    def read(json: JsValue): Annotation = {
      val b: Annotation = json.convertTo[Annotation]
      if (b.isInstanceOf[TextAnnotation]) {
        b.asInstanceOf[TextAnnotation]
      } else {
        b.asInstanceOf[ShapeAnnotation]
      }
      //      val shapeType: String = b.shapeType
      //      if (shapeType.equals("text")) {
      //        b.asInstanceOf[TextAnnotation]
      //      } else {
      //        b.asInstanceOf[ShapeAnnotation]
      //      }
    }
  }
  //
  //  //  WhiteboardProperties2x
  //
  implicit object WhiteboardProperties2xFormat extends JsonFormat[WhiteboardProperties2x] {
    def write(x: WhiteboardProperties2x): JsValue = {
      val textAnnotationJson = new JsObject(Map[String, JsValue](
        "whiteboardId" -> JsString(x.whiteboardId.value),
        "annotationType" -> JsString(x.annotationType.value),
        "annotation" -> AnnotationFormat.write(x.annotation)))

      textAnnotationJson.toJson
    }

    def read(json: JsValue): WhiteboardProperties2x = {
      val b: WhiteboardProperties2x = json.convertTo[WhiteboardProperties2x]
      b
    }
  }

  implicit object SendWbAnnotationReqInMsgBodyFormat extends JsonFormat[SendWbAnnotationReqInMsgBody] {
    def write(x: SendWbAnnotationReqInMsgBody): JsValue = {
      JsObject(Map[String, JsValue]("props" -> WhiteboardProperties2xFormat.write(x.props))).toJson
    }

    def read(json: JsValue): SendWbAnnotationReqInMsgBody = {
      val b: SendWbAnnotationReqInMsgBody = json.convertTo[SendWbAnnotationReqInMsgBody]
      b
    }
  }

  //  // SendWbAnnotationReqInMsg2x
  implicit object SendWbAnnotationReqInMsg2xFormat extends JsonFormat[SendWbAnnotationReqInMsg2x] {
    def write(x: SendWbAnnotationReqInMsg2x): JsValue = {
      JsObject(Map[String, JsValue](
        "body" -> SendWbAnnotationReqInMsgBodyFormat.write(x.body),
        "header" -> InMessageHeaderFormat.write(x.header)))
    }

    def read(json: JsValue): SendWbAnnotationReqInMsg2x = {
      val b: SendWbAnnotationReqInMsg2x = json.convertTo[SendWbAnnotationReqInMsg2x]
      b
    }
  }

  //
  //  implicit object ColorJsonFormat extends RootJsonFormat[Color] {
  //    def write(c: Color) = JsArray(JsString(c.name), JsNumber(c.red), JsNumber(c.green), JsNumber(c.blue))
  //
  //    def read(value: JsValue) = value match {
  //      case JsArray(Vector(JsString(name), JsNumber(red), JsNumber(green), JsNumber(blue))) =>
  //        new Color(name, red.toInt, green.toInt, blue.toInt)
  //      case _ => deserializationError("Color expected")
  //    }
  //  }
  //
  //

  //  implicit object AnyJsonFormat extends JsonFormat[Any] {
  //    def write(x: Any) = x match {
  //      case n: Int => JsNumber(n)
  //      case s: String => JsString(s)
  //      case x: Seq[_] => seqFormat[Any].write(x)
  //      case m: Map[String, String] => mapFormat[String, String].write(m)
  //      //      case m: Map[String, _] => mapFormat[String, Any].write(m)
  //      case b: Boolean if b == true => JsTrue
  //      case b: Boolean if b == false => JsFalse
  //      case x => throw new SerializationException("Do not understand object of type "
  //        + x.getClass.getName)
  //    }
  //    def read(value: JsValue) = value match {
  //      case JsNumber(n) => n.intValue()
  //      case JsString(s) => s
  //      case a: JsArray => listFormat[Any].read(value)
  //      case o: JsObject => mapFormat[String, String].read(value)
  //      //      case o: JsObject => mapFormat[String, Any].read(value)
  //      case JsTrue => true
  //      case JsFalse => false
  //      case x => throw new DeserializationException("Do not understand how to deserialize " + x)
  //    }
  //  }
}
