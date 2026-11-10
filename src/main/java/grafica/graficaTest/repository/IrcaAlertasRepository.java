package grafica.graficaTest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import grafica.graficaTest.model.IrcaAlertas;

public interface IrcaAlertasRepository extends JpaRepository<IrcaAlertas, Integer> {

    @Query(value =
            "SELECT * " +
            "FROM tblIrcaAlertas " +
            "WHERE idLocal = ?1 " +
            "AND idTanque = ?2 " +
            "AND idSensor = ?3 " +
            "AND estado = 'ACTIVA'",
            nativeQuery = true)
    Optional<IrcaAlertas> obtenerAlertaActiva(Integer idLocal,
                                              Integer idTanque,
                                              Integer idSensor);
    
    
    @Query(value =
    					" SELECT [idAlerta]         "   
    				+ "         ,[idLocal]      "   
    				+ "         ,[idTanque]     "   
    				+ "         ,[idSensor]     "   
    				+ "         ,[fechaInicio]  "   
    				+ "         ,[fechaFin]     "   
    				+ "         ,[valor]        "   
    				+ "         ,[minimo]       "   
    				+ "         ,[maximo]       "   
    				+ "         ,[tipo]         "   
    				+ "         ,[estado]       "   
    				+ "         ,[observacion]  " 
    				+ "		FROM tblIrcaAlertas " 
            		+            "WHERE idLocal = ?1 " 
            		+ "AND estado = 'ACTIVA'",
            nativeQuery = true)
    List<IrcaAlertas> obtenerAlertasActivasPorLocal(Integer idLocal);
    
    
    @Query(value =
            "SELECT TOP 10 " +
            "     [idAlerta] " +
            "    ,[idLocal] " +
            "    ,[idTanque] " +
            "    ,[idSensor] " +
            "    ,[fechaInicio] " +
            "    ,[fechaFin] " +
            "    ,[valor] " +
            "    ,[minimo] " +
            "    ,[maximo] " +
            "    ,[tipo] " +
            "    ,[estado] " +
            "    ,[observacion] " +
            "FROM tblIrcaAlertas " +
            "WHERE idLocal = ?1 " +
            "ORDER BY fechaInicio DESC",
            nativeQuery = true)
    List<IrcaAlertas> obtenerUltimasAlertasPorLocal(Integer idLocal);

}