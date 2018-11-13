defmodule ClientProxyWeb.ClientChannel do
  use ClientProxyWeb, :channel

  intercept ["new_msg"]

  def join("client:" <> auth_token, _params, socket) do    
    :timer.send_interval(5_000, :ping)
    {:ok, assign(socket, :user_id, auth_token)}
  end

  def handle_info(:ping, socket) do
   # IO.puts("Socket id: #{socket.id}")
    count = socket.assigns[:count] || 1
    push(socket, "ping", %{count: count})
    
    {:noreply, assign(socket, :count, count + 1)}
  end
    
  def handle_out("new_msg", payload, socket) do
    IO.puts(inspect payload)
    push(socket, "new_msg", payload)
    {:noreply, socket}
  end

  def terminate(reason, state) do
    IO.puts("TERMINATING #{inspect reason}")
    :ok
  end

end
