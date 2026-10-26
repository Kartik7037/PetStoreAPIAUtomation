package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.Endpoints.UserEndpoints;
import api.Payload.User;
import io.restassured.response.Response;

public class UserTest {

	Faker faker;
	User Payload;
	public Logger logger;
	
	
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		Payload = new User();
		
		Payload.setId(faker.idNumber().hashCode());
		Payload.setFirstNam(faker.name().firstName());
		Payload.setLastName(faker.name().lastName());
		Payload.setEmail(faker.internet().safeEmailAddress());
		Payload.setPassword(faker.internet().password(5, 10));
		Payload.setUsername(faker.name().username());
		Payload.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testCreateUser() {
		
		logger.info("************ Create User ****************");
		
		Response response = UserEndpoints.createUser(Payload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User Created ****************");
	}

	
	@Test(priority=2)
	public void testGetUserByUsernam() {
		
		 logger.info("************ Get User ****************");
		 
		 Response response = UserEndpoints.getUser(this.Payload.getUsername());
		 response.then().log().all();
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 logger.info("************ User info displayed ****************");
	}
	
	
	@Test(priority=3)
	public void testUpdateUserByUsername() {
		
		logger.info("************ Update User ****************");
		Payload.setFirstNam(faker.name().firstName());
        Payload.setLastName(faker.name().lastName());
        Payload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints.updateUser(this.Payload, this.Payload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User Updated ****************");
	}
	
	
	@Test(priority=4)
	public void testDeleteUserByUsername() {
		
		logger.info("************ Delete User ****************");
		Response response = UserEndpoints.deleteUser(this.Payload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User Deleted ****************");
	}
}
