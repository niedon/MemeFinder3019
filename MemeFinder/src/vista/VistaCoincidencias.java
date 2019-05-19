package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controlador.ImagenTemp;

public class VistaCoincidencias extends JPanel implements ActionListener{
	
	private ImagenTemp imagenTemp;
	private int indiceActual;
	
	private JPanel panelPrincipal;
	
	private JPanel panelIzq;
	private GroupLayout loPanIzq;
	
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
	
	private JButton botonAnadirIgualmente;
	private JButton botonAnterior;
	private JLabel etiquetaCantidad;
	private JButton botonSiguiente;
	private JButton botonCancelar;
	
	public VistaCoincidencias() {
		
		//indiceActual = 0;
		
		this.setLayout(new BorderLayout());
		panelPrincipal = new JPanel(new GridLayout(1,2,10,10));
		//panelPrincipal.setLayout(new BorderLayout());
//		panelPrincipal.setBackground(Color.RED);
		
		//nuevo
		
		panelIzq = new JPanel();
		panelIzq.setLayout(new BorderLayout());
//		panelIzq.setBackground(Color.WHITE);
		
		panelIzqImagen = new JPanel();
//		panelIzqImagen.setBackground(Color.BLUE);
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
//		panelDer.setBackground(Color.BLACK);
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
//		
		panelPrincipal.add(panelDer);
		
		
		//--------------------------
		
		
		
//		//-----CONFIGURAR PANELIZQ
//		panelIzq = new JPanel();
//		//panelIzq.setBackground(Color.RED);
//		
//		panelIzqImagen = new JPanel();//-------------------------------Este es el de la imagen original
//		panelIzqImagen.setPreferredSize(new Dimension(635,536));
//		panelIzqImagen.setSize(new Dimension(635,536));
//		panelIzqImagen.setMinimumSize(new Dimension(635,536));
//		panelIzqImagen.setMaximumSize(new Dimension(635,536));
//		imagenOriginal = new JLabel("aquí imagen original");
//		panelIzqDatos = new JPanel(new GridLayout(0,1));//TODO orientación arriba-abajo
//		nombreOriginal = new JLabel("test nombre");
//		porcentajeOriginal = new JLabel("test porcentaje");
//		tiempoAnadidaOriginal = new JLabel("test tiempo");
//		
//		panelIzqImagen.add(imagenOriginal);
//		panelIzqDatos.add(nombreOriginal);
//		panelIzqDatos.add(porcentajeOriginal);
//		panelIzqDatos.add(tiempoAnadidaOriginal);
//		ponerGridBag(panelIzq,panelIzqImagen,panelIzqDatos);
//		
//		panelPrincipal.add(panelIzq);
//		
//		//-----CONFIGURAR PANELDER
//		panelDer = new JPanel();
//		
//		panelDerImagen = new JPanel();
//		imagenSimilar = new JLabel("aquí imagen similar");
//		panelDerDatos = new JPanel(new BorderLayout());
//		nombreSimilar = new JLabel("nombre similar",JLabel.CENTER);
//		panelEtiquetasSimilar = new JPanel(new FlowLayout(FlowLayout.CENTER));
//		
//		panelDerImagen.add(imagenSimilar);
//		panelDerDatos.add(nombreSimilar,BorderLayout.NORTH);
//		panelDerDatos.add(panelEtiquetasSimilar,BorderLayout.CENTER);
//		ponerGridBag(panelDer,panelDerImagen,panelDerDatos);
//		
//		panelPrincipal.add(panelDer);
		
		
		//-----CONFIGURAR PANEL INFERIOR
		panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		botonAnadirIgualmente = new JButton("Añadir igualmente");
		botonAnterior = new JButton("< Anterior");
		botonAnterior.addActionListener(this);
		etiquetaCantidad = new JLabel("te/st");
		botonSiguiente = new JButton("Siguiente >");
		botonSiguiente.addActionListener(this);
		botonCancelar = new JButton("Cancelar");
		
		panelBotones.add(botonAnadirIgualmente);
		panelBotones.add(botonAnterior);
		panelBotones.add(etiquetaCantidad);
		panelBotones.add(botonSiguiente);
		panelBotones.add(botonCancelar);
		
		
		//-----AÑADIR AMBOS PANELES
		
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		
//		panelIzqImagen.setBackground(Color.GREEN);
//		panelIzqDatos.setBackground(Color.RED);
//		panelEtiquetasSimilar.setBackground(Color.BLUE);
		
		
		
	}
	
//	private void ponerGridBag(JPanel parent, JPanel superior, JPanel inferior) {
//		
//		GridBagLayout gbl = new GridBagLayout();
//		parent.setLayout(gbl);
//		GridBagConstraints c = new GridBagConstraints();
//		
//		c.fill = GridBagConstraints.BOTH;
//		
//		c.weightx = 1;
//		c.weighty = 0.7;
//		
//		c.gridy = 0;
//		
//		gbl.setConstraints(superior, c);
//		parent.add(superior);
//		
//		c.weighty = 0.3;
//		
//		c.gridy = 1;
//		
//		gbl.setConstraints(inferior, c);
//		parent.add(inferior);
//		
//		
//	}
	
