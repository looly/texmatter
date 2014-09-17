package com.xiaoleilu.texmatter.handler;

import com.xiaoleilu.hutool.ReUtil;
import com.xiaoleilu.hutool.StrUtil;
import com.xiaoleilu.texmatter.util.Constant;

/**
 * 空格符和换行符处理类
 * @author Looly
 *
 */
public class BlankHandler implements Handler {

	@Override
	public String handle(String text) {
		text = removeNbspBetweenChineseOrNumber(text);
		text = removeBlankLine(text);

		return text;
	}

	/**
	 * 去掉中文和数字附近的空格
	 * 
	 * @param text
	 * @return 处理后的文本
	 */
	public static String removeNbspBetweenChineseOrNumber(String text) {
		final String regex = StrUtil.format("(?<=({}|\\d)){}", ReUtil.RE_CHINESE, Constant.RE_BLANKS);
		return text.replaceAll(regex, "");
	}

	/**
	 * 去除空白行
	 * @param text 文本
	 */
	public static String removeBlankLine(String text) {
		final StringBuilder builder = new StringBuilder();
		builder.append("(?i)") // 区分大小写
				.append("<p[^>]*/>") // <p/>标签
				.append("|") // 或
				.append(StrUtil.format("{}<p[^>]*>{}</p>{}", Constant.RE_BLANKS, Constant.RE_BLANKS, Constant.RE_BLANKS));// <p></p>空行
		return text.replaceAll(builder.toString(), StrUtil.EMPTY);
	}
	
	/**
	 * 去除正文两边的空格和空行
	 * @param text 文本
	 * @return 处理后的文本
	 */
	public static String trimBlank(String text) {
		//(?s)(?i)表示点匹配任意字符、忽略大小写
		return ReUtil.get(StrUtil.format("(?s)(?i)^{}(.*?){}$", Constant.RE_BLANK_BRS, Constant.RE_BLANK_BRS), text, 2);
	}
}
