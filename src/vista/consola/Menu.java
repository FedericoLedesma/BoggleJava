package vista.consola;

import controlador.Controlador;

public abstract class Menu {
	protected Controlador miControlador;
	protected VistaConsola miVista;
	public abstract void mostrarMenu();

}