package aritmetica;

import java.sql.SQLException;
import java.util.List;

import persistencia.IOperacionesDAO;

public class CalculadoraDB extends Calculadora {

	IOperacionesDAO repositorio;

	public CalculadoraDB(IOperacionesDAO repositorio) {
		this.repositorio = repositorio;
		this.repositorio.inicializar();
	}

	@Override
	public Fraccion suma(Fraccion f1, Fraccion f2) throws Exception {
		try {
			Fraccion result = super.suma(f1, f2);
			registrarOperacion(OperacionTipo.SUMA, f1, f2, result);
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Fraccion multiplica(Fraccion f1, Fraccion f2) throws Exception {
		try {
			Fraccion result = super.multiplica(f1, f2);
			registrarOperacion(OperacionTipo.MULTIPLICACION, f1, f2, result);
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	private void registrarOperacion(OperacionTipo tipo, Fraccion f1, Fraccion f2, Fraccion fr) throws Exception {
		int idOp = repositorio.cmdRegistrarOperacion(new Operacion(tipo, f1, f2, fr));
	}

	public List<Operacion> qryOperacionesPor(Fraccion f1) throws Exception {
		return repositorio.qryOperacionesPor(f1);
	}

	public List<String> qryRanking() throws Exception {
		return repositorio.qryRanking();
	}

	public List<Operacion> qryResultadosImpropios() throws Exception {
		return repositorio.qryResultadosImpropios();

	}

	public List<Operacion> qryTodaslasOperaciones() throws Exception {
		return repositorio.qryTodasLasOperaciones();
	}

	public  void finalizar() 
	{	
		try {
			this.repositorio.finalizar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
