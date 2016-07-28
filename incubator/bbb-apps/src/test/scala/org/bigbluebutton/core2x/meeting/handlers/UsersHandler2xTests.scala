package org.bigbluebutton.core2x.meeting.handlers

import org.bigbluebutton.core2x.UnitSpec
import org.bigbluebutton.core2x.domain.{Abilities2x, CanEjectUser, CanRaiseHand}
import org.bigbluebutton.core2x.meeting.filters.DefaultAbilitiesFilter

class UsersHandler2xTests extends UnitSpec {
  it should "eject user" in {
    object DefPerm extends DefaultAbilitiesFilter
    val perm: Set[Abilities2x] = Set(CanEjectUser, CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, perm))
  }

  it should "not eject user" in {
    object DefPerm extends DefaultAbilitiesFilter
    val perm: Set[Abilities2x] = Set(CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, perm) != true)
  }
}
