//1 - Package
package petstore;

//2 - Libraries


import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.CoreMatchers.is;

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
                .body("name", is("Atena"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("data"))
        ;

    }
    @Test(priority=2)
    public void consultarPet(){
        String petId = "100";

        String token =
                given()
                        .contentType("application/json")
                        .log().all()
                        .when()
                        .get(uri + "/" + petId)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is("Atena"))
                        .body("category.name", is("dog"))
                        .body("status",is("available"))
                        .extract()
                        .path("category.name")
                ;
        System.out.println("O token é " + token);

    }

    @Test(priority=3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("Data base/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .put(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Atena"))
                .body("status",is("sold"))
        ;
    }

    @Test (priority = 4)
    public void excluirPet(){
        String petId = "100";

        given()
                .contentType("application/json")
                .log().all()
                .when()
                .delete(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))

        ;
    }

}
