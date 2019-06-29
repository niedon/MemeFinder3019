package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controlador.ImagenTemp;

public class VistaResultados extends JPanel implements KeyListener, MouseListener, ChangeListener{
	
	private ImagenTemp[] res;//Resultados de la página actual de la búsqueda
	private ImagenTemp imagenTempSeleccionada;//Imagen seleccionada (vista en panel resultadosEste)
	
	//---Panel oeste (búsqueda y opciones)
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
	//TODO llevar a vistaPrincipal si se repite
	private static final String[] seleccionDias = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	private static final String[] seleccionMeses = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
	
	private JPanel panelEtiquetas;
	
	
	//---Panel centro (resultados y paginación)
	private JPanel resultadosCentro;
	
	private JPanel resultadosGrid;
	
	private JPanel panelPaginacion;
	private JButton anterior;
	private JLabel etiquetaNumeroPagina;
	private JButton siguiente;
	
	
	
	//---Panel este (resultado seleccionado y opción de ampliar)
	private JPanel resultadosEste;
	
	private JPanel panelContenidoEste;
	
	private JPanel panelImagenGrande;
	private JLabel labelImagenGrande;
	
	private JPanel panelImagenDatos;
	//private JPanel panelImagenDatosChico;
	private JLabel labelNombre;
	private JLabel labelFecha;
	private JLabel labelResolucion;
	private JLabel labelPeso;
	
	private JPanel panelImagenEtiquetas;
	
	private JPanel panelImagenBotones;
	private JButton botonVerEnGrande;
	
	
	
