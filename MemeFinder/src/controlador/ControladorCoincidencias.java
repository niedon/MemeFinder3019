package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import vista.VistaCoincidencias;

public class ControladorCoincidencias implements ActionListener{
	
	private VistaCoincidencias vistaCoincidencias;
	
	private ImagenTemp imagenTemp;
	private int indiceActual;
	
	private JButton botonVolver;
	private JButton botonAnterior;
	private JButton botonSiguiente;
	private JButton botonAnadirIgualmente;
	
	public ControladorCoincidencias(VistaCoincidencias vistaCoincidencias) {
		
		this.vistaCoincidencias = vistaCoincidencias;
		
		botonVolver = vistaCoincidencias.getBotonVolver();
		botonVolver.addActionListener(this);
		botonAnterior = vistaCoincidencias.getBotonAnterior();
		botonAnterior.addActionListener(this);
		botonSiguiente = vistaCoincidencias.getBotonSiguiente();
		botonSiguiente.addActionListener(this);
		botonAnadirIgualmente = vistaCoincidencias.getBotonAnadirIgualmente();
		botonAnadirIgualmente.addActionListener(this);
		
	}
	
	public void setImagenTemp(ImagenTemp imagenTemp) {
		
		this.imagenTemp = imagenTemp;
		indiceActual = 0;
		vistaCoincidencias.anadirImagen(this.imagenTemp);
		
		//LLáma al método paginador
		vistaCoincidencias.cambiarImagenSec(0);
		
		botonAnterior.setEnabled(false);
		if(imagenTemp.getArrayComparaciones().size()==1) botonSiguiente.setEnabled(false);
		else botonSiguiente.setEnabled(true);
	}
	
	public void vaciar() {
		
		imagenTemp = null;
		vistaCoincidencias.vaciar();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getSource()==botonAnterior) {
			
			if(indiceActual == 1) botonAnterior.setEnabled(false);
			if(indiceActual == (imagenTemp.getArrayComparaciones().size()-1) && !botonSiguiente.isEnabled()) botonSiguiente.setEnabled(true);
			
			indiceActual--;
			vistaCoincidencias.cambiarImagenSec(indiceActual);
			
		}else if(ev.getSource()==botonSiguiente) {
			
			if(indiceActual == 0 && !botonAnterior.isEnabled()) botonAnterior.setEnabled(true);
			if(indiceActual == imagenTemp.getArrayComparaciones().size()-2) botonSiguiente.setEnabled(false);
			
			indiceActual++;
			vistaCoincidencias.cambiarImagenSec(indiceActual);
			
		}else if(ev.getSource()==botonVolver) {
			
			
			
		}else if(ev.getSource()==botonAnadirIgualmente) {
			
		}
		
		
		
	}

}
