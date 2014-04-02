package {{{staticPackage}}}.utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Factory for SSL global auth accept
 * @author Service Generator
 */
public class CustomSSLSocketFactoryForCertificateValidation extends SSLSocketFactory {
    SSLContext sslContext = SSLContext.getInstance("TLS");

    /**
     * Allows you to trust certificates from additional KeyStores in addition to
     * the default KeyStore
     * 'additional KeyStores' means certs locally stored. The method to load them is not implemented here.
     * http://stackoverflow.com/a/6378872
     */
    public CustomSSLSocketFactoryForCertificateValidation(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(keyStore);

        sslContext.init(null, new TrustManager[]{new CustomTrustManager(keyStore)}, null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}
