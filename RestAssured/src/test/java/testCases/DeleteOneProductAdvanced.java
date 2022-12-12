package testCases;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteOneProductAdvanced {
	String baseURI;
	SoftAssert softAssert;
	String createPayloadPath;
	HashMap <String, String> createPayload;
	String firstProductId;
	HashMap <String, String> deletePayload;
	String deleteProductId;
	
//Map is a key and value	

	public DeleteOneProductAdvanced() {
			baseURI= "https://techfios.com/api-prod/api/product";
			softAssert = new SoftAssert();
			createPayloadPath= "src\\main\\java\\CreatePayload.json";
			createPayload= new HashMap<String,String>();
			deletePayload= new HashMap<String,String>();
			}
			
	public Map<String, String> createPayloadMap(){
		createPayload.put("name", "Amazing Headset 1.0 By MD");
		createPayload.put("description", "The best Headset for amazing programmers.");
		createPayload.put("price", "199");
		createPayload.put("category_id", "2");
		createPayload.put("category_name", "Electronics");
		return createPayload;
	}
	
	public Map<String, String> deletePayloadMap(){
		deletePayload.put("id", deleteProductId);
		return deletePayload;
		}
		/*
		 {
    "name": "Amazing Headset 1.0 By MD",
    "description": "The best Headset for amazing programmers.",
    "price": "199",
    "category_id": "2",
    "category_name": "Electronics"
}
		 */
			//@Test(priority=1)
			public void createOneProduct() {

				System.out.println("Create Payload Map:"+ createPayloadMap());
				Response response =
				given()
						.baseUri(baseURI)
						.header("Content-Type","application/json; charset=UTF-8")
						.auth().preemptive().basic("demo@techfios.com", "abc123")
						//.body(new File(createPayload)).
						.body(createPayloadMap()).
				when()
						.post("/create.php").
				then()
						.extract().response();
						
				/*long responseTime= response.getTimeIn(TimeUnit.MILLISECONDS);
				//System.out.println("Response Time:" + responseTime);
				
				if(responseTime <=2500) {
					System.out.println("Response time is within range.");
				} else {
					System.out.println("Response time is out of range.");*/

			
				int responseStatusCode =response.getStatusCode();
				softAssert.assertEquals(responseStatusCode, 201,"Response Status Code are not matching!");
				System.out.println("Response Status Code:" + responseStatusCode);
				

				String responseHeaderContentType =response.getHeader("Content-Type");
				softAssert.assertEquals(responseHeaderContentType, "application/json; charset=UTF-8", "Response Header Content Types are not matching!");
				System.out.println("Response Header ContentType" + responseHeaderContentType);
				
				
				String responseBody=response.getBody().asString();
				System.out.println("Response Body:"+ responseBody);
				
				
				JsonPath jp = new JsonPath(responseBody);
				
				 String productMessage =jp.getString("message");
				 softAssert.assertEquals(productMessage, "Product was created.","Product Message is not matching!");
				 System.out.println("Product Message:"+ productMessage );
				
				 
				 softAssert.assertAll(); //this must always be at the end
			}
			
			
			//@Test(priority=2)
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
				
				String responseBody=response.getBody().asString();
				System.out.println("Response Body"+ responseBody);
				
				
				JsonPath jp =new JsonPath(responseBody);
				 String firstProductId =jp.getString("records[0].id");
				 System.out.println("First Product Id:"+ firstProductId );
				 deleteProductId= firstProductId;
				
			}
			
	/*
	 * //@Test(priority=3) public void readOneProduct() {
	 * 
	 * Response response = given() .baseUri(baseURI)
	 * .header("Content-Type","application/json")
	 * .auth().preemptive().basic("demo@techfios.com", "abc123") .queryParam("id",
	 * firstProductId). when() .get("/read_one.php"). then() .extract().response();
	 * 
	 * 
	 * String actualresponseBody=response.getBody().asString();
	 * System.out.println("Actual Response Body:"+ actualresponseBody);
	 * 
	 * 
	 * JsonPath jp =new JsonPath(actualresponseBody);
	 * 
	 * String actualproductName =jp.getString("name");
	 * 
	 * softAssert.assertEquals(actualproductName, createPayloadMap().get("name"),
	 * "Product names are a not mathchin!");
	 * System.out.println("Actual Product Name:"+ actualproductName );
	 * 
	 * String actualproductDescription =jp.getString("description");
	 * softAssert.assertEquals(actualproductDescription,
	 * "The best Headset for amazing programmers.");
	 * System.out.println("Actual Product Description:"+ actualproductDescription );
	 * 
	 * String actualproductPrice =jp.getString("price");
	 * softAssert.assertEquals(actualproductPrice, "199");
	 * System.out.println("ActualProduct Price:"+ actualproductPrice );
	 * 
	 * softAssert.assertAll(); //this must always be at the end
	 */				
			
			@Test(priority=3)
			public void deleteOneProduct() {

				System.out.println("Update Payload Map:"+ deletePayloadMap());
				Response response =
				given()
						.baseUri(baseURI)
						.header("Content-Type","application/json; charset=UTF-8")
						.auth().preemptive().basic("demo@techfios.com", "abc123")
						//.body(new File(createPayload)).
						.body(deletePayloadMap()).
				when()
						.delete("/delete.php").
				then()
						.extract().response();
				
				int responseStatusCode =response.getStatusCode();
				softAssert.assertEquals(responseStatusCode, 201,"Response Status Code are not matching!");
				System.out.println("Response Status Code:" + responseStatusCode);
				

				String responseHeaderContentType =response.getHeader("Content-Type");
				softAssert.assertEquals(responseHeaderContentType, "application/json; charset=UTF-8", "Response Header Content Types are not matching!");
				System.out.println("Response Header ContentType" + responseHeaderContentType);
				
				
				String responseBody=response.getBody().asString();
				System.out.println("Response Body:"+ responseBody);
				
				
				JsonPath jp = new JsonPath(responseBody);
				
				 String productMessage =jp.getString("message");
				 softAssert.assertEquals(productMessage, "Product was updated.","Product Message is not matching!");
				 System.out.println("Product Message:"+ productMessage );
			
}
			
			@Test(priority=4)
			public void readOneDeletedProduct() {

				Response response =
				given()
						.baseUri(baseURI)
						.header("Content-Type","application/json")
						.auth().preemptive().basic("demo@techfios.com", "abc123")
						.queryParam("id", deletePayloadMap().get("id")).
				when()
						.get("/read_one.php").
				then()
						.extract().response();
				
				
				String actualresponseBody=response.getBody().asString();
				System.out.println("Actual Response Body:"+ actualresponseBody);
				
				
				JsonPath jp =new JsonPath(actualresponseBody);
				
				 String actualproductName =jp.getString("name");
				// String expectedproductName = updatePayloadMap()
				 
				 softAssert.assertEquals(actualproductName, createPayloadMap().get("name"), "Product names are a not mathchin!");
				 System.out.println("Actual Product Name:"+ actualproductName );
				 
				 String actualproductDescription =jp.getString("description");
				 softAssert.assertEquals(actualproductDescription, "The best Headset for amazing programmers.");
				 System.out.println("Actual Product Description:"+ actualproductDescription );
				 
				 String actualproductPrice =jp.getString("price");
				 softAssert.assertEquals(actualproductPrice, "199");
				 System.out.println("ActualProduct Price:"+ actualproductPrice );
				 
				 softAssert.assertAll(); //this must always be at the end
				
			}
}




