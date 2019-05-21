package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controlador.ImagenTemp;

public class VistaResultados extends JPanel implements KeyListener, MouseListener, ChangeListener{
	
	private ImagenTemp[] res;
	
	private JPanel resultadosOeste;
	
	private JPanel panelBarra;
	private JTextField barraBusqueda;
	private JButton botonBuscar;
	
	private JPanel panelOpcionesBuscar;
	private JPanel panelBuscarSinEtiqueta;
	private JCheckBox opBuscarSinEtiqueta;//TODO añadir des/activar visualmente al pulsar
	
	private JPanel panelFechaDespues;
	private JPanel panelFechaDespuesDer;
	private JCheckBox opFechaDespues;
	private JComboBox<String> opDiaDespues;
	private JComboBox<String> opMesDespues;
	private JComboBox<String> opAnoDespues;
	private JPanel panelFechaAntes;
	private JPanel panelFechaAntesDer;
	private JCheckBox opFechaAntes;
	private JComboBox<String> opDiaAntes;
	private JComboBox<String> opMesAntes;
	private JComboBox<String> opAnoAntes;
	private static final String[] seleccionDias = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	private static final String[] seleccionMeses = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
	
	private JPanel panelEtiquetas;
	
	
	
	private JPanel resultadosCentro;
	
	private JPanel resultadosGrid;
	//private ArrayList<ImagenTemp> arrayResultados;
	
	private JPanel panelPaginacion;
	private JButton anterior;
	private JLabel etiquetaNumeroPagina;
	private JButton siguiente;
	
	
	
	
	private JPanel resultadosEste;
	
	private JPanel panelContenidoEste;
	
	private JPanel panelImagenGrande;
	private JLabel labelImagenGrande;
	
	private JPanel panelImagenDatos;
	private JPanel panelImagenDatosChico;
	private JLabel labelNombre;
	private JLabel labelFecha;
	private JLabel labelResolucion;
	private JLabel labelPeso;
	
	private JPanel panelImagenEtiquetas;
	
	private JPanel panelImagenBotones;
	
	
	
