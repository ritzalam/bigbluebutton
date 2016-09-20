package org.bigbluebutton.core.domain

sealed trait Ability

case object CanRaiseHand extends Ability
case object CanEjectUser extends Ability
case object CanLockLayout extends Ability
case object CanSetRecordingStatus extends Ability
case object CanAssignPresenter extends Ability
case object CanSharePresentation extends Ability
case object CanShareCamera extends Ability
case object CanUseMicrophone extends Ability
case object CanPrivateChat extends Ability
case object CanPublicChat extends Ability
case object CanChangeLayout extends Ability
case object CanDrawWhiteboard extends Ability
case object CanShareDesktop extends Ability
case object CanUploadPresentation extends Ability
case object HasLayoutSupport extends Ability
case object HasWebRtcSupport extends Ability

