# Netty

Some katas about Netty
---
###NettyKata01 
Discard Server simple example written with netty and build with gradle 

For executing Server

```
gradle run
```

For exectuing Client

```
echo "independence day" | nc localhost 8080
```

You will not see in the client terminal nothing (Discard) and some traces in Server
```
run>>
initChannel>>
initChannel<<
channelRead>>
independence day
channelRead<<
```
---
###NettyKata02
Echo Server simple example written with netty and build with gradle 

For executing Server

```
gradle run
```

For exectuing Client

```
echo "independence day" | nc localhost 8080
```

You will see in the client terminal "independence day" and some traces in Server
```
run>>
initChannel>>
initChannel<<
```


---
###NettyKata03
Time Server and Time Client simple example written with netty and build with gradle 

For executing Server

```
gradle run
```

For exectuing Client

```
gradle run  -PappArgs="['localhost','8080']"
```

You will see in the client terminal the date "Sat Mar 28 17:48:38 CET 2015" and some traces in Server
```
run>>
initChannel>>
initChannel<<
```


Interesting parts of source code:

In TimeClient:

````
  // Start the client.
  ChannelFuture f = b.connect(host, port).sync(); 

  // Wait until the connection is closed.
  // If you remove ".sync()" the program exits and not process the
  // response from the server
  f.channel().closeFuture().sync();
````

In TimeClientHandler:

Releasing the message:

```
} finally {
  m.release();
}
```
      
