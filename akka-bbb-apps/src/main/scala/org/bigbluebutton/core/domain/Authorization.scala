package org.bigbluebutton.core.domain

object Authorization {
  val CAN_EJECT_USER = "CanEjectUser"
  val CAN_MUTE_USER = "CanMuteUser"
  val CAN_SHARE_WEBCAM = "CanShareWebcam"
  val CAN_PRIVATE_CHAT = "CanPrivateChat"
  val CAN_PUBLIC_CHAT = "CanPublicChat"
}

case class UserAuthz(allowed: Set[String], pinned: Boolean)