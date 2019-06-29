package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VistaImagenDatos extends JPanel implements KeyListener{
	
	private JLabel labelImagen;
	private JPanel panelIzq;
	
	
	private JPanel panelDer;
	private JPanel panelDerInfo;
	
	private JPanel panelDerNorte;
	private JLabel dNombre;
	private JTextField cajaNombre;
	
	private JPanel panelDerCentro;
	private JLabel dExtension;
	private JLabel dFecha;
	private JLabel dPeso;
	private JLabel dDimensiones;//TODO añadir y ordenar para que haya coherencia con el resultado
	
	private JPanel panelDerSur;
	private JPanel panelDerSurBarra;
	private JTextField cajaEtiquetas;
	private JButton botonAnadirEtiqueta;
	private JPanel panelDerSurEtiquetas;


	private JPanel panelDerBotones;
	
	private JButton botonVolver;
	private JButton botonEditar;
	private JButton botonBorrarImagen;
	
	
	
	
	public VistaImagenDatos() {
		
		
		this.setLayout(new GridLayout(1,2));
			
		panelIzq = new JPanel();
		panelIzq.setLayout(null);
		
		labelImagen = new JLabel();
		
		panelIzq.add(labelImagen);
		panelIzq.setPreferredSize(new Dimension((int)(this.getWidth()*0.7), this.getHeight()));
		
		panelDer = new JPanel(new BorderLayout());
		
		panelDerInfo = new JPanel(new BorderLayout());
		
		panelDerNorte = new JPanel(new FlowLayout(FlowLayout.CENTER));
		dNombre = new JLabel("Nombre test");
		panelDerNorte.add(dNombre);
		cajaNombre = new JTextField(30);
		
		panelDerCentro = new JPanel();
		//TODO comprobar que ese 4 está guay cuando acabe de meter componentes, quizás aumentar para que quede estético
		panelDerCentro.setLayout(new GridLayout(4, 1)); 
		dExtension = new JLabel("Extensión test");
		dFecha = new JLabel("Fecha test");
		dPeso = new JLabel("Peso test");
		dDimensiones = new JLabel("Dimensiones test");
		
		panelDerCentro.add(dExtension);
		panelDerCentro.add(dFecha);
		panelDerCentro.add(dPeso);
		panelDerCentro.add(dDimensiones);
		
		panelDerSur = new JPanel();
		panelDerSur.setLayout(new BorderLayout());
		panelDerSur.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true), "Etiquetas", TitledBorder.LEFT, TitledBorder.TOP));
		panelDerSur.setPreferredSize(new Dimension(500,150));
		panelDerSurBarra = new JPanel();
		cajaEtiquetas = new JTextField(30);
		botonAnadirEtiqueta = new JButton("Añadir");
		panelDerSurBarra.add(cajaEtiquetas);
		panelDerSurBarra.add(botonAnadirEtiqueta);
		panelDerSurEtiquetas = new JPanel();
		
		panelDerSur.add(panelDerSurEtiquetas, BorderLayout.CENTER);
		
		
		panelDerBotones = new JPanel();
		
		botonVolver = new JButton("<Volver");
		botonBorrarImagen = new JButton("Borrar");
		botonEditar = new JButton("Editar");
		
		panelDerBotones.add(botonVolver);
		panelDerBotones.add(botonEditar);
		panelDerBotones.add(botonBorrarImagen);
		
		
		panelDerInfo.add(panelDerNorte, BorderLayout.NORTH);
		panelDerInfo.add(panelDerCentro, BorderLayout.CENTER);
		panelDerInfo.add(panelDerSur, BorderLayout.SOUTH);
		
		panelDer.add(panelDerInfo, BorderLayout.CENTER);
		panelDer.add(panelDerBotones, BorderLayout.SOUTH);
		
		
		this.add(panelIzq);
		this.add(panelDer);
		
		
		cajaEtiquetas.addKeyListener(this);
	
		
	}
	
	
	//------------------GETTERS Y SETTERS
	public JLabel getLabelImagen() {
		return labelImagen;
	}
	
	public JPanel getPanelIzq() {
		return panelIzq;
	}
	
	public JPanel getPanelDer() {
		return panelDer;
	}

	public JLabel getdNombre() {
		return dNombre;
	}
	
	public JTextField getCajaNombre() {
		return cajaNombre;
	}

	public JLabel getdExtension() {
		return dExtension;
	}

	public JLabel getdFecha() {
		return dFecha;
	}

	public JLabel getdPeso() {
		return dPeso;
	}

	public JLabel getdDimensiones() {
		return dDimensiones;
	}

	public JPanel getPanelDerSurEtiquetas() {
		return panelDerSurEtiquetas;
	}
	
	public JTextField getCajaEtiquetas() {
		return cajaEtiquetas;
	}
	
	public JButton getBotonAnadirEtiqueta() {
		return botonAnadirEtiqueta;
	}
	
	public JButton getBotonVolver() {
		return botonVolver;
	}
	
	public JButton getBotonBorrarImagen() {
		return botonBorrarImagen;
	}
	
	public JButton getBotonEditar() {
		return botonEditar;
	}
	
	
	
	
	public void cambiarAModoEdicion(boolean activar) {
		
		panelDerNorte.removeAll();
		
		if(activar) {
			
			panelDerNorte.add(cajaNombre);
			cajaNombre.setText(dNombre.getText());
			
			panelDerSur.add(panelDerSurBarra, BorderLayout.NORTH);
			
			botonEditar.setText("Guardar");
			
		}else {
			
			panelDerNorte.add(dNombre);
			
			panelDerSur.remove(panelDerSurBarra);
			
			botonEditar.setText("Editar");
			
		}
		
		panelDer.revalidate();
		panelDer.repaint();
		
	}


	@Override
	public void keyPressed(KeyEvent ev) {
		if(ev.getKeyCode() == KeyEvent.VK_ENTER) {
			botonAnadirEtiqueta.doClick();
		}
	}


	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	
}