	public VistaResultados() {
		
		imagenTempSeleccionada = null;
		
		this.setLayout(new BorderLayout(5,5));
		
		//------------------------------PANEL OESTE
		resultadosOeste = new JPanel();
		resultadosOeste.setPreferredSize(new Dimension(300,this.getHeight()));
		resultadosOeste.setLayout(new BoxLayout(resultadosOeste, BoxLayout.Y_AXIS));
		
		//---BARRA BÚSQUEDA Y BUSCAR
		panelBarra = new JPanel();
		panelBarra.setMaximumSize(new Dimension((int)panelBarra.getMaximumSize().getWidth(), (int)panelBarra.getMinimumSize().getHeight()));
		
		barraBusqueda = new JTextField(15);
		barraBusqueda.addKeyListener(this);
		botonBuscar = new JButton("Buscar");
		
		panelBarra.add(barraBusqueda);
		panelBarra.add(botonBuscar);
		
		//---PANEL OPCIONES
		
		panelOpcionesBuscar = new JPanel();
		panelOpcionesBuscar.setLayout(new BoxLayout(panelOpcionesBuscar,BoxLayout.Y_AXIS));
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
		
		
		//---ETIQUETAS
		panelEtiquetas = new JPanel();
		panelEtiquetas.setLayout(new FlowLayout());
		panelEtiquetas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true), "Etiquetas", TitledBorder.LEFT, TitledBorder.TOP));
		
		
		
		
		//------------------------------PANEL CENTRO
		resultadosCentro = new JPanel(new BorderLayout());
		
		resultadosGrid = new JPanel(new GridLayout(4,1,0,5));//Aquí se cambia el número de resultados por página (primer parámetro)
		panelPaginacion = new JPanel(new FlowLayout(FlowLayout.CENTER));
		anterior = new JButton("Anterior");
		etiquetaNumeroPagina = new JLabel("x/x");
		siguiente = new JButton("Siguiente");
		
		panelPaginacion.add(anterior);
		panelPaginacion.add(etiquetaNumeroPagina);
		panelPaginacion.add(siguiente);
		
		
		
		
		//------------------------------PANEL ESTE
		resultadosEste = new JPanel(new BorderLayout());
		resultadosEste.setPreferredSize(new Dimension(500,this.getHeight()));
		
		panelContenidoEste = new JPanel(new BorderLayout());
		
		//---IMAGEN AMPLIADA
		panelImagenGrande = new JPanel();
		panelImagenGrande.setLayout(null);
		labelImagenGrande = new JLabel();
		panelImagenGrande.add(labelImagenGrande);
		
		panelImagenGrande.setMaximumSize(new Dimension(500,400));
		panelImagenGrande.setMinimumSize(new Dimension(500,400));
		panelImagenGrande.setPreferredSize(new Dimension(500,400));
		
		//---DATOS DE LA IMAGEN
		panelImagenDatos = new JPanel(new GridLayout(4,1));
		
		labelNombre = new JLabel("Test nombre");
		labelFecha = new JLabel("Test fecha");
		labelResolucion = new JLabel("Test Resolucion");
		labelPeso = new JLabel("Test peso");
		
		panelImagenDatos.add(labelNombre);
		panelImagenDatos.add(labelFecha);
		panelImagenDatos.add(labelResolucion);
		panelImagenDatos.add(labelPeso);
		
		//---ETIQUETAS DE LA IMAGEN
		panelImagenEtiquetas = new JPanel();
		panelImagenEtiquetas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true), "Etiquetas", TitledBorder.LEFT, TitledBorder.TOP));
		panelImagenEtiquetas.setPreferredSize(new Dimension(500,150));
		
		//---PANEL OPCIONES IMAGEN SELECCIONADA
		panelImagenBotones = new JPanel();
		botonVerEnGrande = new JButton("Ver en grande");
		panelImagenBotones.add(botonVerEnGrande);
		
		
		
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
		
		
		resultadosCentro.add(resultadosGrid, BorderLayout.CENTER);
		resultadosCentro.add(panelPaginacion, BorderLayout.SOUTH);
		
		
		this.add(resultadosOeste, BorderLayout.WEST);
		this.add(resultadosCentro, BorderLayout.CENTER);
		this.add(resultadosEste, BorderLayout.EAST);
		

		
	}
	
	
	//---GETTERS-SETTERS POR DEFECTO
	
	public ImagenTemp getImagenTempSeleccionado() {
		return imagenTempSeleccionada;
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
	
	public JButton getBotonVerEnGrande() {
		return botonVerEnGrande;
	}
	
	//---GETTERS-SETTERS PERSONALIZADOS
	
	public ImagenTemp[] getArrayResultadosActuales() {
		return res;
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
	
	public void setTextoBarra(String texto) {
		barraBusqueda.setText(texto);
	}
	
	
	//---MÉTODOS PROPIOS
	
	
	//Añade una página de resultados (paginación incluida)
	public void anadirResultados(ImagenTemp[] res, int num1, int num2) {
		
		this.res = res;
		
		if(num2==0) etiquetaNumeroPagina.setText("0/0");
		else etiquetaNumeroPagina.setText(num1 + "/" + num2);
		
		resultadosGrid.removeAll();
		
		if(res != null) {
			for(int i=0; i<res.length; i++) {
				
				if(res[i] != null) {
					//Se añade la clase personalizada PanelImagenRes para visualizar los resultados
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
	
	//Cambia la imagen seleccionada (ampliada en panel este)
	public void cambiarImagenGrande(ImagenTemp it) {
		
		//TODO automatizar haciendo que el método directamente coloque la imagen escalada en el panel?
		Image imgtemp = VistaPrincipal.ponerImagenEscalada(it.getbImagen(), panelImagenGrande);
		labelImagenGrande.setIcon(new ImageIcon(imgtemp));
		labelImagenGrande.setBounds((int)(((float)panelImagenGrande.getWidth()/2) - ((float)imgtemp.getWidth(null))/((float)2)), (int)(((float)panelImagenGrande.getHeight()/2) - ((float)imgtemp.getHeight(null))/((float)2)),imgtemp.getWidth(null), imgtemp.getHeight(null));
		
		
		//Se rellenan los datos de la imagen seleccionada
		labelNombre.setText(it.getNombre()+it.getExtension());
		
		LocalDate localDate = LocalDate.ofEpochDay(it.getFecha()/(1000*60*60*24));//TODO llevar esto a un método estático de VistaPrincipal si se repite
		labelFecha.setText(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear());
		
		labelResolucion.setText(it.getbImagen().getWidth() + " x " + it.getbImagen().getHeight());
		
		String peso = it.getImagen().length()/1024 < 1000 ? (float)(it.getImagen().length()/1024) + " kB" : (int)(it.getImagen().length()/(1024*1024)) + " MB";
		labelPeso.setText(peso);
		
		
		//Se rellenan las etiquetas
		panelImagenEtiquetas.removeAll();
		for(Etiquetas e : it.getArrayEtiquetas()) panelImagenEtiquetas.add(e);
		resultadosEste.revalidate();
		resultadosEste.repaint();
		
	}
	
	//Se quitan los datos de la imagen seleccionada
	public void vaciarImagenGrande() {
		labelImagenGrande.setIcon(null);
		labelNombre.setText("");
		labelFecha.setText("");
		labelResolucion.setText("");
		labelPeso.setText("");
		panelImagenEtiquetas.removeAll();
		resultadosEste.revalidate();
		resultadosEste.repaint();
		//TODO comprobar si es necesario quitar la referencia de imagenTempActual
		
	}
	
	//Activa/desactiva la interactividad con etiquetas buscadas si se activa al búsqueda sin etiquetas
	private void setEnabledParaBusquedaSinEtiquetas() {
		
		//ojo !
		boolean buscarConEtiquetas = !opBuscarSinEtiqueta.isSelected();
		barraBusqueda.setEditable(buscarConEtiquetas);
		panelEtiquetas.setEnabled(buscarConEtiquetas);
		for(Component c : panelEtiquetas.getComponents())c.setEnabled(buscarConEtiquetas);
		
	}


	@Override
	public void keyPressed(KeyEvent arg0) {	if(arg0.getKeyCode() == KeyEvent.VK_ENTER) botonBuscar.doClick();}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}



	@Override
	public void mouseClicked(MouseEvent ev) {
		
		//TODO cambiar a controlador?
		if(ev.getSource() instanceof PanelImagenRes) {
			cambiarImagenGrande(((PanelImagenRes)ev.getSource()).getImagenTemp());
			imagenTempSeleccionada = ((PanelImagenRes)ev.getSource()).getImagenTemp();
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}



	//Activa y desactiva componentes en base a los checkboxes que se pulsen
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
	

}
