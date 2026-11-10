package grafica.graficaTest.controller;

import grafica.graficaTest.DTO.MobileSensorResponse;
import grafica.graficaTest.model.IrcaSensores;
import grafica.graficaTest.service.SensorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mobile/sensores")
public class MobileSensoresController {

    @Autowired
    private SensorService sensoresService;

    @GetMapping
    public ResponseEntity<?> obtenerSensores() {

        List<IrcaSensores> sensores = sensoresService.obtenerTodosLosSensores();

        List<MobileSensorResponse> respuesta = sensores.stream()
                .map(sensor -> {

                    MobileSensorResponse dto = new MobileSensorResponse();

                    dto.setIdSensor(sensor.getIdSensor());
                    dto.setNombreSensor(sensor.getNombreSensor());
                    dto.setNombreMedida(sensor.getNombreMedida());
                    dto.setMinimoRango(sensor.getMinimoRango());
                    dto.setMaximoRango(sensor.getMaximoRango());

                    dto.setEndpoint(obtenerEndpoint(sensor.getIdSensor()));

                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }

    private String obtenerEndpoint(Integer idSensor) {

        switch (idSensor) {
            case 1:
                return "nivelAgua";
            case 2:
                return "Ph";
            case 3:
                return "Turbiedad";
            case 4:
                return "CloroResidual";
            case 5:
                return "Conductividad";
                
            case 6:
                return "Temperatura";
            case 7:
                return "SeñalWifi";
            case 8:
                return "Humedad";
            
            default:
                return null;
        }
    }
}