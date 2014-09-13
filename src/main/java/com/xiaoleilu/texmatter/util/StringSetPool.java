package com.xiaoleilu.texmatter.util;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;

import com.xiaoleilu.hutool.GroupedSet;
import com.xiaoleilu.hutool.Log;
import com.xiaoleilu.hutool.StrUtil;

/**
 * 字符集合统一管理类
 * @author Looly
 *
 */
public class StringSetPool {
	private final static Logger log = Log.get();
	
	private GroupedSet set;
	
	public StringSetPool() {
		set = new GroupedSet("data/remove.set");
	}
	
	/**
	 * @return 获得HTML需要移除的属性集合
	 */
	public Set<String> getHtmlAttrRemoveSet() {
		HashSet<String> values = set.getValues("html_attr_remove");
		if(null == values) {
			values = new LinkedHashSet<String>();
		}
		log.info("Html attr for remove count: {}", values.size());
		return values;
	}
	
	/**
	 * @return 获得需要移除的HTML集合
	 */
	public Set<String> getHtmlRemoverSet() {
		HashSet<String> values = set.getValues("html_remove");
		if(null == values) {
			values = new LinkedHashSet<String>();
		}
		log.info("Html for remove count: {}", values.size());
		return values;
	}
	
	/**
	 * @return 获得需要只清除HTML标签的集合
	 */
	public Set<String> getHtmlUnwrapSet() {
		HashSet<String> values = set.getValues("html_unwrap");
		if(null == values) {
			values = new LinkedHashSet<String>();
		}
		log.info("Html for unwrap count: {}", values.size());
		return values;
	}
	
	/**
	 * @return 获得不需要转换为半角的字符集合
	 */
	public Set<Character> getDbcNotConvertSet() {
		final Set<Character> charSet = new HashSet<Character>();
		LinkedHashSet<String> values = set.getValues("dbc_not_convert");
		if(null != values) {
			for (String str : values) {
				if(StrUtil.isNotEmpty(str) && str.length() == 1) {
					charSet.add(str.charAt(0));
				}
			}
		}
		log.info("load not convert Character count: {}", charSet.size());
		return charSet;
	}
}
