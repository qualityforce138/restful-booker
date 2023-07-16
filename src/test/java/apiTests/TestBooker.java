package apiTests;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class TestBooker {

String uri = "https://restful-booker.herokuapp.com/booking/";
String ct = "application/json";
private static int bookingId;

    public static String buscarArquivoJson(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test
    @Order(1)
    public void testGetBooking() throws IOException {
        // Dados de Entrada
        String jsonBody = buscarArquivoJson("src/test/resources/json/listarBook.json");
        // Para realizar o teste verificar inicialmente os dados da lista
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
                .body("bookingid [0]",is(84))
                .body("bookingid [1]",is(43))
                .body("bookingid [2]",is(10))
                .body("bookingid [4]",is(202))
        ;

    }

    @Test
    @Order(2)
    // Inicio teste Leonardo
    // teste Commit conta Quality

    public void testCreateBooking(){


    }

    @Test
    @Order(3)
    public void testCadastrarBook() throws IOException {
        String criarBook = buscarArquivoJson("src/test/resources/json/criarBook.json");
        Response resp = (Response) given()
                .contentType(ct)
                .log().all()
                .body(criarBook)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("booking.firstname",is("Everton"))
                .body("booking.totalprice",is(5050))
                .body("booking.bookingdates.checkin",is("2023-07-14"))
                .extract()
                ;
        bookingId = resp.jsonPath().getInt("bookingid");
        System.out.println("O ID a ser deletado é o: " + bookingId);
    }


    @Test
    @Order(4)
   // teste,,,,,,
   // teste conta

  public void testUpdateBooking(){

   }


    @Test
    @Order(5)
    public void testExcluirBooking(){
        //Execute o teste de criação, verificando oid criado para ser excluido
        String authorizationHeader = "Basic YWRtaW46cGFzc3dvcmQxMjM=";
        String bookingId = "248";
        String idExcluir = uri + bookingId;

        given()
                .contentType(ct)
                .accept(ct)
                .log().all()
                .header("Authorization", authorizationHeader)
        .when()
                .delete(idExcluir)
        .then()
                .log().all()
                .statusCode(201)
                .body(is("Created"));
        ;
    }
}
