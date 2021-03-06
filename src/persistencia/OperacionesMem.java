package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aritmetica.Fraccion;
import aritmetica.Operacion;
import aritmetica.OperacionTipo;

public class OperacionesMem implements IOperacionesDAO {

	String filename = "data/operaciones.txt";
	List<Operacion> ops = new ArrayList<Operacion>();

	@Override
	public void inicializar() {
		File miFile = new File(filename);
		if (miFile.exists()) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(filename));
				String line = reader.readLine();
				System.out.println(line);
				while (line != null) {
					try {
						Operacion op = lineToOperacion(line);
						ops.add(op);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Operacion lineToOperacion(String line) throws Exception {
		String[] item = line.split("\\s");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date fh = dateFormat.parse(item[0] + " " + item[1]);

		return new Operacion(fh, item[3].equals("+") ? OperacionTipo.SUMA : OperacionTipo.MULTIPLICACION,
				new Fraccion(item[2]), new Fraccion(item[4]), new Fraccion(item[6]));

	}

	@Override
	public int cmdRegistrarOperacion(Operacion op) throws Exception {
		ops.add(op);
		return ops.size();
	}

	@Override
	public List<Operacion> qryOperacionesPor(Fraccion f) throws Exception {
		List<Operacion> rops = new ArrayList<Operacion>();
		for (Operacion op : ops) {
			int n = f.getNumerador();
			int d = f.getDenominador();
			boolean nr = op.resultado.getNumerador() == n;
			boolean dr = op.resultado.getDenominador() == d;
			boolean n1 = op.f1.getNumerador() == n;
			boolean d1 = op.f1.getDenominador() == d;
			boolean n2 = op.f2.getNumerador() == n;
			boolean d2 = op.f2.getDenominador() == d;
			if ((nr && dr) || (n1 && d1) || (n2 && d2))
				rops.add(op);
		}
		return rops;
	}

	private void add1ToF(Map<String, Integer> ranking, String f) {
		int v = ranking.getOrDefault(f, 0);
		ranking.put(f, v + 1);
	}

	@Override
	public List<String> qryRanking() throws Exception {
		// Obtención del contador de apariciones de cada Fracción
		Map<String, Integer> ranking = new HashMap<String, Integer>();
		for (Operacion op : ops) {
			add1ToF(ranking, op.f1.toString());
			add1ToF(ranking, op.f2.toString());
			add1ToF(ranking, op.resultado.toString());
		}
		// Aplicación de programación funcional
		// para obtener el resultado.
		return ranking.entrySet()
		  .stream()
		  .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
		  .limit(3)
		  .map(e -> e.getKey() + " " + e.getValue())
		  .collect(Collectors.toList());
    }

	@Override
	public List<Operacion> qryResultadosImpropios() throws Exception {
		List<Operacion> rops = new ArrayList<Operacion>();
		for (Operacion op : ops) {
			int n = java.lang.Math.abs(op.resultado.getNumerador());
			int d = java.lang.Math.abs(op.resultado.getDenominador());
			if (n > d)
				rops.add(op);
		}
		return rops;
	}

	@Override
	public List<Operacion> qryTodasLasOperaciones() throws Exception {
		return ops;
	}

	@Override
	public void finalizar() throws Exception {
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			for (Operacion op : ops) {
				fw.write(op.toString() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			throw e;
		}
	}

}
