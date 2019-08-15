package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.Juego.ESTADOS;

public interface IJuego extends IObservableRemoto{

	void iniciar() throws RemoteException;

	void finalizarPartida() throws RemoteException;

	ArrayList<IJugador> getJugadores() throws RemoteException;

	void definirPuntosMaximos(int maximo) throws IllegalArgumentException, RemoteException;

	IJugador agregarJugador(String nombre) throws RemoteException;
	
	void setTirado(int idJugador, boolean b) throws RemoteException;
	
	void tirar() throws RemoteException;

	void jugar(ArrayList<String> palabras, int idJugador) throws RemoteException;
	
	void setBanderaConfig() throws RemoteException;

	boolean getBanderaConfig() throws RemoteException;

	IJugador getGanador() throws RemoteException;

	IJugador getJugadorEnTurno(int idJugador) throws RemoteException;

	void reiniciar() throws RemoteException;

	void setTiempoJuego(long tiempo) throws RemoteException;

	void cambiarRonda() throws RemoteException;

	Dado getDado() throws RemoteException;

	Dado[] getDados() throws RemoteException;

	char[] getCarasDados() throws RemoteException;

	int getPuntosRonda(int id) throws RemoteException;

	int getPuntajeMaximo() throws RemoteException;

	ESTADOS getEstado() throws RemoteException;

	void iniciarJuego() throws RemoteException;

	int getCantidadDeDados() throws RemoteException;

	long getTiempoJuego() throws RemoteException;

	void restaurarPartida(int idJugador) throws FileNotFoundException, IOException, ClassNotFoundException;

	void desconectar(int id)throws RemoteException;


}