package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import vista.VistaCoincidencias;

public class Main {
	
	private static String _ID = "_id";
	

	public static void main(String[] args) {
		
		long aaa = 10;
		int aa = 3;
		long a = aaa/aa;
		System.out.println(a);
		
		
		
		
//		String[] anosComboBox = new String[LocalDate.now().getYear()-1969];
//		
////		for(int i=1970; i<LocalDate.now().getYear()+1; i++) {
////			System.out.println(i + "-");
////		}
//		int anoActual = LocalDate.now().getYear();
//		for(int i=anoActual; i>1969; i--) {
//			System.out.println(anoActual-i + "-" + i);
//		}
//		
//		
////		for(int i=1; i<32; i++) {
////			System.out.print("\"" + i + "\", ");
////		}
////		System.out.println();
////		System.out.println(LocalDate.now().getYear());
//		
////		//Test2 test2 = new Test2();
////		
////		JFrame caca = new JFrame();
////		caca.setSize(new Dimension(1280,900));
////		caca.setLocationRelativeTo(null);
////		
////		
////		
////		JPanel paneltest1 = new JPanel();
////		//paneltest1.add(new JLabel("AAAAAAAAAAAAAAAAAAAAA"));
////		
////		
////		try {
////			BufferedImage bi = ImageIO.read(new File("src/vista/img/bbb.jpg"));
////			
////			Graphics g = bi.getGraphics();
////			
////			//paneltest1.add(new JLabel(new ImageIcon(bi)));
////			
////			g.drawImage(bi,0,0,100,100,null);
////			
////			
////		} catch (IOException e) {
////			
////			System.out.println("caca");
////			
////		}
////		
////		caca.add(paneltest1);
////		
////		caca.setVisible(true);
		
		

	}
	
	private static void pruebaMongoDB() {
		
		try {
			
			MongoClient mc = new MongoClient("localhost", 27017);
			MongoDatabase mdb = mc.getDatabase("DBTest");
			MongoCollection mcol = mdb.getCollection("ColTest");
			System.out.println(mdb.getName());
			
//			Document document = new Document("campoTest",2);
//			mcol.insertOne(document);
			
//			Document documento2 = new Document("campoTest",5);
//			mcol.insertOne(documento2);
//			

			
			System.out.println(mcol.countDocuments(new Document("campoTest",2)));
			
			System.out.println();
			for(Object o : mcol.find()) {
				System.out.println(o);
			}
			
			
			if(mcol.countDocuments(new Document(_ID,"tetillas"))==0) {
				System.out.println("escero");
			}else {
				System.out.println("noescero");
			}
			
			mcol.insertOne(new Document(_ID,"tetillas"));
			
			if(mcol.countDocuments(new Document(_ID,"tetillas"))==0) {
				System.out.println("escero");
			}else {
				System.out.println("noescero");
			}
			
			for(Object o : mcol.find()) {
				System.out.println(o);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


}
