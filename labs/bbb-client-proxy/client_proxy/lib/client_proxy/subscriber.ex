defmodule ClientProxy.Subscriber do
  use GenServer

  def start_link(opts) do
    IO.puts("************* START_LINK **************")
    GenServer.start_link(__MODULE__, :ok, opts)
  end

  def lookup(server, name) do
    GenServer.call(server, {:lookup, name})
  end

  def create(server, name) do
    GenServer.cast(server, {:create, name})
  end

  def init(:ok) do
      names = %{}
      refs = %{}
      IO.puts("************* INIT **************")
      {:ok, pubsub} = Redix.PubSub.start_link()
      {:ok, subs} = Redix.PubSub.subscribe(pubsub, "to-akka-apps-redis-channel", self())
      IO.puts(inspect subs)
      IO.puts("************* SUBSCRIBED **************")
      {:ok, {names, refs}}
  end

  def handle_info({_, _, _, :message, message}, state) do
    #IO.puts(inspect msg)
    ClientProxy.Publisher.send(message.payload)
    {:noreply, state}
  end

  def handle_info(msg, state) do
    IO.puts(inspect msg)
    {:noreply, state}
  end
end