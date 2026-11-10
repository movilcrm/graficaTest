package grafica.graficaTest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grafica.graficaTest.model.IrcaAlertas;
import grafica.graficaTest.repository.IrcaAlertasRepository;

@Service
public class IrcaAlertasService {

    @Autowired
    private IrcaAlertasRepository repository;

    public IrcaAlertas guardar(IrcaAlertas alerta){
        return repository.save(alerta);
    }

    public Optional<IrcaAlertas> obtenerAlertaActiva(Integer idLocal,
                                           Integer idTanque,
                                           Integer idSensor){

        return repository.obtenerAlertaActiva(idLocal,idTanque,idSensor);
    }
    
    public Optional<IrcaAlertas> obtenerPorId(Integer idAlerta) {

        return repository.findById(idAlerta);

    }
    
    public List<IrcaAlertas> obtenerActivasPorLocal(Integer idLocal) {
        return repository.obtenerAlertasActivasPorLocal(idLocal);
    }
    
    public List<IrcaAlertas> obtenerUltimasAlertasPorLocal(Integer idLocal) {
        return repository.obtenerUltimasAlertasPorLocal(idLocal);
    }

}