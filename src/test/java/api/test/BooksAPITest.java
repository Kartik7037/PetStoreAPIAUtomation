package api.test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.Endpoints.BooksAPI;
import api.Payload.BooksPayload;
import io.restassured.response.Response;

public class BooksAPITest {
     
	 Faker faker;
	 BooksPayload Payload;
	 
	 @BeforeClass
	 public void setupData() {
		 
		 faker = new Faker();
		 Payload = new BooksPayload();
		 
		 Payload.setClientName(faker.name().fullName());
		 Payload.setClientEmail(faker.internet().safeEmailAddress());
		 Payload.setCustomerName(faker.name().fullName());
		 Payload.setBookId(faker.random().nextInt(1, 7));
		 System.out.println(Payload.getCustomerName());
	 }
	 
	 @Test
	 public void TC0_APIAuthentication() {
		 
		 Response response = BooksAPI.Authentication(Payload);
		 response.then().log().all();
		 
		  BooksPayload.userAccessToken = response.jsonPath().getString("accessToken");
		 
		 Assert.assertEquals(response.getStatusCode(), 201);
	 }
	 
	 @Test
	 public void TC1_verifyAPIStatus() {
		   Response response = BooksAPI.Status();
		   response.then().log().all();
		   
		   Assert.assertEquals(response.getStatusCode(), 200);
		   
		   String Message = response.jsonPath().getString("status");
		   Assert.assertEquals(Message, "OK");
	 }
	 
	 @Test
	 public void TC2_verifyUserAbleToRetrieveListOfBooks() {
		 
		 Response response = BooksAPI.ListOfBooks();
		 response.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 //Verify Books Name
		 List<String> li = new ArrayList<String>();
	     li = response.jsonPath().getList("name");
		 
	     String expectedBooks[] = {"The Russian","The Vanishing Half","The Midnight Library","Viscount Who Loved Me","Just as I Am","Untamed"};
	     int j=0;
	     for(String i : li) {
	    	 Assert.assertEquals(i, expectedBooks[j]);
	    	 j++;
	     }
	     
	     //Verify Types of Books
	     j=0;
	     li = response.jsonPath().getList("type");
	     String expectedTypes[] = {"fiction","fiction","fiction","fiction","non-fiction","non-fiction"};
         for(String i : li) {
        	 Assert.assertEquals(i,expectedTypes[j] );
        	 j++;
         }
	     
         
         //Verify Books Availability
         j=0;
        List<Boolean> list = response.jsonPath().getList("available");
        Boolean expectedAvaialbility[] = {true,true,true,true,false,true};
        for (int k = 0; k < list.size(); k++) {
            Assert.assertTrue(
                list.get(k).equals(expectedAvaialbility[k]),
                "Mismatch at index: " + k
            );
        
        /* for(Boolean i : list) {
        	Assert.assertEquals(i, expectedAvaialbility[j]);
        	j++;
        }*/    
        }
        
        //Verify Books Ids
        j=0;
        List<Integer> l = response.jsonPath().getList("id");
        int expectedIds[] = {1,3,4,6,2,5};
        for(int i : l) {
        	Assert.assertEquals(i, expectedIds[j]);
        	j++;
        }
	 }
	 
	 @Test
	 public void TC4_verifyUserAbleToSubmitAndSeeTheOrder() {
		 
		 //System.out.println(BooksPayload.userAccessToken); 
		 //Submit Order
		 Response response = BooksAPI.SubmitOrder(Payload);
		 response.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response.getStatusCode(), 201);
		 
		 //Verify Message
		 Assert.assertEquals(response.jsonPath().getBoolean("created"), true);
		 //System.out.println(BooksAPI.OrderId);

		 //Get Order
		 Response response1 = BooksAPI.GetOrder(Payload);
		 response1.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response1.getStatusCode(), 200);
		 
		 //Verify Order Details
		 Assert.assertEquals(response1.jsonPath().getString("id"), BooksAPI.OrderId);
		 Assert.assertEquals(response1.jsonPath().getInt("bookId"), Payload.getBookId());
		 Assert.assertEquals(response1.jsonPath().getString("customerName"), Payload.getCustomerName());
		 
	 }
	 
	 @Test
	 public void TC5_verifyUserAbleToUpdateTheOrder() {
		 
		 //Submit Order
		 Response response = BooksAPI.SubmitOrder(Payload);
		 response.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response.getStatusCode(), 201);
		 
		 //Verify Message
		 Assert.assertEquals(response.jsonPath().getBoolean("created"), true);
		 //System.out.println(BooksAPI.OrderId);

		 //Update Order
		 Payload.setCustomerName(faker.name().fullName());
		 System.out.println(Payload.getCustomerName());
		 Response response2 = BooksAPI.UpdateOrder(Payload);
		 response2.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response2.getStatusCode(), 204);
		 
		 
		 //Get Order
		 Response response1 = BooksAPI.GetOrder(Payload);
		 response1.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response1.getStatusCode(), 200);
		 
		 //Verify Order Details
		 Assert.assertEquals(response1.jsonPath().getString("id"), BooksAPI.OrderId);
		 Assert.assertEquals(response1.jsonPath().getInt("bookId"), Payload.getBookId());
		 Assert.assertEquals(response1.jsonPath().getString("customerName"), Payload.getCustomerName());
         System.out.println(response1.jsonPath().getString("customerName"));
		 
	 }
	 
	 @Test
	 public void TC6_verifyUserAbleToDeleteTheOrder() {
		 
		 //Submit Order
		 Response response = BooksAPI.SubmitOrder(Payload);
		 response.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response.getStatusCode(), 201);
		 
		 //Verify Message
		 Assert.assertEquals(response.jsonPath().getBoolean("created"), true);
		 //System.out.println(BooksAPI.OrderId);
		 
		 //Delete Order
		 Response response1 = BooksAPI.DeleteOrder();
		 response1.then().log().all();
		 
		 //Verify Status Code
		 Assert.assertEquals(response1.getStatusCode(), 204);
		 
	 }
	 
	 @Test
	 public void TC7_verifyUserAbleToRetrieveListOfAllThePlacedOrders() {
		 
		 Response response = BooksAPI.GetAllOrders();
		 response.then().log().all();

		 //Verify Status Code
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 //Verify Json Schema
		 String schema = "{\r\n"
			        + "  \"type\": \"array\",\r\n"
			        + "  \"items\": {\r\n"
			        + "    \"type\": \"object\",\r\n"
			        + "    \"required\": [\"id\", \"bookId\", \"customerName\", \"quantity\", \"timestamp\"],\r\n"
			        + "    \"properties\": {\r\n"
			        + "      \"id\": { \"type\": \"string\" },\r\n"
			        + "      \"bookId\": { \"type\": \"integer\" },\r\n"
			        + "      \"customerName\": { \"type\": \"string\" },\r\n"
			        + "      \"quantity\": { \"type\": \"integer\" },\r\n"
			        + "      \"timestamp\": { \"type\": \"integer\" }\r\n"
			        + "    }\r\n"
			        + "  }\r\n"
			        + "}";;
		 
		 response.then().assertThat().body(matchesJsonSchema(schema));
		 
	 }
}
