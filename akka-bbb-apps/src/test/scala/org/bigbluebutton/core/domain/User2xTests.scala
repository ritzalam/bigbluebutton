package org.bigbluebutton.core.domain

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.filters.DefaultPermissionsFilter

class User2xTests extends UnitSpec {
  val flashListenOnly = FlashWebListenOnly(SessionId("flash-web-listen-only-session-id"))
  val dataApp = DataApp2x(SessionId("data-session-id"))
  val webcamApp = WebcamApp2x(SessionId("webcam-session-id"), Set.empty)
  val voiceApp = VoiceApp2x(SessionId("voice-session-id"), flashListenOnly)
  val screenshareApp = ScreenshareApp2x(SessionId("ss-session-id"), Set.empty)

  it should "update presence" in {
    val presence1 = FlashWebPresence(PresenceId("flash-web-presence-id-1"), dataApp, webcamApp, voiceApp, screenshareApp)
    val presence2 = FlashWebPresence(PresenceId("flash-web-presence-id-2"), dataApp, webcamApp, voiceApp, screenshareApp)
    val presence3 = FlashWebPresence(PresenceId("flash-web-presence-id-3"), dataApp, webcamApp, voiceApp, screenshareApp)

    val perm: Set[Permission2x] = Set(CanEjectUser, CanRaiseHand)
    val user = User3x(
      IntUserId("userid-1"),
      Set.empty,
      false,
      Set(presence1, presence2),
      Set.empty,
      Set.empty)

    val newDataApp = presence2.dataApp.update(presence2.dataApp, SessionId("updated-session"))
    val presence4 = presence2.save(presence2, newDataApp)
    val user2 = user.update(presence2, user, presence4)

    assert(user2.presence.size == 2)
  }

  //  it should "not eject user" in {
  //    object DefPerm extends DefaultPermissionsFilter
  //    val perm: Set[Permission2x] = Set(CanRaiseHand)
  //
  //    assert(DefPerm.can(CanEjectUser, perm) != true)
  //  }

}
