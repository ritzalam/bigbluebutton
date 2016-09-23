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
  annotation: Annotation)

trait Annotation

case class TextAnnotation(text: AnnotationTextContent,
    textBoxHeight: AnnotationTextBoxHeight,
    textBoxWidth: AnnotationTextBoxWidth,
    fontColor: AnnotationTextFontColor,
    fontSize: AnnotationTextFontSize,
    x: AnnotationX,
    calcedFontSize: AnnotationTextCalcedFontSize,
    dataPoints: AnnotationDataPoints,
    y: AnnotationY,
    status: AnnotationStatus,
    id: AnnotationId,
    annotationType: AnnotationType = AnnotationType("text")) extends Annotation {
}

case class ShapeAnnotation(color: AnnotationShapeColor,
    transparency: AnnotationShapeTransparency,
    status: AnnotationStatus,
    id: AnnotationId,
    thickness: AnnotationShapeThickness,
    points: AnnotationShapeDataPoints,
    annotationType: AnnotationType = AnnotationType("shape")) extends Annotation {
}

