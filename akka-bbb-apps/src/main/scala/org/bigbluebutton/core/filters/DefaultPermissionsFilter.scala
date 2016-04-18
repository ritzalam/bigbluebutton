package org.bigbluebutton.core.filters

import org.bigbluebutton.core.domain.Authz

trait DefaultPermissionsFilter {
  def can(action: Authz, permissions: Set[Authz]): Boolean = {
    permissions contains action
  }
}
