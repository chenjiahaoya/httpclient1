package com.mytest.httpclient.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.mytest.httpclient.HttpClientUtil;
import com.mytest.httpclient.User;
import com.mytest.httpclient.Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class RequestMethodTest {

    CloseableHttpClient client;
    CloseableHttpResponse closeableHttpResponse;
    HttpEntity responseBody;
    int responseCode;
    HttpClientUtil hcu;
    String url;
    @BeforeTest
    public void setUp() {
        hcu = new HttpClientUtil ();
    }
    @Test
    public void postByFormTest() throws Exception {
        url = "https://reqres.in/api/login";
        Map<String,String> params = new HashMap<String,String>();
        params.put("email", "eve.holt@reqres.in");
        params.put("password", "cityslicka");
        JSONObject responseJson = hcu.sendPostByForm(url, params);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "status code is not 201");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String token = Util.getValueByJPath(responseJson, "token");
        Assert.assertEquals(token, "QpwL5tke4Pnpja7X4", "token is not right");
    }
    @Test
    public void postByJsonTest() throws Exception {
        // 对象转换成 Json 字符串
        User user = new User("Anthony", "tester");
        url = "https://reqres.in/api/users";
        JSONObject responseJson = hcu.sendPostByJson(url, user);
        // 验证状态码是不是 201
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_CREATED, "status code is not 201");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String name = Util.getValueByJPath(responseJson, "name");
        String job = Util.getValueByJPath(responseJson, "job");
        Assert.assertEquals(name, "Anthony", "name is not same");
        Assert.assertEquals(job, "tester", "job is not same");
    }
    @Test
    public void putTest() throws ClientProtocolException, IOException {
        url = "https://reqres.in/api/users/2";
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-Type", "application/json"); // 这个在 postman 中可以查询到
        // 对象转换成 Json 字符串
        User user = new User("Anthony", "automation tester");
        String userJsonString = JSON.toJSONString(user);
        // System.out.println(userJsonString);
        JSONObject responseJson = hcu.sendPut(url, userJsonString, headermap);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "response status code is not 200");
        // 验证名称为 Anthony 的 jon 是不是 automation tester
        String jobString = Util.getValueByJPath(responseJson, "job");
        Assert.assertEquals(jobString, "automation tester", "job is not same");
    }
    @Test
    public void deleteApiTest() throws ClientProtocolException, IOException {
        url = "https://reqres.in/api/users/2";
        int statusCode = hcu.sendDelete(url);
        Assert.assertEquals(statusCode, HttpStatus.SC_NO_CONTENT, "status code is not 204");
    }

}
