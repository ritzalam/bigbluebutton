package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.domain.{ Abilities, CanEjectUser, CanRaiseHand }
import org.bigbluebutton.core.meeting.filters.DefaultAbilitiesFilter

class UsersHandler2xTests extends UnitSpec {
  it should "eject user" in {
    object DefPerm extends DefaultAbilitiesFilter
    val perm: Set[Abilities] = Set(CanEjectUser, CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, perm))
  }

  it should "not eject user" in {
    object DefPerm extends DefaultAbilitiesFilter
    val perm: Set[Abilities] = Set(CanRaiseHand)

    assert(DefPerm.can(CanEjectUser, perm) != true)
  }
}