	//Método para cargar todos los datos en el panel
	public void anadirImagen(ImagenTemp imagenTemp) {
		
		this.imagenTemp = imagenTemp;
		indiceActual = 0;
		
		//Imagen "fija" de la izquierda
		Image imgOriginal = VistaPrincipal.ponerImagen(imagenTemp.getbImagen(), panelIzqImagen);
		imagenOriginal.setText("");
		imagenOriginal.setIcon(new ImageIcon(imgOriginal));
		imagenOriginal.setBounds((int)(((float)panelIzqImagen.getWidth()/2) - ((float)imgOriginal.getWidth(null))/((float)2)), (int)(((float)panelIzqImagen.getHeight()/2) - ((float)imgOriginal.getHeight(null))/((float)2)),imgOriginal.getWidth(null), imgOriginal.getHeight(null));
		
		//Datos de la imagen
		nombreOriginal.setText(imagenTemp.getNombre()+imagenTemp.getExtension());
//		porcentajeOriginal.setText((100-imagenTemp.getArrayComparaciones().get(0).getIndiceParecido()) + "% de similitud");
//		tiempoAnadidaOriginal.setText(imagenTemp.getFecha()+"");
		
		//LLáma al método paginador
		cambiarImagenSec(0);
		
		botonAnterior.setEnabled(false);
		if(imagenTemp.getArrayComparaciones().size()==1) botonSiguiente.setEnabled(false);
		
	}
	
	private void cambiarImagenSec(int indice) {
		
		//Mensaje de error por los loles
		if(indice>=imagenTemp.getArrayComparaciones().size()) System.out.println("Error en VistaCoincidencias.cambiarImagenSec (indice fuera de tamaño de arraylist)");
		
		//Imagen a cambiar en la derecha
		Image imgSecundaria = VistaPrincipal.ponerImagen(imagenTemp.getArrayComparaciones().get(indice).getbImagen(), panelDerImagen);
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
		porcentajeOriginal.setText((100-imagenTemp.getArrayComparaciones().get(indice).getIndiceParecido()) + "% de similitud");
		tiempoAnadidaOriginal.setText(imagenTemp.getArrayComparaciones().get(indice).getFecha()+"");//TODO poner en formato "hace X días/meses/..."
		
		this.revalidate();
		this.repaint();
		
	}
	
	//------------------------inisio
	
//	public void cargarPanel(ImagenTemp imagenTemp) {
//		
//		this.imagenTemp = imagenTemp;
//		
////		System.out.println(panelIzqImagen.getPreferredSize());
////		System.out.println(panelIzqImagen.getSize());
////		System.out.println(panelIzqImagen.getMinimumSize());
////		System.out.println(panelIzqImagen.getMaximumSize());
//		
//		Image imgOriginal = VistaPrincipal.ponerImagen(imagenTemp.getbImagen(), panelIzqImagen);
//		imagenOriginal.setText("");
//		imagenOriginal.setIcon(new ImageIcon(imgOriginal));
//		imagenOriginal.setBounds((int)(((float)panelIzqImagen.getWidth()/2) - ((float)imgOriginal.getWidth(null))/((float)2)), (int)(((float)panelIzqImagen.getHeight()/2) - ((float)imgOriginal.getHeight(null))/((float)2)),imgOriginal.getWidth(null), imgOriginal.getHeight(null));
//		
//		nombreOriginal.setText(imagenTemp.getNombre()+imagenTemp.getExtension());
//		porcentajeOriginal.setText((100-imagenTemp.getArrayComparaciones().get(0).getIndiceParecido()) + "% de similitud");
//		tiempoAnadidaOriginal.setText(imagenTemp.getFecha()+"");
//		
//		Image imgSecundaria = VistaPrincipal.ponerImagen(imagenTemp.getArrayComparaciones().get(0).getbImagen(), panelDerImagen);
//		imagenSimilar.setText("");
//		imagenSimilar.setIcon(new ImageIcon(imgSecundaria));
//		imagenSimilar.setBounds((int)(((float)panelDerImagen.getWidth()/2) - ((float)imgSecundaria.getWidth(null))/((float)2)), (int)(((float)panelDerImagen.getHeight()/2) - ((float)imgSecundaria.getHeight(null))/((float)2)),imgSecundaria.getWidth(null), imgSecundaria.getHeight(null));
//		
//		nombreSimilar.setText(imagenTemp.getArrayComparaciones().get(0).getNombre() + imagenTemp.getArrayComparaciones().get(0).getExtension());
//		for(Etiquetas e : imagenTemp.getArrayComparaciones().get(0).getArrayEtiquetas()) {
//			panelEtiquetasSimilar.add(e);
//		}
//		
//		botonAnterior.setEnabled(false);
//		if(imagenTemp.getArrayComparaciones().size()==1) {
//			botonSiguiente.setEnabled(false);
//		}
//		etiquetaCantidad.setText("1/" + imagenTemp.getArrayComparaciones().size());
//		
//		this.revalidate();
//		this.repaint();
		
