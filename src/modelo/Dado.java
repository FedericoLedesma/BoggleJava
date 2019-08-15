package modelo;
import java.io.Serializable;
import java.util.Random;

public class Dado implements IDado,Serializable {
	private static char[] vocales= {'A','E','I','O','U'};
	private char carachar;
	private int cantCaras;
	private Random aleatorio = new Random();
	public Dado() {
		this(25);
	}
	
	public Dado(int cantidadDeCaras) {
		if (cantidadDeCaras > 1)
			this.cantCaras = cantidadDeCaras;
		else
			this.cantCaras = 25;
		tirarCara();
	}
	public void tirarCaraVocales() {
		this.carachar= vocales[aleatorio.nextInt(5)];
	}
	public void tirarCara() {
		this.carachar= (char)(66+(aleatorio.nextInt(cantCaras)));
	}
	public char getCarachar() {
		return carachar;
	}
	public String toString() {
		return String.valueOf(carachar);
	}

	
}
