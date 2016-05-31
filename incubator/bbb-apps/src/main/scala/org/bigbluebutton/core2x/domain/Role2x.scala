package org.bigbluebutton.core2x.domain

trait Role2x
case object ModeratorRole extends Role2x
case object ViewerRole extends Role2x
case object PresenterRole extends Role2x
case object GuestRole extends Role2x
case object AuthenticatedGuestRole extends Role2x
case object StenographerRole extends Role2x
case object SignLanguageInterpreterRole extends Role2x

trait RoleData
case class SignLanguageInterpreterRoleData(locale: Locale, stream: Stream) extends RoleData {
  val role: Role2x = SignLanguageInterpreterRole
}
case class StenographerRoleData(locale: Locale, captionStream: CaptionStream) extends RoleData {
  val role: Role2x = StenographerRole
}

case class CaptionStream(url: String)

