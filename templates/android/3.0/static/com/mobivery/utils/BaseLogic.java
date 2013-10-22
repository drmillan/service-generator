/**
  BaseLogic

  Created by Generator on 19/01/12.
  Copyright (c) 2012 Mobivery. All rights reserved.
  Version: ${version}
*/
package com.mobivery.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;


public class BaseLogic implements FilterInterface
{
    List<FilterInterface> filters=new ArrayList<FilterInterface>();
	public String preInjectURLParameters(String url,Object request)
	{
	    String returnURL=url;
        for(FilterInterface filter:filters)
        {
            returnURL=filter.preInjectURLParameters(returnURL,request);
        }
        return returnURL;
	}
	public String postInjectURLParameters(String url,Object request)
	{
	    String returnURL=url;
        for(FilterInterface filter:filters)
        {
            returnURL=filter.postInjectURLParameters(returnURL,request);
        }
        return returnURL;
	}
	public void preExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, CookieStore cookieStore, HttpContext context)
	{
        for(FilterInterface filter:filters)
        {
            filter.preExecute(logic,method,client,request,cookieStore,context);
        }
	}
	public void postExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, HttpResponse response, CookieStore cookieStore)
	{
        for(FilterInterface filter:filters)
        {
            filter.postExecute(logic,method,client,request,response,cookieStore);
        }
	}
	public String preprocessResponse(String responseString)
	{
	    String resultString=responseString;
        for(FilterInterface filter:filters)
        {
            resultString=filter.preprocessResponse(resultString);
        }
        return resultString;
	}
	public JSONObject preProcessJSON(JSONObject jsonObject)
	{
	    JSONObject returnObject=jsonObject;
        for(FilterInterface filter:filters)
        {
            returnObject=filter.preProcessJSON(returnObject);
        }
        return returnObject;
	}
	public void addFilter(FilterInterface filterInterface)
	{
        filters.add(filterInterface);
	}
    public void removeFilter(FilterInterface filterInterface)
    {
         filters.remove(filterInterface);
    }
}
