package com.xiaoleilu.texmatter;

import java.io.IOException;

import com.xiaoleilu.hutool.FileUtil;
import com.xiaoleilu.hutool.Singleton;
import com.xiaoleilu.hutool.URLUtil;
import com.xiaoleilu.texmatter.service.TextFormater;
import com.xiaoleilu.texmatter.util.Constant;


public class FormatTest {
	
	public static void main(String[] args) throws IOException {
		TextFormater formater = Singleton.get(TextFormater.class);
		
		String text = FileUtil.readString(URLUtil.getURL("data/example_text.txt"), Constant.CHARSET);
		
		System.out.println("--------------------------------------------");
		System.out.println(text);
		System.out.println("--------------------------------------------");
		text = formater.format(text);
		System.out.println(text);
		System.out.println("--------------------------------------------");
	}
}
