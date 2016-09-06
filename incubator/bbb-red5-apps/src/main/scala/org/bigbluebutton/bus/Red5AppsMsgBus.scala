package org.bigbluebutton.bus

import akka.actor.ActorRef
import akka.event.{EventBus, LookupClassification}

trait Red5AppsMsgTrait

case class Red5AppsMsg(val topic: String, val payload: Red5AppsMsgTrait)

case class FromClientMsg(val name: String, val json: String, val connectionId: String) extends Red5AppsMsgTrait
case class ToClientMsg(val payload: String) extends Red5AppsMsgTrait
case class FromPubSubJsonMsg(val payload: String) extends Red5AppsMsgTrait
case class ToPubSubJsonMsg(val payload: String) extends Red5AppsMsgTrait

class Red5AppsMsgBus extends EventBus with LookupClassification {
  type Event = Red5AppsMsg
  type Classifier = String
  type Subscriber = ActorRef

  // is used for extracting the classifier from the incoming events  
  override protected def classify(event: Event): Classifier = event.topic

  // will be invoked for each event for all subscribers which registered themselves
  // for the event’s classifier
  override protected def publish(event: Event, subscriber: Subscriber): Unit = {
    subscriber ! event.payload
  }

  // must define a full order over the subscribers, expressed as expected from
  // `java.lang.Comparable.compare`
  override protected def compareSubscribers(a: Subscriber, b: Subscriber): Int =
    a.compareTo(b)

  // determines the initial size of the index data structure
  // used internally (i.e. the expected number of different classifiers)
  override protected def mapSize: Int = 128
}

