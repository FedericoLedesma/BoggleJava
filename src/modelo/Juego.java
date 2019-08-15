package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import modelo.Juego.ESTADOS;



public class Juego extends ObservableRemoto implements IJuego{
		
	private ArrayList<Jugador> jugadores;
	public static enum ESTADOS {CONFIGURANDO, JUGANDO, FINALIZADO};
	
	private int puntajeMaximo=20;
	private Cubilete cubilete;
	private boolean enJuego = true;
	private int jugadorEnTurno;
	private IJugador jugadorActual=null;
	private int puntosRonda;
	private int ganador = -1;
	private long tiempoJuego=1;
	private boolean banderaConfiguracion=false;

	private ESTADOS estado= ESTADOS.CONFIGURANDO;


	/* (non-Javadoc)
	 * @see modelo.IJuego#iniciar()
	 */
	@Override
	public void iniciar() throws RemoteException {
		jugadores = new ArrayList<>();
		cubilete = new Cubilete(16);
		puntajeMaximo = 30;
		jugadorEnTurno = 0;
		notificar(CambiosModelo.CAMBIO_LISTA_JUGADORES);
		notificar(CambiosModelo.CAMBIO_PUNTOS_MAXIMOS);
		notificar(CambiosModelo.CAMBIO_TIEMPO);
		notificar(CambiosModelo.CAMBIO_ESTADO);
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#finalizarPartida()
	 */
	@Override
	public void finalizarPartida() throws RemoteException {
		enJuego = false;
		estado = ESTADOS.FINALIZADO;
		notificar(CambiosModelo.CAMBIO_ESTADO);
	}
	

	private boolean hayGanador() throws RemoteException{
		ganador = -1;
		int posMax = 0;
		int max = 0;
		// Se busca un maximo y luego se compara el maximo con el puntajemaximo
		for (int i = 0; i < jugadores.size(); i ++) {
			if (jugadores.get(i).getPuntaje() > max) {
				max = jugadores.get(i).getPuntaje();
				posMax = i;
			}
		}
		if (max >= puntajeMaximo) {
			ganador = posMax;
		}
		// Si el ganador fue seteado a un nro mayor a 0, hay ganador
		return (ganador >= 0);
	}

	
	/* (non-Javadoc)
	 * @see modelo.IJuego#getJugadores()
	 */
	@Override
	public ArrayList<IJugador> getJugadores()throws RemoteException {
		ArrayList<IJugador> j = new ArrayList<>();
		for (IJugador jj : jugadores) {
			j.add(jj);
		}
		return j;
	}

	public static void main (String args[]) throws RemoteException {
		IJuego miJuego = new Juego();
		miJuego.iniciar();
	}

	/* (non-Javadoc)
	 * @see modelo.IJuego#definirPuntosMaximos(int)
	 */
	@Override
	public void definirPuntosMaximos(int maximo) throws IllegalArgumentException, RemoteException {// Exception {
		if (maximo > 0) {
			puntajeMaximo = maximo;
			notificar(CambiosModelo.CAMBIO_PUNTOS_MAXIMOS);
		}
		else {
			IllegalArgumentException a = new IllegalArgumentException("Puntos Maximo debe ser mayor a 0");
			throw (a);
		}
	}

	/* (non-Javadoc)
	 * @see modelo.IJuego#agregarJugador(java.lang.String)
	 */
	@Override
	public IJugador agregarJugador(String nombre) throws RemoteException {
		Jugador j = new Jugador(nombre);
		jugadores.add(j);
		notificar(CambiosModelo.CAMBIO_LISTA_JUGADORES);
		return j;
	}

	/* (non-Javadoc)
	 * @see modelo.IJuego#tirar()
	 */
	@Override
	public void tirar() throws RemoteException {
		cubilete.tirar();				
		notificar(CambiosModelo.DADOS_TIRADO);
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#jugar(java.util.ArrayList)
	 */
	@Override
	public void jugar(ArrayList<String> palabras,int idJugadorActual) throws RemoteException{
		char[] dados=cubilete.getCaras();
		char[] palab=new char[12];
		String[] palabrasfinales;
		ArrayList<String>palabrasResultado=new ArrayList<>();
		for(int i=0;i<palabras.size();i++) {
			palab= palabras.get(i).toCharArray();
			if (validar(palab,dados)){	
				if (!existe(palabras.get(i),palabrasResultado)) {
					if(validarDiccionario(palabras.get(i)))
						palabrasResultado.add(palabras.get(i));
				}
			}
		}
		palabrasfinales=new String[palabrasResultado.size()];
		palabrasResultado.toArray(palabrasfinales);
		int[] puntajePalabra=new int[palabrasfinales.length];
		puntajePalabra=puntosPalabras(palabrasfinales);
		puntosRonda=0;
		for(int i=0;i<puntajePalabra.length;i++) {
			puntosRonda+=puntajePalabra[i];
		}
		
		/*
		jugadores.get(jugadorEnTurno).setPuntosPorPalabras(puntajePalabra);
		jugadores.get(jugadorEnTurno).setPalabrasEnjuego(palabrasfinales);
		jugadores.get(jugadorEnTurno).sumarPuntos(puntosRonda);*/
		jugadores.get(idJugadorActual).setPuntosPorPalabras(puntajePalabra);
		jugadores.get(idJugadorActual).setPalabrasEnjuego(palabrasfinales);
		jugadores.get(idJugadorActual).sumarPuntos(puntosRonda);
		notificar(CambiosModelo.CAMBIO_PUNTOS_RONDA);
	}
	
	private boolean existe(String st, ArrayList<String> palabras) {
		int i=0;
		boolean b=false;
		while((i<palabras.size())&&(!b)){
			if(st.equals(palabras.get(i))) {
				b=true;
			}i++;
		}return b;
	}	
	
	private boolean validar(char[] palabra,char[] dados) {
		int i=0;
		int j=1;
		boolean b=true;
		while(i<dados.length) {
			j=0;
			while((j<palabra.length)&&(i<dados.length)) {
				if(dados[i]==(palabra[j])){
					palabra[j]='1';
					i++;
					j=-1;
				}j++;
			}i++;			
		}
		j=0;
		while((j<palabra.length)&&b) {
			if(!(palabra[j]=='1')){
				b=false;
			};j++;
		}return b;
	}	

		
	private int[] puntosPalabras(String[] palabras) {
		int[] arrayPuntos = new int[palabras.length];
		for (int i=0; i<arrayPuntos.length;i++) {
			if (palabras[i].length()<5) {
				arrayPuntos[i]=1;
			}if (palabras[i].length()==5) {
				arrayPuntos[i]=2;
			}if (palabras[i].length()==6) {
				arrayPuntos[i]=3;
			}if (palabras[i].length()==7) {
					arrayPuntos[i]=5;
			}if (palabras[i].length()>7) {
				arrayPuntos[i]=11;
			}			
				
		}
		return arrayPuntos ;		
	}
	
	private boolean validarDiccionario(String palabra) {
		boolean b;
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        b=false;
        try {
			archivo = new File ("C:\\Users\\feder\\eclipse-workspace\\POO2018\\BOGGLE-3-12-18\\Boggle\\palabras-boogle.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr); 
			// Lectura del fichero
			String linea;
			while(((linea=br.readLine())!=null)&&(!b)) {
				if(linea.equalsIgnoreCase(palabra)) {
					b=true;
				};
			}
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{
              if( null != fr ){
                 fr.close();
              }
           }catch (Exception e2){
              e2.printStackTrace();
           }
        }    
		return b;
	}



	/* (non-Javadoc)
	 * @see modelo.IJuego#getGanador()
	 */
	@Override
	public IJugador getGanador()throws RemoteException {
		if (ganador >= 0) {
			return jugadores.get(ganador);
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see modelo.IJuego#getJugadorEnTurno()
	 */
	@Override
	public IJugador getJugadorEnTurno(int idJugador) throws RemoteException{
		return jugadores.get(idJugador); 		
	}
	

	/* (non-Javadoc)
	 * @see modelo.IJuego#reiniciar()
	 */
	@Override
	public void reiniciar() throws RemoteException {
		jugadorEnTurno = 0;
		puntosRonda = 0;
		for(Jugador j : jugadores) 
			j.reiniciar();
		estado= ESTADOS.CONFIGURANDO;
		notificar(CambiosModelo.CAMBIO_ESTADO);
	}
	
	/* (non-Javadoc)
	 * @see modelo.IJuego#setTiempoJuego(long)
	 */
	@Override
	public void setTiempoJuego(long tiempo) throws RemoteException {
		if (tiempo<1) {
			tiempoJuego=1;
		}else
			tiempoJuego=tiempo;
		notificar(CambiosModelo.CAMBIO_TIEMPO);
	}

	/* (non-Javadoc)
	 * @see modelo.IJuego#cambiarRonda()
	 */
	@Override
	public void cambiarRonda() throws RemoteException {
		puntosRonda = 0;
		if (jugadorEnTurno == jugadores.size()) {
			jugadorEnTurno = 0;
			if (hayGanador()) {
				//miMenu = new MenuDeLaVictoria(this);
				estado = ESTADOS.FINALIZADO;
				notificar(CambiosModelo.MOSTRAR_GANADOR);
				notificar(CambiosModelo.CAMBIO_ESTADO);
			}
		}
		if (hayGanador()) {
			estado = ESTADOS.FINALIZADO;
			notificar(CambiosModelo.MOSTRAR_GANADOR);
			notificar(CambiosModelo.CAMBIO_ESTADO);
		}else
			if (estado != ESTADOS.FINALIZADO) {
			notificar(CambiosModelo.CAMBIO_PUNTOS);
		}
	}

	/* (non-Javadoc)
	 * @see modelo.IJuego#getDado()
	 */
	@Override
	public Dado getDado()throws RemoteException {
		return cubilete.getDado(cubilete.getCantidadDeDados());
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#getDados()
	 */
	@Override
	public Dado[] getDados() throws RemoteException{
		return cubilete.getDados();
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#getCarasDados()
	 */
	@Override
	public char[] getCarasDados()throws RemoteException {
		return cubilete.getCaras();
	}

	/* (non-Javadoc)
	 * @see modelo.IJuego#getPuntosRonda()
	 */
	@Override
	public int getPuntosRonda(int idJugador)throws RemoteException {
		return jugadores.get(idJugador).getPuntosRonda();
	}

	private void notificar(CambiosModelo cambio) throws RemoteException {
		
		//setChanged();
		//notifyObservers(cambio);
		notificarObservadores(cambio);
	}

	/* (non-Javadoc)
	 * @see modelo.IJuego#getPuntajeMaximo()
	 */
	@Override
	public int getPuntajeMaximo()throws RemoteException {
		return puntajeMaximo;
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#getEstado()
	 */
	@Override
	public ESTADOS getEstado()throws RemoteException {
		return estado;
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#iniciarJuego()
	 */
	@Override
	public void iniciarJuego() throws RemoteException {
		// TODO Auto-generated method stub
		estado= ESTADOS.JUGANDO;
		notificar(CambiosModelo.CAMBIO_JUGADOR);
		notificar(CambiosModelo.CAMBIO_PUNTOS);
		notificar(CambiosModelo.CAMBIO_ESTADO);
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#getCantidadDeDados()
	 */
	@Override
	public int getCantidadDeDados()throws RemoteException {
		// TODO Auto-generated method stub
		return cubilete.getCantidadDeDados();
	}
	/* (non-Javadoc)
	 * @see modelo.IJuego#getTiempoJuego()
	 */
	@Override
	public long getTiempoJuego()throws RemoteException {
		// TODO Auto-generated method stub
		return tiempoJuego;
	}
	@Override
	public boolean getBanderaConfig() throws RemoteException {
		// TODO Auto-generated method stub
		return banderaConfiguracion;
	}
	@Override
	public void setBanderaConfig() {
		// TODO Auto-generated method stub
		banderaConfiguracion=true;
	}
	@Override
	public void setTirado(int idJugador,boolean b) throws RemoteException {
		// TODO Auto-generated method stub
		Jugador jugad=jugadores.get(idJugador);
		jugad.setTirado(b);
		jugadores.set(idJugador,jugad);
	}
	@Override
	public void restaurarPartida(int idJugador) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		FileInputStream f_in = new FileInputStream ("C:\\Users\\feder\\eclipse-workspace\\POO2018\\BOGGLE-3-12-18\\Boggle\\Partidas\\" +jugadores.get(idJugador).getNombre()+".data"); 
		ObjectInputStream obj_in = new ObjectInputStream (f_in); 
		Object objJugador = obj_in.readObject ();
		if (objJugador instanceof Jugador) {
			jugadores.set(idJugador, (Jugador) objJugador);
		}
		
	}
	@Override
	public void desconectar(int id) throws RemoteException {
		// TODO Auto-generated method stub
		jugadores.get(id).setJugando(false);
		notificar(CambiosModelo.CAMBIO_LISTA_JUGADORES);
	}

}
