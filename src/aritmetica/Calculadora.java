package aritmetica;

public class Calculadora {

	public Fraccion suma(Fraccion f1, Fraccion f2) throws Exception{
		int n = f1.numerador * f2.denominador + f2.numerador * f1.denominador;
		int d = f1.denominador * f2.denominador;
		int mDiv = mcd(n, d);
		return new Fraccion(n / mDiv, d / mDiv);
	}

	public Fraccion multiplica(Fraccion f1, Fraccion f2) throws Exception{
		int n = f1.numerador * f2.numerador;
		int d = f1.denominador * f2.denominador;
		int mDiv = mcd(n, d);
		return new Fraccion(n / mDiv, d / mDiv);
	}

	// Máximo Común Divisor
	private int mcd(int xs, int ys) {
		int x =java.lang.Math.abs(xs);
		int y =java.lang.Math.abs(ys);
		if (x == 0)
			return 1;
		while (x != y)
			if (x > y)
				x = x - y;
			else
				y = y - x;
		return x;
	}


}
