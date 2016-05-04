package org.bigbluebutton.core.filters

import org.bigbluebutton.core.domain._

object RoleAbilities {
  val moderatorAbilities = Set(CanAssignPresenter, CanChangeLayout, CanEjectUser, CanLockLayout,
    CanPrivateChat, CanPublicChat)
  val presenterAbilities = Set(CanUploadPresentation, CanSharePresentation)
  val viewerAbilities = Set(CanShareCamera, CanRaiseHand, CanUseMicrophone)
}

object ClientTypeAbilities {
  val flashWebClientAbilities = Set(HasLayoutSupport, HasWebRtcSupport)
}

trait DefaultAbilitiesFilter {
  def can(action: Abilities2x, abilities: Set[Abilities2x]): Boolean = {
    abilities contains action
  }

  def calcRolesAbilities(roles: Set[Role2x]): Set[Abilities2x] = {
    var abilities: Set[Abilities2x] = Set.empty
    println(abilities)
    roles.foreach { r =>
      r match {
        case ModeratorRole =>
          abilities = abilities ++ RoleAbilities.moderatorAbilities; println(abilities)
        case ViewerRole =>
          abilities = abilities ++ RoleAbilities.viewerAbilities; println(abilities)
        case PresenterRole => abilities = abilities ++ RoleAbilities.presenterAbilities; println(abilities)
      }
    }
    abilities
  }

  def calcEffectiveAbilities(
    roles: Set[Role2x],
    userAbilities: UserAbilities,
    meetingAbilities: Set[Abilities2x]): Set[Abilities2x] = {
    var effectiveAbilities: Set[Abilities2x] = Set.empty
    effectiveAbilities = calcRolesAbilities(roles) -- userAbilities.removed
    if (userAbilities.applyMeetingAbilities) {
      effectiveAbilities = effectiveAbilities -- meetingAbilities
    }

    effectiveAbilities
  }

  def calcEffectiveAbilities(
    roles: Set[Role2x],
    clientAbilities: Set[Abilities2x],
    userAbilities: UserAbilities,
    meetingAbilities: Set[Abilities2x]): Set[Abilities2x] = {

    var effectiveAbilities: Set[Abilities2x] = Set.empty
    effectiveAbilities = (calcRolesAbilities(roles) ++ clientAbilities) -- userAbilities.removed
    if (userAbilities.applyMeetingAbilities) {
      effectiveAbilities = effectiveAbilities -- meetingAbilities
    }

    effectiveAbilities
  }

}
