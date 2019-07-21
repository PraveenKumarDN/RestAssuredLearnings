import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
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
 * Note: Not working properly but code has been return for xml string convertion.
 */
public class Learnings4XMLResponse {
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

    //This method helps in generating the xml String for xml payload path passed as argument
    private String generateStringFromResource(String path) throws IOException{
        //return new String(Files.readAllBytes(Paths.get(path)));
        return null ;

    }

    @Test
    public void createDeletePlaceAPIWithXMLResponse() throws IOException {
        RestAssured.baseURI=host;

        String xmlRequest = generateStringFromResource("/home/praveenkumar/workspace/restapi/src/main/resources/files/postData.xml") ;
        //case1: post operation to create add place api in google maps
        Response response = given().
                queryParam("key","qaclick123").
                body(xmlRequest).log().method().log().uri().
                when().
                post("/maps/api/place/add/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status",equalTo("O")).log().ifValidationFails(LogDetail.ALL).
                extract().
                response();

        //converting raw to string response
        System.out.println("----------------------------------------------------");
        String stringResponse = response.asString() ;
        System.out.println("Post api create call response:\n "+ stringResponse);
        System.out.println("----------------------------------------------------");

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

        System.out.println("----------------------------------------------------");
        String deletedStringResponse = responseDelete.asString() ;
        System.out.println("Post api delete call response: "+ deletedStringResponse);
        System.out.println("----------------------------------------------------");
    }

}
