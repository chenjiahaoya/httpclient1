package com.mytest.httpclient.test.M;


import com.alibaba.fastjson.JSONObject;
import com.mytest.httpclient.HttpClientUtil;
import com.mytest.httpclient.User;
import com.mytest.httpclient.Util;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

public class RequireWorkTable {

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
    //工作台-最新动态
    public void dynamicInfo() throws Exception {
        // 对象转换成 Json 字符串
        url = "http://webserver-3.mbse.local:8001/requirement/workTable/dynamicInfo";

        HashMap<String, String> headermap = new HashMap<>();
        headermap.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGVuamgiLCJleHAiOjE2MDUxMjkwMjcsImlhdCI6MTYwNTA1NzAyN30.dg1HPPoOyJyVGbDXfVRxlsCtl9moTxv3gZPG04Lh5ZRRhKfccFI1IRmL0briL6mfoTOA4ZWEUg6acHJaOPR03w"); // 这个在 postman 中可以查询到

        JSONObject responseJson = hcu.sendGet(url,new HashMap<>(), headermap);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "status code is not 200");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String code = Util.getValueByJPath(responseJson, "code");
        String content = Util.getValueByJPath(responseJson, "data[0]/content");
        Assert.assertEquals(code, "1000", "code is not same");
        Assert.assertEquals(content, "登录成功", "content is not same");
    }


    @Test
    //工作台-评审概览
    public void getAssessSum() throws Exception {
        // 对象转换成 Json 字符串
        url = "http://webserver-3.mbse.local:8001/requirement/workTable/getAssessSum";

        HashMap<String, String> headermap = new HashMap<>();
        headermap.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGVuamgiLCJleHAiOjE2MDUxMjkwMjcsImlhdCI6MTYwNTA1NzAyN30.dg1HPPoOyJyVGbDXfVRxlsCtl9moTxv3gZPG04Lh5ZRRhKfccFI1IRmL0briL6mfoTOA4ZWEUg6acHJaOPR03w"); // 这个在 postman 中可以查询到

        JSONObject responseJson = hcu.sendGet(url,new HashMap<>(), headermap);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "status code is not 200");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String code = Util.getValueByJPath(responseJson, "code");
        String itemCount = Util.getValueByJPath(responseJson, "data/itemCount");
        String passItemCount = Util.getValueByJPath(responseJson, "data/passItemCount");
        String notPassItemCount = Util.getValueByJPath(responseJson, "data/notPassItemCount");
        Assert.assertEquals(code, "1000", "code is not same");
        Assert.assertEquals(itemCount, "29354", "itemCount is not same");
        Assert.assertEquals(passItemCount, "2242", "itemCount is not same");
        Assert.assertEquals(notPassItemCount, "25", "itemCount is not same");
    }

    @Test
    //工作台-最近访问
    public void getLastRecent() throws Exception {
        // 对象转换成 Json 字符串
        url = "http://webserver-3.mbse.local:8001/requirement/workTable/getLastRecent";

        HashMap<String, String> headermap = new HashMap<>();
        headermap.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGVuamgiLCJleHAiOjE2MDUxMjkwMjcsImlhdCI6MTYwNTA1NzAyN30.dg1HPPoOyJyVGbDXfVRxlsCtl9moTxv3gZPG04Lh5ZRRhKfccFI1IRmL0briL6mfoTOA4ZWEUg6acHJaOPR03w"); // 这个在 postman 中可以查询到

        JSONObject responseJson = hcu.sendGet(url,new HashMap<>(), headermap);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "status code is not 200");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String code = Util.getValueByJPath(responseJson, "code");
        String projectId1 = Util.getValueByJPath(responseJson, "data[0]/projectId");
        String projectId2 = Util.getValueByJPath(responseJson, "data[1]/projectId");
        String projectId3 = Util.getValueByJPath(responseJson, "data[2]/projectId");
        Assert.assertEquals(code, "1000", "code is not same");
        Assert.assertEquals(projectId1, "274", "projectId1 is not same");
        Assert.assertEquals(projectId2, "274", "projectId2 is not same");
        Assert.assertEquals(projectId3, "274", "projectId3 is not same");
    }

    @Test
    //工作台-需求概览
    public void getRequirementSum() throws Exception {
        // 对象转换成 Json 字符串
        url = "http://webserver-3.mbse.local:8001/requirement/workTable/getRequirementSum";

        HashMap<String, String> headermap = new HashMap<>();
        headermap.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGVuamgiLCJleHAiOjE2MDUxMjkwMjcsImlhdCI6MTYwNTA1NzAyN30.dg1HPPoOyJyVGbDXfVRxlsCtl9moTxv3gZPG04Lh5ZRRhKfccFI1IRmL0briL6mfoTOA4ZWEUg6acHJaOPR03w"); // 这个在 postman 中可以查询到

        JSONObject responseJson = hcu.sendGet(url,new HashMap<>(), headermap);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "status code is not 200");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String code = Util.getValueByJPath(responseJson, "code");
        String itemCount = Util.getValueByJPath(responseJson, "data/itemCount");
        String verifiedItemCount = Util.getValueByJPath(responseJson, "data/verifiedItemCount");
        String satisfiedItemCount = Util.getValueByJPath(responseJson, "data/satisfiedItemCount");
        String traceItemCount = Util.getValueByJPath(responseJson, "data/traceItemCount");
        String refineItemCount = Util.getValueByJPath(responseJson, "data/refineItemCount");
        String copyItemCount = Util.getValueByJPath(responseJson, "data/copyItemCount");
        String deriveItemCount = Util.getValueByJPath(responseJson, "data/deriveItemCount");


        Assert.assertEquals(code, "1000", "code is not same");
        Assert.assertEquals(itemCount, "29354", "itemCount is not same");
        Assert.assertEquals(verifiedItemCount, "7", "verifiedItemCount is not same");
        Assert.assertEquals(satisfiedItemCount, "4", "satisfiedItemCount is not same");
        Assert.assertEquals(traceItemCount, "348", "traceItemCount is not same");
        Assert.assertEquals(refineItemCount, "117", "refineItemCount is not same");
        Assert.assertEquals(copyItemCount, "47", "copyItemCount is not same");
        Assert.assertEquals(deriveItemCount, "76", "deriveItemCount is not same");
    }


    @Test
    //工作台-工作台首页
    public void getWorkInfo() throws Exception {
        // 对象转换成 Json 字符串
        url = "http://webserver-3.mbse.local:8001/requirement/workTable/getWorkInfo";

        HashMap<String, String> headermap = new HashMap<>();
        headermap.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGVuamgiLCJleHAiOjE2MDUxMjkwMjcsImlhdCI6MTYwNTA1NzAyN30.dg1HPPoOyJyVGbDXfVRxlsCtl9moTxv3gZPG04Lh5ZRRhKfccFI1IRmL0briL6mfoTOA4ZWEUg6acHJaOPR03w"); // 这个在 postman 中可以查询到

        JSONObject responseJson = hcu.sendGet(url,new HashMap<>(), headermap);
        // 验证状态码是不是 200
        int statusCode = responseJson.getInteger(hcu.HTTPSTATUS);
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "status code is not 200");
        // 断言响应 json 内容中 name 和 job 是不是期待结果
        // System.out.println(responseString);
        String code = Util.getValueByJPath(responseJson, "code");
        String projectCount = Util.getValueByJPath(responseJson, "data/projectCount");
        String myProjectCount = Util.getValueByJPath(responseJson, "data/myProjectCount");
        String documentCount = Util.getValueByJPath(responseJson, "data/documentCount");
        String myDocumentCount = Util.getValueByJPath(responseJson, "data/myDocumentCount");
        String itemCount = Util.getValueByJPath(responseJson, "data/itemCount");


        Assert.assertEquals(code, "1000", "code is not same");
        Assert.assertEquals(projectCount, "77", "projectCount is not same");
        Assert.assertEquals(myProjectCount, "72", "myProjectCount is not same");
        Assert.assertEquals(documentCount, "423", "documentCount is not same");
        Assert.assertEquals(myDocumentCount, "401", "myDocumentCount is not same");
        Assert.assertEquals(itemCount, "29354", "itemCount is not same");
    }




}
