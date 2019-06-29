package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import controlador.ImagenTemp;

public class PanelImagenRes extends JPanel implements ComponentListener, MouseListener{
	
	ImagenTemp itemp;
	
	private JPanel panelGeneral;
	
	private JPanel panelLateral;
	private JLabel labelImagen;
	
	
	private JPanel panelCentral;
	
	private JPanel panelSuperior;
	private JPanel panelSuperiorNombre;
	private JLabel labelNombre;
	private JPanel panelSuperiorFecha;
	private JLabel labelFecha;
	
	private JPanel panelInferior;
	
	public PanelImagenRes() {
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY,3,true));
		
		panelGeneral = new JPanel(new BorderLayout());
		panelGeneral.setBorder(new EmptyBorder(5,5,5,5));
		
		
		panelLateral = new JPanel();
		panelLateral.setPreferredSize(new Dimension(130,130));//TODO mejorar, está aquí temporal
		
		panelLateral.setLayout(null);
		labelImagen = new JLabel();
		panelLateral.add(labelImagen);
		
		
		panelCentral = new JPanel(new BorderLayout());
		
		panelSuperior = new JPanel(new BorderLayout());
		panelSuperiorNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelNombre = new JLabel();
		panelSuperiorNombre.add(labelNombre);
		panelSuperiorFecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		labelFecha = new JLabel();
		panelSuperiorFecha.add(labelFecha);
		panelSuperior.add(panelSuperiorNombre, BorderLayout.WEST);
		panelSuperior.add(panelSuperiorFecha, BorderLayout.EAST);
		
		panelInferior = new JPanel();
		panelInferior.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true), "Etiquetas", TitledBorder.LEFT, TitledBorder.TOP));
		
		panelCentral.add(panelSuperior,BorderLayout.NORTH);
		panelCentral.add(panelInferior,BorderLayout.CENTER);
		
		panelGeneral.add(panelLateral,BorderLayout.WEST);
		panelGeneral.add(panelCentral, BorderLayout.CENTER);
		
		this.add(panelGeneral);
		
	}
	
	public void setImagen(ImagenTemp itemp) {
		this.itemp = itemp;
		
		panelLateral.setSize(130,130);
		
		Image imgOriginal = VistaPrincipal.ponerImagenEscalada(itemp.getbImagen(), panelLateral);
		labelImagen.setIcon(new ImageIcon(imgOriginal));
		labelImagen.setBounds((int)(((float)panelLateral.getWidth()/2) - ((float)imgOriginal.getWidth(null))/((float)2)), (int)(((float)panelLateral.getHeight()/2) - ((float)imgOriginal.getHeight(null))/((float)2)),imgOriginal.getWidth(null), imgOriginal.getHeight(null));
		labelNombre.setText(itemp.getNombre()+itemp.getExtension());
		labelFecha.setText(itemp.getFecha()+"");
		
		for(Etiquetas e : itemp.getArrayEtiquetas()) {
			panelInferior.add(e);
		}
		
		
		
	}
	
	public ImagenTemp getImagenTemp() {
		return itemp;
	}
	
	@Override
	public void componentHidden(ComponentEvent arg0) {
		System.out.println("hidden");
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		System.out.println("moved");
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		System.out.println("sizeado " + itemp.getNombre());
		labelImagen.setIcon(new ImageIcon(VistaPrincipal.ponerImagenEscalada(itemp.getbImagen(), panelLateral)));
		
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		System.out.println("shwon");
		
	}






	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		
	}






	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}






	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}






	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}






	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
