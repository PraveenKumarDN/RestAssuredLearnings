import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * @author praveenkumar
 * This class illustrates the data provider concepts for parameterisation of inputs parameters.
 */
public class Learnings5DynamicJsonParameterisations {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {
        //This is the cloud base library apis deployed url by udemy - testing purpose
        RestAssured.baseURI = "http://216.10.245.166";
        Response response = given().
                header("Content-Type", "application/json").
                body(Payloads.addBookToLibrary(isbn, aisle)).
                when().
                post("/Library/Addbook.php").
                then().assertThat().statusCode(200).
                extract().response();

        JsonPath js = new JsonPath(response.asString()) ;
        String id = js.get("ID");
        System.out.println("Added library book id: " + id);

        //Similarly we could have POST operation api to delete the added book from library
        //deleteBook
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        //array=collection of elements
        // multidimensional array= collection of arrays
        return new Object[][]{
                {"ojfwty", "9363"},
                {"cwetee", "4253"},
                {"okmfet", "533"}
        };
    }
}
