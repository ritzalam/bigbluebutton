package org.bigbluebutton.core2x.domain

object ConvertRoleHelper {
  val MODERATOR = "moderator"
  val VIEWER = "viewer"
  val PRESENTER = "presenter"
  val GUEST = "guest"

  def convert(role: String): Option[Role2x] = {
    val res = role.toLowerCase match {
      case MODERATOR => Some(ModeratorRole)
      case VIEWER => Some(ViewerRole)
      case PRESENTER => Some(PresenterRole)
      case GUEST => Some(GuestRole)
      case _ => None
    }

    res
  }

  def convert(role: Role2x): Option[String] = {
    val res = role match {
      case ModeratorRole => Some(MODERATOR)
      case ViewerRole => Some(VIEWER)
      case PresenterRole => Some(PRESENTER)
      case GuestRole => Some(GUEST)
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

