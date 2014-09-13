package com.xiaoleilu.texmatter.handler;

import java.util.Set;

import com.xiaoleilu.hutool.HtmlUtil;
import com.xiaoleilu.hutool.Singleton;
import com.xiaoleilu.hutool.StrUtil;
import com.xiaoleilu.texmatter.util.Constant;
import com.xiaoleilu.texmatter.util.StringSetPool;

/**
 * HTML相关处理
 * @author Looly
 *
 */
public class HtmlHandler implements Handler{
	
	StringSetPool pool = Singleton.get(StringSetPool.class);
	
	private Set<String> unwrapHtmlSet = pool.getHtmlUnwrapSet();
	private Set<String> removeHtmlSet = pool.getHtmlRemoverSet();
	private Set<String> removeHtmlAttrSet = pool.getHtmlAttrRemoveSet();
	
	@Override
	public String handle(String text) {
		//去掉HTML标签以及内容
		text = removeHtmlTagAndBlank(text);
		//去掉包围的HTML标签
		text = HtmlUtil.unwrapHtmlTag(text, unwrapHtmlSet.toArray(new String[unwrapHtmlSet.size()]));
		//去掉标签的属性
		text = HtmlUtil.removeHtmlAttr(text, removeHtmlAttrSet.toArray(new String[removeHtmlAttrSet.size()]));
		return text;
	}

	/**
	 * 清除指定HTML标签以及周围的空格符<br>
	 * 不区分大小写
	 * @param text 文本
	 * @return 去除标签后的文本
	 */
	public String removeHtmlTagAndBlank(String text) {
		String regex1 = null;
		String regex2 = null;
		for (String tagName : removeHtmlSet) {
			if(StrUtil.isBlank(tagName)) {
				continue;
			}
			tagName = tagName.trim();
			//(?i)表示其后面的表达式忽略大小写
			regex1 = StrUtil.format("(?i){}<{}\\s?[^>]*?/>{}", Constant.RE_BLANK_BRS, tagName, Constant.RE_BLANK_BRS);
			//标签及其包含内容
			regex2 = StrUtil.format("(?i)(?s){}<{}\\s*?[^>]*?>.*?</{}>{}", Constant.RE_BLANK_BRS, tagName, tagName, Constant.RE_BLANK_BRS);

			text = text
					.replaceAll(regex1, StrUtil.EMPTY)									//自闭标签小写
					.replaceAll(regex2, StrUtil.EMPTY);									//非自闭标签小写
		}
		return text;
	}
}
