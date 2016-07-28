package org.bigbluebutton.core.meeting.filters

import org.bigbluebutton.core.domain._

object RoleAbilities {
  val moderatorAbilities = Set(
    CanAssignPresenter,
    CanChangeLayout,
    CanEjectUser,
    CanLockLayout,
    CanPrivateChat,
    CanPublicChat)

  val presenterAbilities = Set(
    CanUploadPresentation,
    CanSharePresentation)

  val viewerAbilities = Set(
    CanShareCamera,
    CanRaiseHand,
    CanUseMicrophone)
}

object ClientTypeAbilities {
  val flashWebClientAbilities = Set(HasLayoutSupport, HasWebRtcSupport)
}

trait DefaultAbilitiesFilter {
  def can(action: Abilities, abilities: Set[Abilities]): Boolean = {
    abilities contains action
  }

  def calcRolesAbilities(roles: Set[Role]): Set[Abilities] = {
    var abilities: Set[Abilities] = Set.empty

    roles.foreach { r =>
      r match {
        case ModeratorRole =>
          abilities = abilities ++ RoleAbilities.moderatorAbilities
        case ViewerRole =>
          abilities = abilities ++ RoleAbilities.viewerAbilities
        case PresenterRole => abilities = abilities ++ RoleAbilities.presenterAbilities
      }
    }
    abilities
  }

  def add(abilities: Set[Abilities], to: Set[Abilities]): Set[Abilities] = {
    to ++ abilities
  }

  def subtract(abilities: Set[Abilities], from: Set[Abilities]): Set[Abilities] = {
    from -- abilities
  }

  def calcEffectiveAbilities(
    roles: Set[Role],
    userAbilities: UserAbilities,
    meetingAbilities: Set[Abilities]): Set[Abilities] = {
    var effectiveAbilities: Set[Abilities] = Set.empty
    effectiveAbilities = subtract(userAbilities.removed, calcRolesAbilities(roles))
    if (userAbilities.applyMeetingAbilities) {
      effectiveAbilities = subtract(meetingAbilities, effectiveAbilities)
    }

    effectiveAbilities
  }

  def calcEffectiveAbilities(
    roles: Set[Role],
    clientAbilities: Set[Abilities],
    userAbilities: UserAbilities,
    meetingAbilities: Set[Abilities]): Set[Abilities] = {

    var effectiveAbilities: Set[Abilities] = Set.empty
    effectiveAbilities = subtract(userAbilities.removed, add(calcRolesAbilities(roles), clientAbilities))
    if (userAbilities.applyMeetingAbilities) {
      effectiveAbilities = add(effectiveAbilities, meetingAbilities)
    }

    effectiveAbilities
  }

}
