package org.bigbluebutton.core.filters

import org.bigbluebutton.core.domain.Permission2x

trait DefaultPermissionsFilter {
  def can(action: Permission2x, permissions: Set[Permission2x]): Boolean = {
    permissions contains action
  }
}
