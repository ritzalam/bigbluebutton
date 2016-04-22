package org.bigbluebutton.core.filters

import org.bigbluebutton.core.domain._

object RolePermissions {
  val modPermissions = Set(CanAssignPresenter, CanChangeLayout, CanEjectUser, CanLockLayout,
    CanPrivateChat, CanPublicChat)
  val presenterPermissions = Set(CanUploadPresentation, CanSharePresentation)
  val viewPermissions = Set(CanShareCamera, CanRaiseHand, CanUseMicrophone)
}

trait DefaultPermissionsFilter {
  def can(action: Permission2x, permissions: Set[Permission2x]): Boolean = {
    permissions contains action
  }

  def addPermissionsFromRole(role: Role2x, permissions: Set[Permission2x]): Set[Permission2x] = {
    role match {
      case ModeratorRole =>
        permissions ++ RolePermissions.modPermissions
      case PresenterRole =>
        permissions ++ RolePermissions.presenterPermissions
      case ViewerRole =>
        permissions ++ RolePermissions.viewPermissions
    }
  }

  def removePermissionsFromRole(role: Role2x, permissions: Set[Permission2x]): Set[Permission2x] = {
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
