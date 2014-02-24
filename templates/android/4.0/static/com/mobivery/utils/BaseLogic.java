/**
* BaseLogic
* @author Service Generator
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
    public String preInjectURLParameters(String logic,String method,String url,Object request)
    {
        String returnURL=url;
        for(FilterInterface filter:filters)
        {
            returnURL=filter.preInjectURLParameters(logic,method,returnURL,request);
        }
        return returnURL;
    }
    public String postInjectURLParameters(String logic,String method,String url,Object request)
    {
        String returnURL=url;
        for(FilterInterface filter:filters)
        {
            returnURL=filter.postInjectURLParameters(logic,method,returnURL,request);
        }
        return returnURL;
    }

    @Override
    public void cacheHit(String logic, String method, Object request, Object response) {

        for(FilterInterface filter:filters)
        {
            filter.cacheHit(logic,method,request,response);
        }
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
    public String preprocessResponse(String logic,String method,String responseString)
    {
        String resultString=responseString;
        for(FilterInterface filter:filters)
        {
            resultString=filter.preprocessResponse(logic,method,resultString);
        }
        return resultString;
    }
    public JSONObject preProcessJSON(String logic,String method,JSONObject jsonObject)
    {
        JSONObject returnObject=jsonObject;
        for(FilterInterface filter:filters)
        {
            returnObject=filter.preProcessJSON(logic,method,returnObject);
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
