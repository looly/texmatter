package com.xiaoleilu.texmatter.util;

import java.util.HashMap;
import java.util.Map;

import com.xiaoleilu.hutool.CharsetUtil;
import com.xiaoleilu.hutool.StrUtil;

/**
 * 常量
 * @author Looly
 *
 */
public class Constant {
	
	public final static String CHARSET = CharsetUtil.UTF_8;
	
	/** 段落前的空格 */
	public final static String NBSP_P = StrUtil.repeat("&nbsp;", 8);
	
	/** 正则 空格符 */
	public final static String RE_BLANKS = "(\\s|&nbsp;)*";
	/** 正则 空格符和换行符 */
	public final static String RE_BLANK_BRS = "(\\s|&nbsp;|<br\\s*/?>)*";
	/** 正则 视频位置标记 */
	public final static String RE_VIDEO_FLAG = "\\[(视频\\d)\\]";
	
	/**
	 * 获得匹配指定标签的正则<br>
	 * 忽略大小写
	 * @param tagName HTML标签名称
	 */
	public static String tagRegex(String tagName) {
		final Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("tag", tagName);
		return StrUtil.format("(?i)(?s)<{tag}[^>]*>.*?</{tag}>|(<{tag}[^>]*/>", paramMap);
	}
}