	public VistaResultados() {
		
		this.setLayout(new BorderLayout(5,5));
		
		resultadosOeste = new JPanel();
		
		//resultadosOeste.setBackground(Color.RED);
		resultadosOeste.setPreferredSize(new Dimension(300,this.getHeight()));
		resultadosOeste.setLayout(new BoxLayout(resultadosOeste, BoxLayout.Y_AXIS));
		
		//-----------BARRA BÚSQUEDA Y BUSCAR
		panelBarra = new JPanel();
		panelBarra.setMaximumSize(new Dimension((int)panelBarra.getMaximumSize().getWidth(), (int)panelBarra.getMinimumSize().getHeight()));
		panelBarra.setBackground(Color.BLUE);
		
		barraBusqueda = new JTextField(15);
		barraBusqueda.addKeyListener(this);
		botonBuscar = new JButton("Buscar");
		
		panelBarra.add(barraBusqueda);
		panelBarra.add(botonBuscar);
		
		//------PANEL OPCIONES
		
		panelOpcionesBuscar = new JPanel();
		panelOpcionesBuscar.setLayout(new BoxLayout(panelOpcionesBuscar,BoxLayout.Y_AXIS));
		panelOpcionesBuscar.setBackground(Color.RED);
		panelOpcionesBuscar.setMaximumSize(new Dimension((int)resultadosOeste.getPreferredSize().getWidth(),(int)panelOpcionesBuscar.getMinimumSize().getHeight()));
		
		panelBuscarSinEtiqueta = new JPanel(new FlowLayout(FlowLayout.LEFT));
		opBuscarSinEtiqueta = new JCheckBox("Buscar imágenes sin etiquetas");
		opBuscarSinEtiqueta.addChangeListener(this);
		
		panelBuscarSinEtiqueta.add(opBuscarSinEtiqueta);
		panelOpcionesBuscar.add(panelBuscarSinEtiqueta);
		panelOpcionesBuscar.add(Box.createRigidArea(new Dimension(10,10)));
		
		panelFechaDespues = new JPanel(new BorderLayout());
		opFechaDespues = new JCheckBox("Después de: ");
		opFechaDespues.addChangeListener(this);
		panelFechaDespuesDer = new JPanel();
		opDiaDespues = new JComboBox<String>(seleccionDias);
		opDiaDespues.setEnabled(false);
		opMesDespues = new JComboBox<String>(seleccionMeses);
		opMesDespues.setEnabled(false);
		opAnoDespues = new JComboBox<String>();
		opAnoDespues.setEnabled(false);
		
		panelFechaDespues.add(opFechaDespues,BorderLayout.CENTER);
		panelFechaDespuesDer.add(opDiaDespues);
		panelFechaDespuesDer.add(opMesDespues);
		panelFechaDespuesDer.add(opAnoDespues);
		panelFechaDespues.add(panelFechaDespuesDer,BorderLayout.EAST);
		
		
		panelFechaAntes = new JPanel(new BorderLayout());
		opFechaAntes = new JCheckBox("Antes de:");
		opFechaAntes.addChangeListener(this);
		panelFechaAntesDer = new JPanel(); 
		opDiaAntes = new JComboBox<String>(seleccionDias);
		opDiaAntes.setSelectedIndex(LocalDate.now().getDayOfMonth()-1);
		opDiaAntes.setEnabled(false);
		opMesAntes = new JComboBox<String>(seleccionMeses);
		opMesAntes.setSelectedIndex(LocalDate.now().getMonthValue()-1);
		opMesAntes.setEnabled(false);
		opAnoAntes = new JComboBox<String>();
		opAnoAntes.setEnabled(false);
		
		panelFechaAntes.add(opFechaAntes,BorderLayout.CENTER);
		panelFechaAntesDer.add(opDiaAntes);
		panelFechaAntesDer.add(opMesAntes);
		panelFechaAntesDer.add(opAnoAntes);
		panelFechaAntes.add(panelFechaAntesDer,BorderLayout.EAST);
		
		panelOpcionesBuscar.add(panelFechaDespues);
		panelOpcionesBuscar.add(panelFechaAntes);
		
		
		//----------ETIQUETAS
		panelEtiquetas = new JPanel();
		panelEtiquetas.setLayout(new FlowLayout());
		panelEtiquetas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true), "Etiquetas", TitledBorder.LEFT, TitledBorder.TOP));
		
		//panelEtiquetas.setBackground(Color.GREEN);
		//panelEtiquetas.setPreferredSize(new Dimension(super.getWidth(),500));//TODO si todo falla en resultadosOeste, quitar este comentario
		
		
		
		
		
		//------------------------------------------------------------PANEL CENTRO
		resultadosCentro = new JPanel(new BorderLayout());
		//resultadosCentro.setBackground(Color.YELLOW);
		
		resultadosGrid = new JPanel(new GridLayout(4,1,0,5));
		panelPaginacion = new JPanel(new FlowLayout(FlowLayout.CENTER));
		anterior = new JButton("Anterior");
		etiquetaNumeroPagina = new JLabel("x/x");
		siguiente = new JButton("Siguiente");
		
		panelPaginacion.add(anterior);
		panelPaginacion.add(etiquetaNumeroPagina);
		panelPaginacion.add(siguiente);
		
		
		//-----PANEL ESTE
		
		resultadosEste = new JPanel(new BorderLayout());
		//resultadosEste.setBackground(Color.RED);
		resultadosEste.setPreferredSize(new Dimension(500,this.getHeight()));
		
		panelContenidoEste = new JPanel(new BorderLayout());
		
		//panelImagenGrande = new JPanel(new FlowLayout(FlowLayout.CENTER));//TODO poner en el centro justo, a saber cómo
		panelImagenGrande = new JPanel();
		panelImagenGrande.setLayout(null);
		labelImagenGrande = new JLabel();
		panelImagenGrande.add(labelImagenGrande);
		
		//panelImagenGrande.setBackground(Color.YELLOW);
		panelImagenGrande.setMaximumSize(new Dimension(500,400));
		panelImagenGrande.setMinimumSize(new Dimension(500,400));
		panelImagenGrande.setPreferredSize(new Dimension(500,400));
		
