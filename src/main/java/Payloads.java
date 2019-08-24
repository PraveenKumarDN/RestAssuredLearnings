/**
 * @author praveenkumar
 * This class contains the required requiest payloads for POST operation apis.
 */
public class Payloads {

    public static String addBookToLibrary(String isbn, String aisle){
        String addBookString = "{\n" +
                "\"bookName\" : \"lib book name value\",\n" +
                "\"author\" : \"author of book value\",\n" +
                "\"isbn\" : \""+isbn+"\",\n" +
                "\"isle\" : \""+aisle+"\",\n" +
                "\"bookVersion\": \"version value\"\n" +
                "}" ;
        return addBookString ;
    }
}
