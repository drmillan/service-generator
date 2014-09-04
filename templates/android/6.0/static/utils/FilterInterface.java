package {{{staticPackage}}}.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

/**
* Interface for the custom filters executed as hooks
* @author Service Generator
*
* Generated Class - DO NOT MODIFY
*/
public interface FilterInterface
{
	String preInjectURLParameters(String logic,String method,String url,Object request);
	String postInjectURLParameters(String logic,String method,String url,Object request);
	Object cacheHit(String logic, String method, Object request, Object response);
	void preExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, CookieStore cookieStore, HttpContext context);
	void postExecute(String logic,String method,DefaultHttpClient client, HttpRequestBase request, HttpResponse response, CookieStore cookieStore);
	String preprocessResponse(String logic,String method,String responseString);
	DefaultHttpClient preprocessHttpClient(String logic,String method,DefaultHttpClient httpClient);
	JSONObject preProcessJSON(String logic,String method,JSONObject jsonObject);
}
