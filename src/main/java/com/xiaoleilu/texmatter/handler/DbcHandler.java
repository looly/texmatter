package com.xiaoleilu.texmatter.handler;

import java.util.Set;

import com.xiaoleilu.hutool.Conver;
import com.xiaoleilu.hutool.Singleton;
import com.xiaoleilu.texmatter.util.StringSetPool;

/**
 * 全角转半角处理类
 * @author Looly
 *
 */
public class DbcHandler implements Handler{
	
	private Set<Character> notConvert = ((StringSetPool)Singleton.get(StringSetPool.class)).getDbcNotConvertSet();

	@Override
	public String handle(String text) {
		return Conver.toDBC(text, notConvert);
	}
}
