package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelImagen extends JPanel{
	
	
	private JPanel panelPosteriorImagen;
	private JLabel imagen;
	
	private JLabel flechaIzq;
	private JLabel flechaDer;
	private JPanel panelContador;
	private JLabel contador;
	
	public PanelImagen() {
		
		this.setLayout(null);
		//this.setBackground(Color.RED);
		
		
		
		try {
			flechaIzq = new JLabel(new ImageIcon(ImageIO.read(new File("src/vista/img/flechaIzq.png"))));
			flechaDer = new JLabel(new ImageIcon(ImageIO.read(new File("src/vista/img/flechaDer.png"))));
		} catch (IOException e) {
			flechaIzq = new JLabel("<");
			flechaIzq.setFont(new Font("Arial",Font.BOLD,50));
			flechaDer = new JLabel(">");
			flechaDer.setFont(new Font("Arial",Font.BOLD,50));
		}
		this.add(flechaIzq);
		this.add(flechaDer);
		
		contador = new JLabel(" ");
		this.add(contador);
		
		imagen = new JLabel();
		this.add(imagen);
		
		
		//------DEPRECATED?
//		panelPosteriorImagen = new JPanel();
//		imagen = new JLabel();
//		flechaIzq = new JLabel();
//		flechaDer = new JLabel();
//		panelContador = new JPanel();
//		contador = new JLabel();
//		
//		this.add(imagen);
		
		//------------------------------------------------------------
		
//		this.setLayout(new BorderLayout());
//		this.setBackground(Color.RED);
//		
//		panelPosteriorImagen = new JPanel();
//		panelPosteriorImagen.setBackground(Color.GREEN);
//		panelPosteriorImagen.setPreferredSize(this.getPreferredSize());
//		imagen = new JLabel();
//		panelPosteriorImagen.add(imagen);
//		
//		this.add(panelPosteriorImagen);
//		
//		
//		panelContador = new JPanel(new FlowLayout(FlowLayout.CENTER));
//		panelContador.setOpaque(false);
//		contador = new JLabel(" ");
//		panelContador.add(contador);
//		
//		try {
//			flechaIzq = new JLabel(new ImageIcon(ImageIO.read(new File("src/vista/img/flechaIzq.png"))));
//			flechaDer = new JLabel(new ImageIcon(ImageIO.read(new File("src/vista/img/flechaDer.png"))));
//		} catch (IOException e) {
//			flechaIzq = new JLabel("<");
//			flechaIzq.setFont(new Font("Arial",Font.BOLD,50));
//			flechaDer = new JLabel(">");
//			flechaDer.setFont(new Font("Arial",Font.BOLD,50));
//			e.printStackTrace();
//		}
//		
//		
//		flechaIzq.setSize(new Dimension(flechaIzq.getIcon().getIconWidth(),flechaIzq.getIcon().getIconHeight()));
//		flechaIzq.setLocation((flechaIzq.getWidth()/2)+20, (this.getHeight()+flechaIzq.getHeight())/2);
//		
//		this.add(flechaIzq,BorderLayout.WEST);
//		this.add(flechaDer,BorderLayout.EAST);
//		this.add(panelContador,BorderLayout.SOUTH);

		//----------------------------------------------------
		
		
//		
//		
//		this.add(contador);
//		
//		
//		
//		this.add(flechaIzq);
//		this.add(flechaDer);
//		
//		contador.setLocation((this.getWidth()+contador.getWidth())/2, this.getHeight()-20);
//		flechaIzq.setLocation((flechaIzq.getWidth()/2)+20, (this.getHeight()+flechaIzq.getHeight())/2);
//		flechaDer.setLocation(this.getWidth()-(flechaDer.getWidth()+20), (this.getHeight()+flechaDer.getHeight())/2);
//		contador.setSize(200,200);
//		flechaIzq.setSize(100,100);
		
	}
	
	public JLabel getFlechaIzq() {
		return flechaIzq;
	}
	
	public JLabel getFlechaDer() {
		return flechaDer;
	}
	
	
	//public void cambiarImagen(File imagenMostrar, int num1, int num2) {
	public void cambiarImagen(BufferedImage bi, int num1, int num2) {
		
		Image bi2 = VistaPrincipal.ponerImagenEscalada(bi, this);
		
		imagen.setIcon(new ImageIcon(bi2));
		
		imagen.setBounds((int)(((float)this.getWidth()/2) - ((float)bi2.getWidth(null))/((float)2)), (int)(((float)this.getHeight()/2) - ((float)bi2.getHeight(null))/((float)2)),bi2.getWidth(null), bi2.getHeight(null));
		
		flechaIzq.setBounds(40,(this.getHeight()/2) - (flechaIzq.getIcon().getIconHeight()/2), flechaIzq.getIcon().getIconWidth(), flechaIzq.getIcon().getIconHeight());
		flechaDer.setBounds(this.getWidth() - (flechaDer.getIcon().getIconWidth()) - 40, (this.getHeight()/2) - (flechaDer.getIcon().getIconWidth()/2), flechaDer.getIcon().getIconWidth(), flechaDer.getIcon().getIconHeight());
		
		contador.setText(num1 + "/" + num2);
		contador.setBounds((int)(this.getWidth()/2 - contador.getPreferredSize().getWidth()/2), (int)(this.getHeight() - contador.getPreferredSize().getHeight()), (int)contador.getPreferredSize().getWidth(), (int)contador.getPreferredSize().getHeight());
		
//		contador.setSize(contador.getPreferredSize());
//		contador.setLocation(15, 15);
		
		//System.out.println(contador.getPreferredSize().toString());
		
		
//		//TODO reescalar imagen en caso de que sea más grande que el panel
//		
//		contador.setText(num1 + "/" + num2);
//		
//		try {
//			imagen.setIcon(new ImageIcon(ImageIO.read(imagenMostrar)));
//		} catch (IOException e) {
//			imagen.setIcon(null);
//			imagen.setText("Error cargando la imagen");
//		} catch (Exception e) {
//			imagen.setIcon(null);
//			imagen.setText("MEEEEEEEEEEEEEEC");
//		}finally {
//			//this.revalidate();
//		}
//		
	}
	
	public void vaciar() {
		imagen.setIcon(null);
		contador.setText("");
	}
	
//	TODO ver forma de llamar al revalidate super y a este después en caso de que las flechas no se ajusten a la nueva posición al redimensionar jframe	
//	public void revalidate() {
//		
//	}

}
