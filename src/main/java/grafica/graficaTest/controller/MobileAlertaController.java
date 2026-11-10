package grafica.graficaTest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import grafica.graficaTest.model.IrcaAlertas;
import grafica.graficaTest.service.IrcaAlertasService;
import grafica.graficaTest.service.IrcaLocalesTanqueService;

@RestController
@RequestMapping("/api/mobile/alertas")
public class MobileAlertaController {
	
	@Autowired
    private IrcaAlertasService ircaAlertasService;

	@GetMapping("/activas")
	public ResponseEntity<?> obtenerAlertasActivas(HttpServletRequest request) {
		
		Integer idLocal = (Integer) request.getAttribute("idLocal");

	   

	    if (idLocal == null) {
	        return ResponseEntity.status(401)
	                .body("No existe un local en la sesión");
	    }

	    List<IrcaAlertas> alertas =
	            ircaAlertasService.obtenerActivasPorLocal(idLocal);

	    return ResponseEntity.ok(alertas);

	}
	
	@GetMapping("/historico")
	public ResponseEntity<?> obtenerUltimasAlertasPorLocal(HttpServletRequest request) {
		
		Integer idLocal = (Integer) request.getAttribute("idLocal");

	   

	    if (idLocal == null) {
	        return ResponseEntity.status(401)
	                .body("No existe un local en la sesión");
	    }

	    List<IrcaAlertas> alertas =
	            ircaAlertasService.obtenerUltimasAlertasPorLocal(idLocal);

	    return ResponseEntity.ok(alertas);

	}
	
	

}