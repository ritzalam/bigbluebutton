package org.bigbluebutton.core.filters

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.domain.{ Permission2x, CanEjectUser, CanRaiseHand }

class DefaultPermissionsFilterTests extends UnitSpec {
  it should "eject user" in {
    object DefPerm extends DefaultPermissionsFilter
    val perm: Set[Permission2x] = Set(CanEjectUser, CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, perm))
  }

  it should "not eject user" in {
    object DefPerm extends DefaultPermissionsFilter
    val perm: Set[Permission2x] = Set(CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, perm) != true)
  }
}
