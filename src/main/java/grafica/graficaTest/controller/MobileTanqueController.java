package grafica.graficaTest.controller;

import grafica.graficaTest.DTO.IRCAlocalesTanquesDTO;
import grafica.graficaTest.model.IrcaLocalesTanque;
import grafica.graficaTest.service.IrcaLocalesTanqueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mobile/tanques")
public class MobileTanqueController {

    @Autowired
    private IrcaLocalesTanqueService tanqueService;

    @GetMapping
    public ResponseEntity<?> obtenerTanques(HttpServletRequest request) {

        Integer idLocal = (Integer) request.getAttribute("idLocal");

        if (idLocal == null) {
            return ResponseEntity.status(401).body("Token inválido o ausente");
        }

        List<IrcaLocalesTanque> tanques =
                tanqueService.obtenerTanquesPorLocal(idLocal);

        List<IRCAlocalesTanquesDTO> respuesta = tanques.stream()
                .map(t -> new IRCAlocalesTanquesDTO(t.getIdTanque(), t.getNombreTanque()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }
}