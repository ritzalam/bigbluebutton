package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api._
import scala.collection.mutable.ArrayBuffer
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.models.UserVO
import org.bigbluebutton.core.models.Role
import org.bigbluebutton.core.models._

trait LayoutHandler {
  this: LiveMeeting =>

  val outGW: OutMessageGateway

  def handleGetCurrentLayoutRequest(msg: GetCurrentLayoutRequest) {
    outGW.send(new GetCurrentLayoutReply(msg.meetingId, mProps.recorded, msg.requesterId,
      layoutModel.getCurrentLayout(), meeting.getPermissions().lockedLayout, layoutModel.getLayoutSetter()))
  }

  def handleLockLayoutRequest(msg: LockLayoutRequest) {
    layoutModel.applyToViewersOnly(msg.viewersOnly)
    lockLayout(msg.lock)

    outGW.send(new LockLayoutEvent(msg.meetingId, mProps.recorded, msg.setById, msg.lock, affectedUsers))

    msg.layout foreach { l =>
      layoutModel.setCurrentLayout(l)
      broadcastSyncLayout(msg.meetingId, msg.setById)
    }
  }

  private def broadcastSyncLayout(meetingId: IntMeetingId, setById: IntUserId) {
    outGW.send(new BroadcastLayoutEvent(meetingId, mProps.recorded, setById,
      layoutModel.getCurrentLayout(), meeting.getPermissions().lockedLayout, layoutModel.getLayoutSetter(), affectedUsers))
  }

  def handleBroadcastLayoutRequest(msg: BroadcastLayoutRequest) {
    layoutModel.setCurrentLayout(msg.layout)
    broadcastSyncLayout(msg.meetingId, msg.requesterId)
  }

  def handleLockLayout(lock: Boolean, setById: IntUserId) {
    outGW.send(new LockLayoutEvent(mProps.id, mProps.recorded, setById, lock, affectedUsers))

    broadcastSyncLayout(mProps.id, setById)
  }

  def affectedUsers(): Array[UserVO] = {
    if (layoutModel.doesLayoutApplyToViewersOnly()) {
      val au = ArrayBuffer[UserVO]()
      meeting.getUsers foreach { u =>
        if (!u.presenter.value && u.role != Role.MODERATOR) {
          au += u
        }
      }
      au.toArray
    } else {
      meeting.getUsers
    }

  }

}
