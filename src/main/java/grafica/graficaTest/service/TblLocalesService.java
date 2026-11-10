package grafica.graficaTest.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import grafica.graficaTest.repository.TblLocalesRepo;



@Service
public class TblLocalesService {

    @Autowired
    private TblLocalesRepo repository;

    public String ObtenerRazonSocial(int idLocal) {
    	
    	String RazonSocial = repository.ObtenerRazonSocial(idLocal);

        return RazonSocial;
    }
    
    
}