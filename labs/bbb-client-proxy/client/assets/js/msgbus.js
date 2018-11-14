let MsgBus = {
  init(socket){
    socket.connect()
    this.onReady(socket, "foo")
  },

  onReady(socket, authToken) {
    let lastSeenId = 0

    var ul = document.getElementById('msg-list');        // list of messages.

    let clientChannel = socket.channel("client:" + authToken, () => {
      return {last_seen_id: lastSeenId}
    })

    clientChannel.on("ping", (resp) => {
      console.log("Received ping message from server.")
    })

    clientChannel.on("new_msg", (resp) => {
      lastSeenId++;
      var li = document.createElement("li"); // creaet new list item DOM element
      li.innerHTML = '<b>' + lastSeenId + '</b> <br/>'; // set li contents
      ul.appendChild(li); 
    })

    clientChannel.join()
      .receive("ok", resp => {
        console.log(resp)
      })
      .receive("error", reason => console.log("joined failed", reason) )

  }

}

export default MsgBus