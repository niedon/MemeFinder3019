package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class Etiquetas extends JPanel{
	
	private String etiqueta;
	private JLabel etiquetaIcono;
	private int cuenta;
	
	//TODO quitar lo comentado para adaptar a bbdd
	public Etiquetas(String etiqueta, int cuenta) {

		
		this.etiqueta = etiqueta;
		this.cuenta = cuenta;
		
		this.setBorder(BorderFactory.createLineBorder(Color.RED, 1, true));
		this.setBackground(Color.CYAN);
		
		this.setLayout(new BorderLayout(0,0));
		
		this.add(new JLabel(etiqueta + "(" + cuenta + ")"), BorderLayout.CENTER);
		
		try {
			etiquetaIcono = new JLabel(new ImageIcon(ImageIO.read(new File("src/vista/img/borrar.png"))));
			this.add(etiquetaIcono, BorderLayout.EAST);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String getTexto() {
		return etiqueta;
	}
	
	public int getCuenta() {
		return cuenta;
	}
	
	public void anadirListenerEliminar(ActionListener al) {
		etiquetaIcono.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				al.actionPerformed(new ActionEvent(Etiquetas.this,0,etiqueta));
				//System.out.println("clic");
				
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
			
		});
		
	}
	
	public boolean equals(Object otroObjeto) {
		
		if(this==otroObjeto) {
			return true;
		}
		if(otroObjeto instanceof Etiquetas) {
			Etiquetas otro  = (Etiquetas)otroObjeto;
			return (etiqueta == null && otro.etiqueta == null)
			          || etiqueta.equals(otro.etiqueta);
		}
		return false;
		
	}
	

}
