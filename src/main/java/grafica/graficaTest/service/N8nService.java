package grafica.graficaTest.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class N8nService {

    @Autowired
    private RestTemplate restTemplate;

    // Cambia esta URL cuando pases a producción
    private final String URL_WEBHOOK =
            "http://176.9.17.148:5678/webhook-test/alertaWebHook";
    
    private final String URL_PROD_WEBHOOK =
            "http://176.9.17.148:5678/webhook/alertaWebHook";

    public void enviarWebhook(
            String sensor,
            String tanque,
            String tipo,
            Double valor,
            Double minimo,
            Double maximo,
            Timestamp fecha,
            String observacion,
            String linkGrafica) {

        try {

            Map<String, Object> body = new HashMap<>();

            body.put("sensor", sensor);
            body.put("tanque", tanque);
            body.put("tipo", tipo);
            body.put("valor", valor);
            body.put("minimo", minimo);
            body.put("maximo", maximo);

            SimpleDateFormat formato =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            body.put("fecha", formato.format(fecha));

            body.put("observacion", observacion);
            body.put("linkGrafica", linkGrafica);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> respuesta =
                    restTemplate.postForEntity(
                    		URL_PROD_WEBHOOK,
                            request,
                            String.class);

            System.out.println("=====================================");
            System.out.println("Webhook enviado a n8n");
            System.out.println("Respuesta HTTP: " + respuesta.getStatusCode());
            System.out.println("=====================================");

        } catch (Exception e) {

            System.out.println("=====================================");
            System.out.println("ERROR ENVIANDO WEBHOOK A N8N");
            System.out.println(e.getMessage());
            System.out.println("=====================================");

        }

    }

}