package com.mobivery.utils;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * HttpClient Factory class, creates HttpClient instances ignoring certificates
 * @author Service Generator
 */
public class HttpClientSSLValidationHelper {
    public final static String SYSTEM_TIMEOUT="Service_Timeout";
    private final static String DEFAULT_TIMEOUT="4000";
	private static HttpClientSSLValidationHelper instance=new HttpClientSSLValidationHelper();
	/**
	 * private constructor, prevents instantiation
	 */
	private HttpClientSSLValidationHelper()
	{
		
	}
	/**
	 * Returns singleton instance
	 * @returns HttpClientHelper singleton instance
	 */
	public static HttpClientSSLValidationHelper getInstance()
	{
		return instance;
	}
	/**
	 * Returns a new instance of HttpClient (auth ignore)
     */
	public DefaultHttpClient getNewHttpClient() {
	    try {
            // Create a SSLSocketFactory with NO additional keyStores (only the default one)
            SSLSocketFactory sf = new CustomSSLSocketFactoryForCertificateValidation(null);

            BasicHttpParams params = new BasicHttpParams();

	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            int timeout=Integer.parseInt(System.getProperty(SYSTEM_TIMEOUT,DEFAULT_TIMEOUT));
            HttpConnectionParams.setConnectionTimeout(params, timeout);
            HttpConnectionParams.setSoTimeout(params, timeout);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
}
