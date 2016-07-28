package org.bigbluebutton.core.domain

object ConvertRoleHelper {
  val MODERATOR = "moderator"
  val VIEWER = "viewer"
  val PRESENTER = "presenter"
  val GUEST = "guest"

  def convert(role: String): Option[Role] = {
    val res = role.toLowerCase match {
      case MODERATOR => Some(ModeratorRole)
      case VIEWER => Some(ViewerRole)
      case PRESENTER => Some(PresenterRole)
      case GUEST => Some(GuestRole)
      case _ => None
    }

    res
  }

  def convert(role: Role): Option[String] = {
    val res = role match {
      case ModeratorRole => Some(MODERATOR)
      case ViewerRole => Some(VIEWER)
      case PresenterRole => Some(PRESENTER)
      case GuestRole => Some(GUEST)
    }

    res
  }
}

trait Role
case object ModeratorRole extends Role
case object ViewerRole extends Role
case object PresenterRole extends Role
case object GuestRole extends Role
case object AuthenticatedGuestRole extends Role
case object StenographerRole extends Role
case object SignLanguageInterpreterRole extends Role
case object UnknownRole extends Role

trait RoleData
case class SignLanguageInterpreterRoleData(locale: Locale, stream: Stream) extends RoleData {
  val role: Role = SignLanguageInterpreterRole
}
case class StenographerRoleData(locale: Locale, captionStream: CaptionStream) extends RoleData {
  val role: Role = StenographerRole
}

case class CaptionStream(url: String)

