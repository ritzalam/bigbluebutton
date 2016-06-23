package org.bigbluebutton.core2x.domain

object StringToRoleHelper {
  def convert(role: String): Option[Role2x] = {
    val res = role.toLowerCase match {
      case "moderator" => Some(ModeratorRole)
      case "viewer" => Some(ViewerRole)
      case "presenter" => Some(PresenterRole)
      case "guest" => Some(GuestRole)
      case _ => None
    }

    res
  }
}

trait Role2x
case object ModeratorRole extends Role2x
case object ViewerRole extends Role2x
case object PresenterRole extends Role2x
case object GuestRole extends Role2x
case object AuthenticatedGuestRole extends Role2x
case object StenographerRole extends Role2x
case object SignLanguageInterpreterRole extends Role2x
case object UnknownRole extends Role2x

trait RoleData
case class SignLanguageInterpreterRoleData(locale: Locale, stream: Stream) extends RoleData {
  val role: Role2x = SignLanguageInterpreterRole
}
case class StenographerRoleData(locale: Locale, captionStream: CaptionStream) extends RoleData {
  val role: Role2x = StenographerRole
}

case class CaptionStream(url: String)

