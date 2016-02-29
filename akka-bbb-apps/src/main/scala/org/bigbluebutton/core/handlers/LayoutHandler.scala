package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api._
import scala.collection.mutable.ArrayBuffer
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.models.UserVO
import org.bigbluebutton.core.models.Role

trait LayoutHandler {
  this: LiveMeeting =>

  val outGW: OutMessageGateway

  def handleGetCurrentLayoutRequest(msg: GetCurrentLayoutRequest) {
    outGW.send(new GetCurrentLayoutReply(msg.meetingID, mProps.recorded.value, msg.requesterID,
      layoutModel.getCurrentLayout(), meetingModel.getPermissions().lockedLayout, layoutModel.getLayoutSetter()))
  }

  def handleLockLayoutRequest(msg: LockLayoutRequest) {
    layoutModel.applyToViewersOnly(msg.viewersOnly)
    lockLayout(msg.lock)

    outGW.send(new LockLayoutEvent(msg.meetingID, mProps.recorded.value, msg.setById, msg.lock, affectedUsers))

    msg.layout foreach { l =>
      layoutModel.setCurrentLayout(l)
      broadcastSyncLayout(msg.meetingID, msg.setById)
    }
  }

  private def broadcastSyncLayout(meetingId: String, setById: String) {
    outGW.send(new BroadcastLayoutEvent(meetingId, mProps.recorded.value, setById,
      layoutModel.getCurrentLayout(), meetingModel.getPermissions().lockedLayout, layoutModel.getLayoutSetter(), affectedUsers))
  }

  def handleBroadcastLayoutRequest(msg: BroadcastLayoutRequest) {
    layoutModel.setCurrentLayout(msg.layout)
    broadcastSyncLayout(msg.meetingID, msg.requesterID)
  }

  def handleLockLayout(lock: Boolean, setById: String) {
    outGW.send(new LockLayoutEvent(mProps.id.value, mProps.recorded.value, setById, lock, affectedUsers))

    broadcastSyncLayout(mProps.id.value, setById)
  }

  def affectedUsers(): Array[UserVO] = {
    if (layoutModel.doesLayoutApplyToViewersOnly()) {
      val au = ArrayBuffer[UserVO]()
      usersModel.getUsers foreach { u =>
        if (!u.presenter && u.role != Role.MODERATOR) {
          au += u
        }
      }
      au.toArray
    } else {
      usersModel.getUsers
    }

  }

}
