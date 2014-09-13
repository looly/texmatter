package com.xiaoleilu.texmatter.handler;

/**
 * 内容处理接口
 * @author Looly
 *
 */
public interface Handler {
	/**
	 * 处理
	 * @param text 被处理的文本
	 * @return 处理后的文本
	 */
	public String handle(String text);
}
