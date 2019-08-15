package controlador;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.CambiosModelo;
import modelo.IDado;
import modelo.IJuego;
import modelo.IJugador;
import modelo.Juego;
import vista.consola.VistaConsola;
import vista.grafica.VistaGrafica;
import modelo.Juego.ESTADOS;
import modelo.Jugador;
public class Controlador implements IControladorRemoto{
	
	private IJuego juego;
	private IVista vista;
	private IJugador jugador=null;
	public Controlador(IVista vista) {
	//	this.juego = juego;
		this.vista = vista;
		vista.setControlador(this);
	}
	/*public static void main(String[] args) {
		IJuego j = new Juego();
		IVista vista = new VistaGrafica();
		Controlador c = new Controlador(j, vista);
		j.iniciar();
	}*/
	public int getCantidadDeDados(){
		try {
			return juego.getCantidadDeDados();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public boolean getBanderaConfig() {
		try {
			return juego.getBanderaConfig();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public void setBanderaConfig() {
		try {
			juego.setBanderaConfig();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IJugador getJugadorEnTurno() throws RemoteException {
		return juego.getJugadorEnTurno(jugador.getID());
		
	}

	public void finalizarPartida() {
		try {
			juego.finalizarPartida();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void tirar() {
		try {
			juego.setTirado(jugador.getID(),true);
			int i=0;
			boolean b=true;
			while ((i<this.getJugadores().size())&& b) {
				if(this.getJugadores().get(i).getJugando()){
					b=this.getJugadores().get(i).getTirado();
				}
				i++;
			}
			if (b) {
				juego.tirar();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean verificarTirarJugadores() {
		int i=0;
		boolean b=true;
		while ((i<this.getJugadores().size())&& b) {
			if(this.getJugadores().get(i).getJugando()){
				b=this.getJugadores().get(i).getTirado();
			}
			i++;
		}return b;
	}

	public void setTiradoJugadores() {
		for (int j=0;j<getJugadores().size();j++) {
			try {
				juego.setTirado(getJugadores().get(j).getID(),false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setTirado(boolean b) {
		try {
			juego.setTirado(jugador.getID(),b);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public IJugador getGanador() {
		try {
			return juego.getGanador();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void reiniciar() {
		try {
			juego.reiniciar();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void agregarJugador(String nombre) {
		try {
			this.jugador=juego.agregarJugador(nombre);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void definirPuntosMaximos(Integer valueOf) {
		try {
			juego.definirPuntosMaximos(valueOf);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<IJugador> getJugadores() {
		try {
			return juego.getJugadores();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int getPuntosActuales(int id) {
		// TODO Auto-generated method stub
		try {
			return juego.getPuntosRonda(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int getPuntajeMaximo() {
		// TODO Auto-generated method stub
		try {
			return juego.getPuntajeMaximo();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public IDado getDado() {
		// TODO Auto-generated method stub
		try {
			return juego.getDado();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void iniciarJuego() {
		try {
			juego.iniciarJuego();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void setTiempoJuego(long tiempo) {
		try {
			juego.setTiempoJuego(tiempo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public char[] getCaras() {
		// TODO Auto-generated method stub
		try {
			return juego.getCarasDados();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void jugar(ArrayList<String> palabras) {
		// TODO Auto-generated method stub
		try {
			juego.jugar(palabras,jugador.getID());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getTiempoJuego() {
		// TODO Auto-generated method stub
		try {
			return juego.getTiempoJuego();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public void cambiarRonda() {
		// TODO Auto-generated method stub
		try {
			juego.cambiarRonda();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public IJugador getJugador() {
		return jugador;
	}
	public void guardarPartida() throws FileNotFoundException {
		try{
			// Serializar un objeto de datos a un archivo
			ObjectOutputStream f_out = new ObjectOutputStream(new
			FileOutputStream("C:\\Users\\feder\\eclipse-workspace\\POO2018\\BOGGLE-3-12-18\\Boggle\\Partidas\\"+this.jugador.getNombre()+".data"));
			f_out.writeObject(this.getJugadores().get(this.jugador.getID()));
			f_out.close();
			} catch (IOException e) {
			}
	}
	public void restaurarPartida() throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		juego.restaurarPartida(jugador.getID());	
	}
	
	@Override
	public void actualizar(IObservableRemoto arg0, Object arg) throws RemoteException {
		// TODO Auto-generated method stub
		if (arg instanceof CambiosModelo) {
			CambiosModelo cambio = (CambiosModelo) arg;
			switch (cambio) {
			case CAMBIO_ESTADO:
				Juego.ESTADOS e = juego.getEstado();
				if (e == Juego.ESTADOS.JUGANDO) {
					vista.mostrarJugando();
				} else if (e == Juego.ESTADOS.CONFIGURANDO) {
					vista.mostrarConfiguracion();
				} else if (e == Juego.ESTADOS.FINALIZADO) {
					vista.mostrarFinalizado();
				}
				break;
			case CAMBIO_JUGADOR:
				vista.mostrarJugadorActual();
				vista.mostrarJugando();
				break;
			case CAMBIO_LISTA_JUGADORES:				
				vista.mostrarListaJugadores();
				break;
			case CAMBIO_PUNTOS:
				vista.mostrarPuntosActuales();
				break;
			case CAMBIO_PUNTOS_MAXIMOS:
				vista.mostrarPuntosMaximos();
				vista.ocultarSetterPuntos();
				vista.mostrarConfiguracion();
				break;
			case CAMBIO_PUNTOS_RONDA:
				vista.mostrarPuntosRonda();
				vista.mostrarJugadoresPuntos();
				vista.mostrarPalabras();//actualizado
				break;
			case CAMBIO_TIEMPO:
				vista.mostrarTiempo();
				vista.ocultarSetterTiempo();
				break;
			case DADOS_TIRADO:
				vista.mostrarDadosTirado();
				break;
			case JUGADOR_PERDIO:
				vista.mostrarJugadorPerdio();
				break;
			}
		}
	}

	
	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T arg0) throws RemoteException {
		// TODO Auto-generated method stub
		this.juego=(IJuego) arg0;
	}
	public void desconectar() throws RemoteException {
		// TODO Auto-generated method stub
		juego.desconectar(jugador.getID());
	
	}
	

}
