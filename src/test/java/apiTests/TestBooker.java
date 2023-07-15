package apiTests;

import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class TestBooker {
    //teste commit leonardo.
    //teste commit Everton. ajustado 22n novo ajuste  de
    //teste chicão comit
    //teste commit Humberto

String uri = "https://restful-booker.herokuapp.com/booking";
String ct = "application/json";
public static String buscarArquivoJson(String arquivoJson) throws IOException {

    return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test
    @Order(1)
    public void testGetBooking() throws IOException {
        // Dados de Entrada
        String jsonBody = buscarArquivoJson("src/test/resources/json/book.json");
        // Para realizar o teste verificar verificar inicialmente os dados da lista
        // Configura
        given()
                .contentType(ct)
                .log().all()
                .body(jsonBody)
        // Executa
        .when()
                .get(uri)

        // Valida
        .then()
                .log().all()
                .statusCode(200)
                .body("bookingid [0]",is(13))
                .body("bookingid [1]",is(9))
                .body("bookingid [2]",is(48))
                .body("bookingid [4]",is(32))
        ;

    }




    @Test
    @Order(3)
   // teste,,,,,,
   // teste conta

  public void testUpdateBooking(){

   }


    @Test
    @Order(4)
    public void testDeleteBooking(){


    }


}
