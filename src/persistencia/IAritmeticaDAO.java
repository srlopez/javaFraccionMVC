package persistencia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import aritmetica.Fraccion;

public interface IAritmeticaDAO {
	
    void inicializar();

	int cmdRegistrarOperacion(String op, Fraccion f1, Fraccion f2, Fraccion fr) throws Exception ;
	
	void cmdRegistrarOperando(Fraccion f1, int IdOp, int tipo) throws Exception;

	List<String> qryOperacionesPor(Fraccion f) throws Exception ;

	List<String> qryRanking() throws Exception;

	List<String> qryResultadosImpropios() throws Exception;

	List<String> qryTodasLasOperaciones() throws Exception;

}
