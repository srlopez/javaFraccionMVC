package br;

import java.util.Calendar;

import aritmetica.Fraccion;

/**
 * Requisitos Lógicos - NO FUNCIONALES
 * REGLAS DE NEGOCIO
 */
public class Requisitos {
	/**
	 * Requisito Lógico 1
	 * La fracción que operamos debe ser 'propia'
	 * @param f
	 * @return Si se cumple la el requisito
	 * @throws Exception
	 */
	// Una fraccion debe ser PROPIA
	public static boolean rl1FraccionPropia(Fraccion f) throws Exception {
		return  rethrow(f.esPropia(), "RL1 Fracción impropia ("+f+")") ;
	}
	
	/**
	 * Requisito Lógico 2
	 * Solo trabajamos de 8:00 a 15:00
	 * @return Si se cumple la el requisito
	 * @throws Exception
	 */ 
	public static boolean rl2PeriodoValido() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		int hora = rightNow.get(Calendar.HOUR_OF_DAY);
		return  rethrow((hora>=8 && hora<15), "RL2 Fuera de horario ("+hora+")") ;
	}

	private static boolean rethrow( boolean exprCierta, String msg) throws Exception {
		if(!exprCierta) throw new Exception(msg);
		return true;
	}
}
