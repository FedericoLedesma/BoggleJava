package modelo;

import java.rmi.RemoteException;

public interface IJugador {
	public boolean getJugando();
	public String getNombre();
	public int getPuntaje();
	public String[] getPalabrasEnJuego();
	public int[] getPuntajePorPalabra();
	public int getPuntosRonda();
	public int getID();
	public boolean getTirado();
	public void setTirado(boolean b);
}
