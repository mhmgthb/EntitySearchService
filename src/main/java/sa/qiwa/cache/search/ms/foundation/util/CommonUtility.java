package sa.qiwa.cache.search.ms.foundation.util;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

@Slf4j
public class CommonUtility {

    public static SSLContext sslContext(String keystoreFile, String password)//{
            throws GeneralSecurityException, IOException {
        log.info("initializing SSLContext is started.");
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream in = new FileInputStream(keystoreFile)) {
            keystore.load(in, password.toCharArray());
        }
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, password.toCharArray());

        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
                keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(),
                new SecureRandom());

        log.info(" SSLContext is initialized.");
        return sslContext;
        /*}catch(SecurityException sex){
            log.error(sex.getMessage(),sex);
            //throw new EntitySearchException("An error occurred while reading certificate from jks file.",Error.UNKNOWN);
        }catch (IOException ioex){
            log.error(ioex.getMessage(),ioex);
           // throw new EntitySearchException("An error occurred while reading certificate from jks file.",Error.UNKNOWN);
        }*/
    }
}
