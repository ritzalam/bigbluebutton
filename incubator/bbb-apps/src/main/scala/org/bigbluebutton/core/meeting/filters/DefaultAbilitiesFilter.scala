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
  def can(action: Ability, abilities: Set[Ability]): Boolean = {
    abilities contains action
  }

  def calcRolesAbilities(roles: Set[Role]): Set[Ability] = {
    var abilities: Set[Ability] = Set.empty

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

  def add(abilities: Set[Ability], to: Set[Ability]): Set[Ability] = {
    to ++ abilities
  }

  def subtract(abilities: Set[Ability], from: Set[Ability]): Set[Ability] = {
    from -- abilities
  }

  def calcEffectiveAbilities(
    roles: Set[Role],
    userAbilities: UserAbilities,
    meetingAbilities: Set[Ability]): Set[Ability] = {
    var effectiveAbilities: Set[Ability] = Set.empty
    effectiveAbilities = subtract(userAbilities.removed, calcRolesAbilities(roles))
    if (userAbilities.applyMeetingAbilities) {
      effectiveAbilities = subtract(meetingAbilities, effectiveAbilities)
    }

    effectiveAbilities
  }

  def calcEffectiveAbilities(
    roles: Set[Role],
    clientAbilities: Set[Ability],
    userAbilities: UserAbilities,
    meetingAbilities: Set[Ability]): Set[Ability] = {

    var effectiveAbilities: Set[Ability] = Set.empty
    effectiveAbilities = subtract(userAbilities.removed, add(calcRolesAbilities(roles), clientAbilities))
    if (userAbilities.applyMeetingAbilities) {
      effectiveAbilities = add(effectiveAbilities, meetingAbilities)
    }

    effectiveAbilities
  }

}
