package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test2 {
	
	private JPanel paneltest1,paneltest2,paneltest3;
	
	public Test2() {
		
		System.out.println("test");
		
		JFrame caca = new JFrame();
		caca.setLocationRelativeTo(null);
		
		paneltest1 = new JPanel();
		paneltest1.add(new JLabel("AAAAAAAAAAAAAAAAAAAAA"));
		
		
		try {
			BufferedImage bi = ImageIO.read(new File("src/vista/img/aaa.gif"));
			
			Graphics g = bi.getGraphics();
			paneltest1.add(new JLabel(new ImageIcon(bi)));
			
			g.drawImage(bi,100,100,null);
			
			
		} catch (IOException e) {
			
			System.out.println("caca");
			
		}
		
		caca.add(paneltest1);
		
	}	
		
	

}
