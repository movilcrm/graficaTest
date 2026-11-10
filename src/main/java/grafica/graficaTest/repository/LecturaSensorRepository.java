package grafica.graficaTest.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import grafica.graficaTest.model.LecturaSensorId;
import grafica.graficaTest.model.LecturaSensorModel;

public interface LecturaSensorRepository
		extends JpaRepository<LecturaSensorModel, LecturaSensorId> {

    @Query(value =
            "SELECT * " +
            "FROM tblIrcaLocalesSensor " +
            "WHERE idLocal = ?1 " +
            "AND idSensor = ?2 " +
            "AND idTanque = ?3 " +
            "ORDER BY fechaHora",
            nativeQuery = true)
    List<LecturaSensorModel> consultarHistorico(Integer idLocal, Integer idSensor, Integer idTanque);

    
    @Query(value =
            "SELECT * " +
            "FROM tblIrcaLocalesSensor " +
            "WHERE idLocal = ?1 " +
            "AND idSensor = ?2 " +
            "AND idTanque = ?3 " +
            "AND fechaHora BETWEEN ?4 AND ?5 " +
            "ORDER BY fechaHora",
            nativeQuery = true)
    List<LecturaSensorModel> consultarHistoricoxFecha(
    		Integer idLocal,
    		Integer idSensor,
    		Integer idTanque,
            String fechaInicio,
            String fechaFin);
    
}