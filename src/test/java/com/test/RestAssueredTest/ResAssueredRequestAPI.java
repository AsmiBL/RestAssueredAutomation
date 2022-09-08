package com.test.RestAssueredTest;

import RestAssueredAutomation.XLUtils;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class ResAssueredRequestAPI {
    @Test(description = "TC1_GETRequest",priority = 0,enabled = false)
    void getUserDetails()
    {
        //Specify base URI
        RestAssured.baseURI="https://reqres.in/api/users";

        //Request object
        RequestSpecification httpsRequest=RestAssured.given();

        //Response object
        Response response=httpsRequest.request(Method.GET,"/2");

        String resposneBody=response.getBody().asString();

        System.out.println("Respone Body is:\n"+resposneBody);
        System.out.println("Status is:\n"+response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
        System.out.println("Status line:\n"+response.getStatusLine());
        Assert.assertEquals(response.getStatusLine(),"HTTP/1.1 200 OK");

    }

    @Test(description = "TC2_POSTRequest",priority = 1,enabled = false)
    void createUser()
    {
        //Specifying BASEURI
        RestAssured.baseURI="https://reqres.in/api/users";

        //Request
        RequestSpecification httpRequest=RestAssured.given();

        JSONObject requestparams=new JSONObject();
        requestparams.put("name","pragatiMM");
        requestparams.put("job","automation");

        httpRequest.header("Content-Type","application/json");
        httpRequest.body(requestparams.toJSONString());

        //Response
        Response response=httpRequest.request(Method.POST);

        System.out.println("Respone Body is:\n"+response.getBody().asString());
        System.out.println("Status is:\n"+response.statusCode());
        System.out.println("Id  is:\n"+response.jsonPath().get("id").toString());
        System.out.println("Timing  is:\n"+response.jsonPath().get("createdAt").toString());

        //Validating Header
        Assert.assertEquals(response.header("Content-Type").toString(),"application/json; charset=utf-8");

        //Capturing all headers
        Headers allHeaders=response.headers();

        //Printing Header
        for(Header header:allHeaders)
        {
            System.out.println(header.getName()+"    "+header.getValue());
        }

    }
    @Test(description = "TC3_ResponseValidation",priority = 2,enabled = false)
    void responseValidation()
    {
        //Specifying BASEURI
        RestAssured.baseURI="https://reqres.in/api/users";

        //Request
        RequestSpecification httpRequest=RestAssured.given();

        //Response
        Response response=httpRequest.request(Method.GET,"/2");

        System.out.println("Respone Body is:\n"+response.getBody().asString());
        System.out.println("Status is:\n"+response.statusCode());

        //Printing response body each node value
        JsonPath jsonPath=response.jsonPath();
        System.out.println("Id  is:  "+jsonPath.get("data.id"));
        System.out.println("email  is:  "+jsonPath.get("data.email").toString());
        System.out.println("first_name  is:  "+jsonPath.get("data.first_name").toString());
    }

    @Test(description = "TC4_AuthenticationValidation",priority = 3,enabled = false)
    void authenticationValidation()
    {
        //Specifying BASEURI
        RestAssured.baseURI="https://reqres.in/api/login";

        //Basic Authentication Object
        PreemptiveBasicAuthScheme AuthScheme=new PreemptiveBasicAuthScheme();
        AuthScheme.setUserName("eve.holt@reqres.in");
        AuthScheme.setPassword("cityslicka");

        RestAssured.authentication=AuthScheme;

        //Request
        RequestSpecification httpRequest=RestAssured.given();

        //Response
        Response response=httpRequest.request(Method.GET,"/");

        System.out.println("Respone Body is:\n"+response.getBody().asString());
        System.out.println("Status is:\n"+response.statusCode());
    }
    @Test(description = "TC5_POSTRequestwithstaticdata",priority = 4,enabled = false,dataProvider = "userdatastatic")
    void createMultiplUserwithStaticData(String name,String role)
    {
        //Specifying BASEURI
        RestAssured.baseURI="https://reqres.in/api/users";

        //Request
        RequestSpecification httpRequest=RestAssured.given();

        JSONObject requestparams=new JSONObject();
        requestparams.put("name",name);
        requestparams.put("job",role);

        httpRequest.header("Content-Type","application/json");
        httpRequest.body(requestparams.toJSONString());

        //Response
        Response response=httpRequest.request(Method.POST);

        System.out.println("Respone Body is:\n"+response.getBody().asString());
        System.out.println("Status is:\n"+response.statusCode());
        System.out.println("Id  is:\n"+response.jsonPath().get("id").toString());

    }

    @Test(description = "TC6_POSTRequestwithstaticdata",priority = 5,enabled = true,dataProvider = "userdatadynamic")
    void createMultiplUserwithDyanamicData(String name,String role)
    {
        //Specifying BASEURI
        RestAssured.baseURI="https://reqres.in/api/users";

        //Request
        RequestSpecification httpRequest=RestAssured.given();

        JSONObject requestparams=new JSONObject();
        requestparams.put("name",name);
        requestparams.put("job",role);

        httpRequest.header("Content-Type","application/json");
        httpRequest.body(requestparams.toJSONString());

        //Response
        Response response=httpRequest.request(Method.POST);

        System.out.println("Respone Body is:\n"+response.getBody().asString());
        System.out.println("Status is:\n"+response.statusCode());
        System.out.println("Id  is:\n"+response.jsonPath().get("id").toString());

    }
    @DataProvider(name="userdatastatic")
    Object [][] getuserData()
    {
        String [][] userdata={{"pragatiMM1","automation1"},{"pragatiMM2","automation2"},{"pragatiMM3","automation3"}};
        return (userdata);
    }

    @DataProvider(name="userdatadynamic")
    Object [][] getuserDatafromSheet() throws IOException {
        String DataFilePath=System.getProperty("user.dir")+"\\src\\main\\resources\\UserData.xlsx";
        int rownum=XLUtils.getRowCount(DataFilePath,"Sheet1");
        int cellnum=XLUtils.getColumnCount(DataFilePath,"Sheet1",1);

        String [][] userdata=new String[rownum][cellnum];
        for (int i=1;i<=rownum;i++)
        {
            for (int j=0;j<cellnum;j++)
            {
                userdata[i-1][j]=XLUtils.getcellData(DataFilePath,"Sheet1",i,j);
            }
        }
        return (userdata);
    }
}
