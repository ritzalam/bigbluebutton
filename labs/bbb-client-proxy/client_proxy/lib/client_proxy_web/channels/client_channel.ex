defmodule ClientProxyWeb.ClientChannel do
  use ClientProxyWeb, :channel

  def join("client:" <> auth_token, _params, socket) do    
    :timer.send_interval(5_000, :ping)
    {:ok, assign(socket, :user_id, auth_token)}
  end

  def handle_info(:ping, socket) do
    count = socket.assigns[:count] || 1
    push(socket, "ping", %{count: count})

    {:noreply, assign(socket, :count, count + 1)}
  end
end
