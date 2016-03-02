package org.bigbluebutton.core.models

// Use Value Classes to help with type safety.
// https://ivanyu.me/blog/2014/12/14/value-classes-in-scala/
case class Name(value: String) extends AnyVal
case class IntMeetingId(value: String) extends AnyVal
case class ExtMeetingId(value: String) extends AnyVal
case class Duration(value: Int) extends AnyVal
case class Recorded(value: Boolean) extends AnyVal
case class VoiceConf(value: String) extends AnyVal
case class AuthToken(value: String) extends AnyVal
case class IntUserId(value: String) extends AnyVal
case class ExtUserId(value: String) extends AnyVal
case class EmojiStatus(value: String) extends AnyVal
case class IsPresenter(value: Boolean) extends AnyVal
case class HasStream(value: Boolean) extends AnyVal
case class Locked(value: Boolean) extends AnyVal
case class PhoneUser(value: Boolean) extends AnyVal
case class ListenOnly(value: Boolean) extends AnyVal
case class JoinedWeb(value: Boolean) extends AnyVal
case class VoiceUserId(value: String) extends AnyVal
case class CallerIdName(value: String) extends AnyVal
case class CallerIdNum(value: String) extends AnyVal
case class JoinedVoice(value: Boolean) extends AnyVal
case class Muted(value: Boolean) extends AnyVal
case class Talking(value: Boolean) extends AnyVal
case class PresentationId(value: String) extends AnyVal
case class ReplyTo(value: String) extends AnyVal //TODO can we use IntUserId here?

case class BreakoutId(value: String) extends AnyVal
