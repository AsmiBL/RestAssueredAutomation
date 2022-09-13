package restAssuredTest;

import basePackage.BaseSetup;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import restAssueredUtility.Listeners;
import restAssueredUtility.RestUtility;

public class TestA extends BaseSetup {

    String empname= RestUtility.getEmpName();
    String emprole= RestUtility.getEmpRole();
    Listeners listeners=new Listeners();
    @Test(description = "TC7_POSTRequest", priority = 1, enabled = true)
    void createRandomUser() {
        //Specifying BASEURI
        RestAssured.baseURI = "https://reqres.in/api/users";

        //Request
        httpsRequest = RestAssured.given();

        JSONObject requestparams = new JSONObject();
        requestparams.put("name", empname);
        requestparams.put("job", emprole);

        httpsRequest.header("Content-Type", "application/json");
        httpsRequest.body(requestparams.toJSONString());

        //Response
        response = httpsRequest.request(Method.POST);

        System.out.println("Respone Body is:\n" + response.getBody().asString());
        System.out.println("Status is:\n" + response.statusCode());
        System.out.println("Id  is:\n" + response.jsonPath().get("id").toString());
        System.out.println("Timing  is:\n" + response.jsonPath().get("createdAt").toString());

        //Validating Header
        Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");

        //Capturing all headers
        Headers allHeaders = response.headers();

        //Printing Header
        for (Header header : allHeaders) {
            System.out.println(header.getName() + "    " + header.getValue());
        }

    }

}

