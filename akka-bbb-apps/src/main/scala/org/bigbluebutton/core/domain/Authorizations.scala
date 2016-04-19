package org.bigbluebutton.core.domain

sealed trait Permission2x
case object CanRaiseHand extends Permission2x
case object CanEjectUser extends Permission2x
case object CanLockLayout extends Permission2x
case object CanSetRecordingStatus extends Permission2x
case object CanAssignPresenter extends Permission2x
case object CanSharePresentation extends Permission2x

