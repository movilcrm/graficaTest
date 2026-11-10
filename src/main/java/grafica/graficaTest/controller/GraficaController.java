package grafica.graficaTest.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import grafica.graficaTest.model.IrcaAlertas;
import grafica.graficaTest.service.IrcaAlertasService;
import grafica.graficaTest.service.IrcaLocalesTanqueService;
import grafica.graficaTest.service.TblLocalesService;

@Controller
public class GraficaController {

    @Autowired
    TblLocalesService tblLocalesService;

    @Autowired
    IrcaLocalesTanqueService tblLocalesTanqueService;

    @Autowired
    IrcaAlertasService ircaAlertasService;


    @GetMapping("/")
    public String inicio(
            HttpServletRequest request,
            Model model,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) Integer idAlerta) {

        Integer idLocal;

        /*
         * =====================================================
         * CASO 1: ENTRADA DESDE UNA ALERTA
         * =====================================================
         */
        if (idAlerta != null) {

            System.out.println("======================================");
            System.out.println("ENTRADA DESDE ALERTA");
            System.out.println("Id Alerta: " + idAlerta);
            System.out.println("======================================");

            Optional<IrcaAlertas> alerta =
                    ircaAlertasService.obtenerPorId(idAlerta);

            if (alerta.isEmpty()) {

                System.out.println("ALERTA NO ENCONTRADA: " + idAlerta);

                return "error";
            }

            IrcaAlertas datosAlerta = alerta.get();

            // El local viene de la alerta
            idLocal = datosAlerta.getIdLocal();

            // Tanque donde ocurrió la alerta
            Integer tanqueAlerta = datosAlerta.getIdTanque();

            // Sensor que generó la alerta
            Integer sensorAlerta = datosAlerta.getIdSensor();


            System.out.println("Id Local: " + idLocal);
            System.out.println("Id Tanque: " + tanqueAlerta);
            System.out.println("Id Sensor: " + sensorAlerta);


            String nombreLocal =
                    tblLocalesService.ObtenerRazonSocial(idLocal);

            String nombreTanque =
                    tblLocalesTanqueService.ObtenerNombreTanque(
                            idLocal,
                            tanqueAlerta);


            model.addAttribute("nombreLocal", nombreLocal);
            model.addAttribute("nombreTanque", nombreTanque);

            model.addAttribute("idAlerta", idAlerta);
            model.addAttribute("idTanque", tanqueAlerta);
            model.addAttribute("idSensor", sensorAlerta);

            model.addAttribute("modoAlerta", true);


        } else {

            /*
             * =====================================================
             * CASO 2: ENTRADA NORMAL DESDE EL SISTEMA
             * =====================================================
             */

            idLocal =
                    (Integer) request.getSession().getAttribute("idLocal");


            System.out.println("======================================");
            System.out.println("ENTRADA NORMAL");
            System.out.println("Id Local sesión: " + idLocal);
            System.out.println("Id Tanque seleccionado: " + idTanque);
            System.out.println("======================================");


            String nombreLocal =
                    tblLocalesService.ObtenerRazonSocial(idLocal);

            model.addAttribute("nombreLocal", nombreLocal);

            model.addAttribute("idTanque", idTanque);

            model.addAttribute("modoAlerta", false);
        }


        /*
         * =====================================================
         * FECHA DEL SERVIDOR
         * =====================================================
         */

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        model.addAttribute(
                "fechaServidor",
                LocalDateTime.now().format(formatter)
        );


        return "monitoreo";
    }
}