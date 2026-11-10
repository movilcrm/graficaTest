package grafica.graficaTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import grafica.graficaTest.model.ctrlUsuarios;

import java.util.Optional;

public interface ctrlUsuariosRepository
        extends JpaRepository<ctrlUsuarios, Integer> {

    @Query(value = "SELECT * " 
           + "FROM bdaquamovil.dbo.ctrlUsuarios " 
           + "WHERE idUsuario = ?1 "
            + "AND clave = ?2 "
           + "AND idNivel = 40 ",
            nativeQuery = true)
    Optional<ctrlUsuarios> findByIdUsuarioAndClave(
            Integer idUsuario, String clave);
    
   
     @Query(value =  "   SELECT tblLocales.razonSocial                	  "                             
        		+ "   FROM     bdaquamovil.dbo.ctrlUsuarios               "   
        		+ "     INNER JOIN  bdaquamovil.dbo.tblLocales            "   
        		+ "   		ON ctrlUsuarios.idLocal = tblLocales.idLocal  "   
        		+ "		  where ctrlUsuarios.idUsuario = ?1               "   
        		+ "   	and ctrlUsuarios.clave = ?2                       " ,
                nativeQuery = true)
        String obtenerRazonSocialPorUsuarioYClave(
                Integer idUsuario, String clave);

    

}