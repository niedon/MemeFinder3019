package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;

import org.bson.types.ObjectId;

import modelo.ModeloPrincipal;
import vista.Etiquetas;
import vista.VistaImagenDatos;
import vista.VistaPrincipal;

public class ControladorImagenDatos implements ActionListener{
	
	private ModeloPrincipal modeloPrincipal;
	
	private boolean modoEdicion;
	
	private ArrayList<Etiquetas> arrayEtiquetasClonado;
	private ArrayList<Etiquetas> etiquetasNuevas;
	private ArrayList<Etiquetas> etiquetasBorradas;
	private String elNuevoNombre;
	
	private VistaImagenDatos vistaImagenDatos;
	private ImagenTemp imagenTemp;
	
	private JPanel panelIzq;
	
	private JLabel labelImagen;
	
	private JLabel dNombre;
	private JTextField cajaNombre;
	private JLabel dExtension;
	private JLabel dFecha;
	private JLabel dPeso;
	private JLabel dDimensiones;
	
	private JPanel panelDerSurEtiquetas;
	private JTextField cajaEtiquetas;
	private JButton botonAnadirEtiqueta;
	
	private JButton botonBorrarImagen;
	private JButton botonEditar;//Se convierte en Guardar si modoEdicion==true
	
	public ControladorImagenDatos(VistaImagenDatos vistaImagenDatos, ModeloPrincipal modeloPrincipal) {
		
		this.modeloPrincipal = modeloPrincipal;
		
		modoEdicion = false;
		
		arrayEtiquetasClonado = new ArrayList<Etiquetas>();
		etiquetasNuevas = new ArrayList<Etiquetas>();
		etiquetasBorradas = new ArrayList<Etiquetas>();
		elNuevoNombre = "";
		
		this.vistaImagenDatos = vistaImagenDatos;
		imagenTemp = null;
		
		panelIzq = vistaImagenDatos.getPanelIzq();
		
		labelImagen = vistaImagenDatos.getLabelImagen();
		dNombre = vistaImagenDatos.getdNombre();
		cajaNombre = vistaImagenDatos.getCajaNombre();
		dExtension = vistaImagenDatos.getdExtension();
		dFecha = vistaImagenDatos.getdFecha();
		dPeso = vistaImagenDatos.getdPeso();
		dDimensiones = vistaImagenDatos.getdDimensiones();
		
		panelDerSurEtiquetas = vistaImagenDatos.getPanelDerSurEtiquetas();
		cajaEtiquetas = vistaImagenDatos.getCajaEtiquetas();
		botonAnadirEtiqueta = vistaImagenDatos.getBotonAnadirEtiqueta();
		
		//botonVolver = vistaImagenDatos.getBotonVolver();
		botonBorrarImagen = vistaImagenDatos.getBotonBorrarImagen();
		botonEditar = vistaImagenDatos.getBotonEditar();
		
		botonBorrarImagen.addActionListener(this);
		botonEditar.addActionListener(this);
		botonAnadirEtiqueta.addActionListener(this);
		
	}
	
	public ImagenTemp getImagenTemp() { return imagenTemp; }
	
