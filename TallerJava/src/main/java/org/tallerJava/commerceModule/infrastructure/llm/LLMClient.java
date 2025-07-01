package org.tallerJava.commerceModule.infrastructure.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

public class LLMClient {

    private static final String LLM_URL = "http://localhost:11434/api/generate";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String clasificarReclamo(String textoReclamo) throws IOException, InterruptedException {
        String prompt = "Dado el siguiente reclamo, respondé únicamente con una de estas 3 palabras  : POSITIVO, NEGATIVO o NEUTRO. A modo de referencia un comentario diciendo 'el servicio es malo' es considerado como negativo. \\nReclamo: \"" + textoReclamo + "\"";

        String requestBody = String.format(
                """
                {
                    "model": "llama2",
                    "prompt": "%s",
                    "stream": false
                }
                """, prompt.replace("\"", "\\\"")
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LLM_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        System.out.println("Respuesta cruda del LLM: " + body);

        // Parsear JSON con Jackson
        JsonNode root = mapper.readTree(body);

        // El nombre del campo puede variar según API de LLM:
        // Puede ser "response", "text", "result", "choices",
        JsonNode responseNode = root.get("response");

        if (responseNode == null) return "ERROR";

        String responseText = responseNode.asText().toLowerCase(Locale.ROOT);

        if (responseText.contains("positivo")) return "POSITIVO";
        if (responseText.contains("negativo")) return "NEGATIVO";
        if (responseText.contains("neutro")) return "NEUTRO";

        return "error";
    }
}
