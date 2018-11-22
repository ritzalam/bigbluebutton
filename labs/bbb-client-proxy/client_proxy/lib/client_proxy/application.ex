defmodule ClientProxy.Application do
  # See https://hexdocs.pm/elixir/Application.html
  # for more information on OTP Applications
  @moduledoc false

  use Application

  def start(_type, _args) do
    # List all child processes to be supervised
    children = [
      # Start the endpoint when the application starts
      ClientProxyWeb.Endpoint,
      # Starts a worker by calling: ClientProxy.Worker.start_link(arg)
      # {ClientProxy.Worker, arg},
      ClientProxy.Publisher,
      ClientProxy.Subscriber,
      ClientProxy.MsgRouter,
      {Registry, keys: :unique, name: Registry.Client},
      ClientProxy.ClientSupervisor,
      EventSubscriber.Supervisor
    ]

    # See https://hexdocs.pm/elixir/Supervisor.html
    # for other strategies and supported options
    opts = [strategy: :one_for_one, name: ClientProxy.Supervisor]
    Supervisor.start_link(children, opts)
  end

  # Tell Phoenix to update the endpoint configuration
  # whenever the application is updated.
  def config_change(changed, _new, removed) do
    ClientProxyWeb.Endpoint.config_change(changed, removed)
    :ok
  end
end
