//package com.mytest.httpclient.test;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.util.*;
//
//public class testPost {
//
//    private CloseableHttpClient httpClient;
//    private CloseableHttpResponse response;
//    private RequestConfig requestConfig;
//    public String HTTPSTATUS = "HttpStatus";
//
//
//
//
//    public JSONObject sendPostByJson(String url, Object object, HashMap<String, String> headers)
//            throws Exception {
//        httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            httpPost.setConfig(requestConfig);
//            String json = JSON.toJSONString(object);
//            StringEntity entity = new StringEntity(json, "utf-8");
//            entity.setContentType("application/json");
//            httpPost.setEntity(entity);
//            if (headers != null) {
//                // 加载请求头到 httppost 对象
//                for (Map.Entry<String, String> entry : headers.entrySet()) {
//                    httpPost.setHeader(entry.getKey(), entry.getValue());
//                } }
//            response = httpClient.execute(httpPost);
//            HttpEntity responseEntity = response.getEntity();
//            String result = null;
//            if (responseEntity != null) {
//                result = EntityUtils.toString(responseEntity, "UTF-8");
//            }
//            EntityUtils.consume(responseEntity);
//            JSONObject jsonobj = JSON.parseObject(result);
//            jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
//            return jsonobj;
//        } finally {
//            httpClient.close();
//            response.close();
//        }
//    }
//
//    public JSONObject sendPostByForm(String url, Map<String, String> form, HashMap<String, String>
//            headers)
//            throws Exception {
//        httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            httpPost.setConfig(requestConfig);
//            // 设置请求主体格式
//            if (form.size() > 0) {
//                ArrayList<BasicNameValuePair> list = new ArrayList<>();
//                form.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
//                entity.setContentType("application/x-www-form-urlencoded");
//                httpPost.setEntity(entity);
//            }
//            if (headers != null) {
//                // 设置头部信息
//                Set<String> set = headers.keySet();
//                for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
//                    String key = iterator.next();
//                    String value = headers.get(key);
//                    httpPost.setHeader(key, value);
//                }
//            }
//            response = httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            String result = null;
//            if (entity != null) {
//                result = EntityUtils.toString(entity, "utf-8");
//            }
//            EntityUtils.consume(entity);
//            JSONObject jsonobj = JSON.parseObject(result);
//            jsonobj.put(HTTPSTATUS, response.getStatusLine().getStatusCode());
//            return jsonobj;
//        } finally {
//            httpClient.close();
//            response.close();
//        }
//    }
//}
//
//
//
//
//
//
