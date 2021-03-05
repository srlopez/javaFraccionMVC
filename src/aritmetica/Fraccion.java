package aritmetica;

public class Fraccion {
    int numerador = 0;
    int denominador = 1;

    /**
     * Default constructor. 
     * Fracción nula 0/1
     */
    public Fraccion() {
    }
    
    /**
     * Constructor indicando el numerador y el denominador
     * @param n 
     * @param d
     * @throws IllegalArgumentException 
     */
    public Fraccion(int n, int d) throws IllegalArgumentException {
    	if (d==0) throw new IllegalArgumentException("Denominador ilegal");
        this.numerador = n;
        this.denominador = d;
    }

    /**
     * Constructor indicando un string tipo "5/7"
     * Si el parámetro es incorrecto crea la fracción nula 0/1
     * @param f 
     */
    public Fraccion(String f) {
        String[] enteross = f.split("/");
        if (enteross.length == 2)
            try {
                numerador = Integer.parseInt(enteross[0].trim());
                denominador = Integer.parseInt(enteross[1].trim());
            	if (denominador==0) throw new IllegalArgumentException("Denominador ilegal ("+numerador+"/"+denominador+")");
            } catch (Exception e) {
            	throw e;
            }
        
    }
    
    public int getNumerador() { return numerador; }
    
    public int getDenominador() { return denominador; }
    
    /**
     * Una Fraccion es propia cuando el numerador es menor que el denominador
     * Un fracción impropia tiene el numerador > denominador
     * Cuando el numerador y el denominador son iguales se trata de la unidad (1)
     * @return
     */
    public boolean esPropia() {
    	//return numerador<denominador;
    	return java.lang.Math.abs(numerador)<java.lang.Math.abs(denominador);
    }

    /**
     * @return
     */
    public String toString() {
        return numerador + "/" + denominador;
    }
}
