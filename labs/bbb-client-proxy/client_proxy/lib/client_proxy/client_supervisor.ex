defmodule ClientProxy.ClientSupervisor do
  use Supervisor

  alias ClientProxy.Client

  def start_link(_opts), do:
    Supervisor.start_link(__MODULE__, :ok, name: __MODULE__)

  def init(:ok) do
    Supervisor.init([Client], strategy: :simple_one_for_one)
  end

  def start_client(name), do:
    Supervisor.start_child(__MODULE__, [name])

  def stop_client(name) do
    Supervisor.terminate_child(__MODULE__, pid_from_name(name))    
  end

  defp pid_from_name(name) do
    name
    |> Client.via_tuple()
    |> GenServer.whereis()
  end

end