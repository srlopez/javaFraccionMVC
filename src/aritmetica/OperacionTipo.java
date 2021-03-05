package aritmetica;

public enum OperacionTipo {
	SUMA {
		@Override
		public String toString() {
			return "+";
		}
	},
	MULTIPLICACION {
		@Override
		public String toString() {
			return "*";
		}
	}
}
