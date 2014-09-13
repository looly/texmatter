package com.xiaoleilu.texmatter.util;

import java.util.Map.Entry;

import com.xiaoleilu.hutool.Setting;

/**
 * 字符替换映射
 * @author Looly
 *
 */
public class Mapping{
	
	public Setting mapping;
	
	public Mapping() {
		mapping = new Setting("data/mapping.setting");
	}
	
	/**
	 * 替换全部
	 * @param text 原文本
	 * @return 替换后的文本
	 */
	public String replace(String text) {
		for (Entry<String, String> entry : mapping.entrySet()) {
			text = text.replace(entry.getKey(), entry.getValue());
		}
		return text;
	}
}
