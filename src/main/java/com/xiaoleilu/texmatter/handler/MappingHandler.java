package com.xiaoleilu.texmatter.handler;

import com.xiaoleilu.hutool.Singleton;
import com.xiaoleilu.texmatter.util.Mapping;

/**
 * 文本替换
 * @author Looly
 *
 */
public class MappingHandler implements Handler{
	
	Mapping mapping = Singleton.get(Mapping.class);

	@Override
	public String handle(String text) {
		return mapping.replace(text);
	}

}
