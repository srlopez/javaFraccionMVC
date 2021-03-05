package aritmetica;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Operacion {
	public Date fh;
	public OperacionTipo tipo;
	public Fraccion f1;
	public Fraccion f2;
	public Fraccion resultado;

	public Operacion(Date fh, OperacionTipo tipo, Fraccion f1, Fraccion f2, Fraccion resultado) {
		this.fh = fh;
		this.tipo = tipo;
		this.f1 = f1;
		this.f2 = f2;
		this.resultado = resultado;
	}
	
	public Operacion(OperacionTipo tipo, Fraccion f1, Fraccion f2, Fraccion resultado) {
		this(new Date(),tipo, f1, f2, resultado);
	}
	
    public String toString() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(fh) + "\t" + f1.toString() + " " + tipo.toString()
		+ " " + f2.toString() + " = " + resultado.toString();

    }
}
