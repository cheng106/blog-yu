package idv.cheng.utils;

import idv.cheng.utils.entity.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class HttpClientUtil {

    public static HttpResponse doRequest(HttpRequestBase httpRequest) {
        // httpGet and httpPost are child from httpRequestBase
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response;
        HttpResponse httpResponse = null;

        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        httpRequest.setConfig(defaultConfig);

        try {
            response = httpclient.execute(httpRequest);
            httpResponse = new HttpResponse(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("error occurs");
        }

        return httpResponse;
    }

    private HttpPost lineLoginBase(String URL) {
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;");

        return httpPost;
    }

    private static HttpPost airTableBase(String URL, String api_Key) {
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.setHeader("Authorization", "Bearer " + api_Key);

        return httpPost;
    }

    private static HttpPost airTableBase(String URL) {
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");

        return httpPost;
    }


    public HttpPost getLineUserDetail(String URL, String clientId, String idToken) {
        HttpPost httpPost = lineLoginBase(URL);

        try {
            URI uri = new URIBuilder(httpPost.getURI())
                    .addParameter("client_id", clientId)
                    .addParameter("id_token", idToken).build();
            httpPost.setURI(uri);
        } catch (Exception e) {
            log.error("error occurs while creating uri");
        }

        return httpPost;
    }

    public HttpGet getUserProfile(String URL, String accessToken, String userId) {
        URL = String.format(URL, userId);
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization", "Bearer " + accessToken);

        return httpGet;
    }

    public HttpGet getLineUser(String URL, String accessToken) {
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization", "Bearer " + accessToken);

        return httpGet;
    }


    public static HttpPost airTableCreate(String airTableURL, String airTableAPIKey, String fields) {

        HttpPost httpPost = airTableBase(airTableURL, airTableAPIKey);
        httpPost.setEntity(new StringEntity(fields, StandardCharsets.UTF_8));

        return httpPost;
    }

    public static HttpPost airTableCreate(String airTableURL, String fields) {

        HttpPost httpPost = airTableBase(airTableURL);
        httpPost.setEntity(new StringEntity(fields, StandardCharsets.UTF_8));

        return httpPost;
    }
}
