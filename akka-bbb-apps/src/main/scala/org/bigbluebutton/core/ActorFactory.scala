package org.bigbluebutton.core

import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.domain.{IntUserId, MeetingProperties, UserActorRef}


class ActorFactory {

  def createUserActor(props: MeetingProperties,
                          eventBus: IncomingEventBus,
                          outGW: OutMessageGateway, userId: IntUserId): UserActorRef = {
    context.actorOf(UserActor.props(props, eventBus, outGW), props.id.value.concat("/").concat(userId.value))
  }
}
