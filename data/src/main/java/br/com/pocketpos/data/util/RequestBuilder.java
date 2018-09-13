package br.com.pocketpos.data.util;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

//TODO: Implementar listener do envio/recebimento conforme exemplo em: https://blogs.oracle.com/pavelbucek/jersey-client-using-containerlistener
public class RequestBuilder {

//    private static final String REST_SERVER_URL = "http://www.pocketpos.com.br:8080/app/rest/";

    private static final String REST_SERVER_URL = "http://192.168.0.101:8080/PocketPOS-Server/rest/";

    private static Client client;

    static {

        if (client == null){

            client = ClientBuilder.newClient();

            client.register(JacksonJsonProvider.class);

            client.register(AndroidFriendlyFeature.class);

        }

    }

    public static WebTarget build(String... paths){

        WebTarget webTarget = client.target(REST_SERVER_URL);

        for (String s: paths)

            webTarget = webTarget.path(s);

        return webTarget;

    }

}