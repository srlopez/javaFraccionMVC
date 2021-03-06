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
	 * Ciclo de Ejecuci�n
	 */
	public void run() {
		loop: while (true) {
			char opcion = vista.mostrarMenu();
			switch (opcion) {
			case '1': {
				uc1SumaDeFracciones();
				break;
			}
			case '2': {
				uc1MultiplicacionDeFracciones();
				break;
			}
			case '3': {
				uc3RankingDeFracciones();
				break;
			}
			case '4': {
				uc4OperacionesXFraccion();
				break;
			}
			case '5': {
				uc5TodasLasOperaciones();
				break;
			}
			case '6': {
				uc6ResultadosImpropios();
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
	// invocaci�n: useCaseOperacion((f1, f2) -> sistema.suma(f1, f2));
	// declaraci�n: void useCaseOperacion(BiFunction<Fraccion, Fraccion, Fraccion>
	// op) {}
	/**
	 * Punto de Entrada UC1
	 */
	public void uc1SumaDeFracciones() {
		try {
			//Obtencion de informaci�n de usuario
			Fraccion f1 = vista.leerFraccion();
			Fraccion f2 = vista.leerFraccion();
			// Reglas de Negocio
			Requisitos.rl1FraccionPropia(f1);
			Requisitos.rl1FraccionPropia(f2);
			Requisitos.rl2PeriodoValido();
			// Ejecuci�n de Caso de Uso
			Fraccion result = sistema.suma(f1, f2);
			// Presentaci�n al usuario
			vista.mostrarMsg("Resultado+ %s",result);
		} catch (Exception e) {
			vista.mostrarMsg("Excepci�n uc1: %s",e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC2
	 */
	public void uc1MultiplicacionDeFracciones() {
		try {
			//Obtencion de informaci�n de usuario
			Fraccion f1 = vista.leerFraccion();
			Fraccion f2 = vista.leerFraccion();
			// Reglas de Negocio
			Requisitos.rl1FraccionPropia(f1);
			Requisitos.rl1FraccionPropia(f2);
			Requisitos.rl2PeriodoValido();
			// Ejecuci�n de Caso de Uso
			Fraccion result = sistema.multiplica(f1, f2);
			// Presentaci�n al usuario
			vista.mostrarMsg("Resultado* %s",result);
		} catch (Exception e) {
			vista.mostrarMsg("Excepci�n uc2: %s",e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC3
	 */
	public void uc3RankingDeFracciones() {
		vista.mostrarMsg("UC#3 Ranking");

		try {
			vista.mostrarResultados(sistema.qryRanking());
		} catch (Exception e) {
			vista.mostrarMsg("Excepci�n uc3: %s",e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC4
	 */
	public void uc4OperacionesXFraccion() {
		vista.mostrarMsg("UC#4 Operaciones por Fraccion");
		try {
			Fraccion f1 = vista.leerFraccion();
			vista.mostrarOperaciones(sistema.qryOperacionesPor(f1));
		} catch (Exception e) {
			vista.mostrarMsg("Excepci�n uc4: %s",e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC5
	 */
	public void uc5TodasLasOperaciones() {
		vista.mostrarMsg("UC#5 Todas las operaciones");
		try {
			vista.mostrarOperaciones(sistema.qryTodaslasOperaciones());
		} catch (Exception e) {
			vista.mostrarMsg("Excepci�n uc5: %s",e.getMessage());
		}
	}
	
	/**
	 * Punto de Entrada UC6
	 */
	public void uc6ResultadosImpropios() {
		vista.mostrarMsg("UC#6 Resultados impropios");
		try {
			vista.mostrarOperaciones(sistema.qryResultadosImpropios());
		} catch (Exception e) {
			vista.mostrarMsg("Excepci�n uc6: %s",e.getMessage());
		}
	}

	/**
	 * Punto de Entrada UC9
	 */
	public void useCase9() {
		vista.mostrarMsg("UC#9 No implementado");
	}

}
