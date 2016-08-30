package org.bigbluebutton.core.domain

sealed trait Abilities

case object CanRaiseHand extends Abilities
case object CanEjectUser extends Abilities
case object CanLockLayout extends Abilities
case object CanSetRecordingStatus extends Abilities
case object CanAssignPresenter extends Abilities
case object CanSharePresentation extends Abilities
case object CanShareCamera extends Abilities
case object CanUseMicrophone extends Abilities
case object CanPrivateChat extends Abilities
case object CanPublicChat extends Abilities
case object CanChangeLayout extends Abilities
case object CanDrawWhiteboard extends Abilities
case object CanShareDesktop extends Abilities
case object CanUploadPresentation extends Abilities
case object HasLayoutSupport extends Abilities
case object HasWebRtcSupport extends Abilities

