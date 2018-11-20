defmodule ClientProxy.Client do
  use GenServer, start: {__MODULE__, :start_link, []}, restart: :transient


  def via_tuple(name), do: {:via, Registry, {Registry.Client, name}}

  def start_link(name) when is_binary(name), do:
    GenServer.start_link(__MODULE__, name, name: via_tuple(name))

  def init(name) do
    {:ok, %{name: name}}
  end

  def get_auth_token_info(client, auth_token) when is_binary(auth_token), do:
    GenServer.call(client, {:get_auth_token_info, auth_token})

  def handle_call({:get_auth_token_info, auth_token}, _from, state_data) do
    result = ClientProxy.DataProxy.get_auth_token_info(auth_token)
    ClientProxy.Subscriber.subscribe(self(), result.meetingid)
    ClientProxy.Subscriber.subscribe(self(), result.meetingid <> ":" <> result.userid)
    {:reply, :ok, state_data}
  end

  def handle_info({_, _, _, :subscribed, message}, state) do
    IO.puts(inspect message)
    {:noreply, state}
  end

  def handle_info({_, _, _, :message, message}, state) do
    #IO.puts(inspect message)
    rx_msg = Poison.decode!(message.payload)
    ClientProxyWeb.Endpoint.broadcast("client:" <> state.name, "new_msg", rx_msg["core"])
    {:noreply, state}
  end

  def handle_info(:timeout, state_data) do
    {:stop, {:shutdown, :timeout}, state_data}
  end

  def terminate(reason, _state) do
    IO.puts("terminate client #{reason}")
    :ok
  end


end