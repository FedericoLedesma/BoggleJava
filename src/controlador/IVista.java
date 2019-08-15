package controlador;

import modelo.IJugador;

public interface IVista {

	void mostrarJugando();

	void mostrarConfiguracion();

	void mostrarFinalizado();

	void mostrarListaJugadores();

	void mostrarPuntosActuales();

	void mostrarPuntosRonda();

	void mostrarDadosTirado();

	void mostrarJugadorPerdio();

	void mostrarPalabras();

	void setControlador(Controlador controlador);

	void mostrarJugadorActual();

	void mostrarPuntosMaximos();
	
	void mostrarMenuPalabras();
	
	void definirTiempo();

	void cambiarRonda();
	
	void mostrarJugadoresPuntos();
	
	void mostrarTiempo();

	void ocultarSetterPuntos();

	void ocultarSetterTiempo();


}
