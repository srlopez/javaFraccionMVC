package br;

import java.util.Calendar;

import aritmetica.Fraccion;

/**
 * Requisitos NO FUNCIONALES
 * Reglas de Negocio
 */
public class Requisitos {
	// Una fraccion debe ser PROPIA
	public static boolean regla1(Fraccion f) throws Exception {
		return  rethrow(f.esPropia(), f+"REQUISITO: No es propia") ;
	}

	// Solo trabajamos de 8:00 a 15:00
	public static boolean regla2() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		int hora = rightNow.get(Calendar.HOUR_OF_DAY);
		return  rethrow((hora>=8 && hora<15), "REQUISITO: Fuera de horario: "+hora) ;
	}

	private static boolean rethrow( boolean exprCierta, String msg) throws Exception {
		if(!exprCierta) throw new Exception(msg);
		return true;
	}
}
