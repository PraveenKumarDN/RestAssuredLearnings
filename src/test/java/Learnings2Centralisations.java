import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author praveenkumar
 * This class contains the simple GET and POST rest calls java automation.
 * Note: RestAssured consists of 4 blocks: given(), when(), then(), extract()..
 */
public class Learnings2Centralisations {
    private String host = null ;

    @BeforeClass
    private void beforeClass() throws IOException {
        //load the config env.properties file
        Properties properties = new Properties() ;
        File file = new File("/home/praveenkumar/workspace/restapi/src/main/resources/configs/env.properties") ;
        FileInputStream fileInputStream = new FileInputStream(file) ;
        properties.load(fileInputStream);
        host = properties.getProperty("host") ;

    }

    @Test
    public void createPlaceAPI()
    {
        RestAssured.baseURI=host;

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
                body("status",equalTo("OK")).and().header("string1", "string").
                extract().
                response();

        Headers headers = given().
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
                body("status",equalTo("OK")).and().header("string1", "string").
                extract().
                response().getHeaders();
        for(Header header : headers.asList()){
            if(header.getName().equalsIgnoreCase("Session")){
                String sessionToken = header.getValue() ;
            }
        }

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
