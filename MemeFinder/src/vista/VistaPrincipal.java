package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class VistaPrincipal extends JFrame{
	
	//CONSTANTES
	
	public static final String PANELINICIO = "PANELINICIO";
	public static final String PANELRESULTADOS = "PANELRESULTADOS";
	public static final String PANELANADIR = "PANELANADIR";
	public static final String PANELCOINCIDENCIAS = "PANELCOINCIDENCIAS";
	public static final String PANELIMAGENDATOS = "PANELIMAGENDATOS";
	
	//FIN CONSTANTES
	
	private JPanel panelGeneral;
	
	private JPanel panelMenuGeneral;
	private JMenuBar menuGeneral;
	//private JMenuItem anadirNuevos;
	
	private JPanel panelPrincipal;
	private CardLayout cardLayoutPrincipal;
	
	//TODO meter layout que convenga, ahora por defecto
	private JPanel panelInicio;
	private JTextField barraBusquedaPrincipal;
	private JButton botonBusquedaPrincipal;
	
	//private JPanel panelResultados = new JPanel();
	private VistaResultados panelResultados;
	private VistaAnadir panelAnadir;
	private VistaCoincidencias panelCoincidencias;
	private VistaImagenDatos panelImagenDatos;
	
	
	public VistaPrincipal() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//PANEL GENERAL (todo) y PRINCIPAL (todo-menú)
		panelGeneral = new JPanel(new BorderLayout());
		
		panelPrincipal = new JPanel();
		//TODO llevar cardlayout a controlador
		//TODO definir nombres de paneles por variables estáticas final
		cardLayoutPrincipal = new CardLayout();
		panelPrincipal.setLayout(cardLayoutPrincipal);
		
		
		//-----PANEL MENÚ
		panelMenuGeneral = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menuGeneral = new JMenuBar();
		panelMenuGeneral.add(menuGeneral);
		panelGeneral.add(panelMenuGeneral, BorderLayout.NORTH);
		
		
		//-----PANEL INICIO
		panelInicio = new JPanel(); 
		barraBusquedaPrincipal = new JTextField(20);
		botonBusquedaPrincipal = new JButton("Buscar");
		
		addPulsarEnterListener();
		panelInicio.add(barraBusquedaPrincipal);
		panelInicio.add(botonBusquedaPrincipal);
		cardLayoutPrincipal.addLayoutComponent(panelInicio, PANELINICIO);
		panelPrincipal.add(panelInicio);
		
		
		//-----PANEL RESULTADOS
		panelResultados = new VistaResultados();
		cardLayoutPrincipal.addLayoutComponent(panelResultados, PANELRESULTADOS);
		panelPrincipal.add(panelResultados);
		
		
		//-----PANEL ANADIR
		panelAnadir = new VistaAnadir();
		cardLayoutPrincipal.addLayoutComponent(panelAnadir, PANELANADIR);
		panelPrincipal.add(panelAnadir);
		panelAnadir.revalidate();//TODO comprobar si esto va bien para que se ordenen los componentes
		
		
		//-----PANEL COINCIDENCIAS
		panelCoincidencias = new VistaCoincidencias();
		cardLayoutPrincipal.addLayoutComponent(panelCoincidencias, PANELCOINCIDENCIAS);
		panelPrincipal.add(panelCoincidencias);
		
		
		//-----PANEL IMAGEN INDIVIDUAL
		panelImagenDatos = new VistaImagenDatos();
		cardLayoutPrincipal.addLayoutComponent(panelImagenDatos,PANELIMAGENDATOS);
		panelPrincipal.add(panelImagenDatos);
		
		
		
		
		//-----AÑADIR GENERALES
		panelGeneral.add(panelPrincipal, BorderLayout.CENTER);
		this.add(panelGeneral);
		this.setBounds(250, 250, 1280, 720);
		
		//this.pack();
		
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		
//		panelGeneral = new JPanel(new BorderLayout());
//		
//		panelMenuGeneral = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		menuGeneral = new JMenuBar();
//		//anadirNuevos = new JMenuItem("Añadir");
//		
////		anadirNuevos.addActionListener(new ActionListener() {
////
////			@Override
////			public void actionPerformed(ActionEvent arg0) {
////				System.out.println("FUNCHONA");
////				
////			}
////			
////		});
//		
//		//menuGeneral.add(anadirNuevos);
//		//JMenu menuTest = new JMenu("testaso");
//		//JMenuItem itemTest = new JMenuItem("testaso2");
//		
//		//menuTest.add(itemTest);
//		//menuGeneral.add(menuTest);
//		
//		//----------PANEL MENÚ
//		
//		panelMenuGeneral.add(menuGeneral);
//		
//		panelGeneral.add(panelMenuGeneral, BorderLayout.NORTH);
//		
//		
//		//----------PANEL INICIO
//		
//		addPulsarEnterListener();
//		
//		panelInicio.add(barraBusquedaPrincipal);
//		panelInicio.add(botonBusquedaPrincipal);
//		
////		//prueba de icono con X
////		try {
////			JLabel c = new JLabel("holi",new ImageIcon(ImageIO.read(new File("src/vista/img/borrar.png"))), JLabel.LEADING);
////			c.setHorizontalTextPosition(SwingConstants.LEADING);
////            c.setHorizontalAlignment(SwingConstants.LEADING);
////			panelInicio.add(c);
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		//new ImageIcon(ImageIO.read(new File("src/vista/img/borrar.png")))
//		cardLayoutPrincipal.addLayoutComponent(panelInicio, "PANELINICIO");
//		
//		
//		
//		
////		JLabel bien = new JLabel("Bien");
////		panelResultados.add(bien);
//		
//		//---------PANEL RESULTADOS
//		
//		cardLayoutPrincipal.addLayoutComponent(panelResultados, "PANELRESULTADOS");
//		
//		//---------PANEL ANADIR
//		
//		panelAnadir = new VistaAnadir();
//		cardLayoutPrincipal.addLayoutComponent(panelAnadir, "PANELANADIR");
//		
//		
//		panelPrincipal.add(panelInicio);
//		panelPrincipal.add(panelResultados);
//		panelPrincipal.add(panelAnadir);
//		
//		
//		panelPrincipal.setLayout(cardLayoutPrincipal);
//		panelGeneral.add(panelPrincipal, BorderLayout.CENTER);
//		this.add(panelGeneral);
//		
//		this.setBounds(250, 250, 1280, 720);
		
		
	}
	
	public String getTextoBarraBusqueda() {
		return barraBusquedaPrincipal.getText();
	}
	
	public JMenuBar getMenuPrincipal() {
		return menuGeneral;
	}
	
	public JButton getBotonBusquedaPrincipal() {
		return botonBusquedaPrincipal;
	}
	
	public VistaAnadir getVistaAnadir() {return panelAnadir;}
	public VistaResultados getVistaResultados() {return panelResultados;}
	public VistaCoincidencias getVistaCoincidencias() {return panelCoincidencias;}
	public VistaImagenDatos getVistaImagenDatos() {return panelImagenDatos;}
	
	public void cambiaCardLayout(String panel) {
		cardLayoutPrincipal.show(panelPrincipal, panel);
	}
	
