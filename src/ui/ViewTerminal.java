package ui;

import aritmetica.Fraccion;
import aritmetica.Operacion;

import java.util.List;
import java.util.Scanner;

public class ViewTerminal {
	protected Scanner input = new Scanner(System.in);

	private String leerFraccionString() {
		System.out.print("Indica una fracción (0/1): ");
		return input.next();
	}

	public Fraccion leerFraccion() {
		try {
			return new Fraccion(leerFraccionString());
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			throw e;
		}
	}

	public void mostrarMsg(String format, Object... args) {
		System.out.printf(format, args);
		System.out.println();
	}

	// MENU
	public char mostrarMenu() {
		System.out.println(" MENU");
		System.out.println(" 1.- Sumar dos fracciones");
		System.out.println(" 2.- Multiplicar dos fracciones");
		System.out.println(" 3.- Ranking de fracciones");
		System.out.println(" 4.- Consultar operaciones");
		System.out.println(" 5.- Todas las operaciones");
		System.out.println(" 6.- Resultados impropios");
		System.out.println(" F.- FIN");
		return input.next().toUpperCase().charAt(0);
	}

	public void mostrarOperaciones(List<Operacion> list) {
		for (Operacion op : list) {
			System.out.println(op);
		}
	}
	public void mostrarResultados(List<String> list) {
		for (String text : list) {
			System.out.println(text);
		}
	}
}
