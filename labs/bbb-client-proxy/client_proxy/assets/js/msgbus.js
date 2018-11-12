let MsgBus = {
  init(socket){
    socket.connect()
    this.onReady(socket, "foo")
  },

  onReady(socket, authToken) {
    let lastSeenId = 0

    let clientChannel = socket.channel("client:" + authToken, () => {
      return {last_seen_id: lastSeenId}
    })

    clientChannel.on("ping", (resp) => {
      console.log("Received ping message from server.")
    })

    clientChannel.join()
      .receive("ok", resp => {
        console.log(resp)
      })
      .receive("error", reason => console.log("joined failed", reason) )

  }

}

export default MsgBus