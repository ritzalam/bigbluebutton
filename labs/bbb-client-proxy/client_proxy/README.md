# ClientProxy

Setup a BBB 2.2 (master) server. Make sure it runs.

Install Elixir

[Ubuntu Install Instructions](https://elixir-lang.org/install.html#unix-and-unix-like)

```
wget https://packages.erlang-solutions.com/erlang-solutions_1.0_all.deb && sudo dpkg -i erlang-solutions_1.0_all.deb
sudo apt-get update
sudo apt-get install esl-erlang
sudo apt-get install elixir
```

Install Hex

```
mix local.hex
```

Install Phoenix Framework without Postgres

```
mix archive.install hex phx_new 1.4.0
```

Install Nodejs

```
curl -sL https://deb.nodesource.com/setup_8.x -o nodesource_setup.sh
sudo bash nodesource_setup.sh

sudo apt-get install -y nodejs

```

Install Dependencies

```
mix deps.get
```

Edit `assets/js/socket.js` from

```
  let socket = new Socket("ws://10.130.218.38:4000/socket", {
    params: {token: "foo" /*window.userToken*/},
    logger: (kind, msg, data) => { console.log(`${kind}: ${msg}`, data) }
  })
```

to

```
  let socket = new Socket("/socket", {
    params: {token: "foo" /*window.userToken*/},
    logger: (kind, msg, data) => { console.log(`${kind}: ${msg}`, data) }
  })
```

Note we hardcode the token for now. We can substitute it with the sessionToken later.

Run the application.

```
mix phx.server
```

