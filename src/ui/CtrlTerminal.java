package ui;

import aritmetica.*;
import br.Requisitos;

public class CtrlTerminal {
	CalculadoraDB sistema;
	ViewTerminal vista;

	public CtrlTerminal(CalculadoraDB sistema, ViewTerminal vista) {
		this.sistema = sistema;
		this.vista = vista;
	}

	/**
	 * Ciclo de Ejecución
	 */
	public void run() {
		loop: while (true) {
			char opcion = vista.mostrarMenu();
			switch (opcion) {
			case '1': {
				useCase1();
				break;
			}
			case '2': {
				useCase2();
				break;
			}
			case '3': {
				useCase3();
				break;
			}
			case '4': {
				useCase4();
				break;
			}
			case '5': {
				useCase5();
				break;
			}
			case '6': {
				useCase6();
				break;
			}
			case 'F': {
				break loop;
			}
			default:
			}
		}
		sistema.finalizar();
		vista.mostrarMsg("MVC Fin");
	}

	// import java.util.function.BiFunction;
	// invocación: useCaseOperacion((f1, f2) -> sistema.suma(f1, f2));
	// declaración: void useCaseOperacion(BiFunction<Fraccion, Fraccion, Fraccion>
	// op) {}
	/**
	 * Punto de Entrada UC1
	 */
	public void useCase1() {
		try {
			Fraccion f1 = vista.leerFraccion();
			Fraccion f2 = vista.leerFraccion();
			// Reglas de Negocio
			Requisitos.regla1(f1);
			Requisitos.regla1(f2);
			Requisitos.regla2();
			// Ejecución de Caso de Uso
			Fraccion result = sistema.suma(f1, f2);
			vista.mostrarMsg("Resultado+ %s",result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC2
	 */
	public void useCase2() {
		try {
			Fraccion f1 = vista.leerFraccion();
			Fraccion f2 = vista.leerFraccion();
			// Reglas de Negocio
			Requisitos.regla1(f1);
			Requisitos.regla1(f2);
			Requisitos.regla2();
			// Ejecución de Caso de Uso
			Fraccion result = sistema.multiplica(f1, f2);
			vista.mostrarMsg("Resultado* %s",result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC3
	 */
	public void useCase3() {
		vista.mostrarMsg("UC#3 Ranking");

		try {
			vista.mostrarResultados(sistema.qryRanking());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC4
	 */
	public void useCase4() {
		vista.mostrarMsg("UC#4 Operaciones por Fraccion");
		try {
			Fraccion f1 = vista.leerFraccion();
			vista.mostrarOperaciones(sistema.qryOperacionesPor(f1));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC5
	 */
	public void useCase5() {
		vista.mostrarMsg("UC#5 Todas las operaciones");
		try {
			vista.mostrarOperaciones(sistema.qryTodaslasOperaciones());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Punto de Entrada UC6
	 */
	public void useCase6() {
		vista.mostrarMsg("UC#6 Resultados impropios");
		try {
			vista.mostrarOperaciones(sistema.qryResultadosImpropios());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC9
	 */
	public void useCase9() {
		vista.mostrarMsg("UC#9 No implementado");
	}

}
