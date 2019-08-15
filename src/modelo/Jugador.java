package modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class Jugador implements IJugador,Serializable{
	private String nombre;
	private int puntos; // totales
	private static int ID = 0;
	private int id;
	private String[] palabrasenjuego= new String[0];
	private int[] puntosPorPalabras= new int[0];
	private boolean banderaDadoTirado=false;
	private boolean jugando=true;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.id = Jugador.ID++;
	}
	public void setID(int id) {
		this.id=id;
	}
	public String toString() {
		return nombre + " tiene " + puntos + " puntos";
	}

	public int getPuntaje() {
		// TODO Auto-generated method stub
		return puntos;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void sumarPuntos(int p) {
		puntos += p;
	}

	public void reiniciar() {
		puntos = 0;
		
	}
	
	public void setTirado(boolean b) {
		this.banderaDadoTirado=b;
	}
	
	public void setPalabrasEnjuego(String[] palabrasju) {
		palabrasenjuego=new String[palabrasju.length];
		for(int i=0;i<palabrasju.length;i++) {
			palabrasenjuego[i]=palabrasju[i];
		}
	}
	public void setPuntosPorPalabras(int[] puntosPalabras) {
		puntosPorPalabras=new int[puntosPalabras.length];
		for(int i=0;i<puntosPorPalabras.length;i++) {
			puntosPorPalabras[i]=puntosPalabras[i];
		}
	}
	public String[] getPalabrasEnJuego() {
		return palabrasenjuego;
	}

	@Override
	public int[] getPuntajePorPalabra() {
		// TODO Auto-generated method stub
		return puntosPorPalabras;
	}

	@Override
	public int getPuntosRonda() {
		// TODO Auto-generated method stub
		int result=0;
		for(int i=0;i<puntosPorPalabras.length;i++) {
			result+=puntosPorPalabras[i];
		}
		return result;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean getTirado() {
		// TODO Auto-generated method stub
		return this.banderaDadoTirado;
	}
	public void setJugando(boolean b) {
		// TODO Auto-generated method stub
		this.jugando=false;
	}
	@Override
	public boolean getJugando() {
		// TODO Auto-generated method stub
		return this.jugando;
	}
}