defmodule ClientProxy.MsgRouter do
  use GenServer

  # for a singleton specific to this node
  @name :msg_router

  @from_akka_apps "from-akka-apps-redis-channel"
  @from_akka_apps_wb "from-akka-apps-wb-redis-channel"
  @from_akka_apps_chat "from-akka-apps-chat-redis-channel"
  @from_akka_apps_pres "from-akka-apps-pres-redis-channel"

  @on_msg_from_server "on-msg-from-server"

  def start_link(_opts) do
    IO.puts("************* START MSG ROUTER **************")
    GenServer.start_link(__MODULE__, :ok, name: @name)
  end

  def init(name) do
    send(self(), {:subscribe_to_channels})
    {:ok, %{name: name}}
  end

  def handle_info({:subscribe_to_channels}, state) do
    ClientProxy.Subscriber.subscribe(self(), @from_akka_apps)
    ClientProxy.Subscriber.subscribe(self(), @from_akka_apps_wb)
    ClientProxy.Subscriber.subscribe(self(), @from_akka_apps_chat)
    ClientProxy.Subscriber.subscribe(self(), @from_akka_apps_pres)
    {:noreply, state}
  end


  def handle_info({_, _, _, :subscribed, message}, state) do
    IO.puts(inspect message)
    {:noreply, state}
  end

  def handle_info({_, _, _, :unsubscribed, message}, state) do
    IO.puts(inspect message)
    {:noreply, state}
  end

  def handle_info({_, _, _, :message, message}, state) do
    #IO.puts(inspect message)
    rx_msg = Poison.decode!(message.payload)
    envelope = rx_msg["envelope"]["routing"]
    #IO.puts("ENVELOPE: #{inspect envelope}")
    body = rx_msg["core"]
    process_message(envelope, body, state)
    {:noreply, state}
  end

  def terminate(reason, state) do
    IO.puts("terminate client #{reason}")
    IO.puts("terminate client #{inspect state}")
    ClientProxy.Subscriber.unsubscribe(self(), @from_akka_apps)
    ClientProxy.Subscriber.unsubscribe(self(), @from_akka_apps_wb)
    ClientProxy.Subscriber.unsubscribe(self(), @from_akka_apps_chat)
    ClientProxy.Subscriber.unsubscribe(self(), @from_akka_apps_pres)
    :ok
  end

  defp process_message(%{"meetingId" => meetingid, "msgType" => "DIRECT", "userId" => userid}, body, state) do
    #IO.puts("PUBLISHING DIRECT MESSAGE")
    channel = "client" <> ":" <> meetingid <> ":" <> userid
    ClientProxyWeb.Endpoint.broadcast(channel, @on_msg_from_server, body)
  end

  defp process_message(%{"meetingId" => meetingid, "msgType" => "BROADCAST_TO_MEETING"}, body, state) do
    IO.puts("PUBLISHING BROADCAST MESSAGE")
    #channel = "client" <> ":" <> meetingid
    #ClientProxyWeb.Endpoint.broadcast(channel, @on_msg_from_server, body)
    ClientProxy.MsgBridge.broadcast({"foo", "bar", "baz"})
  end

  defp process_message(envelope, body, _) do
    #IO.puts("NOT PROCESSING MESSAGE #{inspect envelope}")
  end
end