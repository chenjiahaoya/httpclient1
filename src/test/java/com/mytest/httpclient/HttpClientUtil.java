package com.mytest.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;
    private RequestConfig requestConfig;
    public String HTTPSTATUS = "HttpStatus";

    public HttpClientUtil() {
        requestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setConnectionRequestTimeout(1000)
                        .setSocketTimeout(10000)
                        .build();
    }

    /**
     * @param connectTimeout           设置连接超时时间，单位毫秒。
     * @param connectionRequestTimeout 设置从 connect Manager(连接池)获取 Connection
     *                                 超时时间，单位毫秒。这个属性是新加的属性，因为
     *                                 目前版本是可以共享连接池的。
     * @param socketTimeout            请求获取数据的超时时间(即响应时间)，单位毫秒。
     *                                 如果访问一个接口，多少时间内无法返回数据，就直
     *                                 接放弃此次调用。
     **/

    public HttpClientUtil(int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
        requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout).build();
    }

    public JSONObject sendGet(String url, HashMap<String, String> params, HashMap<String, String>
            headers)
            throws Exception {
        //创建一个httpClient的实例
        httpClient = HttpClients.createDefault();
        // 拼接 url
        if (params != null) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (!value.isEmpty()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs), "UTF-8");
        }
        //创建一个httpGet的请求实例
        HttpGet httpGet = new HttpGet(url);
        try {
            httpGet.setConfig(requestConfig);
            // 加载请求头到 httpGet 对象
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            //使用 httpClient 实例的 execute 方法发送刚创建的 get 请求，并用 httpResponse 将响应接收
            response = httpClient.execute(httpGet);
            // 获取返回参数
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                //将结果转为字符串
                result = EntityUtils.toString(entity, "UTF-8");
            }
            // 释放请求，关闭连接
            EntityUtils.consume(entity);
            // 创建 Json 对象，把上面字符串序列化成 Json 对象
            JSONObject jsonobj = JSON.parseObject(result);
            jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
            return jsonobj;
        } finally {
            httpClient.close();
            response.close();
        }
    }

    public JSONObject sendGet(String url, HashMap<String, String> params) throws Exception {
        return this.sendGet(url, params, null);
    }

    public JSONObject sendGet(String url) throws Exception {
        return this.sendGet(url, null, null);
    }


    //post请求
    //序列化为JONS字符串
    public JSONObject sendPostByJson(String url, Object object, HashMap<String, String> headers)
            throws Exception {
        httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setConfig(requestConfig);
            String json = JSON.toJSONString(object);
            StringEntity entity = new StringEntity(json, "utf-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            if (headers != null) {
                // 加载请求头到 httppost 对象
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String result = null;
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity, "UTF-8");
            }
            EntityUtils.consume(responseEntity);
            JSONObject jsonobj = JSON.parseObject(result);
            jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
            return jsonobj;
        } finally {
            httpClient.close();
            response.close();
        }
    }


    //表单post
    public JSONObject sendPostByForm(String url, Map<String, String> form, HashMap<String, String>
            headers)
            throws Exception {
        httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setConfig(requestConfig);
            // 设置请求主体格式
            if (form.size() > 0) {
                ArrayList<BasicNameValuePair> list = new ArrayList<>();
                form.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            if (headers != null) {
                // 设置头部信息
                Set<String> set = headers.keySet();
                for (Iterator<String> iterator = set.iterator(); iterator.hasNext(); ) {
                    String key = iterator.next();
                    String value = headers.get(key);
                    httpPost.setHeader(key, value);
                }
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            JSONObject jsonobj = JSON.parseObject(result);
            jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
            return jsonobj;
        } finally {
            httpClient.close();
            response.close();
        }
    }


    public JSONObject sendPostByForm(String url, Map<String, String> form) throws Exception {
        return sendPostByForm(url, form, null);
    }

    public JSONObject sendPostByJson(String url, Object object) throws Exception {
        return sendPostByJson(url, object, null);

    }

    //put请求
    public JSONObject sendPut(String url, String entityString, HashMap<String, String> headers)
            throws ClientProtocolException, IOException {
        httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        try {
            httpPut.setConfig(requestConfig);
            httpPut.setEntity(new StringEntity(entityString, "utf-8"));
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPut.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 发送 put 请求
            response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            JSONObject jsonobj = JSON.parseObject(result);
            jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
            return jsonobj;
        } finally {
            httpClient.close();
            response.close();
        }
    }


    //delete方法
    public int sendDelete(String url) throws ClientProtocolException, IOException {
        httpClient = HttpClients.createDefault();
        HttpDelete httpDel = new HttpDelete(url);
        try {
            httpDel.setConfig(requestConfig);
            // 发送 delete 请求
            response = httpClient.execute(httpDel);
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode;
        } finally {
            httpClient.close();
            response.close();
        }
    }


}
