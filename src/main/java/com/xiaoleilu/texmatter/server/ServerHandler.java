package com.xiaoleilu.texmatter.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import org.slf4j.Logger;

import com.xiaoleilu.hutool.Log;
import com.xiaoleilu.hutool.StrUtil;
import com.xiaoleilu.texmatter.util.Constant;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	private final static Logger log = Log.get();
	
	/** 返回内容类型：普通文本 */
	public final static String CONTENT_TYPE_TEXT = "text/plain";
	/** 返回内容类型：HTML */
	public final static String CONTENT_TYPE_HTML = "text/html";
	/** 返回内容类型：XML */
	public final static String CONTENT_TYPE_XML = "text/xml";
	/** 返回内容类型：JAVASCRIPT */
	public final static String CONTENT_TYPE_JAVASCRIPT = "application/javascript";
	/** 返回内容类型：JSON */
	public final static String CONTENT_TYPE_JSON = "application/json";
	public final static String CONTENT_TYPE_JSON_IE = "text/json";
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (false == msg instanceof HttpRequest) {
			return;
		}
		HttpRequest request = (HttpRequest) msg;
		
		//状态100 continue
		if (HttpHeaders.is100ContinueExpected(request)) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
		}
		
		//提取HttpRequest参数
		log.debug("uri: " + request.getUri());
		
		FullHttpResponse response = buildOkResponse("text/plain", "中文你好！");
		
		if (HttpHeaders.isKeepAlive(request)) {
			log.debug("Use keep alive");
			response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
			ctx.write(response);
		} else {
			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error(cause.getMessage(), cause);
		ctx.close();
	}
	
	/**
	 * 构建返回信息
	 * @param contentType 返回信息类型
	 * @param content 内容
	 * @return FullHttpResponse
	 */
	private FullHttpResponse buildOkResponse(String contentType, String content) {
		final FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, 
				HttpResponseStatus.OK, 
				Unpooled.wrappedBuffer(StrUtil.encode(content, Constant.CHARSET))
		);
		
		final HttpHeaders headers = response.headers();
		
		headers.set(Names.CONTENT_TYPE, StrUtil.format("{};charset={}", contentType, Constant.CHARSET));
		headers.set(Names.CONTENT_ENCODING, Constant.CHARSET);
		headers.set(Names.CONTENT_LENGTH, response.content().readableBytes());
		
		return response;
	}
}
