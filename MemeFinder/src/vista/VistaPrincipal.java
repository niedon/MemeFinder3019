package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.*;


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
	
	private JPanel panelPrincipal;
	private CardLayout cardLayoutPrincipal;
	
	private JPanel panelInicio;
	private JTextField barraBusquedaPrincipal;
	private JButton botonBusquedaPrincipal;
	
	private VistaResultados panelResultados;
	private VistaAnadir panelAnadir;
	private VistaCoincidencias panelCoincidencias;
	private VistaImagenDatos panelImagenDatos;
	
	
	public VistaPrincipal() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//PANEL GENERAL (todo) y PRINCIPAL (todo-menú)
		panelGeneral = new JPanel(new BorderLayout());
		
		panelPrincipal = new JPanel();
		//TODO llevar cardlayout a controlador??
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
	
	
	//Hace que pulsar enter sea lo mismo que pulsar el botón
	private void addPulsarEnterListener() {
		
		barraBusquedaPrincipal.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) botonBusquedaPrincipal.doClick();
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		
		
	}
	
	//TODO cuidado, el siguiente método estaba en protected y ahora está en public
	public static Image ponerImagenEscalada(BufferedImage imagen, Container contenedor) {
		
		//Ojo a los !!
		//Si la imagen no es mayor que el contenedor, ni en alto ni en ancho
		//TODO restar aquí valor si hay que meterle un "padding" o marco imaginario para que la imagen no dé en el borde del contenedor
		if(!(imagen.getWidth()>contenedor.getWidth()) && !(imagen.getHeight()>contenedor.getHeight())) {
			
			return imagen;

		}else if(contenedor.getWidth()*contenedor.getHeight() == 0){
			
			return imagen;
			
		}else {
			
			float deltaX = imagen.getWidth()==0 ? 1 : ((float)contenedor.getWidth()) / ((float)imagen.getWidth());
			float deltaY = imagen.getHeight()==0 ? 1 : ((float)contenedor.getHeight()) / ((float)imagen.getHeight());
			
			float deltaGen = Math.min(deltaX, deltaY);
			
			Image aRetornar = imagen.getScaledInstance((int)(imagen.getWidth()*deltaGen), (int)(imagen.getHeight()*deltaGen), Image.SCALE_SMOOTH);
			
			return aRetornar;
			
		}
		
	}

}
