package com.xiaoleilu.texmatter.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import org.slf4j.Logger;

import com.xiaoleilu.hutool.Log;

public class NettyServer {
	private final static Logger log = Log.get();

	private static int port = 8090;
	
	/**
	 * 启动服务
	 * @param port 端口
	 * @throws InterruptedException 
	 */
	public void start(int port) throws InterruptedException {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
//				.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline()
					.addLast(new HttpServerCodec())
					.addLast(new ServerHandler());
				}
			});
			
			log.info("Netty Http Server Start on port {}", port);
			
			Channel ch = b.bind(port).sync().channel();
			ch.closeFuture().sync();
			
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception{
		new NettyServer().start(port);
	}
}
