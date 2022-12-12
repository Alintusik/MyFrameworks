package testCases;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadOneProduct {
	String baseURI;
	SoftAssert softAssert;
	/*
	 * 02. ReadOneProduct
	 * http method=GET
	 * EndPointUrl=https://techfios.com/api-prod/api/product/read_one.php
	 * Authorization:(basic auth) username=demo@techfios.com
	 *  password=abc123 Query
	 * Parameters: id=6034 
	 * Header/s: Content-Type=application/json
	 *  http status code=200 responseTime= <=1500ms
	 * 
	 * * given()= all input details(baseUri, header, authorization, queryParams,Payload/Body) 
	 * when()= submit request =httpMethod(endPoint) 
	 * the()=response validation(statusCOde, header/s, responseTime, response,Payload/Body)
	 */

	public ReadOneProduct() {
		
		baseURI= "https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
		}
		
		@Test
		public void readOneProduct() {

			Response response =
			given()
					.baseUri("baseURI")
					.header("Content-Type","application/json")
					.auth().preemptive().basic("demo@techfios.com", "abc123")
					.queryParam("id", "6209").
			when()
					.get("/read_one.php").
			then()
					.extract().response();
					
			long responseTime= response.getTimeIn(TimeUnit.MILLISECONDS);
			System.out.println("Response Time:" + responseTime);
			
			if(responseTime <=2500) {
				System.out.println("Response time is within range.");
			} else {
				System.out.println("Response time is out of range.");
			}
			
			int responseStatusCode =response.getStatusCode();
			softAssert.assertEquals(responseStatusCode, 200);
			System.out.println("Response Status Code:" + responseStatusCode);
			

			String responseHeaderContentType =response.getHeader("Content-Type");
			softAssert.assertEquals(responseHeaderContentType, "application/json");
			System.out.println("Response Header ContentType" + responseHeaderContentType);
			
			
			String responseBody=response.getBody().asString();
			System.out.println("Response Body"+ responseBody);
			
			
			JsonPath jp =new JsonPath(responseBody);
			
			 String productName =jp.getString("name");
			 softAssert.assertEquals(productName, "Amazing Headset 1.0 By MD");
			 System.out.println("Product Name:"+ productName );
			 
			 String productDescription =jp.getString("description");
			 softAssert.assertEquals(productDescription, "The best Headset for amazing programmers.");
			 System.out.println("Product Description:"+ productDescription );
			 
			 String productPrice =jp.getString("price");
			 softAssert.assertEquals(productPrice, "199");
			 System.out.println("Product Price:"+ productPrice );
			 
			 softAssert.assertAll(); //this must always be at the end
			
		}
		
	}
