defmodule ClientProxy.Client do
  use GenServer, start: {__MODULE__, :start_link, []}, restart: :transient

  alias ClientProxy.MsgBridge

  @on_msg_from_server "on-msg-from-server"

  def via_tuple(name), do: {:via, Registry, {Registry.Client, name}}

  def start_link(name) when is_binary(name), do:
    GenServer.start_link(__MODULE__, name, name: via_tuple(name))

  def init(name) do
    :ok = MsgBridge.subscribe("foo")
    {:ok, %{name: name}}
  end

  def get_auth_token_info(client, auth_token) when is_binary(auth_token), do:
    GenServer.call(client, {:get_auth_token_info, auth_token})

  def handle_call({:get_auth_token_info, auth_token}, _from, state_data) do
    {:reply, :ok, state_data}
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
    #IO.puts("#{envelope["name"]} #{envelope["routing"]["msgType"]}")
    
    #ClientProxyWeb.Endpoint.broadcast("client:" <> state.name, "new_msg", rx_msg["core"])
    {:noreply, state}
  end

  def handle_info({topic, name, event}, state) do
    IO.puts "the new moving average of the store is #{inspect name}"
    {:noreply, state}
  end

  def handle_info(:timeout, state_data) do
    {:stop, {:shutdown, :timeout}, state_data}
  end

  def terminate(reason, state) do
    IO.puts("terminate client #{reason}")
    IO.puts("terminate client #{inspect state}")
    :ok
  end

  defp process_message(%{"meetingId" => meetingid, "msgType" => "DIRECT", "userId" => userid}, body, state) do
    IO.puts("PUBLISHING DIRECT MESSAGE")
    channel = "client:" <> state.name # meetingid <> ":" <> userid
    ClientProxyWeb.Endpoint.broadcast(channel, @on_msg_from_server, body)
  end

  defp process_message(%{"meetingId" => meetingid, "msgType" => "BROADCAST_TO_MEETING"}, body, state) do
    IO.puts("PUBLISHING BROADCAST MESSAGE")
    channel = "client:" <> state.name #meetingid
    ClientProxyWeb.Endpoint.broadcast(channel, @on_msg_from_server, body)
  end

  defp process_message(envelope, body, _) do
    IO.puts("NOT PROCESSING MESSAGE #{inspect envelope}")
  end

end