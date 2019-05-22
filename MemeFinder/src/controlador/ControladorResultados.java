package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.ModeloPrincipal;
import vista.Etiquetas;
import vista.VistaResultados;

public class ControladorResultados implements ActionListener {
	
	private VistaResultados vistaResultados;
	private ModeloPrincipal modeloPrincipal;
	
	private ArrayList<Etiquetas> arrayEtiquetasBusqueda;
	private ArrayList<ImagenTemp> arrayResultados;
	private Long fechaDespuesDe, fechaAntesDe;
	
	private int numPagina, numTotalPaginas;
	
	private JButton anterior, siguiente;
	
	
	public ControladorResultados(VistaResultados vistaResultados, ModeloPrincipal modeloPrincipal) {
		
		this.vistaResultados = vistaResultados;
		this.modeloPrincipal = modeloPrincipal;
		
		arrayEtiquetasBusqueda = new ArrayList<Etiquetas>();
		
		vistaResultados.getBotonBuscar().addActionListener(this);
		vistaResultados.getOpBuscarSinEtiqueta().addActionListener(this);
		
		anterior = vistaResultados.getAnterior();
		anterior.addActionListener(this);
		siguiente = vistaResultados.getSiguiente();
		siguiente.addActionListener(this);
		
		int anoActual = LocalDate.now().getYear();
		//String[] anosComboBox = new String[anoActual-1969];
		for(int i=anoActual; i>1969; i--) {
			//System.out.println(anoActual-i + "-" + i);
			//anosComboBox[anoActual-1] = i+"";
			vistaResultados.getOpAnoAntes().addItem(i+"");
			vistaResultados.getOpAnoDespues().addItem(i+"");
		}
		vistaResultados.getOpAnoDespues().setSelectedIndex(vistaResultados.getOpAnoAntes().getItemCount()-1);
		vistaResultados.getOpAnoAntes().setSelectedIndex(0);
		
		
		vistaResultados.getBotonBorrarImagen().addActionListener(this);
		
		
	}
	
	
	
	private void empezarBusqueda(boolean buscarConEtiquetas) {//TODO sincronizar y hacer a thread de búsqueda acceder aquí?
		
		System.out.println("buscar, etiquetas: " + buscarConEtiquetas);
		
		numPagina = 0;
		
		if(buscarConEtiquetas) arrayResultados = modeloPrincipal.getArrayResultados(arrayEtiquetasBusqueda,fechaDespuesDe,fechaAntesDe);
		else arrayResultados = modeloPrincipal.getArrayResultados(null, fechaDespuesDe,fechaAntesDe);
		
		mostrarResultados();
		
		
	}
	
	
	private void mostrarResultados(){
		
		int maxPorPagina = vistaResultados.getNumeroFilas();//Número de filas del panel resultados central
		
		//numPagina = 0;//declarar como campo de clase (empieza en cero)
		numTotalPaginas = 0;
		
		//Suma una página si %!=0
		numTotalPaginas = (arrayResultados.size() % maxPorPagina == 0) ?  arrayResultados.size() / maxPorPagina : (arrayResultados.size() / maxPorPagina) + 1;
		
		//Ternario para que retorna[x] valga null en las posiciones que se salgan del array (última página si hay resultados colganderos)
		//TODO extrapolar retorna a maxPorPagina distintos (dar a retorna.length el valor de maxPorPagina y meter en for)
		ImagenTemp[] retorna = {((numPagina*maxPorPagina)+0)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+0),
								((numPagina*maxPorPagina)+1)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+1),
								((numPagina*maxPorPagina)+2)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+2),
								((numPagina*maxPorPagina)+3)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+3)};
		
		vistaResultados.anadirResultados(retorna, numPagina+1, numTotalPaginas);//TODO evitar bug de pág 1/0 cuando no hay resultados
		
