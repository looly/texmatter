package com.xiaoleilu.texmatter.handler;

import java.util.ArrayList;

import com.xiaoleilu.hutool.CollectionUtil;
import com.xiaoleilu.hutool.ReUtil;
import com.xiaoleilu.hutool.StrUtil;
import com.xiaoleilu.texmatter.util.Constant;

/**
 * 特殊处理
 * @author Looly
 *
 */
public class SpecialHandler implements Handler{
	
	@Override
	public String handle(String text) {
		text = removeKeywordsA(text, "weibo", "weixin");
		
		text = trimVideoFlag(text);
		text = nbspAfterVideoFlag(text);
		
		text = centerImg(text);
		
		return text;
	}
	
	/**
	 * 去除带有特定关键字的a标签的属性
	 * @param text 文本
	 * @param keywords 关键字
	 * @return 处理后的文本
	 */
	public static String removeKeywordsA(String text, String... keywords) {
		final String keywordsRe = CollectionUtil.join(keywords, "|");
		final String regex = StrUtil.format("(?i)<a[^>]*href=[\"'].*?({}).*?[\"']\\s*>", keywordsRe);
		return text.replaceAll(regex, "<a>");
	}

	/**
	 * 去除视频标记左边的空格和右边的换行符和空格
	 * @param text 文本
	 * @return 处理后的文本
	 */
	public static String trimVideoFlag(String text) {
//		text = text.replaceAll(StrUtil.format("{}(?={})", Constant.RE_BLANK_BRS, Constant.RE_VIDEO_FLAG), "\n");
//		text = text.replaceAll(StrUtil.format("(?<={}){}", Constant.RE_VIDEO_FLAG, Constant.RE_BLANK_BRS), "\n");
		
		String regex = StrUtil.format("{}({}){}", Constant.RE_BLANK_BRS, Constant.RE_VIDEO_FLAG, Constant.RE_BLANK_BRS);
		text = ReUtil.replaceAll(text, regex, "\n$2\n");
		return text;
	}
	
	/**
	 * 视频标记后的正文加空格
	 * @param text 文本
	 * @return 处理后的文本
	 */
	public static String nbspAfterVideoFlag(String text) {
		final String regex = StrUtil.format("(?i)(?<={})\\s(?!</?p)", Constant.RE_VIDEO_FLAG);
		return text.replaceAll(regex, "\n" + Constant.NBSP_P);
	}
	
	/**
	 * 居中图片
	 * @param text 文本
	 * @return 处理后的文本
	 */
	public static String centerImg(String text) {
		ArrayList<String> imgEles = ReUtil.findAll("(?i)(<p[^>]*?>(\\s|&nbsp;)*)*((<img[^>]*?>[^<]*?</img>)|(<img[^<]*?/>))(</p>)*", text, 0, new ArrayList<String>());
		if(false == imgEles.isEmpty()) {
			for (String imgEle : imgEles) {
				final String img = ReUtil.get("(?i)(<img[^>]*?>[^<]*?</img>)|(<img[^<]*?/>)", imgEle, 0);
				text = text.replace(imgEle, "<p style=\"text-align:center;\">" + img + "</p>");
			}
		}
		return text;
	}
}
