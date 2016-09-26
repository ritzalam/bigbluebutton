package org.bigbluebutton.core.domain

import com.softwaremill.quicklens._
import org.bigbluebutton.core.user.client._

object User {
  def create(id: IntUserId, externalId: ExtUserId, name: Name, roles: Set[Role]): User = {

    new User(id, externalId, name, EmojiStatus("none"), Avatar("none"), roles, Set.empty,
      Set.empty, new UserAbilities(Set.empty, Set.empty, false), Set.empty, Set.empty)
  }

  def findClientWithSessionToken(clients: Set[Client], sessionToken: SessionToken): Option[Client] = {
    clients.find(c => c.sessionToken == sessionToken)
  }

  def create(id: ClientId, userId: IntUserId, sessionToken: SessionToken, userAgent: ClientUserAgent): Client = {
    new Client(id, userId, sessionToken, UserAgent("Flash"), new AppsComponent(Set.empty), new VoiceComponent(Set.empty),
      new WebCamComponent(Set.empty), new ScreenShareComponent(Set.empty))
  }

}

case class User(id: IntUserId, externalId: ExtUserId, name: Name, emojiStatus: EmojiStatus,
    avatar: Avatar, roles: Set[Role], roleData: Set[RoleData],
    clients: Set[Client], permissions: UserAbilities,
    config: Set[String], externalData: Set[String]) {

  def isModerator: Boolean = roles.contains(ModeratorRole)
  def add(client: Client): User = modify(this)(_.clients).setTo(this.clients + client)
  def add(role: Role): User = modify(this)(_.roles).setTo(this.roles + role)
  def remove(role: Role): User = modify(this)(_.roles).setTo(roles - role)
  def update(emoji: EmojiStatus): User = modify(this)(_.emojiStatus).setTo(emoji)
  def findWithClientId(clientId: ClientId): Option[Client] = clients.find(p => p.id == clientId)
}

case class UserAbilities(removed: Set[Ability], added: Set[Ability], applyMeetingAbilities: Boolean)