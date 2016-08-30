package org.bigbluebutton.core.apps.presentation.domain

import org.bigbluebutton.core.domain.{ IntUserId, Name }

case class CurrentPresenter(id: IntUserId, name: Name, assignedBy: IntUserId)
case class CursorLocation(xPercent: Double = 0D, yPercent: Double = 0D)

case class Coordinate(xOffset: XOffset = XOffset(0), yOffset: YOffset = YOffset(0))
case class Dimension(widthRatio: WidthRatio = WidthRatio(100D), width: Double = 0D,
  heightRatio: HeightRatio = HeightRatio(100D), height: Double = 0D)

/**
 *   Use Value Classes to help with type safety.
 *   https://ivanyu.me/blog/2014/12/14/value-classes-in-scala/
 */
case class PresentationId(value: String) extends AnyVal
case class ThumbUrl(value: String) extends AnyVal
case class SwfUrl(value: String) extends AnyVal
case class TextUrl(value: String) extends AnyVal
case class SvgUrl(value: String) extends AnyVal
case class XOffset(value: Double) extends AnyVal
case class YOffset(value: Double) extends AnyVal
case class WidthRatio(value: Double) extends AnyVal
case class HeightRatio(value: Double) extends AnyVal
