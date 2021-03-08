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
		
		// Run con un Repositorio SQL/Mem
		// IOperacionesDAO repo = new OperacioneSQLite();
		IOperacionesDAO repo = new OperacionesMem();
		CalculadoraDB sistemaDB = new CalculadoraDB(repo);
		ViewTerminal view = new ViewTerminal();
		CtrlTerminal ctrlt = new CtrlTerminal(sistemaDB, view);
		ctrlt.run();
		
	}
}