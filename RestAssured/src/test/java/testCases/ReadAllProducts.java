package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;


public class ReadAllProducts {

	/*
	 * this 01. is the API test we are automating 
	 * 01.ReadAllProducts HTTP method is GET 
	 * end Point URL =https://techfios.com/api-prod/api/product/read.php 
	 * Authorization is : BasciAuth 
	 * username:demo@techfios.com 
	 * password:abc123 
	 * Header/s: Content-Type =application.json; charset =UTF-8 http 
	 * status code = 200 
	 * responseTime = <=1500ms
	 
	 * given()= all input details(baseUri, header, authorization, queryParams,Payload/Body) 
	 * when()= submit request =httpMethod(endPoint) 
	 * the()=response validation(statusCOde, header/s, responseTime, response,Payload/Body)
	 */

	@Test
	public void readAllProducts() {

		Response response =
		(Response) given()
				//.log().all()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type","application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123").
		when()
				//.log().all()
				.get("/read.php").
		then()
				//.log().all()
				.extract().response();
				
		long responseTime= response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time:" + responseTime);
		
		if(responseTime <=2500) {
			System.out.println("Response time is within range.");
		} else {
			System.out.println("Response time is out of range.");
		}
		
		int responseStatusCode =response.getStatusCode();
		System.out.println("Response Status Code:" + responseStatusCode);
		Assert.assertEquals(responseStatusCode, 200);

		String responseHeaderContentType =response.getHeader("Content-Type");
		System.out.println("Response Header ContentType" + responseHeaderContentType);
		Assert.assertEquals(responseHeaderContentType, "application/json; charset=UTF-8");
		
		String responseBody=response.getBody().asString();
		System.out.println("Response Body"+ responseBody);
		
		
		JsonPath jp =new JsonPath(responseBody);
		 String firstProductId =jp.getString("records[0].id");
		 System.out.println("First Product Id:"+ firstProductId );
		
		 if (firstProductId !=null) {
			 System.out.println("Products list is not empty.");
		 } else {
			 System.out.println("Products list is empty");
		 }
	}
	


}