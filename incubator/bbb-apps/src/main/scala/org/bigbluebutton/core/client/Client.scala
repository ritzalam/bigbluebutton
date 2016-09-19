package org.bigbluebutton.core.client

import com.softwaremill.quicklens._
import org.bigbluebutton.core.domain.{ ClientId, IntUserId, SessionId, SessionToken, Stream, UserAgent, Voice4x }

trait ClientUserAgent
case object FlashWebUserAgent extends ClientUserAgent
case object Html5WebUserAgent extends ClientUserAgent

object Client {
  def save(client: Client, component: AppsComponent): Client = {
    modify(client)(_.appsComponent).setTo(component)
  }

  def save(client: Client, component: VoiceComponent): Client = {
    modify(client)(_.voiceComponent).setTo(component)
  }

  def save(client: Client, component: WebCamComponent): Client = {
    modify(client)(_.webCamComponent).setTo(component)
  }

  def save(client: Client, component: ScreenShareComponent): Client = {
    modify(client)(_.screenShareComponent).setTo(component)
  }
}

case class Client(
  id: ClientId,
  userId: IntUserId,
  sessionToken: SessionToken,
  userAgent: UserAgent,
  appsComponent: AppsComponent,
  voiceComponent: VoiceComponent,
  webCamComponent: WebCamComponent,
  screenShareComponent: ScreenShareComponent)

object AppsComponent {
  //  def update(data: AppsComponent, session: SessionId): AppsComponent = {
  //    modify(data)(_.sessionId).setTo(session)
  //  }
}

case class AppsComponent(sessionIds: Set[SessionId])
case class WebCamComponent(sessionIds: Set[SessionId])
case class VoiceComponent(sessionIds: Set[SessionId])
case class ScreenShareComponent(sessionIds: Set[SessionId])

case class WebCamStreams(streams: Set[Stream])
case class VoiceApp(sessionId: SessionId, voice: Voice4x)
case class ScreenShareStreams(streams: Set[Stream])

class Clients {
  private var clients: collection.immutable.HashMap[ClientId, Client] = new collection.immutable.HashMap[ClientId, Client]

  def toVector: Vector[Client] = clients.values.toVector

  def save(client: Client): Unit = {
    clients += client.id -> client
  }

  def remove(id: ClientId): Option[Client] = {
    val client = clients.get(id)
    client foreach (u => clients -= id)
    client
  }
}