package org.raig.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class EchoServerHandler extends ChannelHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    ctx.write(msg); // Buffering the message itnernally
    ctx.flush(); // flushing out th thw wire and
                 // doing ReferenceCountUtil.release(msg) automatically
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }
}
