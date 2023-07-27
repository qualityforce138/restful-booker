package apiTests;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBooker {
    private static int bookingId;
    String uri = "https://restful-booker.herokuapp.com/booking/";
    String ct = "application/json";
    String authorizationHeader = "Basic YWRtaW46cGFzc3dvcmQxMjM=";

    public static String buscarArquivoJson(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test
    @DisplayName("Testando o cadastro de Reserva")
    @Order(1)
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
                .body("booking.firstname", is("Everton"))
                .body("booking.totalprice", is(5050))
                .body("booking.bookingdates.checkin", is("2023-07-14"))
                .extract();
        bookingId = resp.jsonPath().getInt("bookingid");
        System.out.println("O ID a ser deletado é o: " + bookingId);
    }

    @Test
    @DisplayName("Testando a atualizaçãod de um cadastro de Reserva")
    @Order(2)
    public void testAtualizarBook() throws IOException {
        String atualizarBook = buscarArquivoJson("src/test/resources/json/atualizarBook.json");
        given()
                .contentType(ct)
                .log().all()
                .header("Authorization", authorizationHeader)
                .body(atualizarBook)
                .when()
                .put(uri + bookingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", is("Altera"));
        System.out.println("O ID a ser atualizado será o: " + bookingId);
    }

    @Test
    @DisplayName("Testando a busca da reserva criada previamente")
    @Order(3)
    public void testGetBookingPorId() throws IOException {
        given()
                .contentType(ct)
                .log().all()
                .when()
                .get(uri + bookingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", is("Altera"))
        ;
        System.out.println("O ID a ser localizado será o: " + bookingId);
    }

    @Test
    @DisplayName("Testando a listagem das reservas, visando apenas a resposta de sucesso.")
    @Order(4)
    public void testGetBooking() throws IOException {
        String buscarBooking = buscarArquivoJson("src/test/resources/json/listarBook.json");
        given()
                .contentType(ct)
                .log().all()
                .body(buscarBooking)
                .when()
                .get(uri)
                .then()
                .log().all()
                .statusCode(200);
        System.out.println("Todos foram listados.");
    }

    @Test
    @DisplayName("Testando a exclusão da reserva cadastrada.")
    @Order(5)

    public void testExcluirBooking() {
        given()
                .contentType(ct)
                .accept(ct)
                .log().all()
                .header("Authorization", authorizationHeader)
                .when()
                .delete(uri + bookingId)
                .then()
                .log().all()
                .statusCode(201)
                .body(is("Created"));
        System.out.println("O ID deletado foi o: " + bookingId);
        ;
    }
}