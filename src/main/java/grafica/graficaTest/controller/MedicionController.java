package grafica.graficaTest.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import grafica.graficaTest.DTO.ApiResponse;
import grafica.graficaTest.DTO.MedicionRequest;
import grafica.graficaTest.DTO.SensoresRequest;
import grafica.graficaTest.model.IrcaAlertas;
import grafica.graficaTest.model.IrcaSensores;
import grafica.graficaTest.model.LecturaSensorModel;
import grafica.graficaTest.service.IrcaAlertasService;
import grafica.graficaTest.service.IrcaLocalesTanqueService;
import grafica.graficaTest.service.LecturaSensorService;
import grafica.graficaTest.service.N8nService;
import grafica.graficaTest.service.SensorService;

@RestController
@RequestMapping("/api")
public class MedicionController {

    @Autowired
    private LecturaSensorService service;
    
    @Autowired
    private SensorService sensorService;
    
    @Autowired
    private IrcaLocalesTanqueService tanqueService;
    
    @Autowired
    private IrcaAlertasService alertasService;
    
    @Autowired
    private N8nService n8nService;

    @PostMapping("/mediciones")
    public ResponseEntity<ApiResponse> guardar(
            @RequestBody MedicionRequest request) {

        try {
        	
        	

            LecturaSensorModel lectura = new LecturaSensorModel();

            lectura.setIDLOCAL(request.getIdLocal());
            lectura.setIdTanque(request.getIdTanque());
            lectura.setIdSensor(request.getIdSensor());

            
            lectura.setValor(request.getValor());
            
            Timestamp fecha = Timestamp.valueOf(request.getFechaHora());

            lectura.setFechaHora(fecha);

            service.guardar(lectura);

            ApiResponse respuesta = new ApiResponse(
                    true,
                    200,
                    "Medición almacenada correctamente"
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {

            ApiResponse respuesta = new ApiResponse(
                    false,
                    500,
                    "Error al almacenar la medición",
                    e.getMessage()
            );

            return ResponseEntity.internalServerError().body(respuesta);

        }

    }
    
    
    @PostMapping("/mediciones/lote")
    public ResponseEntity<ApiResponse> guardar(
            @RequestBody SensoresRequest request) {

        try {

            for (MedicionRequest medicion : request.getSensores()) {

                LecturaSensorModel lectura = new LecturaSensorModel();

                lectura.setIDLOCAL(medicion.getIdLocal());
                lectura.setIdTanque(medicion.getIdTanque());
                lectura.setIdSensor(medicion.getIdSensor());

                lectura.setValor(medicion.getValor());
                
                Timestamp fecha = Timestamp.valueOf(medicion.getFechaHora());

                lectura.setFechaHora(fecha);
                
                

                service.guardar(lectura);
                
                procesarAlerta(lectura); 
            }

            ApiResponse respuesta = new ApiResponse(
                    true,
                    200,
                    "Mediciones almacenadas correctamente"
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {

            ApiResponse respuesta = new ApiResponse(
                    false, 
                    500,
                    "Error al almacenar las mediciones",
                    e.getMessage()
            );

            return ResponseEntity.internalServerError().body(respuesta);

        }
    }
    
    private String convertirFecha(String fecha){

        if(fecha == null || fecha.isEmpty()){
            return fecha;
        }

        return fecha.replace("T", " ") + ":00";

    }
    
    
    private void procesarAlerta(LecturaSensorModel lectura) {

        Double minimo;
        Double maximo;
        Double valor = lectura.getValor();

        // ==========================================
        // 1. Obtener información del sensor
        // ==========================================

        IrcaSensores sensor =
                sensorService.obtenerSensor(
                        lectura.getIdSensor());

        if (sensor == null) {
            return;
        }

        // ==========================================
        // 2. Obtener nombre del tanque
        // ==========================================

        String nombreTanque =
                tanqueService.ObtenerNombreTanque(
                        lectura.getIDLOCAL(),
                        lectura.getIdTanque());

        // ==========================================
        // 3. Obtener límites
        // ==========================================

        if (lectura.getIdSensor() == 1) {

            Double profundidad =
                    tanqueService.ObtenerProfundidadTanque(
                            lectura.getIDLOCAL(),
                            lectura.getIdTanque());

            if (profundidad == null || profundidad == 0) {
                return;
            }

            // Convertir distancia a porcentaje
            valor =
                    ((profundidad - valor) / profundidad) * 100;

            // Evitar valores fuera de 0-100
            valor = Math.max(0, Math.min(100, valor));

            minimo = 10.0;
            maximo = 100.0;

        } else {

            minimo = sensor.getMinimoRango();
            maximo = sensor.getMaximoRango();
        }

        // ==========================================
        // 4. Verificar si está fuera de rango
        // ==========================================

        boolean fueraDeRango =
                valor < minimo || valor > maximo;

        // ==========================================
        // 5. Buscar alerta activa
        // ==========================================

        Optional<IrcaAlertas> alertaActiva =
                alertasService.obtenerAlertaActiva(
                        lectura.getIDLOCAL(),
                        lectura.getIdTanque(),
                        lectura.getIdSensor());

        // ==========================================
        // Información de depuración
        // ==========================================

        System.out.println("---------------------------");
        System.out.println("Local          : " + lectura.getIDLOCAL());
        System.out.println("Tanque         : " + nombreTanque);
        System.out.println("idTanque       : " + lectura.getIdTanque());
        System.out.println("Sensor         : " + sensor.getNombreSensor());
        System.out.println("idSensor       : " + lectura.getIdSensor());
        System.out.println("Valor          : " + valor);
        System.out.println("Minimo         : " + minimo);
        System.out.println("Maximo         : " + maximo);
        System.out.println("Fuera de rango : " + fueraDeRango);
        System.out.println("Alerta activa  : " + alertaActiva.isPresent());

        // ==========================================
        // 6. CREAR ALERTA
        // ==========================================

        if (fueraDeRango && !alertaActiva.isPresent()) {

            System.out.println(">>> Debe crearse una nueva alerta <<<");

            // --------------------------------------
            // Guardar alerta
            // --------------------------------------

            IrcaAlertas alerta =
                    crearAlerta(
                            lectura,
                            sensor,
                            nombreTanque,
                            valor,
                            minimo,
                            maximo);

            // --------------------------------------
            // AQUÍ YA TENEMOS EL ID DE LA ALERTA
            // --------------------------------------

            Integer idAlerta =
                    alerta.getIdAlerta();

            System.out.println("ID ALERTA GENERADA: " + idAlerta);

            // --------------------------------------
            // Crear enlace para WhatsApp
            // --------------------------------------

            String linkGrafica =
                    "https://lab.mobile-tic.com/IRCA/?idAlerta="
                    + idAlerta;

            System.out.println("LINK GRÁFICA:");
            System.out.println(linkGrafica);

            // --------------------------------------
            // Enviar alerta a n8n
            // --------------------------------------

            n8nService.enviarWebhook(
                    sensor.getNombreSensor(),
                    nombreTanque,
                    alerta.getTipo(),
                    alerta.getValor(),
                    alerta.getMinimo(),
                    alerta.getMaximo(),
                    alerta.getFechaInicio(),
                    alerta.getObservacion(),
                    linkGrafica
            );

            System.out.println(">>> Alerta enviada a n8n <<<");

            return;
        }

        // ==========================================
        // 7. CERRAR ALERTA
        // ==========================================

        if (!fueraDeRango && alertaActiva.isPresent()) {

            cerrarAlerta(
                    alertaActiva.get(),
                    lectura);

            return;
        }
    }
    
    private IrcaAlertas crearAlerta(
            LecturaSensorModel lectura,
            IrcaSensores sensor,
            String nombreTanque,
            Double valor,
            Double minimo,
            Double maximo) {

        IrcaAlertas alerta = new IrcaAlertas();

        alerta.setIdLocal(lectura.getIDLOCAL());
        alerta.setIdTanque(lectura.getIdTanque());
        alerta.setIdSensor(lectura.getIdSensor());

        alerta.setFechaInicio(lectura.getFechaHora());
        alerta.setFechaFin(null);

        alerta.setValor(valor);
        alerta.setMinimo(minimo);
        alerta.setMaximo(maximo);

        if (valor < minimo) {
            alerta.setTipo("BAJO");
        } else {
            alerta.setTipo("ALTO");
        }

        alerta.setEstado("ACTIVA");

        String observacion = generarObservacion(
                sensor,
                nombreTanque,
                valor,
                minimo,
                maximo,
                lectura.getFechaHora());

        alerta.setObservacion(observacion);

        IrcaAlertas alertaGuardada = alertasService.guardar(alerta);
        
        String linkAlerta =
                "https://lab.mobile-tic.com/IRCA/?idAlerta="
                + alertaGuardada.getIdAlerta();

        System.out.println("Link de alerta: " + linkAlerta);
        
        alertaGuardada.getIdAlerta();

        System.out.println("=======================================");
        System.out.println("ALERTA CREADA CORRECTAMENTE");
        System.out.println(observacion);
        System.out.println("=======================================");

        System.out.println("Id Alerta: " + alerta.getIdAlerta());
        
        return alertaGuardada;
    }
    
    
    private String generarObservacion(
            IrcaSensores sensor,
            String nombreTanque,
            Double valor,
            Double minimo,
            Double maximo,
            Timestamp fecha) {

        SimpleDateFormat formato =
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String fechaTexto = formato.format(fecha);

        if (valor < minimo) {

            return "Alerta de monitoreo: El sensor "
                    + sensor.getNombreSensor()
                    + " del tanque "
                    + nombreTanque
                    + " registró un valor de "
                    + valor
                    + " el día "
                    + fechaTexto
                    + ", por debajo del mínimo permitido de "
                    + minimo
                    + ".";

        }

        return "Alerta de monitoreo: El sensor "
                + sensor.getNombreSensor()
                + " del tanque "
                + nombreTanque
                + " registró un valor de "
                + valor
                + " el día "
                + fechaTexto
                + ", superando el máximo permitido de "
                + maximo
                + ".";

    }
    
    
    private void cerrarAlerta(
            IrcaAlertas alerta,
            LecturaSensorModel lectura) {

        alerta.setFechaFin(lectura.getFechaHora());

        alerta.setEstado("CERRADA");

        alertasService.guardar(alerta);

        System.out.println("=======================================");
        System.out.println("ALERTA CERRADA");
        System.out.println("Id Alerta : " + alerta.getIdAlerta());
        System.out.println("Fecha Fin : " + lectura.getFechaHora());
        System.out.println("=======================================");

    }
    

}