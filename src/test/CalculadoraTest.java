package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import aritmetica.*;

class CalculadoraTest {

    @Test
    @DisplayName("La suma de 5/10 + 3/6 es 48/60")
    void testSumaFraccion1() throws Exception {
        Fraccion f1 = new Fraccion(5, 10);
        Fraccion f2 = new Fraccion(3, 6);
        Calculadora calc = new Calculadora();

        Fraccion result = calc.suma(f1, f2);

        assertEquals("1/1", result.toString());
    }

    @Test
    @DisplayName("La multiplicacion de 5/10 + 3/6 es 1/4")
    void testMultiplicaFraccion1() throws Exception {

    	Fraccion f1 = new Fraccion(5, 10);
        Fraccion f2 = new Fraccion(3, 6);
        Calculadora calc = new Calculadora();

        Fraccion result = calc.multiplica(f1, f2);

        assertEquals("1/4", result.toString());
    }
}