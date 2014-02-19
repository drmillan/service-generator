/**
  FilterInterface

  Created by Generator on 19/01/12.
  Copyright (c) 2012-2014 Mobivery. All rights reserved.
  Version: ${version}
*/
package com.mobivery.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

public interface FilterInterface
{
	String preInjectURLParameters(String logic,String method,String url,Object request);
	String postInjectURLParameters(String logic,String method,String url,Object request);
	void preExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, CookieStore cookieStore, HttpContext context);
	void postExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, HttpResponse response, CookieStore cookieStore);
	String preprocessResponse(String logic,String method,String responseString);
	JSONObject preProcessJSON(String logic,String method,JSONObject jsonObject);
}
