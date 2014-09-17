package com.xiaoleilu.texmatter.handler;

import com.xiaoleilu.hutool.StrUtil;
import com.xiaoleilu.texmatter.util.Constant;

/**
 * 段落相关处理类
 * @author Looly
 *
 */
public class ParagraphHandler implements Handler{

	@Override
	public String handle(String text) {
		text = divAsP(text);
		text = trimP(text);
		text = addWhiteSpace(text);
		return text;
	}

	/**
	 * 将div标签的内容转换为一个段落
	 * @param text 文本
	 * @return 处理后的文本
	 */
	public static String divAsP(String text) {
		text = text.replaceAll("(?i)<div[^>]*?>", "\n<p>");
		text = text.replaceAll("(?i)</div>", "</p>");
		
		return text;
	}
	
	/**
	 * 去除段落结束标签周围的换行、空格符
	 * @param text 文本
	 * @return 处理后的文本
	 */
	public static String trimP(String text) {
		text = text.replaceAll(StrUtil.format("(?i){}</p>{}", Constant.RE_BLANK_BRS, Constant.RE_BLANK_BRS), "</p>");
		text = text.replaceAll(StrUtil.format("(?i){}<p[^>]*>{}", Constant.RE_BLANK_BRS, Constant.RE_BLANK_BRS), "\n<p>");
		
		return text;
	}
	
	/**
	 * 增加段落开头的空格符<br>
	 * 段落分为两种：
	 * 1、p标签包围的
	 * 2、br标签之后的
	 * @param text 文本
	 * @return 处理后的文本
	 */
	public static String addWhiteSpace(String text) {
		//在P内的首部加空格
		text = text.replaceAll(StrUtil.format("(?i)<p[^>]*>{}", Constant.RE_BLANK_BRS), "<p>" + Constant.NBSP_P);
		//在br标签的之后加空格，同时解决多个br问题
		text = text.replaceAll(StrUtil.format("(?i)<br[^>]*>{}", Constant.RE_BLANK_BRS), "<br/>\n" + Constant.NBSP_P);
		//在P之后的非标签文本理解为独立段落
		text = text.replaceAll(StrUtil.format("(?i)</p>(?!<p|<br|$|\\s)"), "</p>\n" + Constant.NBSP_P);
		return text;
	}
}
