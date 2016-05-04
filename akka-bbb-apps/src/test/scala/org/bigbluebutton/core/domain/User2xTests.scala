package org.bigbluebutton.core.domain

import org.bigbluebutton.core.UnitSpec

class User2xTests extends UnitSpec {
  val flashListenOnly = FlashWebListenOnly(SessionId("flash-web-listen-only-session-id"))
  val dataApp = DataApp2x(SessionId("data-session-id"))
  val webcamApp = WebcamApp2x(SessionId("webcam-session-id"), Set.empty)
  val voiceApp = VoiceApp2x(SessionId("voice-session-id"), flashListenOnly)
  val screenshareApp = ScreenshareApp2x(SessionId("ss-session-id"), Set.empty)

  it should "update presence" in {
    val presence1 = new FlashWebPresence2x(PresenceId("flash-web-presence-id-1"))
    val presence2 = new FlashWebPresence2x(PresenceId("flash-web-presence-id-2"))
    val presence3 = new FlashWebPresence2x(PresenceId("flash-web-presence-id-3"))

    val perm: Set[Abilities2x] = Set(CanEjectUser, CanRaiseHand)
    val user = new User3x(IntUserId("userid-1"), Set(ModeratorRole, PresenterRole), Set(presence1, presence2),
      UserAbilities(Set.empty, Set.empty, false), Set.empty, Set.empty, Set.empty)

    val newDataApp = DataApp2x(SessionId("updated-session"))
    val presence1a = FlashWebPresence2x.save(presence1, newDataApp)
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
