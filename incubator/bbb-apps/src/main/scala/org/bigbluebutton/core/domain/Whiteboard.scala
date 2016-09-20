package org.bigbluebutton.core.domain

case class AnnotationVO(
  id: String,
  status: String,
  shapeType: String,
  shape: scala.collection.immutable.Map[String, Object],
  wbId: String)

case class Whiteboard(id: String, shapes: Seq[AnnotationVO])

case class WhiteboardProperties2x(
  whiteboardId: WhiteboardId,
  annotationType: AnnotationType,
  annotation: Annotation)

trait Annotation

case class TextAnnotation(text: AnnotationTextContent, fontColor: AnnotationTextFontColor,
  thickness: AnnotationTextThickness) extends Annotation
case class ShapeAnnotation(color: Int) extends Annotation
