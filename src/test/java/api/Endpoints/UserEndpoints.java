package api.Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.Payload.User;

public class UserEndpoints {

	
	public static Response createUser(User Payload) {
		
		Response response = given()
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
          .body(Payload)		
          .when()
             .post(Routes.post_url);
		
		return response;
	}
	
	
	public static Response getUser(String userName) {
		
		Response response = given()
		 .accept(ContentType.JSON)
		 .pathParam("username", userName)
		 .when()
		   .get(Routes.get_url);
		
		return response;		 
	}
	
	
	public static Response updateUser(User Payload, String userName) {
		
	Response response =	given()
		 .contentType(ContentType.JSON)  
		 .accept(ContentType.JSON)
		 .pathParam("username", userName)
		 .body(Payload)
		 .when()
		    .put(Routes.update_url);
		
	  return response;
	}
	
	public static Response deleteUser(String userName) {
	
		Response response = given()
		    .accept(ContentType.JSON)
		    .pathParam("username", userName)
		    .when()
		      .delete(Routes.delete_url);
		
		return response;
	}
}
