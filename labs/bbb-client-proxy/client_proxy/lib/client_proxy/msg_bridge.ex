defmodule ClientProxy.MsgBridge do
  def subscribe do
    Phoenix.PubSub.subscribe(EventSubscriber.PubSub, "all")
  end

  def subscribe(topic) do
    Phoenix.PubSub.subscribe(EventSubscriber.PubSub, topic)
  end

  def broadcast({topic, name, event}) do
    IO.puts("BROADCAST EVENT")
    Phoenix.PubSub.broadcast(EventSubscriber.PubSub, topic, {topic, name, event})
    #Phoenix.PubSub.broadcast(EventSubscriber.PubSub, "all", {topic, name, event})
  end
end