	public void setImagenTemp(ImagenTemp it) {
		
		//Vaciar datos de la entrada anterior (si procede)
		panelDerSurEtiquetas.removeAll();
		
		imagenTemp = it;
		
		Image imgtemp = VistaPrincipal.ponerImagenEscalada(it.getbImagen(), panelIzq);
		labelImagen.setIcon(new ImageIcon(imgtemp));
		labelImagen.setBounds((int)(((float)panelIzq.getWidth()/2) - ((float)imgtemp.getWidth(null))/((float)2)), (int)(((float)panelIzq.getHeight()/2) - ((float)imgtemp.getHeight(null))/((float)2)),imgtemp.getWidth(null), imgtemp.getHeight(null));
		
		dNombre.setText(it.getNombre());
		dExtension.setText(it.getExtension().substring(1));
		
		LocalDate localDate = LocalDate.ofEpochDay(it.getFecha()/(1000*60*60*24));//TODO llevar esto a un método estático de VistaPrincipal si se repite
		dFecha.setText(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear());
		
		String peso = it.getImagen().length()/1024 < 1000 ? (float)(it.getImagen().length()/1024) + " kB" : (int)(it.getImagen().length()/(1024*1024)) + " MB";
		dPeso.setText(peso);
		
		dDimensiones.setText(it.getbImagen().getWidth() + " x " + it.getbImagen().getHeight());
		
		panelDerSurEtiquetas.removeAll();
		for(Etiquetas e : it.getArrayEtiquetas()) panelDerSurEtiquetas.add(e);
		
		vistaImagenDatos.revalidate();
		vistaImagenDatos.repaint();
		
	}
	
	private boolean comprobarCambiosEdicion() {
		
		elNuevoNombre = "";
		etiquetasBorradas.clear();
		etiquetasNuevas.clear();
		
		boolean retorna = false;
		
		elNuevoNombre = cajaNombre.getText();
		
		boolean[] arrayIndicesEtBorradas = new boolean[imagenTemp.getArrayEtiquetas().size()];
		for(int i=0; i<arrayIndicesEtBorradas.length; i++) arrayIndicesEtBorradas[i]=false;
		
		for(Etiquetas et : arrayEtiquetasClonado) {
			
			//Si arrayviejo contiene la etiqueta et (de arraynuevo), se marca true en el bool[]
			if(imagenTemp.getArrayEtiquetas().contains(et)) {
				arrayIndicesEtBorradas[imagenTemp.getArrayEtiquetas().indexOf(et)] = true;
				
			//Si arraynuevo contiene una et que arrayviejo no, significa que se ha añadido la etiqueta
			}else {
				etiquetasNuevas.add(et);
			}
		}
		
		 //Se recorre el bool[], y en los casos false, se manda a borrar la etiqueta, porque serán
		 //los únicos en los que no han sido cambiados a true por el foreach anterior
		 for(int i=0; i<imagenTemp.getArrayEtiquetas().size(); i++) {
			 if(arrayIndicesEtBorradas[i] == false) {
				 
				 //TODO comprobar si hay que añadir desde imagenTemp.getarray o desde arrayetiquetasclonado
				 etiquetasBorradas.add(imagenTemp.getArrayEtiquetas().get(i));
				 
			 }
		 }
		 
		 
		//ojo a todos los !!
		 if(!elNuevoNombre.equals(imagenTemp.getNombre()) && !elNuevoNombre.isEmpty()) retorna = true;
		 else elNuevoNombre = "";
		 
		 if(!etiquetasBorradas.isEmpty()) retorna = true;
		 if(!etiquetasNuevas.isEmpty()) retorna = true;
		 
		 //Línea alternativa, no necesita declarar bool retorna:
		 //return !etiquetasBorradas.isEmpty() || !etiquetasNuevas.isEmpty() || (!elNuevoNombre.equals(imagenTemp.getNombre()) && !elNuevoNombre.isEmpty());
		 
		 arrayIndicesEtBorradas = null;
		 
		 return retorna;
	}
	
	private void guardarCambiosEdicion() {
		
		modeloPrincipal.updateImagen(imagenTemp, elNuevoNombre, etiquetasNuevas, etiquetasBorradas);
		
		ObjectId idTemporal = imagenTemp.getIdMongo();
		imagenTemp = modeloPrincipal.getResultadoUnico(idTemporal);
		idTemporal = null;
		
		salirModoEdicion();
		
	}
	
