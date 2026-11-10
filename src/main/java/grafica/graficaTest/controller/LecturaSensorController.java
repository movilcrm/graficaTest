package grafica.graficaTest.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import grafica.graficaTest.DTO.MedicionRequest;
import grafica.graficaTest.model.LecturaSensorModel;
import grafica.graficaTest.service.LecturaSensorService;
import grafica.graficaTest.model.IrcaAlertas;
import grafica.graficaTest.model.IrcaLocalesTanque;
import grafica.graficaTest.model.IrcaSensores;
import grafica.graficaTest.service.SensorService;
import grafica.graficaTest.service.IrcaAlertasService;
import grafica.graficaTest.service.IrcaLocalesTanqueService;



@RestController
public class LecturaSensorController {


    @Autowired
    private LecturaSensorService service;
    
    @Autowired
    private SensorService sensorService;
    
    @Autowired
    private IrcaLocalesTanqueService ircaLocalesTanqueService;
    
    @Autowired
    private IrcaAlertasService ircaAlertasService;


    
    
    @GetMapping("/api/tanques")
    public ResponseEntity<?> obtenerTanques(HttpServletRequest request) {

        Integer idLocal =
                (Integer) request.getSession().getAttribute("idLocal");

        if (idLocal == null) {

            return ResponseEntity.status(401)
                    .body("No existe un local en la sesión");

        }

        List<IrcaLocalesTanque> tanques =
                ircaLocalesTanqueService.obtenerTanquesPorLocal(idLocal);

        return ResponseEntity.ok(tanques);

    }
    

    @GetMapping("/api/nivelAgua")
    public Map<String,Object> consultarHistoricoNivelAgua(HttpServletRequest request,
            @RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }

        List<LecturaSensorModel> lecturas;

        int idSensorNivelAgua = 1;

