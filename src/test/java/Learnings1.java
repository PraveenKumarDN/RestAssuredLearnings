import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author praveenkumar
 * This class contains the simple GET and POST rest calls java automation.
 * Note: RestAssured consists of 4 blocks: given(), when(), then(), extract()..
 */
public class Learnings1 {

    @Test
    public void createPlaceAPI()
    {
        RestAssured.baseURI="http://216.10.245.166";

        //case1: post operation to create add place api in google maps
        Response response = given().
                queryParam("key","qaclick123").
                body("{"+
                        "\"location\": {"+
                        "\"lat\": -33.8669710,"+
                        "\"lng\": 151.1958750"+
                        "},"+
                        "\"accuracy\": 50,"+
                        "\"name\": \"Google Shoes!\","+
                        "\"phone_number\": \"(02) 9374 4000\","+
                        "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+
                        "\"types\": [\"shoe_store\"],"+
                        "\"website\": \"http://www.google.com.au/\","+
                        "\"language\": \"en-AU\""+
                        "}").
                when().
                post("/maps/api/place/add/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status",equalTo("OK")).
                extract().
                response();

        //converting raw to string response
        String stringResponse = response.asString() ;
        System.out.println("Post api create call response: "+ stringResponse);

        //parsing the string response to json string. More info: https://jsoneditoronline.org/
        JsonPath jsonPath = new JsonPath(stringResponse);
        String responsePlaceId = jsonPath.get("place_id") ;



        //case2: POST operation to delete created place api in google maps using above responsePlaceId
        Response responseDelete = given().
                queryParam("key","qaclick123").
                body("{\n" +
                        "\"place_id\": \""+responsePlaceId+"\"\n" +
                        "}").
                when().
                post("/maps/api/place/delete/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status",equalTo("OK")).
                extract().
                response();

        String deletedStringResponse = responseDelete.asString() ;
        System.out.println("Post api delete call response: "+ deletedStringResponse);

    }

}
