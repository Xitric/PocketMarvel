package dk.sdu.pocketmarvel.repository.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dk.sdu.pocketmarvel.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class is used to intercept requests to the Marvel Comics API to inject an api key for
 * authorizing requests.
 */
public class ApiKeyInterceptor implements Interceptor {

    private static final String privateKey = BuildConfig.marvelPrivateKey;
    private static final String publicKey = BuildConfig.marvelPublicKey;

    @Override
    public Response intercept(Chain chain) throws IOException {
        long time = getTimeStamp();
        String hash = generateHash(time);

        HttpUrl url = chain.request().url().newBuilder()
                .addQueryParameter("ts", String.valueOf(time))
                .addQueryParameter("apikey", publicKey)
                .addQueryParameter("hash", hash).build();

        Request newRequest = chain.request().newBuilder().url(url).build();
        return chain.proceed(newRequest);
    }

    private String generateHash(long timeStamp) {
        String input = timeStamp + privateKey + publicKey;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            return toHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private long getTimeStamp() {
        return System.currentTimeMillis();
    }

    private String toHexString(byte[] bytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexBuilder.append(String.format("%02x", b & 0xff));
        }
        return hexBuilder.toString();
    }
}