//		anterior.setEnabled(false);
//		if(numTotalPaginas==1) siguiente.setEnabled(false);
//		else siguiente.setEnabled(true);
		
		//TEST activar/desactivar botones anterior/siguiente en función a las páginas que hay
		anterior.setEnabled(numPagina != 0);
		siguiente.setEnabled(numPagina+1 != numTotalPaginas);
		
		
	}
	
	
	private static Long getFechaDesdeTexto(String input) {
		//TODO comprobar que las fechas estén bien (ajustadas a las 00 en España)
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			Date d = format.parse(input);
			return d.getTime();
		}catch(IllegalArgumentException e) {
			return null;
		}catch(ParseException e) {
			return null;
		}
	}



	@Override
	public void actionPerformed(ActionEvent ev) {
		
//		if(ev.getSource()==vistaResultados.getOpBuscarSinEtiqueta()) {
//			
//			if(vistaResultados.getOpBuscarSinEtiqueta().isSelected()) {
//				empezarBusqueda(false);
//			}else {
//				empezarBusqueda(true);
//			}
//			
//		}else if(ev.getSource()==vistaResultados.getBotonBuscar()) {
		if(ev.getSource()==vistaResultados.getBotonBuscar()) {
			
//			System.out.println(vistaResultados.getFechaAntes());
//			System.out.println(vistaResultados.getFechaDespues());
			
			boolean erroresFormulario = false;
//			boolean hayFechaBien = false;
			
			//Comprueba que la fecha sea correcta (eg¬31feb)
			if(vistaResultados.getOpFechaDespues().isSelected()){
				fechaDespuesDe = getFechaDesdeTexto(vistaResultados.getFechaDespues());
				if(fechaDespuesDe==null) erroresFormulario = true;//TODO mostrar tooltip
				System.out.println("despues " + fechaDespuesDe);
			}else {
				fechaDespuesDe = null;
			}
			
			//Otra fecha correcta
			if(vistaResultados.getOpFechaAntes().isSelected()) {
				fechaAntesDe = getFechaDesdeTexto(vistaResultados.getFechaAntes());
				if(fechaAntesDe==null) erroresFormulario = true;//TODO mostrar tooltip
				System.out.println("antes " + fechaAntesDe);
			}else {
				fechaAntesDe = null;
			}
			
			//Si está activada la búsqueda sin etiquetas
			if(vistaResultados.getOpBuscarSinEtiqueta().isSelected()) {
				
				if(!erroresFormulario) {
					System.out.println("no hay errores (buscar sin etiq)");
					empezarBusqueda(false);
				}
				
			
			//Si se busca normal (con etiquetas)	
			}else {
				
				Etiquetas temp = null;
				//ojo !!
				if(!vistaResultados.getBarraBusqueda().getText().isEmpty() && !vistaResultados.getOpBuscarSinEtiqueta().isSelected()) {
					temp = new Etiquetas(vistaResultados.getBarraBusqueda().getText(),modeloPrincipal.getCountEtiquetas(vistaResultados.getBarraBusqueda().getText()));
					if(arrayEtiquetasBusqueda.contains(temp)) {
						erroresFormulario = true;
						//TODO mostrar tooltip
						//TODO borrar texto de barra y hacer relucir la etiqueta en especial
						System.out.println("etiqueta repe");
					}else {
						temp.anadirListenerEliminar(this);
						arrayEtiquetasBusqueda.add(temp);//Añade etiqueta al arraylist
						vistaResultados.getPanelEtiquetas().add(arrayEtiquetasBusqueda.get(arrayEtiquetasBusqueda.size()-1));//Añade etiqueta a la vista
						vistaResultados.getBarraBusqueda().setText("");//Vacía caja de texto de búsqueda
						vistaResultados.getPanelEtiquetas().revalidate();//TODO comprobar si después se revalida para no revalidar dos veces
					}
				}
				
				if(!erroresFormulario) {
					System.out.println("no hay errores (buscar con etiq)");
					//TODO más adelante, no buscar si la fecha **no ha cambiado**, para optimizar recursos
					empezarBusqueda(true);
					
				
				}
				
				
				
			}
			
		}else if(ev.getSource()==anterior) {
			
			numPagina--;
			mostrarResultados();
//			if(!siguiente.isEnabled()) siguiente.setEnabled(true);
//			if(numPagina==1) anterior.setEnabled(false); 
			
		}else if(ev.getSource()==siguiente) {
			
			numPagina++;
			mostrarResultados();
//			if(!anterior.isEnabled()) anterior.setEnabled(true);
//			if(numPagina == numTotalPaginas-1) siguiente.setEnabled(false);
			
			
			
		}else if(ev.getSource()==vistaResultados.getBotonBorrarImagen()) {	
			
			if(JOptionPane.showConfirmDialog(null, "¿Borrar la imgen? (Este proceso no puede revertirse)")==JOptionPane.YES_OPTION) {
				ImagenTemp t = vistaResultados.getImagenTempSeleccionado();
				if(t==null) {
					System.out.println("-------------EXCEPCIÓN RARUNA EN ControladorResultados ev.getsource==botonborrarimagen");
				}else {
					/*
						TODO
						-borrar imagen de tablaimagenes
						-descontar (y borrar si procede) etiqueta de tablaetiquetas
						-borrar del array de resultados, cargar imagen siquiente o anterior si es la última
					*/
					
					arrayResultados.remove(t);
					mostrarResultados();
					vistaResultados.vaciarImagenGrande();
					
					modeloPrincipal.borrarImagen(t.getIdMongo());//TODO refrescar cuenta de las demás etiquetas presentes?
				}
				
			}
			
			
			
			
			
			
			
			
		//}else if(ev.getSource().getClass().getSimpleName().equals("Etiquetas")) {
		}else if(ev.getSource() instanceof Etiquetas) {
			
			if(arrayEtiquetasBusqueda.contains(ev.getSource())) {
				
				arrayEtiquetasBusqueda.remove(ev.getSource());
				vistaResultados.getPanelEtiquetas().remove((Etiquetas)ev.getSource());
				
				if(arrayEtiquetasBusqueda.isEmpty()) {
					vistaResultados.anadirResultados(null, 0, 0);//TODO crear método de vaciar vista en vez de hacerlo así cutre??
				}else {
					
					if(((Etiquetas)ev.getSource()).getCuenta() != 0) {
						empezarBusqueda(true);
					}
					
				}
				
				vistaResultados.revalidate();
				vistaResultados.repaint();
				
			}
			
		}
		
	}

}
