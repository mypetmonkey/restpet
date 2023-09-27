package ApiTest;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CRUDoperation {
	
	@Test
	public void test() {
		baseURI="https://petstore.swagger.io/v2/";
		
		HashMap<Object,Object> hm=new HashMap<Object, Object>();
		hm.put("id", 101);
		hm.put("username", "Rajhans Mehta");
		hm.put("firstName", "Rajhans");
		hm.put("lastName","Mehta");
		hm.put("email", "raju@gmail.com");
		hm.put("password", "qa@123");
		hm.put("phone", "8888888888");
		hm.put("userStatus",0);
		
		Set<String> headers=new HashSet<String>();
		headers.add("Server");
		headers.add("Content-Type");
		headers.add("Date");
		
		
		Response resp = given()
		.auth().oauth2(" ")
		.body(hm)
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.header("hName", "value1","value2").log().headers()
		.config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers)))
		
		.when()
		.post("/user");
		
		
		resp.then().log().ifError()
		.assertThat()
		.statusCode(200)
		//.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("NewCust.json"))
		.contentType(ContentType.JSON)
		.time(Matchers.lessThan(3000l),TimeUnit.MILLISECONDS)
		.log().all();
		
		
		
		given()
		.when()
		.get("/user/Rajhans Mehta")
		
		.then().log().all();
//		List<String> un = resp.jsonPath().get("firstName");
//		for(String temp:un) {
//			System.out.println("username :"+temp);
//		}
//		
		
	}
	
	public static int getRandom() {
		Random ran=new Random();
		int val = ran.nextInt(500);
		return val;
	}

}
