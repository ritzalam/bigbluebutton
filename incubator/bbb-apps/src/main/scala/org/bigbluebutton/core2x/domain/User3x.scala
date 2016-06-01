package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._

object User3x {
  def create(id: IntUserId, externalId: ExtUserId, name: Name, roles: Set[Role2x]): User3x = {

    new User3x(id, externalId, name, EmojiStatus("none"), roles,
      Set.empty, new UserAbilities(Set.empty, Set.empty, false),
      Set.empty, Set.empty, Set.empty)
  }

  def update(old: Presence2x, user: User3x, updated: Presence2x): User3x = {
    modify(user)(_.presence).setTo((user.presence - old) + updated)
  }

  def findWithPresenceId(presence: Set[Presence2x], presenceId: PresenceId): Option[Presence2x] = {
    presence.find(p => p.id == presenceId)
  }

  def add(user: User3x, presence: Presence2x): User3x = {
    modify(user)(_.presence).setTo(user.presence + presence)
  }

  def add(user: User3x, role: Role2x): User3x = {
    modify(user)(_.roles).setTo(user.roles + role)
  }

  def remove(user: User3x, role: Role2x): User3x = {
    modify(user)(_.roles).setTo(user.roles - role)
  }

  def update(user: User3x, emoji: EmojiStatus): User3x = {
    modify(user)(_.emojiStatus).setTo(emoji)
  }

  def create(id: PresenceId, userAgent: PresenceUserAgent): Presence2x = {
    userAgent match {
      case FlashWebUserAgent => Presence2x(
        id, UserAgent("Flash"), Set.empty, DataApp2x(SessionId("none")), Voice4x(VoiceUserId("foo")),
        WebCamStreams(Set.empty), ScreenShareStreams(Set.empty))
      case Html5WebUserAgent => Presence2x(id, UserAgent("Html5"), Set.empty, DataApp2x(SessionId("none")),
        Voice4x(VoiceUserId("foo")), WebCamStreams(Set.empty), ScreenShareStreams(Set.empty))
    }
  }
}

case class User3x(
    id: IntUserId,
    externalId: ExtUserId,
    name: Name,
    emojiStatus: EmojiStatus,
    roles: Set[Role2x],
    presence: Set[Presence2x],
    permissions: UserAbilities,
    roleData: Set[RoleData],
    config: Set[String],
    externalData: Set[String]) {

  def isModerator: Boolean = roles.contains(ModeratorRole)
}

case class UserAbilities(removed: Set[Abilities2x], added: Set[Abilities2x], applyMeetingAbilities: Boolean)