package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import controlador.ImagenTemp;

public class VistaAnadir extends JPanel implements ActionListener{
	
	private JPanel panelPrincipalAnadir;
	
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
	private JButton botonEnviarAnadir;
	private JButton botonCancelarAnadir;
	
	
	//private PanelCoincidencias panelCoincidenciasAnadir;
	
	
	private JFileChooser elegidor;
	
	public VistaAnadir() {
		
		//CardLayout layOutAnadir = new CardLayout();
		
		
		//panelPrincipalAnadir = new JPanel(new GridLayout(1,2,10,10));
		
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
		//panelOpcionesImagen.setBackground(Color.YELLOW);
		
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
		botonEnviarAnadir = new JButton("Enviar imagen");
		botonCancelarAnadir = new JButton("Eliminar imagen (//TODO)");
		
		
		
		panelOpcionesImagen.add(panelBarraEtiquetas);
		panelOpcionesImagen.add(Box.createVerticalStrut(15));
		panelOpcionesImagen.add(panelNombreImagen);
		panelOpcionesImagen.add(panelInfoHash);
		panelOpcionesImagen.add(Box.createVerticalStrut(15));
//		panelOpcionesImagen.add(new JLabel("test1"));
//		panelOpcionesImagen.add(new JLabel("test2"));
//		panelOpcionesImagen.add(new JLabel("test1"));
//		panelOpcionesImagen.add(new JLabel("test2"));
//		panelOpcionesImagen.add(new JLabel("test1"));
//		panelOpcionesImagen.add(new JLabel("test2"));
//		panelOpcionesImagen.add(new JLabel("test1"));
//		panelOpcionesImagen.add(new JLabel("test2"));
		panelDerecho.add(panelOpcionesImagen, BorderLayout.NORTH);
		panelDerecho.add(panelEtiquetasAnadir, BorderLayout.CENTER);
		panelBotonesEtiquetas.add(botonCancelarAnadir);
		panelBotonesEtiquetas.add(botonEnviarAnadir);
		panelDerecho.add(panelBotonesEtiquetas, BorderLayout.SOUTH);
		
		
		this.add(panelIzquierdo);
		this.add(panelDerecho);
		
		//this.add(panelPrincipalAnadir);
		
		//panelCoincidenciasAnadir = new PanelCoincidencias();
		
		
		//TODO borrar?
		botonDireccion.addActionListener(this);
		barraEtiquetas.addActionListener(this);
		
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
	public JButton getBotonEnviar() {
		return botonEnviarAnadir;
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
	
//	private void hashCompleto() {
//		
//	}
	
	public synchronized void setNuevaImagen(ImagenTemp imagenTemp, int num1, int num2) {
		
		//System.out.println("testnom: " + imagenTemp.getNombre());
		//barraDireccion.setText(direccion); //DEPRECATED preventivo, barra useless ahora mismo
		panelImagen.cambiarImagen(imagenTemp.getbImagen(), num1, num2);
		
		//barraDireccion.setText(imagenTemp.getNombre()); //DEPRECATED??
		barraNombreImagen.setText(imagenTemp.getNombre());
		
		panelEtiquetasAnadir.removeAll();
		//ojo al !
		if(!imagenTemp.getArrayEtiquetas().isEmpty()) {
			for(int i=0; i<imagenTemp.getArrayEtiquetas().size(); i++) {
				anadirEtiqueta(imagenTemp.getArrayEtiquetas().get(i));
			}
		}
		
		if(imagenTemp.getPHash().equals("")) {
			
			//panelInfoHash.remove(botonCoincidencias);
			botonCoincidencias.setEnabled(false);
			botonEnviarAnadir.setEnabled(false);//asegurar el true si hash!=""
			estadoHashing.setText("El hashing va en proceso.");
			panelInfoHash.setBackground(Color.RED);
			
		}else {
			
			botonEnviarAnadir.setEnabled(true);
			//estadoHashing.setText(imagenTemp.getTextoHashing());
			panelInfoHash.setBackground(Color.GREEN);
			
			int cuenta = (imagenTemp.getArrayComparaciones().isEmpty()) ? 0 : imagenTemp.getArrayComparaciones().size();
			String textoAMostrar;
			
			switch(cuenta) {
			case 0:
				textoAMostrar = "No se han encontrado imágenes similares.";
				//panelInfoHash.remove(botonCoincidencias);
				botonCoincidencias.setEnabled(false);
				break;
			case 1:
				textoAMostrar = "Se ha encontrado 1 imagen similar";
				//panelInfoHash.add(botonCoincidencias);
				botonCoincidencias.setEnabled(true);
				break;
			default:
				textoAMostrar = "Se han encontrado " + cuenta + " imágenes similares.";
				//panelInfoHash.add(botonCoincidencias);
				botonCoincidencias.setEnabled(true);
				break;
			}
			
			estadoHashing.setText(textoAMostrar);
			
			
		}
		
		//TODO meter if:
		//si hash=="" desactivar enviar y texto cargando
		//si hash!="" cargar con coincidencias (almacenar coincidencias en imagentemp???)
		panelEtiquetasAnadir.repaint();
		panelEtiquetasAnadir.revalidate();
		
		//i.e. si se llama desde la interfaz
		//ojo !
//		if(!cambiaHash) {
//			
//			
//		
//		//i.e. si se llama desde el hilo de hashing
//		}else {
//			
//			hashCompleto();
//			
//		}

		
	}
	
	public void vaciar() {
		panelImagen.vaciar();
		panelImagen.repaint();
		panelImagen.revalidate();
		estadoHashing.setText("-");
		botonCoincidencias.setEnabled(false);
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


	@Override
	public void actionPerformed(ActionEvent ev) {
		
	}
	
	//GIF ANIMADO
//	ImageIcon imgif = new ImageIcon("src/vista/img/aaa.gif");
////	imgif.setImageObserver(new ImageObserver() {
////
////		@Override
////		public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
////			repaint();
////			return false;
////		}
////		
////	});
//	JLabel test = new JLabel(imgif);
//	panelInicio.add(test);


}
