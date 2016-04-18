package org.bigbluebutton.core.domain

sealed trait Authz
case object CanRaiseHand extends Authz
case object CanEjectUser extends Authz
case object CanLockLayout extends Authz
case object CanSetRecordingStatus extends Authz
case object CanAssignPresenter extends Authz
case object CanSharePresentation extends Authz

