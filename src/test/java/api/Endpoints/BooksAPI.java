package api.Endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.Payload.BooksPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BooksAPI {

	public static String OrderId;

	
	public static Response Status() {
		
	Response response =	given().when().get(Routes.Status_url);
	  return response;	
	}
	
	public static Response Authentication(BooksPayload Payload) {
		
		Response response = given().contentType(ContentType.JSON)
				.body(Payload)
		.when().post(Routes.Authentication_url);
		
		return response;
	}
	
	public static Response ListOfBooks() {
		
		Response response = given().when().get(Routes.Listofbooks_url);
		return response;
	}
	
	public static Response SubmitOrder(BooksPayload Payload) {
		
		Response response = given().contentType(ContentType.JSON)
				 .header("Authorization",Payload.userAccessToken)
				 .body(Payload)
				 .when().post(Routes.Submitorder_url);
		
		OrderId = response.jsonPath().get("orderId");
				
	    return response;
	}
	
	public static Response GetOrder(BooksPayload Payload) {
		
		Response response = given().header("Authorization", Payload.userAccessToken)
				            .pathParam("orderId", OrderId)
				            .when().get(Routes.Getorder_url);
		return response;
	}
	
	public static Response UpdateOrder(BooksPayload Payload) {
		
		Response response = given().header("Authorization", BooksPayload.userAccessToken)
				            .contentType(ContentType.JSON)
				            .pathParam("orderId", OrderId)
				            .body(Payload)
				            .when().patch(Routes.Updateorder_url);
		return response;
	}
	
	public static Response DeleteOrder() {
		Response response = given().header("Authorization",BooksPayload.userAccessToken)
				            .pathParam("orderId", OrderId)
				            .when().delete(Routes.Deleteorder_url);
		return response;
	}
	
	public static Response GetAllOrders() {
		Response resposne = given().header("Authorization",BooksPayload.userAccessToken)
				            .when().get(Routes.Gettallorders_url);
		return resposne;
	}
}
