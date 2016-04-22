package org.bigbluebutton.core.domain

sealed trait Permission2x

case object CanRaiseHand extends Permission2x
case object CanEjectUser extends Permission2x
case object CanLockLayout extends Permission2x
case object CanSetRecordingStatus extends Permission2x
case object CanAssignPresenter extends Permission2x
case object CanSharePresentation extends Permission2x
case object CanShareCamera extends Permission2x
case object CanUseMicrophone extends Permission2x
case object CanPrivateChat extends Permission2x
case object CanPublicChat extends Permission2x
case object CanChangeLayout extends Permission2x
case object CanDrawWhiteboard extends Permission2x
case object CanShareDesktop extends Permission2x
case object CanUploadPresentation extends Permission2x

