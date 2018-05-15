package com.cjlu.crm.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Http请求工具类
 *
 * @author minghui.y
 * @create 2018-05-15 15:44
 **/
public class HttpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**从连接池中获取连接的超时时间设置*/
    private static final int CONNECTION_REQUEST_TIMEOUT = 2000;
    /**与服务器简历链接的超时时间设置*/
    private static final int CONNECT_TIMEOUT = 8000;
    /**接收服务器响应数据的超时时间设置*/
    private static final int SOCKET_TIMEOUT = 15000;


    private static CloseableHttpClient httpClient;

    static {
        try {

            // 指定信任密钥存储对象
            SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType()), new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http",
                    PlainConnectionSocketFactory.INSTANCE).register("https",
                    // 重点是设置NoopHostnameVerifier.INSTANCE,解决异常：javax.net.ssl.SSLPeerUnverifiedException
                    new SSLConnectionSocketFactory(sslContext, new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"}, null, new NoopHostnameVerifier())).build();

            // 设置默认Socket Config : 包括可以指定默认的超时时间等等
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
            // Create connection configuration : 包括可以指定默认的通信字符集等等
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();


            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setDefaultConnectionConfig(connectionConfig);
            cm.setDefaultSocketConfig(socketConfig);
            cm.setMaxTotal(400);
            cm.setDefaultMaxPerRoute(50);

            httpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .evictExpiredConnections()
                    .evictIdleConnections(5L, TimeUnit.SECONDS)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            LOGGER.warn(">> 初始化HttpClient配置失败 : ", e);
        }
    }


    public static String doGet(String url, List<NameValuePair> queryParams, List<Header> headers) throws Exception{
        HttpGet method = new HttpGet();
        HttpResponse response = null;
        builderRequestMethodURI(method, url, queryParams);
        builderRequestMethodHeader(method, headers);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        method.setConfig(requestConfig);
        try {
            response = httpClient.execute(method);
        } catch (IOException var7) {
            throw new Exception("HttpClientUtil#doGet - 网络请求错误 : ", var7);
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("HttpClientUtil#doGet - 请求返回状态码异常 : " + response.getStatusLine().getStatusCode());
        } else {
            return consumeResponse(response);
        }
    }

    public static String doPost(String url, Map<String, String> formParams, Charset paramCharset) throws Exception {
        HttpPost method = new HttpPost();
        HttpResponse response = null;
        builderRequestMethodURI(method, url, null);
        builderRequestMethodHeader(method, null);
        builderRequestMethodEntityData(method, paramsConverter(formParams), paramCharset);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        method.setConfig(requestConfig);

        try {
            response = httpClient.execute(method);
        } catch (IOException var7) {
            throw new Exception("HttpClientUtil#doPost - 网络请求错误 : ", var7);
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("HttpClientUtil#doPost - 请求返回状态码异常 : " + response.getStatusLine().getStatusCode());
        } else {
            return consumeResponse(response);
        }
    }

    public static String doPost(String url, List<NameValuePair> queryParams, List<NameValuePair> formParams,
                                List<Header> headers) throws Exception {
        HttpResponse response = _doPost(url, queryParams, formParams, headers, CONNECT_TIMEOUT, CONNECTION_REQUEST_TIMEOUT, SOCKET_TIMEOUT);

        return consumeResponse(response);
    }

    public static String doPost(String url, List<NameValuePair> queryParams, String entityText, List<Header> headers) throws Exception {
        HttpResponse response =  _doPost(url, queryParams, entityText, headers, CONNECT_TIMEOUT, CONNECTION_REQUEST_TIMEOUT, SOCKET_TIMEOUT);

        return consumeResponse(response);
    }

    public static String doPost(String url, Map<String, String> formParams) throws Exception {
        return doPost(url, formParams, CONNECT_TIMEOUT, CONNECTION_REQUEST_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String doPost(String url, Map<String, String> formParams, int connectTimeout, int connectionRequestTimeout, int socketTimeout) throws Exception {
        HttpResponse response = doPost(url, null, paramsConverter(formParams), null, connectTimeout, connectionRequestTimeout, socketTimeout);

        return consumeResponse(response);
    }

    private static String consumeResponse(HttpResponse response) throws Exception {
        // 返回状态不是200
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                LOGGER.error(">> 释放响应发生异常", e);
            }
            throw new Exception(">> 请求返回状态码异常 : " + response.getStatusLine().getStatusCode());
        }

        try {
            return getResponseAsString(response.getEntity().getContent());
        } catch (IOException e) {
            throw new Exception(">> 读取响应内容异常", e);
        } finally {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                LOGGER.error(">> 释放响应发生异常", e);
            }
        }
    }

    /**
     * 基础POST请求，返回响应对象HttpResponse
     *
     * @param url         请求url
     * @param queryParams 查询参数
     * @param formParams  提交的表单参数
     * @param headers     自定义的请求头
     * @return 响应结果实例
     * @throws Exception
     */
    public static HttpResponse doPost(String url, List<NameValuePair> queryParams, List<NameValuePair> formParams,
                                      List<Header> headers, int connectTimeout, int connectionRequestTimeout,
                                      int socketTimeout) throws Exception {
        return _doPost(url, queryParams, formParams, headers, connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    private static HttpResponse _doPost(String url, List<NameValuePair> queryParams, Object formParams,
                                        List<Header> headers, int connectTimeout, int connectionRequestTimeout,
                                        int socketTimeout) throws Exception {
        HttpPost method = new HttpPost();
        builderRequestMethodURI(method, url, queryParams);
        builderRequestMethodHeader(method, headers);
        builderRequestMethodEntityData(method, formParams, null);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
        method.setConfig(requestConfig);
        try {
            return httpClient.execute(method);
        } catch (IOException e) {
            method.abort();
            throw new Exception(">> 网络请求错误 : ", e);
        }
    }

    private static void builderRequestMethodURI(HttpRequestBase method, String url,
                                                List<NameValuePair> queryParams) throws Exception {
        // URI 构建
        try {
            URIBuilder builder = new URIBuilder(url);
            // 填入查询参数
            if (queryParams != null && !queryParams.isEmpty()) {
                builder.addParameters(queryParams);
            }
            method.setURI(builder.build());
        } catch (URISyntaxException e) {
            throw new Exception(">> 构建请求地址及参数错误 : ", e);
        }
    }

    private static void builderRequestMethodHeader(HttpRequestBase method, List<Header> headers) {
        // 设置请求头信息
        if (headers != null && !headers.isEmpty()) {
            for (Header header : headers) {
                method.addHeader(header);
            }
        }
    }

    /**
     * 设置POST请求的Entity数据.
     * <p/>
     * TODO 字符集暂时写死为UTF8,后续再看有没有需要改动
     *
     * @param method     Request Post Method 实例
     * @param formParams Post请求的Entity参数
     */
    private static void builderRequestMethodEntityData(HttpPost method, Object formParams, Charset charset) throws Exception {
        if (formParams == null) {
            return;
        }
        // 分支支持: List
        if (formParams instanceof List) {
            if (!((List) formParams).isEmpty()) {
                method.setEntity(new UrlEncodedFormEntity((List<NameValuePair>) formParams,
                        charset == null ? Charsets.UTF_8 : charset));
            }
        }
        // String Entity
        else if (formParams instanceof String) {
            if (StringUtils.isNotEmpty(formParams.toString())) {
                method.setEntity(new StringEntity(formParams.toString(), charset == null ? Charsets.UTF_8 : charset));
            }
        }
        // 不支持的类型
        else {
            throw new Exception(">> 不支持的HttpPostEntity参数 : " + JSON.toJSONString(formParams));
        }
    }

    /**
     * 将Map类型的数据转换为{@link List<NameValuePair>}类型的数据
     *
     * @param params 参数集合
     * @return {@link List<NameValuePair>}
     */
    private static List<NameValuePair> paramsConverter(Map<String, String> params) {
        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Map.Entry<String, String>> paramsSet = params.entrySet();
        for (Map.Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        return nvps;
    }

    public static String getResponseAsString(InputStream es) throws IOException {
        String charset = "UTF-8";
        if (es != null) {
            return getStreamAsString(es, charset);
        } else {
            throw new IOException();
        }
    }

    public static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();
            char[] chars = new char[256];
            boolean count = false;

            int count1;
            while ((count1 = reader.read(chars)) > 0) {
                writer.write(chars, 0, count1);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }

        }
    }

}
