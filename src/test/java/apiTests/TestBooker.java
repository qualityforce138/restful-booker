package apiTests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestBooker {
    private static String token;
    private static int bookingId;
    String jsonBody;
    String uri = "https://restful-booker.herokuapp.com/booking/";
    String ct = "application/json";
    String authorizationHeader = "Basic YWRtaW46cGFzc3dvcmQxMjM=";
    Gson gson = new Gson();

    public static String buscarArquivoJson(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Tag("login")
    @Test
    public void geraToken() throws IOException {
        //  Dados de Entrada
        String jsonBody = buscarArquivoJson("src/test/resources/json/userToken.json");

        Response resp = (Response)
                // Executa
                given()
                        .contentType(ct)
                        .log().all()
                        .body(jsonBody)
                        .when()
                        .post("https://restful-booker.herokuapp.com/auth")
                        // Valida
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("token", not(emptyOrNullString()))
                        .extract();
        token = resp.jsonPath().getString("token");
        System.out.println("Token obtido: " + token);

    }


    @Test
    @Order(0)
    public void testGetBookinglds() throws IOException {
        // Inicio teste Humberto
        // Dados de Entrada
        String jsonBody = buscarArquivoJson("src/test/resources/json/getBookinglds.json");
        // Para realizar o teste verificar inicialmente os dados da lista dinamica

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
        ; // fim do teste


    }

    @Test
    @Order(1)
    public void testGetBooking() throws IOException {
        // Inicio teste Humberto
        // Dados de Entrada
        String jsonBody = buscarArquivoJson("src/test/resources/json/listarBook.json");
        // Para realizar o teste verificar inicialmente os dados da lista dinamica
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
        ; // fim do teste

    }

    @Test
    @Tag("login")
    @Order(2)
    // Inicio teste Leonardo
    // teste Commit conta Quality

    public void testCreateBooking() throws IOException {
        String createBooking = buscarArquivoJson("src/test/resources/json/createBooking.json");
        Response resp = (Response)

                // Executa
                given()
                        .contentType(ct)
                        .log().all()
                        .body(createBooking)
                        .when()
                        .post(uri)
                        // Valida
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("booking.firstname", is("Leonardo"))
                        .body("booking.lastname", is("Garson"))
                        .body("booking.totalprice", is(350))
                        .body("booking.bookingdates.checkin", is("2023-07-19"))
                        .extract();
        bookingId = resp.jsonPath().getInt("bookingid");
        System.out.println("O ID a ser deletado é o: " + bookingId);

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
                .body("booking.firstname", is("Everton"))
                .body("booking.totalprice", is(5050))
                .body("booking.bookingdates.checkin", is("2023-07-14"))
                .extract();
        bookingId = resp.jsonPath().getInt("bookingid");

        System.out.println("O ID a ser deletado é o: " + bookingId);
    }


    @Tag("login")
    @Test
    @Order(4)
    public void testUpdateBooking() throws IOException {  // Analizar RESERVA
        //Atualiza e corrige teste de criação, verificando uma reserva atual pela URL
        // inicio dados entrada

        String jsonBody = buscarArquivoJson("src/test/resources/json/updateBook.json");

        //String idAnalizar = ct;
        //String bookingId = 111;
        //String corrigirId = uri;

        //configuração
        given()
                .contentType(ct)
                .accept(ct)
                //.cookie ("token", token)
                .header("Authorization", authorizationHeader)
                .log().all()
                .body(jsonBody)
                //Executa
                .when()
                .put(uri + bookingId)
                //Valida
                .then()
                .log().all()
                .statusCode(200);


    }

    //.authorization()
    @Test
    @Order(5)
    public void testExcluirBooking() {
        //Execute o teste de criação, verificando oid criado para ser excluido
        //String bookingId = bookingId;
        //String idExcluir = uri + bookingId;

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
        ;
    }
}
