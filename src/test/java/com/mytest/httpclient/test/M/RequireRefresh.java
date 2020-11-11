package com.mytest.httpclient.test.M;

import com.alibaba.fastjson.JSONObject;
import com.mytest.httpclient.HttpClientUtil;
import com.mytest.httpclient.Util;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

public class RequireRefresh {

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
    //刷新
    public void dynamicInfo() throws Exception {
        // 对象转换成 Json 字符串
        url = "http://webserver-3.mbse.local:8001/requirement/token/refresh";

        HashMap<String, String> headermap = new HashMap<>();
        headermap.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGVuamgiLCJleHAiOjE2MDUxMjkwMjcsImlhdCI6MTYwNTA1NzAyN30.dg1HPPoOyJyVGbDXfVRxlsCtl9moTxv3gZPG04Lh5ZRRhKfccFI1IRmL0briL6mfoTOA4ZWEUg6acHJaOPR03w"); // 这个在 postman 中可以查询到

        JSONObject responseJson = hcu.sendGet(url,new HashMap<>(), headermap);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "status code is not 200");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String code = Util.getValueByJPath(responseJson, "code");
        //String content = Util.getValueByJPath(responseJson, "data[0]/content");
        Assert.assertEquals(code, "1000", "code is not same");
        //Assert.assertEquals(content, "登录成功", "content is not same");
    }



}
