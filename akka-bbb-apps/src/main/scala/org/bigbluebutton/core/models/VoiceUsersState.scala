package org.bigbluebutton.core.models

import com.softwaremill.quicklens._

object VoiceUsersState {
  def findWithIntId(users: VoiceUsersState, intId: String): Option[VoiceUserState] = users.toVector.find(u => u.intId == intId)

  def findWithVoiceUserId(users: VoiceUsersState, voiceUserId: String): Option[VoiceUserState] = {
    users.toVector.find(u => u.voiceUserId == voiceUserId)
  }

  def add(users: VoiceUsersState, state: VoiceUserState): Unit = {
    users.save(state)
  }

  def remove(users: VoiceUsersState, intId: String): Option[VoiceUserState] = {
    users.remove(intId)
  }

}

class VoiceUsersState {
  private var users: collection.immutable.HashMap[String, VoiceUserState] = new collection.immutable.HashMap[String, VoiceUserState]

  private def toVector: Vector[VoiceUserState] = users.values.toVector

  private def save(user: VoiceUserState): VoiceUserState = {
    users += user.intId -> user
    user
  }

  private def remove(intId: String): Option[VoiceUserState] = {
    val user = users.get(intId)
    user foreach (u => users -= intId)
    user
  }
}

case class VoiceUserState(intId: String, voiceUserId: String, callingWith: String, callerName: String,
  callerNum: String, muted: Boolean, talking: Boolean, listenOnly: Boolean)
