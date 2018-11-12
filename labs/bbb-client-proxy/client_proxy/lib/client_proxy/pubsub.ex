defmodule ClientProxy.PubSub do
  use Supervisor

  def start_link(_opts), do:
    Supervisor.start_link(__MODULE__, :ok, name: __MODULE__)

  def init(:ok) do
   Supervisor.init([Publisher], strategy: :simple_one_for_one)
   Supervisor.init([Subscriber], strategy: :simple_one_for_one)
  end
end