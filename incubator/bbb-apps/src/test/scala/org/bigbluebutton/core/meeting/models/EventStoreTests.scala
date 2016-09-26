package org.bigbluebutton.core.meeting.models

import org.bigbluebutton.core.UnitSpec

class EventStoreTests extends UnitSpec {
  it should "eject user" in {
    val eventStore = new EventStore
    val eventNumber = new EventNumber
    var eventNum = eventNumber.increment()
    eventStore.add(new Event(eventNum))

    eventStore.add(new Event(eventNumber.increment()))
    assert(eventStore.getAll.length == 2)
  }
}
