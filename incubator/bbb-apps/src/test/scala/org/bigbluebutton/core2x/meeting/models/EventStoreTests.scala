package org.bigbluebutton.core2x.meeting.models

import org.bigbluebutton.core2x.UnitSpec
import org.bigbluebutton.core2x.domain.{Abilities2x, CanEjectUser, CanRaiseHand}
import org.bigbluebutton.core2x.meeting.filters.DefaultAbilitiesFilter

class EventStoreTests extends UnitSpec {
  it should "eject user" in {
    val eventStore = new EventStore
    val eventNumber = new EventNumber
    var eventNum = eventNumber.increment()
    eventStore.add(new Event(eventNum))

    println(eventStore.getAll)
    eventStore.add(new Event(eventNumber.increment()))
    println(eventStore.getAll)
    assert(eventStore.getAll.length == 2)
  }
}
