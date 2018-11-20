defmodule ClientProxy.DataProxy do
  use GenServer

  # for a singleton specific to this node
  @name :redis_data_proxy

  def start_link(opts) do
    IO.puts("************* DATAPROXY START_LINK **************")
    GenServer.start_link(__MODULE__, opts, name: @name)
  end

  def get_auth_token_info(auth_token) when is_binary(auth_token), do:
    GenServer.call(@name, {:get_auth_token_info, auth_token})


  def init(_opts) do
      IO.puts("************* DATAPROXY INIT **************")
      {:ok, client} = Redix.start_link()
      IO.puts(inspect client)
      IO.puts("************* DATAPROXY INITED **************")
      {:ok, %{client: client}}
  end

  def handle_call({:get_auth_token_info, auth_token}, _from, state) do
    {:ok, [meetingid, userid]} = Redix.command(state.client, ["HMGET", "client:auth_token:" <> auth_token, "meetingId", "userId"])
    {:reply, %{meetingid: meetingid, userid: userid}, state}
  end

  defp send_reply({:ok, result}, state) do
    {:reply, {:ok, result}, state}
  end

  defp send_reply({:error, reason}, state) do
    {:reply, {:error, reason}, state}
  end

end