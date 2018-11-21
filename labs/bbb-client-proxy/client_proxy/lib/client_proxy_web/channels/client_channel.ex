defmodule ClientProxyWeb.ClientChannel do
  use ClientProxyWeb, :channel

  alias ClientProxy.Client

  intercept ["new_msg"]

  defp via("client:" <> auth_token), do: Client.via_tuple(auth_token)

  def join("client:" <> auth_token, _params, socket) do    
    :timer.send_interval(5_000, :ping)
    send(self(), {:after_join, auth_token})
    {:ok, assign(socket, :user_id, auth_token)}
  end

  def handle_info({:after_join, auth_token}, socket) do
    #{:ok, client} = ClientProxy.ClientSupervisor.start_client(auth_token)
    #ClientProxy.Client.get_auth_token_info(client, auth_token)
    {:noreply, socket}
  end

  def handle_info(:ping, socket) do
   # IO.puts("Socket id: #{socket.id}")
    count = socket.assigns[:count] || 1
    push(socket, "ping", %{count: count})
    
    {:noreply, assign(socket, :count, count + 1)}
  end

  def handle_in("msg-from-client", msg, socket) do
    IO.puts("from client: #{inspect msg}")
    {:noreply, socket}
  end
    
  def handle_out("new_msg", payload, socket) do
    msg_count = socket.assigns[:msg_count] || 1
    #IO.puts(msg_count)
    push(socket, "on-msg-from-server", payload)
    {:noreply, assign(socket, :msg_count, msg_count + 1)}
  end

  def terminate(reason, socket) do
    IO.puts("TERMINATING #{inspect reason}")
    IO.puts("TERMINATING #{inspect socket}")
    #ClientProxy.ClientSupervisor.stop_client(socket.assigns.user_id)
    :ok
  end



end
