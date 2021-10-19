//1 - Package
package petstore;

//2 - Libraries


import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

//3 - Class
public class Pet {
    //3.1 - Atributos

    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet
    //3.2 - Method -> não retorna valor
    //3.2 - Function -> retorna valor
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    // Incluir - Create - Post
    @Test(priority = 1)  // Identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("Data base/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() // Dado
                .contentType("application/json") // comum em API REST - antigas era "text/xml"
                .log().all()
                .body(jsonBody)
        .when()  // Quando
                .post(uri)

        .then()
                .log().all()
                .statusCode(200)
        ;

    }

}
