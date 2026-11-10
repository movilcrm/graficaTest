package grafica.graficaTest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import grafica.graficaTest.model.IrcaLocalesTanque;
import grafica.graficaTest.model.TblLocales;

public interface IrcaLocalesTanqueRepository
		extends JpaRepository<IrcaLocalesTanque, Integer> {

	@Query(value = "SELECT tblIrcaLocalesTanque.nombreTanque " + 
			"FROM BDMailMarketing.dbo.tblIrcaLocalesTanque " +
			"WHERE tblIrcaLocalesTanque.idLocal = ?1 " +
			"AND tblIrcaLocalesTanque.idTanque = ?2 ",
			nativeQuery = true)
	String ObtenerNombreTanque(int idLocal, int idTanque);
	
	@Query(value = "SELECT tblIrcaLocalesTanque.profundo " + 
			"FROM BDMailMarketing.dbo.tblIrcaLocalesTanque " +
			"WHERE tblIrcaLocalesTanque.idLocal = ?1 " +
			"AND tblIrcaLocalesTanque.idTanque = ?2 ",
			nativeQuery = true)
	Double ObtenerProfundidadTanque(int idLocal, int idTanque);
	
	@Query(value =
	        "SELECT * " +
	        "FROM tblIrcaLocalesTanque " +
	        "WHERE idLocal = ?1",
	        nativeQuery = true)
	List<IrcaLocalesTanque> obtenerTanquesPorLocal(Integer idLocal);

}