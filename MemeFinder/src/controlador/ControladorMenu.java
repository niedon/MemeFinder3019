package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import vista.*;

public class ControladorMenu implements ActionListener{
	
	private JMenuBar menuPrincipal;
	private VistaPrincipal vistaPrincipal;
	
	private JMenu archivo;
	private JMenuItem anadirImagenes;
	private JMenuItem opciones;

	public ControladorMenu(VistaPrincipal vistaPrincipal) {
		
		this.vistaPrincipal = vistaPrincipal;
		this.menuPrincipal = vistaPrincipal.getMenuPrincipal();

		archivo = new JMenu("Archivo");
		
		anadirImagenes = new JMenuItem("Añadir imágenes");
		anadirImagenes.addActionListener(this);
		
		opciones = new JMenuItem("Opciones");
		
		archivo.add(anadirImagenes);
		archivo.add(opciones);
		
		menuPrincipal.add(archivo);
		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getSource() == anadirImagenes) {
			vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELANADIR);
		}
		
	}

}


