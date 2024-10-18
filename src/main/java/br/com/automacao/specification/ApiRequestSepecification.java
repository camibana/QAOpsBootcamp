package br.com.automacao.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static br.com.automacao.specification.Environments.PATH_USUARIOS;
import static br.com.automacao.specification.Environments.URI_API;
import static io.restassured.http.ContentType.JSON;

public class ApiRequestSepecification {
    private static RequestSpecBuilder getRequestSpecification() {
        return new RequestSpecBuilder().setConfig(new RestAssuredConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation())
                        .logConfig(LogConfig.logConfig()
                                .enablePrettyPrinting(true)
                                .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)))
                .setRelaxedHTTPSValidation()
                .setContentType(JSON)
                .log(LogDetail.ALL);
    }
    public RequestSpecification getUsuarios() {
        return getRequestSpecification().setBaseUri(URI_API)
                .setBasePath(PATH_USUARIOS)
                .build();
    }

}
