package controlador;

import vista.VistaCoincidencias;

public class ControladorCoincidencias {
	
	private VistaCoincidencias vistaCoincidencias;
	
	private ImagenTemp imagenTemp;
	
	public ControladorCoincidencias(VistaCoincidencias vistaCoincidencias) {
		
		this.vistaCoincidencias = vistaCoincidencias;
		
	}
	
	public void setImagenTemp(ImagenTemp imagenTemp) {
		this.imagenTemp = imagenTemp;
		vistaCoincidencias.anadirImagen(this.imagenTemp);
	}

}
