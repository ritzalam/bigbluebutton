package org.bigbluebutton.core.filters

import org.bigbluebutton.core.domain._

object RolePermissions {
  val modPermissions = Set(CanAssignPresenter, CanChangeLayout, CanEjectUser, CanLockLayout,
    CanPrivateChat, CanPublicChat)
  val presenterPermissions = Set(CanUploadPresentation, CanSharePresentation)
  val viewPermissions = Set(CanShareCamera, CanRaiseHand, CanUseMicrophone)
}

object ClientTypeAbilities {
  val flashWebClientAbilities = Set(HasLayoutSupport, HasWebRtcSupport)
}

trait DefaultPermissionsFilter {
  def can(action: Abilities2x, permissions: Set[Abilities2x]): Boolean = {
    permissions contains action
  }

  def addPermissionsFromRole(role: Role2x, permissions: Set[Abilities2x]): Set[Abilities2x] = {
    role match {
      case ModeratorRole =>
        permissions ++ RolePermissions.modPermissions
      case PresenterRole =>
        permissions ++ RolePermissions.presenterPermissions
      case ViewerRole =>
        permissions ++ RolePermissions.viewPermissions
    }
  }

  def removePermissionsFromRole(role: Role2x, permissions: Set[Abilities2x]): Set[Abilities2x] = {
    role match {
      case ModeratorRole =>
        permissions -- RolePermissions.modPermissions
      case PresenterRole =>
        permissions -- RolePermissions.presenterPermissions
      case ViewerRole =>
        permissions -- RolePermissions.viewPermissions
    }
  }
}
