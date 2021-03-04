package app;

import aritmetica.*;
import persistencia.*;
import ui.*;

public class Main {
	public static void main(String[] args) throws Exception {
		// MVC en modo terminal
		System.out.println("MVC Consola");
//		String dir = System.getProperty("user.dir");
//		System.out.println("Estás corriendo en " + dir);
		
		// Run con un Repositorio
		IAritmeticaDAO repo = new ArtimeticaSQLite();
		CalculadoraDB sistemaDB = new CalculadoraDB(repo);
		ViewTerminal view = new ViewTerminal();
		CtrlTerminal ctrlt = new CtrlTerminal(sistemaDB, view);
		ctrlt.run();

	}
}