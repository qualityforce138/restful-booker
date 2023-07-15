package apiTests;

import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestBooker {
    //teste commit leonardo.
    //teste commit Everton. ajustado 22n novo ajuste  de
    //teste chicão comit
    //teste commit Humberto

String uri = "https://restful-booker.herokuapp.com/booking";
String content = "application/json";

    @Test
    @Order(1)
    public void testGetBooking(){
        // Dados de Entrega


        // Configura
        given()
                .log().all()
                .contentType(ct)
                .body()
        .when()

        // Executa


        // Valida



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
