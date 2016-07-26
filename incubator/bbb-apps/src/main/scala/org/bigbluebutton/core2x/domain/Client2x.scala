package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._

trait ClientUserAgent
case object FlashWebUserAgent extends ClientUserAgent
case object Html5WebUserAgent extends ClientUserAgent

object Client2x {
  def save(client: Client2x, component: AppsComponent): Client2x = {
    modify(client)(_.appsComponent).setTo(component)
  }

  def save(client: Client2x, component: VoiceComponent): Client2x = {
    modify(client)(_.voiceComponent).setTo(component)
  }

  def save(client: Client2x, component: WebCamComponent): Client2x = {
    modify(client)(_.webCamComponent).setTo(component)
  }

  def save(client: Client2x, component: ScreenShareComponent): Client2x = {
    modify(client)(_.screenShareComponent).setTo(component)
  }
}

case class Client2x(
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
case class VoiceApp2x(sessionId: SessionId, voice: Voice4x)
case class ScreenShareStreams(streams: Set[Stream])

class Clients {
  private var clients: collection.immutable.HashMap[ClientId, Client2x] = new collection.immutable.HashMap[ClientId, Client2x]

  def toVector: Vector[Client2x] = clients.values.toVector

  def save(client: Client2x): Unit = {
    clients += client.id -> client
  }

  def remove(id: ClientId): Option[Client2x] = {
    val client = clients.get(id)
    client foreach (u => clients -= id)
    client
  }
}