package grafica.graficaTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import grafica.graficaTest.model.IrcaSensores;

@Repository
public interface SensorRepository extends JpaRepository<IrcaSensores, Integer>{

}