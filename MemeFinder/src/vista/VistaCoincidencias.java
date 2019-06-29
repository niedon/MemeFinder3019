package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;

import controlador.ImagenTemp;

public class VistaCoincidencias extends JPanel{
	
	private ImagenTemp imagenTemp;
	
	private JPanel panelPrincipal;
	
	private JPanel panelIzq;
	
	private JPanel panelIzqImagen;
	private JLabel imagenOriginal;
	
	private JPanel panelIzqDatos;
	private JLabel nombreOriginal;
	private JLabel porcentajeOriginal;
	private JLabel tiempoAnadidaOriginal;
	
	
	private JPanel panelDer;
	
	private JPanel panelDerImagen;
	private JLabel imagenSimilar;
	
	private JPanel panelDerDatos;
	private JPanel panelNombreSimilar;
	private JLabel nombreSimilar;
	private JPanel panelEtiquetasSimilar;
	
	
	
	private JPanel panelBotones;
	
	private JButton botonVolver;
	private JButton botonAnterior;
	private JLabel etiquetaCantidad;
	private JButton botonSiguiente;
	private JButton botonAnadirIgualmente;
	
	public VistaCoincidencias() {
		
		this.setLayout(new BorderLayout());
		panelPrincipal = new JPanel(new GridLayout(1,2,10,10));
		
		panelIzq = new JPanel();
		panelIzq.setLayout(new BorderLayout());
		
		panelIzqImagen = new JPanel();
		panelIzqImagen.setLayout(null);
		panelIzqImagen.setPreferredSize(new Dimension(635,536));
		imagenOriginal = new JLabel("imagen original");
		panelIzqDatos = new JPanel(new GridLayout(0,1));
		nombreOriginal = new JLabel("nombre");
		porcentajeOriginal = new JLabel("porcentaje");
		tiempoAnadidaOriginal = new JLabel("tiempo");
		
		panelIzqImagen.add(imagenOriginal);//TODO bounds de imagenoriginal
		panelIzqDatos.add(nombreOriginal);
		panelIzqDatos.add(porcentajeOriginal);
		panelIzqDatos.add(tiempoAnadidaOriginal);	
		
		panelIzq.add(panelIzqImagen, BorderLayout.NORTH);
		panelIzq.add(panelIzqDatos, BorderLayout.CENTER);
		
		panelPrincipal.add(panelIzq);
		
		
		panelDer = new JPanel();
		panelDer.setLayout(new BorderLayout());
		
		panelDerImagen = new JPanel();
		panelDerImagen.setLayout(null);
		panelDerImagen.setPreferredSize(new Dimension(635,536));
		imagenSimilar = new JLabel("imagen similar");
		panelDerDatos = new JPanel(new BorderLayout());
		panelNombreSimilar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		nombreSimilar = new JLabel("nombre similar");
		panelEtiquetasSimilar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		panelDerImagen.add(imagenSimilar);
		panelNombreSimilar.add(nombreSimilar);
		panelDerDatos.add(panelNombreSimilar,BorderLayout.NORTH);
		panelDerDatos.add(panelEtiquetasSimilar,BorderLayout.CENTER);
		
		panelDer.add(panelDerImagen, BorderLayout.NORTH);
		panelDer.add(panelDerDatos, BorderLayout.CENTER);
		
		panelPrincipal.add(panelDer);
		
		
		
		//-----CONFIGURAR PANEL INFERIOR
		panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		botonVolver = new JButton("<< Volver");
		botonAnterior = new JButton("< Anterior");
		etiquetaCantidad = new JLabel("te/st");
		botonSiguiente = new JButton("Siguiente >");
		botonAnadirIgualmente = new JButton("Añadir igualmente");
		
		panelBotones.add(botonVolver);
		panelBotones.add(botonAnterior);
		panelBotones.add(etiquetaCantidad);
		panelBotones.add(botonSiguiente);
		panelBotones.add(botonAnadirIgualmente);
		
		
		//-----AÑADIR AMBOS PANELES
		
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		
		
	}
	
