package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controlador.ImagenTemp;

public class VistaAnadir extends JPanel {
	
	private JPanel panelIzquierdo;
	
	private JPanel panelDireccion;
	private JTextField barraDireccion;
	private JButton botonDireccion;
	
	private PanelImagen panelImagen;
	
	
	private JPanel panelDerecho;
	
	private BoxLayout loBoxOpcionesImagen;
	private JPanel panelOpcionesImagen;
	private JPanel panelBarraEtiquetas;
	private JLabel labelEtiquetas;
	private JTextField barraEtiquetas;
	private JPanel panelNombreImagen;
	private JLabel nombreImagen;
	private JTextField barraNombreImagen;
	private JPanel panelInfoHash;
	private JLabel estadoHashing;
	private JButton botonCoincidencias;

	private JPanel panelEtiquetasAnadir;
	private JPanel panelBotonesEtiquetas;
	private JButton botonVolver;
	private JButton botonEnviarAnadir;
	private JButton botonCancelarAnadir;
	
	private JFileChooser elegidor;
	
	public VistaAnadir() {
		
		this.setLayout(new GridLayout(1, 2, 10, 10));
		
		panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BorderLayout());
		
		panelDireccion = new JPanel();
		barraDireccion = new JTextField(20);
		botonDireccion = new JButton("Examinar");
		panelImagen = new PanelImagen();
		
		panelDireccion.add(barraDireccion);
		panelDireccion.add(botonDireccion);
		panelIzquierdo.add(panelDireccion, BorderLayout.NORTH);
		panelIzquierdo.add(panelImagen, BorderLayout.CENTER);
		
		
		//-----PANEL DERECHO
		panelDerecho = new JPanel();
		panelDerecho.setLayout(new BorderLayout());
		
			//--NORTE: opciones
		panelOpcionesImagen = new JPanel();
		loBoxOpcionesImagen = new BoxLayout(panelOpcionesImagen,BoxLayout.Y_AXIS);
		panelOpcionesImagen.setLayout(loBoxOpcionesImagen);
		
				//--1. barra etiquetas
		panelBarraEtiquetas = new JPanel();
		labelEtiquetas = new JLabel("Etiquetas: ");
		barraEtiquetas = new JTextField(25);
		panelBarraEtiquetas.add(labelEtiquetas);
		panelBarraEtiquetas.add(barraEtiquetas);
		
				//--2. nombre imagen
		panelNombreImagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nombreImagen = new JLabel("Nombre:");
		barraNombreImagen = new JTextField(20);
		panelNombreImagen.add(nombreImagen);
		panelNombreImagen.add(barraNombreImagen);
		
				//--3. hashing
		panelInfoHash = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botonCoincidencias = new JButton("Coincidencias");
		estadoHashing = new JLabel("Estado de hashing...");
		panelInfoHash.add(botonCoincidencias);
		botonCoincidencias.setEnabled(false);
		panelInfoHash.add(estadoHashing);
		