//		//Cagalera para que no dé error de "BoxLayout can't be shared" y quede estético
//		panelImagenDatos = new JPanel();
//		
//		BoxLayout cajita = new BoxLayout(panelImagenDatos,BoxLayout.X_AXIS);
//		panelImagenDatos.setLayout(cajita);		
//		panelImagenDatosChico = new JPanel();
//		panelImagenDatosChico.setBackground(Color.BLUE);
//		panelImagenDatosChico.setPreferredSize(panelImagenDatosChico.getPreferredSize());
//		panelImagenDatos.setMaximumSize(panelImagenDatos.getPreferredSize());
//		BoxLayout cajitaChica = new BoxLayout(panelImagenDatosChico, BoxLayout.Y_AXIS);
//		panelImagenDatosChico.setLayout(cajitaChica);
//		panelImagenDatos.add(panelImagenDatosChico);
//		panelImagenDatos.add(Box.createHorizontalGlue());
//		// </cagelera>
		
		panelImagenDatos = new JPanel(new GridLayout(4,1));
		
		labelNombre = new JLabel("Test nombre");
		labelFecha = new JLabel("Test fecha");
		labelResolucion = new JLabel("Test Resolucion");
		labelPeso = new JLabel("Test peso");
		
		panelImagenDatos.add(labelNombre);
		panelImagenDatos.add(labelFecha);
		panelImagenDatos.add(labelResolucion);
		panelImagenDatos.add(labelPeso);
		
		panelImagenEtiquetas = new JPanel();
		panelImagenEtiquetas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true), "Etiquetas", TitledBorder.LEFT, TitledBorder.TOP));
		panelImagenEtiquetas.setPreferredSize(new Dimension(500,150));
		
		panelImagenBotones = new JPanel();
		panelImagenBotones.add(new JButton("TEST BOTONES SUR"));
		
		
		
		panelContenidoEste.add(panelImagenGrande,BorderLayout.NORTH);
		panelContenidoEste.add(panelImagenDatos,BorderLayout.CENTER);
		panelContenidoEste.add(panelImagenEtiquetas,BorderLayout.SOUTH);
		
		
		
		resultadosEste.add(panelContenidoEste,BorderLayout.CENTER);
		resultadosEste.add(panelImagenBotones,BorderLayout.SOUTH);
		
		

		
		
		
		//-----------METER COMPONENTES
		resultadosOeste.add(panelBarra);
		resultadosOeste.add(Box.createRigidArea(new Dimension(15,15)));
		resultadosOeste.add(panelOpcionesBuscar);
		resultadosOeste.add(panelEtiquetas);
		//resultadosOeste.add(Box.createGlue());
		
		
		resultadosCentro.add(resultadosGrid, BorderLayout.CENTER);
		resultadosCentro.add(panelPaginacion, BorderLayout.SOUTH);
		
		
		this.add(resultadosOeste, BorderLayout.WEST);
		this.add(resultadosCentro, BorderLayout.CENTER);
		this.add(resultadosEste, BorderLayout.EAST);
		

		
	}
	
	
	
	public JButton getAnterior() {
		return anterior;
	}

	public JPanel getPanelEtiquetas() {
		return panelEtiquetas;
	}

	public JLabel getEtiquetaNumeroPagina() {
		return etiquetaNumeroPagina;
	}

	public JButton getSiguiente() {
		return siguiente;
	}

	public JLabel getLabelNombre() {
		return labelNombre;
	}

	public JLabel getLabelFecha() {
		return labelFecha;
	}

	public JLabel getLabelResolucion() {
		return labelResolucion;
	}

	public JLabel getLabelPeso() {
		return labelPeso;
	}

	public JPanel getPanelImagenEtiquetas() {
		return panelImagenEtiquetas;
	}

	public JTextField getBarraBusqueda() {
		return barraBusqueda;
	}

	public JButton getBotonBuscar() {
		return botonBuscar;
	}
	
	public JCheckBox getOpBuscarSinEtiqueta() {
		return opBuscarSinEtiqueta;
	}
	
	public JCheckBox getOpFechaDespues() {
		return opFechaDespues;
	}

	public JCheckBox getOpFechaAntes() {
		return opFechaAntes;
	}
	
	public JComboBox<String> getOpAnoDespues() {
		return opAnoDespues;
	}

	public JComboBox<String> getOpAnoAntes() {
		return opAnoAntes;
	}
	
	public int getNumeroFilas() {
		return ((GridLayout)resultadosGrid.getLayout()).getRows();
	}
	
	public String getFechaDespues() {
		
		
		return (((String)opDiaDespues.getSelectedItem()).length()==1 ? "0"+((String)opDiaDespues.getSelectedItem()) : ((String)opDiaDespues.getSelectedItem()))
				+ "/"
				+ ((opMesDespues.getSelectedIndex()+1 < 10) ? "0"+(opMesDespues.getSelectedIndex()+1) : (opMesDespues.getSelectedIndex()+1))  
				+ "/"
				+ (String)opAnoDespues.getSelectedItem();
	}
	
	public String getFechaAntes() {
		
		return (((String)opDiaAntes.getSelectedItem()).length()==1 ? "0"+((String)opDiaAntes.getSelectedItem()) : ((String)opDiaAntes.getSelectedItem()))
				+ "/"
				+ ((opMesAntes.getSelectedIndex()+1 < 10) ? "0"+(opMesAntes.getSelectedIndex()+1) : (opMesAntes.getSelectedIndex()+1))  
				+ "/"
				+ (String)opAnoAntes.getSelectedItem();
		
	}

