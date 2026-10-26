package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.Endpoints.UserEndpoints;
import api.Payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	
	@Test(priority=1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String Id, String userName, String firstName, String lastName, String Email, String Phone, String Password) {
		
		User payload = new User();
		payload.setId(Integer.parseInt(Id));
		payload.setUsername(userName);
		payload.setFirstNam(firstName);
		payload.setLastName(lastName);
		payload.setEmail(Email);
		payload.setPhone(Phone);
		payload.setPassword(Password);
		
		Response response = UserEndpoints.createUser(payload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUser(String userName) {
		
		Response response = UserEndpoints.deleteUser(userName);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
}
