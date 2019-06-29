package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.*;
import vista.*;

public class ControladorPrincipal implements ActionListener{
	
	//TODO añadir mensaje de aviso por si quedaron imágenes sin etiquetar de la sesión anterior (etiquetar? eliminar?),
	//mover por defecto a carpeta temp secundaria, y traer a principal o borrar dependiendo de la opción que se elija
	ModeloPrincipal modeloPrincipal;
	VistaPrincipal vistaPrincipal;
	
	
	ControladorMenu controladorMenu;
	ControladorAnadir controladorAnadir;
	ControladorResultados controladorResultados;
	ControladorCoincidencias controladorCoincidencias;
	ControladorImagenDatos controladorImagenDatos;
	
	public ControladorPrincipal(ModeloPrincipal modeloPrincipal, VistaPrincipal vistaPrincipal){
		
		this.modeloPrincipal = modeloPrincipal;
		this.vistaPrincipal = vistaPrincipal;
		
		controladorMenu = new ControladorMenu(vistaPrincipal);
		controladorAnadir = new ControladorAnadir(vistaPrincipal.getVistaAnadir(), modeloPrincipal);
		controladorResultados = new ControladorResultados(vistaPrincipal.getVistaResultados(), modeloPrincipal);
		controladorCoincidencias = new ControladorCoincidencias(vistaPrincipal.getVistaCoincidencias());
		controladorImagenDatos = new ControladorImagenDatos(vistaPrincipal.getVistaImagenDatos(), modeloPrincipal);
		
		//Se añaden los Listener implicados en el cambio de panel
		vistaPrincipal.getBotonBusquedaPrincipal().addActionListener(this);
		vistaPrincipal.getVistaAnadir().getBotonCoincidencias().addActionListener(this);
		vistaPrincipal.getVistaAnadir().getBotonVolver().addActionListener(this);
		vistaPrincipal.getVistaCoincidencias().getBotonAnadirIgualmente().addActionListener(this);
		vistaPrincipal.getVistaCoincidencias().getBotonVolver().addActionListener(this);
		vistaPrincipal.getVistaResultados().getBotonVerEnGrande().addActionListener(this);
		vistaPrincipal.getVistaImagenDatos().getBotonVolver().addActionListener(this);
		
		//Por defecto, se empieza en el panel PANELINICIO
		vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELINICIO);
		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		//Botón de búsqueda de la vista Principal
		if(ev.getSource()==vistaPrincipal.getBotonBusquedaPrincipal()) {
			
			//La búsqueda empieza si hay algo escrito en la barra
			if(!vistaPrincipal.getTextoBarraBusqueda().isEmpty()) {
				vistaPrincipal.getVistaResultados().setTextoBarra(vistaPrincipal.getTextoBarraBusqueda());
				vistaPrincipal.getVistaResultados().getBotonBuscar().doClick();//TODO chapucero, cambiar
				vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELRESULTADOS);
			}
		
		//Botón de coincidencias encontradas de la vista Añadir	
		}else if(ev.getSource() == vistaPrincipal.getVistaAnadir().getBotonCoincidencias()) {
			
			controladorCoincidencias.setImagenTemp(controladorAnadir.getImagenTempActual());
			vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELCOINCIDENCIAS);
			
			
		//Botón volver en VistaAñadir	
		}else if(ev.getSource() == vistaPrincipal.getVistaAnadir().getBotonVolver()) {
			
			//ojo !
			if(!controladorAnadir.getArrayListImagenes().isEmpty()) {
				
				int res = JOptionPane.showConfirmDialog(null, "Quedan imágenes por añadir, ¿las quieres guardar?","",JOptionPane.YES_NO_CANCEL_OPTION);
				
				if(res == JOptionPane.NO_OPTION) {
					controladorAnadir.vaciar();
					vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELINICIO);
				}else if(res == JOptionPane.YES_OPTION) {
					vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELINICIO);
				}
				
			}else {
				vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELINICIO);
			}
		
		//Botón de añadir igualmente en VistaCoincidencias	
		}else if(ev.getSource() == vistaPrincipal.getVistaCoincidencias().getBotonAnadirIgualmente()) {	
		
			controladorCoincidencias.vaciar();
			controladorAnadir.getImagenTempActual().setAnadirIgualmente(true);
			vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELANADIR);
			
		//Botón de volver en VistaCoincidencias	
		}else if(ev.getSource() == vistaPrincipal.getVistaCoincidencias().getBotonVolver()) {		
			
			controladorCoincidencias.vaciar();
			vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELANADIR);
			
			
		//Botón de ampliar vista en vista Resultados	
		}else if(ev.getSource() == vistaPrincipal.getVistaResultados().getBotonVerEnGrande()) {
			
			//TODO seleccionar desde controladorResultados en vez de vistaReusltados???
			if(vistaPrincipal.getVistaResultados().getImagenTempSeleccionado() != null) {
				controladorImagenDatos.setImagenTemp(vistaPrincipal.getVistaResultados().getImagenTempSeleccionado());
				vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELIMAGENDATOS);
			}
			
			
			
		//Botón de volver a VistaResultados desde VistaImagenDatos	
		}else if(ev.getSource() == vistaPrincipal.getVistaImagenDatos().getBotonVolver()) {
			
			if(controladorImagenDatos.botonVolverPulsado()) {
				
				controladorResultados.busquedaParaActualizarImagenTempUpdateada(controladorImagenDatos.getImagenTemp().getIdMongo());
				vistaPrincipal.cambiaCardLayout(VistaPrincipal.PANELRESULTADOS);
				
			}
			
		}
		
	}
	
	

}
