import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class TestUtils {
    public static JsonPath cadastrarUsuario(RequestSpecification serverestSpec, String nome, String email, String senha, String adm, int status){
        JSONObject usuario = new JSONObject();
        usuario.put("nome", nome);
        usuario.put("email", email);
        usuario.put("password", senha);
        usuario.put("administrador", adm);
        return given().spec(serverestSpec)
                .body(usuario.toString())
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(status)
                .extract()
                .jsonPath();
    }

    public static JsonPath deletarUsuario(RequestSpecification serverestSpec, String id, int status){
            return given().spec(serverestSpec)
                    .pathParam("_id", id)
                    .when()
                    .delete("/{_id}")
                    .then()
                    .log().all()
                    .statusCode(status)
                    .extract()
                    .jsonPath();
    }
}
