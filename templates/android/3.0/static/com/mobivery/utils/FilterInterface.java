/**
  FilterInterface

  Created by Generator on 19/01/12.
  Copyright (c) 2012 Mobivery. All rights reserved.
  Version: ${version}
*/
package com.mobivery.utils.logic;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

public interface FilterInterface;
{
	public String preInjectURLParameters(String url,Object request);
	public String postInjectURLParameters(String url,Object request);
	public void preExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, CookieStore cookieStore, HttpContext context);
	public void postExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, HttpResponse response, CookieStore cookieStore);
	public String preprocessResponse(String responseString);
	public JSONObject preProcessJSON(JSONObject jsonObject);
}
