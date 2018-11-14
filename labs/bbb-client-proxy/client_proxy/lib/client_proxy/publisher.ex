defmodule ClientProxy.Publisher do
  use GenServer

  # for a singleton specific to this node
  @name :redis_publisher

  def start_link(opts) do
    IO.puts("************* PUBLISHER START_LINK **************")
    GenServer.start_link(__MODULE__, opts, name: @name)
  end

  def send(message) do
    GenServer.cast(@name, {:send, message})
  end

  def init(_opts) do
      IO.puts("************* PUBLISHER INIT **************")
      #{:ok, pubsub} = Redix.PubSub.start_link()
      {:ok, client} = Redix.start_link()
      IO.puts(inspect client)
      IO.puts("************* PUBLISHER INITED **************")
      {:ok, %{client: client}}
  end
  
  def handle_cast({:send, message}, state) do
    IO.puts("SENDING #{message}")
    Redix.command!(state.client, ["PUBLISH", "FOO-FOO", message]) 
    {:noreply, state}
  end

  def handle_info(msg, state) do
    IO.puts(inspect msg)
    {:noreply, state}
  end
end