//	public void addCambiadorTest(ActionListener al) {
//		botonBusquedaPrincipal.addActionListener(al);
//	}
	
	//Hace que pulsar enter sea lo mismo que pulsar el botón
	private void addPulsarEnterListener() {
		
		barraBusquedaPrincipal.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					botonBusquedaPrincipal.doClick();
					System.out.println("enter en barra principal");
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		
		
	}
	
	//TODO cuidado, los dos siguientes métodos estaban en protected y ahora están en public
	public static Image ponerImagenEscalada(BufferedImage imagen, Container contenedor) {
		
		//Ojo a los !!
		//Si la imagen no es mayor que el contenedor, ni en alto ni en ancho
		//TODO restar aquí valor si hay que meterle un "padding" o marco imaginario para que la imagen no dé en el borde del contenedor
		if(!(imagen.getWidth()>contenedor.getWidth()) && !(imagen.getHeight()>contenedor.getHeight())) {
			//System.out.println("Chico");
			return imagen;

		}else if(contenedor.getWidth()*contenedor.getHeight() == 0){
			
			return imagen;
			
		}else {
			
			
			float deltaX = imagen.getWidth()==0 ? 1 : ((float)contenedor.getWidth()) / ((float)imagen.getWidth());
			float deltaY = imagen.getHeight()==0 ? 1 : ((float)contenedor.getHeight()) / ((float)imagen.getHeight());
			
			//System.out.println("w" + contenedor.getWidth() + " h" + contenedor.getHeight());
			
			float deltaGen = Math.min(deltaX, deltaY);
			
			Image aRetornar = imagen.getScaledInstance((int)(imagen.getWidth()*deltaGen), (int)(imagen.getHeight()*deltaGen), Image.SCALE_SMOOTH);
			
			//BufferedImage retorna = new BufferedImage(aRetornar.getWidth(null), aRetornar.getHeight(null),BufferedImage.TYPE_INT_RGB);
			
			
			
			return aRetornar;
			
			
			
			
			
		}
		
	}
	
	
	public static Image ponerImagenEscalada(Image imagen, Container contenedor, int zoomazo) {
		
		
		return imagen.getScaledInstance((int)(imagen.getWidth(null)*zoomazo), (int)(imagen.getHeight(null)*zoomazo), Image.SCALE_FAST);
		
		
//		//Ojo a los !!
//		//Si la imagen no es mayor que el contenedor, ni en alto ni en ancho
//		//TODO restar aquí valor si hay que meterle un "padding" o marco imaginario para que la imagen no dé en el borde del contenedor
//		if(!(imagen.getWidth(null)>contenedor.getWidth()) && !(imagen.getHeight(null)>contenedor.getHeight())) {
//			//System.out.println("Chico");
//			return imagen;
//
//		}else if(contenedor.getWidth()*contenedor.getHeight() == 0){
//			
//			return imagen;
//			
//		}else {
//			
//			
//			float deltaX = imagen.getWidth(null)==0 ? 1 : ((float)contenedor.getWidth()) / ((float)imagen.getWidth(null));
//			float deltaY = imagen.getHeight(null)==0 ? 1 : ((float)contenedor.getHeight()) / ((float)imagen.getHeight(null));
//			
//			//System.out.println("w" + contenedor.getWidth() + " h" + contenedor.getHeight());
//			
//			float deltaGen = Math.min(deltaX, deltaY);
//			deltaGen *= zoomazo;
//			
//			Image aRetornar = imagen.getScaledInstance((int)(imagen.getWidth(null)*deltaGen), (int)(imagen.getHeight(null)*deltaGen), Image.SCALE_SMOOTH);
//			
//			//BufferedImage retorna = new BufferedImage(aRetornar.getWidth(null), aRetornar.getHeight(null),BufferedImage.TYPE_INT_RGB);
//			
//			
//			
//			return aRetornar;
//			
//			
//			
//			
//			
//		}
		
		
		
		
	}
	
	
	
	
//	public static void testCambiarImagen(int coordenadaCero, int coordenadaFinal, float indiceRaton, int zoomOriginal, int zoomObjetivo) {
//		
//		float zoomZeta = ((float)zoomObjetivo)/((float)zoomOriginal);
//		System.out.println("zoom zeta: " + zoomZeta);
//		
//		float numeroARestar = zoomZeta-1;
//		numeroARestar = numeroARestar/zoomZeta;
//		numeroARestar = numeroARestar * indiceRaton;
//		
//		int resultado = 
//		
//	}

}
