defmodule ClientProxy.Subscriber do
  use GenServer

  # for a singleton specific to this node
  @name :redis_subscriber

  def start_link(_opts) do
    IO.puts("************* START_LINK **************")
    GenServer.start_link(__MODULE__, :ok, name: @name)
  end

  def subscribe(client, channel) do
    GenServer.cast(@name, {:subscribe, %{client: client, channel: channel}})
  end

  def unsubscribe(client, channel) do
    GenServer.cast(@name, {:unsubscribe, %{client: client, channel: channel}})
  end

  def init(:ok) do
      IO.puts("************* INIT **************")
      {:ok, pubsub} = Redix.PubSub.start_link()
      {:ok, %{pubsub: pubsub}}
  end

  def handle_cast({:unsubscribe, %{client: client, channel: channel}}, state) do
    :ok = Redix.PubSub.unsubscribe(state.pubsub, channel, client)
    IO.puts("UNSUBSCRIBED from #{channel}")
    {:noreply, state}
  end

  def handle_cast({:subscribe, %{client: client, channel: channel}}, state) do
    {:ok, _subs} = Redix.PubSub.subscribe(state.pubsub, channel, client)
    {:noreply, state}
  end

  def handle_info(msg, state) do
    IO.puts(inspect msg)
    {:noreply, state}
  end
end