package com.xiaoleilu.texmatter.server.action;

import com.xiaoleilu.texmatter.server.Request;
import com.xiaoleilu.texmatter.server.Response;

public interface Action {
	public void doAction(Request request, Response response);
}
