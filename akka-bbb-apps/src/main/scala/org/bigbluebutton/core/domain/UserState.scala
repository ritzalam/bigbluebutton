package org.bigbluebutton.core.domain

class UserState(id: IntUserId, extId: ExtUserId) {

  // If user is joined in the meeting.
  var joined = false
  // Time user left the meeting. Allows us
  // to remove user after a certain time.
  var leftOn = 0L

  var logoutUrl: String = ""

  var name: Name = Name("")
  var emojiStatus = EmojiStatus("none")
  val roles: Set[Role2x] = Set.empty
  var avatar = ""
  var welcomeMessage = ""
  var dialNumber = ""
  var pinNumber = 0
  val presence: Set[Presence2x] = Set.empty
  var permissions: UserAbilities = UserAbilities(Set.empty, Set.empty, false)
  var roleData: Set[RoleData] = Set.empty
  var config: Set[String] = Set.empty
  var extData: Set[String] = Set.empty
}
