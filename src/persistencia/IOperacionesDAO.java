package persistencia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import aritmetica.Fraccion;
import aritmetica.Operacion;

public interface IOperacionesDAO {
	
    void inicializar();
    
    void finalizar() throws Exception;
	
	int cmdRegistrarOperacion(Operacion op) throws Exception ;
	
	List<Operacion> qryOperacionesPor(Fraccion f) throws Exception ;

	List<String> qryRanking() throws Exception;

	List<Operacion> qryResultadosImpropios() throws Exception;

	List<Operacion> qryTodasLasOperaciones() throws Exception;

}
