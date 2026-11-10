package grafica.graficaTest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alerta")
public class IrcaAlertaController {

    @GetMapping("/{idAlerta}")
    public String verAlerta(@PathVariable Integer idAlerta) {

        return "Entraste a la alerta: " + idAlerta;

    }

}