package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import aritmetica.*;

public class FraccionTest {
    @Test
    @DisplayName("Fraccion basica")
    void ConstructorVacio() {
        // fail("Not yet implemented");
       
        Fraccion f = new Fraccion();
        assertEquals("0/1", f.toString());
    }

    @Test
    @DisplayName("Fraccion de String 3/5")
    void ConstructorDeString35() {       
        Fraccion f = new Fraccion("3/5");
        assertEquals("3/5", f.toString());
    }

    @Test
    @DisplayName("Fraccion de String aa/bb")
    void ConstructorDeStringAABB() {
        Exception exception = assertThrows(Exception.class, () -> {
        	new Fraccion("aa/bb");
        });
        String expectedMessage = "Denominador ilegal";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
