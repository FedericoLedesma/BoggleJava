package vista.consola;

import java.util.Scanner;

import controlador.Controlador;


public class MenuConfiguracion extends Menu {

	public MenuConfiguracion(Controlador miControlador, VistaConsola vista) {
		this.miControlador = miControlador;
		this.miVista = vista;
	}
	
	@Override
	public void mostrarMenu() {
		System.out.println("=======JUEGO BOGGLE========");
		System.out.println();
		System.out.println("0 - Salir");
		System.out.println("1 - Registrar jugador");
		System.out.println("2 - Definir puntos maximos");
		System.out.println("3 - Listar jugadores");
		System.out.println("4 - Definir tiempo para ingresar palabras");
		System.out.println("5 - Iniciar partida");
		System.out.println();
		System.out.println("Ingrese su opcion => ");
		Scanner miScanner = new Scanner(System.in);
		String opcion = miScanner.nextLine();
		System.out.println("opcion: [" + opcion + "]");
		switch (opcion) {
			case "0" :miControlador.finalizarPartida();
					
					break;
			case "1" : miVista.agregarJugador();
						break;
			case "2" :miVista.definirPuntosMaximos(); 
						break;
			case "3" :miVista.listarJugadores();
						mostrarMenu();
						break;
			case "4" :miVista.definirTiempo();
	           break;
			case "5" :miVista.iniciarJuego();
			           break;
			default: System.out.println("OPCION INCORRECTA");
					break;
						
		}
	}
	

}