package cliente;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import controlador.Controlador;
import vista.consola.VistaConsola;
import vista.grafica.VistaGrafica;

public class AppCliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				null
		);
		String port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				9999
		);
		String ipServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la corre el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				null
		);
		String portServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que corre el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
		VistaGrafica vista = new VistaGrafica();
		Controlador controlador = new Controlador(vista);		
		Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
		
		try {
			c.iniciar(controlador);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (RMIMVCException e) {
			e.printStackTrace();
		}
	}

}
