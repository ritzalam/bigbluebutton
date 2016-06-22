package org.bigbluebutton.core2x.bus.helpers

import org.bigbluebutton.core2x.domain.{ ModeratorRole, PresenterRole, Role2x, ViewerRole }

object StringToRoleHelper {
  def convert(role: String): Option[Role2x] = {
    val res = role match {
      case "moderator" => Some(ModeratorRole)
      case "viewer" => Some(ViewerRole)
      case "presenter" => Some(PresenterRole)
      case _ => None
    }

    res
  }
}
