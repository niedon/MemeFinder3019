package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;

import modelo.*;
import vista.*;

public class ControladorPrincipal implements ActionListener{
	
	//TODO añadir mensaje de aviso por si quedaron imágenes sin etiquetar de la sesión anterior (etiquetar? eliminar?),
	//mover por defecto a carpeta temp secundaria, y traer a principal o borrar dependiendo de la opción que se elija
	ModeloPrincipal modeloPrincipal;
	VistaPrincipal vistaPrincipal;
	
	//CambiadorTest cambiadorTest;
	
	ControladorMenu controladorMenu;
	ControladorAnadir controladorAnadir;
	ControladorResultados controladorResultados;
	ControladorCoincidencias controladorCoincidencias;
	
	public ControladorPrincipal(ModeloPrincipal modeloPrincipal, VistaPrincipal vistaPrincipal){
		
		this.modeloPrincipal = modeloPrincipal;
		this.vistaPrincipal = vistaPrincipal;
		
		//cambiadorTest = new CambiadorTest(vistaPrincipal);
		
		//vistaPrincipal.addCambiadorTest(cambiadorTest);
		vistaPrincipal.getBotonBusquedaPrincipal().addActionListener(this);
		vistaPrincipal.getVistaAnadir().getBotonCoincidencias().addActionListener(this);
		
		controladorMenu = new ControladorMenu(vistaPrincipal);
		controladorAnadir = new ControladorAnadir(vistaPrincipal.getVistaAnadir(), modeloPrincipal);
		controladorResultados = new ControladorResultados(vistaPrincipal.getVistaResultados(), modeloPrincipal);
		controladorCoincidencias = new ControladorCoincidencias(vistaPrincipal.getVistaCoincidencias());
		

		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getSource()==vistaPrincipal.getBotonBusquedaPrincipal()) {
			
			if(vistaPrincipal.getTextoBarraBusqueda().equals("") || vistaPrincipal.getTextoBarraBusqueda().equals(null)) {
				System.out.println("buscar principal vacío");
			}else {
				vistaPrincipal.getVistaResultados().setTextoBarra(vistaPrincipal.getTextoBarraBusqueda());
				//controladorResultados.empezarBusqueda();
				vistaPrincipal.getVistaResultados().getBotonBuscar().doClick();
				vistaPrincipal.cambiaCardLayout("PANELRESULTADOS");
				System.out.println("cambiado a PANELRESULTADOS");
			}
			
		}else if(ev.getSource() == vistaPrincipal.getVistaAnadir().getBotonCoincidencias()) {
			
			controladorCoincidencias.setImagenTemp(controladorAnadir.getImagenTempActual());
			vistaPrincipal.cambiaCardLayout("PANELCOINCIDENCIAS");
			System.out.println("cambiado a PANELCOINCIDENCIAS");
			
		}
		
	}
	
	

}
