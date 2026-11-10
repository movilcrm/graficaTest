package grafica.graficaTest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import grafica.graficaTest.model.TblLocales;

public interface TblLocalesRepo
		extends JpaRepository<TblLocales, Integer> {

	@Query(value = "SELECT tblLocales.razonSocial " + 
			"FROM bdaquamovil.dbo.tblLocales " +
			"WHERE tblLocales.idLocal = ?1 ",
			nativeQuery = true)
	String ObtenerRazonSocial(int idLocal);

}