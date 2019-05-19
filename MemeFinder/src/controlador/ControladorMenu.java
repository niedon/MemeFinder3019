package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import vista.*;

public class ControladorMenu {
	
	private JMenuBar menuPrincipal;
	private VistaPrincipal vistaPrincipal;
	
	private JMenuItem anadirElementos;

	public ControladorMenu(VistaPrincipal vistaPrincipal) {
		
		this.vistaPrincipal = vistaPrincipal;
		this.menuPrincipal = vistaPrincipal.getMenuPrincipal();
		
		anadirElementos = new JMenuItem("Añadir");
		anadirElementos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				vistaPrincipal.cambiaCardLayout("PANELANADIR");
				System.out.println("cambiado a PANELANADIR");
				
			}
			
		});
		
		menuPrincipal.add(anadirElementos);

		
		
		
		
		
	}

}


