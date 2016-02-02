package org.bigbluebutton.core.authz

object Authz {
  val NAME = "name"
}

trait DefaultAuthz {
  def can(action: String, userAuthz: Set[String]): Boolean = {
    return true
  }
}
object Authorization {

}