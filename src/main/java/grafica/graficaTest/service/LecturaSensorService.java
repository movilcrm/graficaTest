package grafica.graficaTest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grafica.graficaTest.model.LecturaSensorModel;
import grafica.graficaTest.repository.LecturaSensorRepository;



@Service
public class LecturaSensorService {

    @Autowired
    private LecturaSensorRepository repository;

    public List<LecturaSensorModel> consultarHistorico(
            Integer idLocal,
            Integer idTanque,
            Integer idSensor) {

        return repository.consultarHistorico(
                idLocal,
                idTanque,
                idSensor);

    }
    
    public List<LecturaSensorModel> consultarHistoricoxFecha(
    		Integer idLocal,
    		Integer idSensor,
            Integer idTanque,
            String fechaInicio,
            String fechaFin) {
    	return repository.consultarHistoricoxFecha(idLocal, idSensor, idTanque, fechaInicio, fechaFin);
    }
    
    
    public void guardar(LecturaSensorModel lectura) {

        repository.save(lectura);

    }
    
}