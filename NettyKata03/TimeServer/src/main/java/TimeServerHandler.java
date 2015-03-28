package org.raig.TimeServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Handles a server-side channel.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

  @Override
  public void channelActive(final ChannelHandlerContext ctx) {
    // channel Active is established when a connections is established
    final ByteBuf time = ctx.alloc().buffer(4);
    time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

    final ChannelFuture channelFuture = ctx.writeAndFlush(time); // (3)
    channelFuture.addListener(new ChannelFutureListener() {

      public void operationComplete(ChannelFuture future) {
        assert channelFuture == future;
        ctx.close();
      }
    }); // (4)
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}