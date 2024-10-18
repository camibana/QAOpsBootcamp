import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidarUsuariosTest extends BaseTest {
    private final String nomeUsuario = "Diego Patricio";
    private final String emailUsuario = gerarEmail(10);
    private final String senhaUsuario = "12345";
    private final String administrador = "true";

    @Test
    void listarUsuarioRetorneSucesso() {
        given().spec(serverestSpec)
                .when()
                .get()
                .then()
                .statusCode(SC_OK)
                .log().all();
    }

    @Test
    void buscarUsuarioRetorneSucesso() {
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_CREATED);
        given().spec(serverestSpec)
                .pathParam("_id", teste.get("_id").toString())
                .when()
                .get("/{_id}")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("nome", equalTo(nomeUsuario))
                .body("email", equalTo(emailUsuario))
                .body("password", equalTo(senhaUsuario))
                .body("administrador", equalTo(administrador))
                .body("_id", equalTo(teste.get("_id").toString()));
        TestUtils.deletarUsuario(serverestSpec, teste.get("_id").toString(), SC_OK);
    }

    @Test
    void cadastrarUsuarioRetorneSucesso() {
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_CREATED);
        assertEquals("Cadastro realizado com sucesso", teste.get("message"));
        TestUtils.deletarUsuario(serverestSpec, teste.get("_id").toString(), SC_OK);
    }

    @Test
    void buscarUsuarioCadastradoRetorneSucesso() {
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_CREATED);
        given().spec(serverestSpec)
                .pathParam("_id", teste.get("_id").toString())
                .when()
                .get("/{_id}")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("nome", equalTo(nomeUsuario))
                .body("email", equalTo(emailUsuario))
                .body("password", equalTo(senhaUsuario))
                .body("administrador", equalTo(administrador))
                .body("_id", equalTo(teste.get("_id").toString()));
    }

    @Test
    void cadastrarUsuarioComMesmoEmail() {
        TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_CREATED);
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_BAD_REQUEST);
        assertEquals("Este email já está sendo usado", teste.get("message"));
    }

    @Test
    void cadastrarEmailInvalido() {
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, "123", senhaUsuario, administrador, SC_BAD_REQUEST);
        assertEquals("email deve ser um email válido", teste.get("email"));
    }

    @Test
    void deletarUsuarioSucesso() {
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_CREATED);
        given().spec(serverestSpec)
                .pathParam("_id", teste.get("_id").toString())
                .when()
                .delete("/{_id}")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    void deletarUsuarioSucessoDois() {
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_CREATED);
        var t = TestUtils.deletarUsuario(serverestSpec, teste.get("_id").toString(), SC_OK);
        assertEquals("Registro excluído com sucesso", t.get("message"));

    }

    @Test
    void atualizarUsuarioSucesso() {
        var teste = TestUtils.cadastrarUsuario(serverestSpec, nomeUsuario, emailUsuario, senhaUsuario, administrador, SC_CREATED);
        JSONObject usuario = new JSONObject();
        usuario.put("nome", "Novo Nome");
        usuario.put("email", emailUsuario);
        usuario.put("password", senhaUsuario);
        usuario.put("administrador", administrador);
        given().spec(serverestSpec)
                .pathParam("_id", teste.get("_id").toString())
                .body(usuario.toString())
                .when()
                .put("/{_id}")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("message", equalTo("Registro alterado com sucesso"));
    }
}
