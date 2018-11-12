defmodule ClientProxyWeb.Router do
  use ClientProxyWeb, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/api", ClientProxyWeb do
    pipe_through :api
  end
end