//	public ArrayList<ImagenTemp> getArrayResultados() {
//		return arrayResultados;
//	}

	public void setTextoBarra(String texto) {
		barraBusqueda.setText(texto);
	}
	
	
	
	
	public void anadirResultados(ImagenTemp[] res, int num1, int num2) {
		
		this.res = res;
		
		etiquetaNumeroPagina.setText(num1 + "/" + num2);
		
		resultadosGrid.removeAll();
		
		if(res != null) {
			for(int i=0; i<res.length; i++) {
				
				if(res[i] != null) {
					PanelImagenRes temp = new PanelImagenRes();
					resultadosGrid.add(temp);
					temp.setImagen(res[i]);
					temp.addMouseListener(this);
				}
			}
		}
		
		
		resultadosGrid.revalidate();
		resultadosGrid.repaint();
		
		
	}
	
	private void cambiarImagenGrande(ImagenTemp it) {
		
		Image imgtemp = VistaPrincipal.ponerImagen(it.getbImagen(), panelImagenGrande);
		labelImagenGrande.setIcon(new ImageIcon(imgtemp));
		labelImagenGrande.setBounds((int)(((float)panelImagenGrande.getWidth()/2) - ((float)imgtemp.getWidth(null))/((float)2)), (int)(((float)panelImagenGrande.getHeight()/2) - ((float)imgtemp.getHeight(null))/((float)2)),imgtemp.getWidth(null), imgtemp.getHeight(null));
		
		
		
		labelNombre.setText(it.getNombre()+it.getExtension());
		
		LocalDate localDate = LocalDate.ofEpochDay(it.getFecha()/(1000*60*60*24));//TODO llevar esto a un método estático de VistaPrincipal si se repite
		labelFecha.setText(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear());
		
		labelResolucion.setText(it.getbImagen().getWidth() + " x " + it.getbImagen().getHeight());
		
		String peso = it.getImagen().length()/1024 < 1000 ? (float)(it.getImagen().length()/1024) + " kB" : (int)(it.getImagen().length()/(1024*1024)) + " MB";
		labelPeso.setText(peso);
		
		
		
		panelImagenEtiquetas.removeAll();
		for(Etiquetas e : it.getArrayEtiquetas()) {
			panelImagenEtiquetas.add(e);
		}
		
		resultadosEste.revalidate();
		resultadosEste.repaint();
		
	}
	
	private void setEnabledParaBusquedaSinEtiquetas() {
		
		//ojo !
		boolean comoPoner = !opBuscarSinEtiqueta.isSelected();
		
//		opFechaDespues.setEnabled(comoPoner);
//		if(opFechaDespues.isSelected()) {
//			opDiaDespues.setEnabled(comoPoner);
//			opMesDespues.setEnabled(comoPoner);
//			opAnoDespues.setEnabled(comoPoner);
//		}
//		opFechaAntes.setEnabled(comoPoner);
//		if(opFechaAntes.isSelected()) {
//			opDiaAntes.setEnabled(comoPoner);
//			opMesAntes.setEnabled(comoPoner);
//			opAnoAntes.setEnabled(comoPoner);
//		}
		barraBusqueda.setEditable(comoPoner);
		panelEtiquetas.setEnabled(comoPoner);
		for(Component c : panelEtiquetas.getComponents()) {
			c.setEnabled(comoPoner);
		}
		
	}

	
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {	if(arg0.getKeyCode() == KeyEvent.VK_ENTER) botonBuscar.doClick();}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}



	@Override
	public void mouseClicked(MouseEvent ev) {
		//System.out.println("reclic");
		//System.out.println(ev.getSource().getClass().getSimpleName());
		if(ev.getSource().getClass().getSimpleName().equals((new PanelImagenRes()).getClass().getSimpleName())) {
			//System.out.println("clic");
			cambiarImagenGrande(((PanelImagenRes)ev.getSource()).getImagenTemp());
			
		}
		
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



	@Override
	public void stateChanged(ChangeEvent ev) {
		
		if(ev.getSource()==opFechaDespues) {
			if(opFechaDespues.isSelected()) {
				opDiaDespues.setEnabled(true);
				opMesDespues.setEnabled(true);
				opAnoDespues.setEnabled(true);
			}else {
				opDiaDespues.setEnabled(false);
				opMesDespues.setEnabled(false);
				opAnoDespues.setEnabled(false);
			}
		}else if(ev.getSource()==opFechaAntes) {
			if(opFechaAntes.isSelected()) {
				opDiaAntes.setEnabled(true);
				opMesAntes.setEnabled(true);
				opAnoAntes.setEnabled(true);
			}else {
				opDiaAntes.setEnabled(false);
				opMesAntes.setEnabled(false);
				opAnoAntes.setEnabled(false);
			}
		}else if(ev.getSource()==opBuscarSinEtiqueta) {
			setEnabledParaBusquedaSinEtiquetas();
		}
		
	}
	
//	private void testAddKeyListener() {
//		
//		barraBusqueda.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyPressed(KeyEvent arg0) {
//				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
//					//TODO aquí no debería estar este actionperformed
//					//panelEtiquetas.add(new Etiquetas(barraBusqueda.getText()));
//					//panelEtiquetas.add(new JLabel(barraBusqueda.getText()));
//					//System.out.println(barraBusqueda.getText());
//					//barraBusqueda.setText("");
//					//panelEtiquetas.revalidate();
//					
//					botonBuscar.doClick();
//				}
//				
//			}
//
//			@Override
//			public void keyReleased(KeyEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		
//	}

}