			//--CENTRO: etiquetas
		panelEtiquetasAnadir = new JPanel();
		panelEtiquetasAnadir.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true), "Etiquetas", TitledBorder.LEFT, TitledBorder.TOP));
		
		
			//-----SUR: botones
		panelBotonesEtiquetas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botonVolver = new JButton("<< Volver");
		botonEnviarAnadir = new JButton("Enviar imagen");
		botonCancelarAnadir = new JButton("Eliminar imagen");
		
		
		
		panelOpcionesImagen.add(panelBarraEtiquetas);
		panelOpcionesImagen.add(Box.createVerticalStrut(15));
		panelOpcionesImagen.add(panelNombreImagen);
		panelOpcionesImagen.add(panelInfoHash);
		panelOpcionesImagen.add(Box.createVerticalStrut(15));

		panelDerecho.add(panelOpcionesImagen, BorderLayout.NORTH);
		panelDerecho.add(panelEtiquetasAnadir, BorderLayout.CENTER);
		panelBotonesEtiquetas.add(botonVolver);
		panelBotonesEtiquetas.add(botonCancelarAnadir);
		panelBotonesEtiquetas.add(botonEnviarAnadir);
		panelDerecho.add(panelBotonesEtiquetas, BorderLayout.SOUTH);
		
		
		this.add(panelIzquierdo);
		this.add(panelDerecho);
		
		
	}
	
	
	public JTextField getBarraDireccion() {
		return barraDireccion;
	}
	
	public JButton getBotonExaminar() {
		return botonDireccion;
	}
	
	public JFileChooser getFileChooser() {
		return elegidor;
	}
	
	public JTextField getBarraEtiquetas() {
		return barraEtiquetas;
	}
	
	public JTextField getBarraNombreImagen()
	{
		return barraNombreImagen;
	}
	
	public JButton getBotonVolver() {
		return botonVolver;
	}
	
	public JButton getBotonEnviar() {
		return botonEnviarAnadir;
	}
	
	public JButton getBotonCancelarAnadir() {
		return botonCancelarAnadir;
	}
	
	public JLabel getFlechaIzq() {
		return panelImagen.getFlechaIzq();
	}
	
	public JLabel getFlechaDer() {
		return panelImagen.getFlechaDer();
	}
	
	public JButton getBotonCoincidencias() {
		return botonCoincidencias;
	}
	
	
	public synchronized void setNuevaImagen(ImagenTemp imagenTemp, int num1, int num2) {
		
		panelImagen.cambiarImagen(imagenTemp.getbImagen(), num1, num2);
		
		barraNombreImagen.setText(imagenTemp.getNombre());
		
		panelEtiquetasAnadir.removeAll();
		//ojo al !
		if(!imagenTemp.getArrayEtiquetas().isEmpty()) {
			for(Etiquetas et : imagenTemp.getArrayEtiquetas()) anadirEtiqueta(et);
		}
		
		if(imagenTemp.getPHash() == null) {
			
			botonCoincidencias.setEnabled(false);
			botonEnviarAnadir.setEnabled(false);
			panelInfoHash.setBackground(Color.RED);
			
		}else {
			
			botonEnviarAnadir.setEnabled(true);
			panelInfoHash.setBackground(Color.GREEN);
			
			int cuenta = (imagenTemp.getArrayComparaciones().isEmpty()) ? 0 : imagenTemp.getArrayComparaciones().size();
			int ms = (int)imagenTemp.getTiempoEnHashear();
			String textoAMostrar;
			
			switch(cuenta) {
			case 0:
				textoAMostrar = "No se han encontrado imágenes similares (" + ms + "ms).";
				botonCoincidencias.setEnabled(false);
				break;
			case 1:
				textoAMostrar = "Se ha encontrado 1 imagen similar (" + ms + "ms).";
				botonCoincidencias.setEnabled(true);
				break;
			default:
				textoAMostrar = "Se han encontrado " + cuenta + " imágenes similares (" + ms + "ms).";
				botonCoincidencias.setEnabled(true);
				break;
			}
			
			estadoHashing.setText(textoAMostrar);
			
		}
		
		panelEtiquetasAnadir.repaint();
		panelEtiquetasAnadir.revalidate();
		
	}
	
	public void vaciar() {
		
		panelImagen.vaciar();
		panelImagen.repaint();
		panelImagen.revalidate();
		estadoHashing.setText("");
		barraNombreImagen.setText("");
		botonCoincidencias.setEnabled(false);
		botonEnviarAnadir.setEnabled(false);
		botonCancelarAnadir.setEnabled(false);
		panelEtiquetasAnadir.removeAll();
		panelEtiquetasAnadir.repaint();
		panelEtiquetasAnadir.revalidate();
		
	}
	
	public void anadirEtiqueta(Etiquetas et) {
		
		panelEtiquetasAnadir.add(et);
		panelEtiquetasAnadir.repaint();
		panelEtiquetasAnadir.revalidate();
		
	}
	
	public void quitarEtiqueta(Etiquetas et) {
		
		panelEtiquetasAnadir.remove(et);
		panelEtiquetasAnadir.repaint();
		panelEtiquetasAnadir.revalidate();
		
	}


}
