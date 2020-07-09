package ExcelDriven;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ExcelDrivenTests {

	public static void main(String[] args) throws IOException {
		  DataDriven d=new DataDriven();
	      ArrayList data=d.getData("Add Profile");
	
	System.out.println(data.get(0));
	System.out.println(data.get(1));
	System.out.println(data.get(2));
	System.out.println(data.get(3));
	System.out.println(data.get(4));
	System.out.println(data.get(5));
	}

	@Test
	public void addBook() throws IOException {
		DataDriven d=new DataDriven();
		ArrayList data=d.getData("RestAddbook","RestAssured");

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));

		RestAssured.baseURI="http://216.10.245.166";
		Response resp=given().
				header("Content-Type","application/json").
				body(map).
				when().
				post("/Library/Addbook.php").
				then().assertThat().statusCode(200).
				extract().response();
		JsonPath js= ReusableMethods.rawToJson(resp);
		String id=js.get("ID");
		System.out.println(id);

	}

}
