package org.bigbluebutton.core.filters

trait DefaultPermissionsFilter {
  def can(action: Authz, userAuthz: Set[Authz]): Boolean = {
    userAuthz contains action
  }
}