	public JButton getBotonVolver() {
		return botonVolver;
	}
	
	public JButton getBotonAnterior() {
		return botonAnterior;
	}
	
	public JButton getBotonSiguiente() {
		return botonSiguiente;
	}
	
	public JButton getBotonAnadirIgualmente() {
		return botonAnadirIgualmente;
	}
	
	
	
	//Método para cargar todos los datos en el panel
	public void anadirImagen(ImagenTemp imagenTemp) {
		
		this.imagenTemp = imagenTemp;
		
		//Imagen "fija" de la izquierda
		Image imgOriginal = VistaPrincipal.ponerImagenEscalada(imagenTemp.getbImagen(), panelIzqImagen);
		imagenOriginal.setText("");
		imagenOriginal.setIcon(new ImageIcon(imgOriginal));
		imagenOriginal.setBounds((int)(((float)panelIzqImagen.getWidth()/2) - ((float)imgOriginal.getWidth(null))/((float)2)), (int)(((float)panelIzqImagen.getHeight()/2) - ((float)imgOriginal.getHeight(null))/((float)2)),imgOriginal.getWidth(null), imgOriginal.getHeight(null));
		
		//Datos de la imagen
		nombreOriginal.setText(imagenTemp.getNombre()+imagenTemp.getExtension());
		
	}
	
	public void cambiarImagenSec(int indice) {
		
		//Mensaje de error por los loles
		if(indice>=imagenTemp.getArrayComparaciones().size()) System.out.println("Error en VistaCoincidencias.cambiarImagenSec (indice fuera de tamaño de arraylist)");
		
		//Imagen a cambiar en la derecha
		Image imgSecundaria = VistaPrincipal.ponerImagenEscalada(imagenTemp.getArrayComparaciones().get(indice).getbImagen(), panelDerImagen);
		imagenSimilar.setText("");
		imagenSimilar.setIcon(new ImageIcon(imgSecundaria));
		imagenSimilar.setBounds((int)(((float)panelDerImagen.getWidth()/2) - ((float)imgSecundaria.getWidth(null))/((float)2)), (int)(((float)panelDerImagen.getHeight()/2) - ((float)imgSecundaria.getHeight(null))/((float)2)),imgSecundaria.getWidth(null), imgSecundaria.getHeight(null));
		
		//Nombre y etiquetas de la imagen secundaria
		nombreSimilar.setText(imagenTemp.getArrayComparaciones().get(indice).getNombre() + imagenTemp.getArrayComparaciones().get(indice).getExtension());
		panelEtiquetasSimilar.removeAll();
		for(Etiquetas e : imagenTemp.getArrayComparaciones().get(indice).getArrayEtiquetas()) {
			panelEtiquetasSimilar.add(e);
		}
		
		etiquetaCantidad.setText((indice+1) + "/" + imagenTemp.getArrayComparaciones().size());
		
		//Datos secundarios (ojo, están en el panel de la izquierda)
		porcentajeOriginal.setText(String.format("%.02f%% similares.", 100*imagenTemp.getArrayComparaciones().get(indice).getIndiceParecido()));
		tiempoAnadidaOriginal.setText(imagenTemp.getArrayComparaciones().get(indice).getFecha()+"");//TODO poner en formato "hace X días/meses/..." (método static en VistaPrincipal)
		
		this.revalidate();
		this.repaint();
		
	}
	
	public void vaciar() {
		
		imagenTemp = null;
		
		imagenOriginal.setIcon(null);
		nombreOriginal.setText("");
		porcentajeOriginal.setText("");
		tiempoAnadidaOriginal.setText("");
		imagenSimilar.setIcon(null);
		nombreSimilar.setText("");
		etiquetaCantidad.setText("0/0");
		
	}

}
