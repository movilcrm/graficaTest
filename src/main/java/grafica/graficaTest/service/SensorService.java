package grafica.graficaTest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grafica.graficaTest.model.IrcaSensores;
import grafica.graficaTest.repository.SensorRepository;

@Service
public class SensorService {

    @Autowired
    private SensorRepository repository;

    public IrcaSensores obtenerSensor(Integer idSensor) {

        Optional<IrcaSensores> sensor = repository.findById(idSensor);

        if(sensor.isPresent()) {
            return sensor.get();
        }

        return null;
    }
    
    public List<IrcaSensores> obtenerTodosLosSensores() {
        return repository.findAll();
    }

}