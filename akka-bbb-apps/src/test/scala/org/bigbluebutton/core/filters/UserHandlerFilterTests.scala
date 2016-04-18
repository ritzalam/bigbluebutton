package org.bigbluebutton.core.filters

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.domain.{ Authz, CanEjectUser, CanRaiseHand }
import org.bigbluebutton.core.models.RegisteredUsers

/**
 * Created by ralam on 4/18/2016.
 */
class UserHandlerFilterTests extends UnitSpec {
  it should "eject user" in {
    object DefPerm extends DefaultPermissionsFilter
    val authz: Set[Authz] = Set(CanEjectUser, CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, authz))
  }

  it should "not eject user" in {
    object DefPerm extends DefaultPermissionsFilter
    val authz: Set[Authz] = Set(CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, authz) != true)
  }
}
