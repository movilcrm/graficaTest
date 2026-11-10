package grafica.graficaTest.service;

import grafica.graficaTest.model.ctrlUsuarios;
import grafica.graficaTest.repository.ctrlUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ctrlUsuariosService {

    @Autowired
    private ctrlUsuariosRepository repository;

    public Optional<ctrlUsuarios> buscarPorUsuarioYClave(
    		Integer idUsuario, String clave) {

        return repository.findByIdUsuarioAndClave(
                idUsuario, clave);

    }
    
    public String obtenerRazonSocial(Integer idUsuario, String clave) {
        return repository.obtenerRazonSocialPorUsuarioYClave(idUsuario, clave);
    }

}