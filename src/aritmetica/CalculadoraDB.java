package aritmetica;

import java.sql.SQLException;
import java.util.List;

import persistencia.IAritmeticaDAO;

public class CalculadoraDB extends Calculadora {

	IAritmeticaDAO repositorio;

	public CalculadoraDB(IAritmeticaDAO repositorio) {
		this.repositorio = repositorio;
		this.repositorio.inicializar();
	}

	@Override
	public Fraccion suma(Fraccion f1, Fraccion f2) throws Exception {
		try {
			Fraccion result = super.suma(f1, f2);
			cmdRegistrarOperacion("+", f1, f2, result);
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Fraccion multiplica(Fraccion f1, Fraccion f2) throws Exception {
		try {
			Fraccion result = super.multiplica(f1, f2);
			cmdRegistrarOperacion("*", f1, f2, result);
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	private void cmdRegistrarOperacion(String op, Fraccion f1, Fraccion f2, Fraccion fr) throws Exception {
		int idOp = repositorio.cmdRegistrarOperacion("+", f1, f2, fr);
		repositorio.cmdRegistrarOperando(fr, idOp, 0);
		repositorio.cmdRegistrarOperando(f1, idOp, 1);
		repositorio.cmdRegistrarOperando(f2, idOp, 2);
	}

	public List<String> qryOperacionesPor(Fraccion f1) throws Exception {
		return repositorio.qryOperacionesPor(f1);		
	}	
	
	public List<String> qryRanking() throws Exception {
		return repositorio.qryRanking();		
	}

	public List<String> qryResultadosImpropios() throws Exception {
		return repositorio.qryResultadosImpropios();		

	}

	public List<String> qryTodaslasOperaciones()throws Exception {
		return repositorio.qryTodasLasOperaciones();		
	}
}
