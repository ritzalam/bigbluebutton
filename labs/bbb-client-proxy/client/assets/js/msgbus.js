let MsgBus = {
  init(socket){
    socket.connect()
    this.onReady(socket, "etyonsqqjcdh")
  },

  onReady(socket, authToken) {
    let lastSeenId = 0

    var ul = document.getElementById('msg-count');        // list of messages.

    let meetingChannel = socket.channel("client:foo", () => {
      return {last_seen_id: lastSeenId}
    })

    meetingChannel.on("ping", (resp) => {
      console.log("Received ping message from server.")
    })

    meetingChannel.on("on-msg-from-server", (resp) => {
      lastSeenId++;
      ul.innerHTML = '<b>' + lastSeenId + '</b>'; // set li contents
      //clientChannel.push('msg-from-client', resp)
    })

    meetingChannel.join()
      .receive("ok", resp => {
        console.log(resp)
      })
      .receive("error", reason => console.log("joined failed", reason) )

    let userChannel = socket.channel("client:foo:bar", () => {
      return {last_seen_id: lastSeenId}
    })

    userChannel.on("ping", (resp) => {
      console.log("Received ping message from server.")
    })

    userChannel.on("on-msg-from-server", (resp) => {
      lastSeenId++;
      ul.innerHTML = '<b>' + lastSeenId + '</b>'; // set li contents
      //clientChannel.push('msg-from-client', resp)
    })

    userChannel.join()
      .receive("ok", resp => {
        console.log(resp)
      })
      .receive("error", reason => console.log("joined failed", reason) )
  }

}

export default MsgBus