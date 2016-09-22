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

  implicit object AnnotationTextContentFormat extends JsonFormat[AnnotationTextContent] {
    def write(obj: AnnotationTextContent): JsValue = JsString(obj.value)

    def read(json: JsValue): AnnotationTextContent = json match {
      case JsString(str) => AnnotationTextContent(str)
      case _ => throw DeserializationException("String expected")
    }
  }

  implicit object AnnotationTextBoxWidthFormat extends JsonFormat[AnnotationTextBoxWidth] {
    def write(obj: AnnotationTextBoxWidth): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationTextBoxWidth = json match {
      case JsNumber(num) => AnnotationTextBoxWidth(num.doubleValue())
      case _ => throw DeserializationException("Double expected")
    }
  }

  implicit object AnnotationTextFontSizeFormat extends JsonFormat[AnnotationTextFontSize] {
    def write(obj: AnnotationTextFontSize): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationTextFontSize = json match {
      case JsNumber(num) => AnnotationTextFontSize(num.toInt)
      case _ => throw new DeserializationException("Int expected")
    }
  }

  implicit object AnnotationYFormat extends JsonFormat[AnnotationY] {
    def write(obj: AnnotationY): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationY = json match {
      case JsNumber(num) => AnnotationY(num.doubleValue())
      case _ => throw new DeserializationException("Double expected")
    }
  }

  implicit object AnnotationXFormat extends JsonFormat[AnnotationX] {
    def write(obj: AnnotationX): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationX = json match {
      case JsNumber(num) => AnnotationX(num.doubleValue())
      case _ => throw new DeserializationException("Double expected")
    }
  }

  implicit object AnnotationTextCalcedFontSizeFormat extends JsonFormat[AnnotationTextCalcedFontSize] {
    def write(obj: AnnotationTextCalcedFontSize): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationTextCalcedFontSize = json match {
      case JsNumber(num) => AnnotationTextCalcedFontSize(num.doubleValue())
      case _ => throw new DeserializationException("Double expected")
    }
  }

  implicit object AnnotationDataPointsFormat extends JsonFormat[AnnotationDataPoints] {
    def write(obj: AnnotationDataPoints): JsValue = JsString(obj.value)

    def read(json: JsValue): AnnotationDataPoints = json match {
      case JsString(str) => AnnotationDataPoints(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  implicit object AnnotationIdFormat extends JsonFormat[AnnotationId] {
    def write(obj: AnnotationId): JsValue = JsString(obj.value)

    def read(json: JsValue): AnnotationId = json match {
      case JsString(str) => AnnotationId(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  implicit object AnnotationStatusFormat extends JsonFormat[AnnotationStatus] {
    def write(obj: AnnotationStatus): JsValue = JsString(obj.value)

    def read(json: JsValue): AnnotationStatus = json match {
      case JsString(str) => AnnotationStatus(str)
      case _ => throw new DeserializationException("String expected")
    }
  }

  //implicit val annotationTextBoxHeight = jsonFormat1(AnnotationTextBoxHeight)
  implicit object AnnotationTextBoxHeightFormat extends JsonFormat[AnnotationTextBoxHeight] {
    def write(obj: AnnotationTextBoxHeight): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationTextBoxHeight = json match {
      case JsNumber(num) => AnnotationTextBoxHeight(num.doubleValue())
      case _ => throw new DeserializationException("Double expected")
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

  implicit object AnnotationShapeColorFormat extends JsonFormat[AnnotationShapeColor] {
    def write(obj: AnnotationShapeColor): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationShapeColor = json match {
      case JsNumber(num) => AnnotationShapeColor(num.toInt)
      case _ => throw new DeserializationException("Int expected")
    }
  }

  implicit object AnnotationShapeThicknessFormat extends JsonFormat[AnnotationShapeThickness] {
    def write(obj: AnnotationShapeThickness): JsValue = JsNumber(obj.value)

    def read(json: JsValue): AnnotationShapeThickness = json match {
      case JsNumber(num) => AnnotationShapeThickness(num.toInt)
      case _ => throw DeserializationException("Int expected")
    }
  }

  implicit object AnnotationShapeTransparencyFormat extends JsonFormat[AnnotationShapeTransparency] {
    def write(obj: AnnotationShapeTransparency): JsValue = JsBoolean(obj.value)

    def read(json: JsValue): AnnotationShapeTransparency = json match {
      case JsBoolean(num) => AnnotationShapeTransparency(num)
      case _ => throw DeserializationException("Int expected")
    }
  }

  implicit object AnnotationShapeDataPointsFormat extends JsonFormat[AnnotationShapeDataPoints] {

    def write(obj: AnnotationShapeDataPoints) = {
      val jsNumVector = obj.value.map(num => JsNumber(num))
      JsArray(jsNumVector)
    }

    def read(json: JsValue): AnnotationShapeDataPoints = json match {
      case JsArray(num) =>
        val vectorOfDouble = num.map(a => a.asInstanceOf[JsNumber].value.doubleValue())
        AnnotationShapeDataPoints(vectorOfDouble)
      case _ => throw DeserializationException("Vector[Double] expected")
    }
  }

  implicit val TextAnnotationFormat = jsonFormat12(TextAnnotation)
  implicit val ShapeAnnotationFormat = jsonFormat7(ShapeAnnotation)

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

  // AnnotationHistory
  //  implicit object AnnotationHistoryFormat extends JsonFormat[AnnotationHistory] {
  //
  //    def write(obj: AnnotationHistory) = {
  //      //      val jsNumVector = obj.value.map(num => JsNumber(num))
  //      //      JsArray(jsNumVector)
  //      new JsString("aaa")
  //    }
  //
  //    def read(json: JsValue): AnnotationHistory = json match {
  //      case JsArray(num) =>
  //        println("\n~~1:" + json)
  //        json.asJsObject.getFields("annotationType") match {
  //          case Seq(JsString(annotationType)) =>
  //            println("\n~~1: text--" + annotationType)
  //            val annotations2 = Vector[Annotation]
  //            AnnotationHistory(annotations2)
  //
  //          case _ => throw DeserializationException("WhiteboardProperties2x expected")
  //        }
  //      case _ => throw DeserializationException("Vector[Double] expected")
  //    }
  //  }

}
