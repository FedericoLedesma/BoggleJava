package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Cubilete implements Serializable{
	private Dado[] dados;
	private int cantidadDados;
	
	public Cubilete(int cantidadDeDados) {
		if(cantidadDeDados <= 0) {
			cantidadDados = 12;
		}else
			cantidadDados=cantidadDeDados;
		dados = new Dado[cantidadDados];
		for(int x = 0; x < (cantidadDados); x ++)
			dados[x] = new Dado();
		for(int x=0;x<(cantidadDados/3);x++) {
			dados[x].tirarCaraVocales();
		}
	}
	
	public void tirar(){
		for (int i=0;i<dados.length;i++) {
			dados[i].tirarCara();;
		}
		for (int i=0;i<dados.length/3;i++) {
			dados[i].tirarCaraVocales();
		}
	}
	public ArrayList<Character> getCaras1(){
		ArrayList<Character> respuesta=new ArrayList<>();
		for (Dado d : dados)
			respuesta.add(d.getCarachar());
		return respuesta;
	};
	
	public char[] getCaras() {
		char[] respuesta = new char[dados.length];
		int i = 0;
		for (Dado d : dados)
			respuesta[i++] = d.getCarachar();
			return respuesta;
	}
	public int getCantidadDeDados() {
		return cantidadDados;
	}
	
	public Dado getDado(int cara) {
		if (cara <= dados.length && cara > 0) {
			return dados[cara - 1];
		} return null;
	}

	public Dado[] getDados() {
		// TODO Auto-generated method stub
		return dados;
	}
}