	private void salirModoEdicion() {
		
		modoEdicion = false;
		
		elNuevoNombre="";
		arrayEtiquetasClonado.clear();
		etiquetasBorradas.clear();
		etiquetasNuevas.clear();
		
		dNombre.setText(imagenTemp.getNombre());
		panelDerSurEtiquetas.removeAll();
		for(Etiquetas et : imagenTemp.getArrayEtiquetas()) panelDerSurEtiquetas.add(et);
		
		vistaImagenDatos.cambiarAModoEdicion(false);
		
	}
	
	public boolean botonVolverPulsado() {
		
			//Si está en modo edición
			if(modoEdicion) {
				
				//Si hay cambios en la edición, salta un diálogo
				if(comprobarCambiosEdicion()) {
					
					int respuesta = JOptionPane.showConfirmDialog(null, "Hay cambios en la imagen, ¿quieres guardarlos?", "", JOptionPane.YES_NO_CANCEL_OPTION);
					
					//Si pulsa sí, se guarda y vuelve
					if(respuesta == JOptionPane.YES_OPTION) {
						guardarCambiosEdicion();
						
					//Si pulsa no, vuelve sin guardar	
					}else if(respuesta == JOptionPane.NO_OPTION) {
						salirModoEdicion();
					}//Si pulsa otra cosa (cancelar, X...) no pasa nada
				
				//Si no, sale directamente	
				}else {
					salirModoEdicion();
				}
				
				return false;
				
			//Si no está en modo edición	
			}else {
				return true;
			}
	}
	
	public void vaciar() {
		//TODO
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getSource()==botonEditar) {
			
			//Se convierte en Guardar si modoEdicion==true
			if(modoEdicion) {
				
				if(comprobarCambiosEdicion()) guardarCambiosEdicion();
				else salirModoEdicion();
				
			}else {
				
				modoEdicion = true;
				
				//Llena el array clonado de etiquetas
				for(Etiquetas et : imagenTemp.getArrayEtiquetas()) {
					arrayEtiquetasClonado.add(new Etiquetas(et.getTexto(), et.getCuenta()));
				}
				
				//Rellena el panel con las etiquetas clonadas (para el Listener en la edición)
				panelDerSurEtiquetas.removeAll();
				for(Etiquetas et : arrayEtiquetasClonado) {
					et.anadirListenerEliminar(this);
					panelDerSurEtiquetas.add(et);
				}
				
				vistaImagenDatos.cambiarAModoEdicion(true);
				
			}
		
		}else if(ev.getSource()==botonBorrarImagen) {	
			
			if(JOptionPane.showConfirmDialog(null, "Esta imagen se borrará de la base de datos, esta acción no es reversible. ¿Continuar?",null,JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				
				modeloPrincipal.borrarImagen(imagenTemp.getIdMongo());
				
				if(modoEdicion) salirModoEdicion();
				
				vistaImagenDatos.getBotonVolver().doClick();
			}
			
			
		}else if(ev.getSource()==botonAnadirEtiqueta) {
			
			//ojo !
			if(!cajaEtiquetas.getText().isEmpty()) {
				
				Etiquetas etTemp = new Etiquetas(cajaEtiquetas.getText(), modeloPrincipal.getCountEtiquetas(cajaEtiquetas.getText()));
				etTemp.anadirListenerEliminar(this);
				
				if(arrayEtiquetasClonado.contains(etTemp)) {
					//TODO resaltar etiqueta duplicada
					cajaEtiquetas.setText("");
				}else {
					arrayEtiquetasClonado.add(etTemp);
					panelDerSurEtiquetas.add(etTemp);
					panelDerSurEtiquetas.revalidate();
					panelDerSurEtiquetas.repaint();
					cajaEtiquetas.setText("");
				}
				
			}

			
		}else if(ev.getSource() instanceof Etiquetas) {
			
			arrayEtiquetasClonado.remove(ev.getSource());
			panelDerSurEtiquetas.remove((Etiquetas)ev.getSource());
			panelDerSurEtiquetas.revalidate();
			panelDerSurEtiquetas.repaint();
			
		}
		
	}


}
	
	
	


