package org.raig.TimeServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/** Discards any incoming data.
 */

public class TimeServer {

  private int port;

  public TimeServer(final int port) {
    this.port = port;
  }
  /**
   * run
   */
  public void run() throws Exception {
    System.out.println("run>>");
    EventLoopGroup bossGroup = new NioEventLoopGroup(); // Boss and Worker
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      // Set up the server
      ServerBootstrap boostStrap = new ServerBootstrap();
      boostStrap.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
       .childHandler(new ChannelInitializer<SocketChannel>() {
         @Override
         public void initChannel(final SocketChannel ch) throws Exception {
           System.out.println("initChannel>>");
           ch.pipeline().addLast(new org.raig.TimeServer.TimeServerHandler());
           System.out.println("initChannel<<");
         }
       })
        .option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);

      // Bind and start to accept incoming connections.
      ChannelFuture channelFuture = boostStrap.bind(port).sync();

      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      channelFuture.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
      System.out.println("run<<");
    }
  }

  /** main
   */
  public static void main(String[] args) throws Exception {
    int port;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    } else {
      port = 8080;
    }
    new TimeServer(port).run();
  }
}