		//---------------------------fin
		
//		System.out.println(panelIzqImagen.getPreferredSize());
//		System.out.println(panelIzqImagen.getSize());
//		System.out.println(panelIzqImagen.getMinimumSize());
//		System.out.println(panelIzqImagen.getMaximumSize());
		
		
		
		
		//---------------------------------------------------------
		
		
//		System.out.println(panelIzqImagen.getPreferredSize());
//		System.out.println(panelIzqImagen.getSize());
//		System.out.println(panelIzqImagen.getMinimumSize());
//		System.out.println(panelIzqImagen.getMaximumSize());
//		
//		imagenOriginal.setText("");
//		try {
//			imagenOriginal.setIcon(new ImageIcon(ImageIO.read(imagenTemp.getImagen())));
//		} catch (IOException e) {
//			imagenOriginal.setText("Error al cargar la imagen");
//		}
//		
//		nombreOriginal.setText(imagenTemp.getNombre()+imagenTemp.getExtension());
//		porcentajeOriginal.setText("" + imagenTemp.getArrayComparaciones().get(0).getIndiceParecido());
//		tiempoAnadidaOriginal.setText(imagenTemp.getFecha()+"");
//		
//		imagenSimilar.setText("");
//		try {
//			imagenSimilar.setIcon(new ImageIcon(ImageIO.read(imagenTemp.getArrayComparaciones().get(0).getImagen())));
//		} catch (IOException e) {
//			imagenSimilar.setText("Error al cargar la imagen");
//		}
//		
//		nombreSimilar.setText(imagenTemp.getArrayComparaciones().get(0).getNombre() + imagenTemp.getArrayComparaciones().get(0).getExtension());
//		for(Etiquetas e : imagenTemp.getArrayComparaciones().get(0).getArrayEtiquetas()) {
//			panelEtiquetasSimilar.add(e);
//		}
//		
//		botonAnterior.setEnabled(false);
//		if(imagenTemp.getArrayComparaciones().size()==1) {
//			botonSiguiente.setEnabled(false);
//		}
//		etiquetaCantidad.setText("1/" + imagenTemp.getArrayComparaciones().size());
//		
//		this.revalidate();
//		this.repaint();
//		
//		System.out.println(panelIzqImagen.getPreferredSize());
//		System.out.println(panelIzqImagen.getSize());
//		System.out.println(panelIzqImagen.getMinimumSize());
//		System.out.println(panelIzqImagen.getMaximumSize());
//		
//		
//	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getSource()==botonAnterior) {
			
			if(indiceActual == 1) botonAnterior.setEnabled(false);
			if(indiceActual == (imagenTemp.getArrayComparaciones().size()-1) && !botonSiguiente.isEnabled()) botonSiguiente.setEnabled(true);
			
			indiceActual--;
			cambiarImagenSec(indiceActual);
			
		}else if(ev.getSource()==botonSiguiente) {
			
			if(indiceActual == 0 && !botonAnterior.isEnabled()) botonAnterior.setEnabled(true);
			if(indiceActual == imagenTemp.getArrayComparaciones().size()-2) botonSiguiente.setEnabled(false);
			
			indiceActual++;
			cambiarImagenSec(indiceActual);
		}
		
		
	}

}
