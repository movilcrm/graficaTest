package grafica.graficaTest.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grafica.graficaTest.model.IrcaLocalesTanque;
import grafica.graficaTest.repository.IrcaLocalesTanqueRepository;



@Service
public class IrcaLocalesTanqueService {

    @Autowired
    private IrcaLocalesTanqueRepository repository;

    public String ObtenerNombreTanque(int idLocal, int idTanque) {
    	
    	String nombreTanque = repository.ObtenerNombreTanque(idLocal, idTanque);

        return nombreTanque;
    }
    
    public Double ObtenerProfundidadTanque(int idLocal, int idTanque) {
    	
    	Double profundidadTanque = repository.ObtenerProfundidadTanque(idLocal, idTanque);

        return profundidadTanque;
    }
    
    public List<IrcaLocalesTanque> obtenerTanquesPorLocal(Integer idLocal) {

        return repository.obtenerTanquesPorLocal(idLocal);

    }
    
    
    
}