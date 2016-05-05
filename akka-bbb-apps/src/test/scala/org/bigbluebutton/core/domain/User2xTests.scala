package org.bigbluebutton.core.domain

import org.bigbluebutton.core.UnitSpec

class User2xTests extends UnitSpec {
  val flashListenOnly = Voice4x(new UserAgent("flash-web-listen-only-session-id"),
    IntUserId("userId"), CallerId(CallerIdName("foo"), CallerIdNum("foo")),
    ListenDirection(true), TalkDirection(false), Muted(true), Talking(false))
  val dataApp = DataApp2x(SessionId("data-session-id"))
  val webcamApp = WebcamApp2x(SessionId("webcam-session-id"), Set.empty)
  val voiceApp = VoiceApp2x(SessionId("voice-session-id"), flashListenOnly)
  val screenshareApp = ScreenshareApp2x(SessionId("ss-session-id"), Set.empty)

  it should "update presence" in {
    val presence1 = new Presence2x(
      PresenceId("flash-web-presence-id-1"),
      UserAgent("Flash"), None, None, None, None)
    val presence2 = new Presence2x(
      PresenceId("flash-web-presence-id-2"),
      UserAgent("Flash"), None, None, None, None)
    val presence3 = new Presence2x(
      PresenceId("flash-web-presence-id-3"),
      UserAgent("Flash"), None, None, None, None)

    val perm: Set[Abilities2x] = Set(CanEjectUser, CanRaiseHand)
    val user = new User3x(
      IntUserId("userid-1"),
      ExtUserId("userid-1"),
      Name("Foo"),
      EmojiStatus("none"),
      Set(ModeratorRole, PresenterRole),
      Set(presence1, presence2),
      UserAbilities(Set.empty, Set.empty, false),
      Set.empty, Set.empty, Set.empty)

    val newDataApp = DataApp2x(SessionId("updated-session"))
    val presence1a = Presence2x.save(presence1, newDataApp)
    val newUser = User3x.update(presence1, user, presence1a)

    assert(newUser.presence.size == 2)
  }

  //  it should "not eject user" in {
  //    object DefPerm extends DefaultPermissionsFilter
  //    val perm: Set[Permission2x] = Set(CanRaiseHand)
  //
  //    assert(DefPerm.can(CanEjectUser, perm) != true)
  //  }

}