        if (fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            lecturas = service.consultarHistorico(idLocal, idSensorNivelAgua, idTanque);

        } else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
                    idLocal,
                    idSensorNivelAgua,
                    idTanque,
                    fechaInicio,
                    fechaFin);
        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (LecturaSensorModel lectura : lecturas) {
            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor()); // valor crudo del sensor
        }

        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);

        return respuesta;
    }
    
    
    
    
    @GetMapping("/api/Ph")
    public Map<String,Object> consultarHistoricoPh(HttpServletRequest request,

    		@RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }
        int idSensorPh = 2;

        List<LecturaSensorModel> lecturas;

        if(fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            lecturas = service.consultarHistorico(idLocal, idSensorPh, idTanque);

        } else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
            		idLocal,
                    idSensorPh,
                    idTanque,
                    fechaInicio,
                    fechaFin);

        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(LecturaSensorModel lectura : lecturas){

            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor());

        }

        // NUEVO
        IrcaSensores sensor = sensorService.obtenerSensor(idSensorPh);

        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);

        // NUEVO
        respuesta.put("minimo", sensor.getMinimoRango());
        respuesta.put("maximo", sensor.getMaximoRango());

        return respuesta;

    }
    
    
    
    @GetMapping("/api/Turbiedad")
    public Map<String,Object> consultarHistoricoTurbiedad(HttpServletRequest request,
    		
    		@RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }
    	
    	int idSensorTurbiedad = 3;

        List<LecturaSensorModel> lecturas;

        if(fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            // Sigue funcionando exactamente igual que hoy
            lecturas = service.consultarHistorico(idLocal, idSensorTurbiedad, idTanque);

        }else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
            		idLocal,
            		idSensorTurbiedad,
            		idTanque,
                    fechaInicio,
                    fechaFin);

        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(LecturaSensorModel lectura : lecturas){

            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor());

        }
        
     // NUEVO
        IrcaSensores sensor = sensorService.obtenerSensor(idSensorTurbiedad);

        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);

        // NUEVO
        respuesta.put("minimo", sensor.getMinimoRango());
        respuesta.put("maximo", sensor.getMaximoRango());
        
        return respuesta;

    }
    
    
    
    @GetMapping("/api/CloroResidual")
    public Map<String,Object> consultarHistoricoCloroResidual(HttpServletRequest request,
    		
    		@RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }

    	int idSensorCloroResidual = 4;

        List<LecturaSensorModel> lecturas;

        if(fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            // Sigue funcionando exactamente igual que hoy
            lecturas = service.consultarHistorico(idLocal, idSensorCloroResidual, idTanque);

        }else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
            		idLocal,
            		idSensorCloroResidual,
            		idTanque,
                    fechaInicio,
                    fechaFin);

        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(LecturaSensorModel lectura : lecturas){

            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor());

        }

        IrcaSensores sensor = sensorService.obtenerSensor(idSensorCloroResidual);
        
        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);
        
     // NUEVO
        respuesta.put("minimo", sensor.getMinimoRango());
        respuesta.put("maximo", sensor.getMaximoRango());

        return respuesta;

    }
    
    
    @GetMapping("/api/Conductividad")
    public Map<String,Object> consultarHistoricoConductividad(HttpServletRequest request,
    		
    		@RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }
    	
    	

    	int idSensorConductividad = 5;

        List<LecturaSensorModel> lecturas;

        if(fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            // Sigue funcionando exactamente igual que hoy
            lecturas = service.consultarHistorico(idLocal, idSensorConductividad, idTanque);

        }else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
            		idLocal,
            		idSensorConductividad,
            		idTanque,
                    fechaInicio,
                    fechaFin);

        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(LecturaSensorModel lectura : lecturas){

            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor());

        }
        
        IrcaSensores sensor = sensorService.obtenerSensor(idSensorConductividad);

        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);
        
        // NUEVO
        respuesta.put("minimo", sensor.getMinimoRango());
        respuesta.put("maximo", sensor.getMaximoRango());

        return respuesta;

    }
    
    
    @GetMapping("/api/Temperatura")
    public Map<String,Object> consultarHistoricoTemperatura(HttpServletRequest request,
    		
    		@RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }


    	int idSensorTemperatura = 6;

        List<LecturaSensorModel> lecturas;

        if(fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            // Sigue funcionando exactamente igual que hoy
            lecturas = service.consultarHistorico(idLocal, idSensorTemperatura, idTanque);

        }else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
            		idLocal,
            		idSensorTemperatura,
            		idTanque,
                    fechaInicio,
                    fechaFin);

        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(LecturaSensorModel lectura : lecturas){

            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor());

        }
        
        IrcaSensores sensor = sensorService.obtenerSensor(idSensorTemperatura);

        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);
        
        // NUEVO
        respuesta.put("minimo", sensor.getMinimoRango());
        respuesta.put("maximo", sensor.getMaximoRango());

        return respuesta;

    }

   
    @GetMapping("/api/SeñalWifi")
    public Map<String,Object> consultarHistoricoSeñalWifi(HttpServletRequest request,
    		
    		@RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }

    	int idSensorSeñalWifi = 7;

        List<LecturaSensorModel> lecturas;

        if(fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            // Sigue funcionando exactamente igual que hoy
            lecturas = service.consultarHistorico(idLocal, idSensorSeñalWifi, idTanque);

        }else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
            		idLocal,
            		idSensorSeñalWifi,
            		idTanque,
                    fechaInicio,
                    fechaFin);

        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(LecturaSensorModel lectura : lecturas){

            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor());

        }
        
        IrcaSensores sensor = sensorService.obtenerSensor(idSensorSeñalWifi);

        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);
        
        // NUEVO
        respuesta.put("minimo", sensor.getMinimoRango());
        respuesta.put("maximo", sensor.getMaximoRango());

        return respuesta;

    }
    
    
    @GetMapping("/api/Humedad")
    public Map<String,Object> consultarHistoricoHumedad(HttpServletRequest request,
    		
    		@RequestParam(required = false) Integer idLocal,
            @RequestParam(required = false) Integer idTanque,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (idLocal == null) {
            idLocal = (Integer) request.getSession().getAttribute("idLocal");
        }
    	
    	int idSensorHumedad = 8;

        List<LecturaSensorModel> lecturas;

        if(fechaInicio == null || fechaInicio.isEmpty()
                || fechaFin == null || fechaFin.isEmpty()) {

            // Sigue funcionando exactamente igual que hoy
            lecturas = service.consultarHistorico(idLocal, idSensorHumedad, idTanque);

        }else {

            fechaInicio = convertirFecha(fechaInicio);
            fechaFin = convertirFecha(fechaFin);

            lecturas = service.consultarHistoricoxFecha(
            		idLocal,
            		idSensorHumedad,
            		idTanque,
                    fechaInicio,
                    fechaFin);

        }

        List<String> fechas = new ArrayList<>();
        List<Double> niveles = new ArrayList<>();

        SimpleDateFormat formato =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(LecturaSensorModel lectura : lecturas){

            fechas.add(formato.format(lectura.getFechaHora()));
            niveles.add(lectura.getValor());

        }
        
        IrcaSensores sensor = sensorService.obtenerSensor(idSensorHumedad);

        Map<String,Object> respuesta = new HashMap<>();

        respuesta.put("fechas", fechas);
        respuesta.put("niveles", niveles);
        
     // NUEVO
        respuesta.put("minimo", sensor.getMinimoRango());
        respuesta.put("maximo", sensor.getMaximoRango());

        return respuesta;

    }
    
    @GetMapping("/api/alerta/{idAlerta}")
    public ResponseEntity<?> obtenerAlerta(
            @PathVariable Integer idAlerta) {

        Optional<IrcaAlertas> alerta =
        		ircaAlertasService.obtenerPorId(idAlerta);

        if (alerta.isEmpty()) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(alerta.get());

    }
    
    
    
    private String convertirFecha(String fecha){

        if(fecha == null || fecha.isEmpty()){
            return fecha;
        }

        return fecha.replace("T", " ") + ":00";

    }
    

}