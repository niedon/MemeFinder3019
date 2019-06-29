package vista;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelImagen extends JPanel{
	
	private JLabel imagen;
	
	private JLabel flechaIzq;
	private JLabel flechaDer;
	private JLabel contador;
	
	public PanelImagen() {
		
		this.setLayout(null);
		
		try {
			flechaIzq = new JLabel(new ImageIcon(ImageIO.read(new File("src/vista/img/flechaIzq.png"))));
			flechaDer = new JLabel(new ImageIcon(ImageIO.read(new File("src/vista/img/flechaDer.png"))));
		}catch (IOException e) {
			flechaIzq = new JLabel("<");
			flechaIzq.setFont(new Font("Arial",Font.BOLD,50));
			flechaDer = new JLabel(">");
			flechaDer.setFont(new Font("Arial",Font.BOLD,50));
		}finally {
			this.add(flechaIzq);
			this.add(flechaDer);
		}
		
		
		contador = new JLabel(" ");
		this.add(contador);
		
		imagen = new JLabel();
		this.add(imagen);
		
	}
	
	public JLabel getFlechaIzq() {
		return flechaIzq;
	}
	
	public JLabel getFlechaDer() {
		return flechaDer;
	}
	
	
	public void cambiarImagen(BufferedImage bi, int num1, int num2) {
		
		Image bi2 = VistaPrincipal.ponerImagenEscalada(bi, this);
		
		imagen.setIcon(new ImageIcon(bi2));
		
		imagen.setBounds((int)(((float)this.getWidth()/2) - ((float)bi2.getWidth(null))/((float)2)), (int)(((float)this.getHeight()/2) - ((float)bi2.getHeight(null))/((float)2)),bi2.getWidth(null), bi2.getHeight(null));
		
		flechaIzq.setBounds(40,(this.getHeight()/2) - (flechaIzq.getIcon().getIconHeight()/2), flechaIzq.getIcon().getIconWidth(), flechaIzq.getIcon().getIconHeight());
		flechaDer.setBounds(this.getWidth() - (flechaDer.getIcon().getIconWidth()) - 40, (this.getHeight()/2) - (flechaDer.getIcon().getIconWidth()/2), flechaDer.getIcon().getIconWidth(), flechaDer.getIcon().getIconHeight());
		
		contador.setText(num1>num2 ? (num1-1)+"/"+num2 : num1+"/"+num2);//Evita el bug 1/0 cuando el panel está vacío
//		if(num1>num2)contador.setText((num1-1) + "/" + num2);
//		else contador.setText(num1 + "/" + num2);
		
		contador.setBounds((int)(this.getWidth()/2 - contador.getPreferredSize().getWidth()/2), (int)(this.getHeight() - contador.getPreferredSize().getHeight()), (int)contador.getPreferredSize().getWidth(), (int)contador.getPreferredSize().getHeight());
		
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
