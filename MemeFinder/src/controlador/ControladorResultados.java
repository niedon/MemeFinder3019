package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;

import org.bson.types.ObjectId;

import modelo.ModeloPrincipal;
import vista.Etiquetas;
import vista.VistaResultados;

public class ControladorResultados implements ActionListener {
	
	private VistaResultados vistaResultados;
	private ModeloPrincipal modeloPrincipal;
	
	private ArrayList<Etiquetas> arrayEtiquetasBusqueda;
	private ArrayList<ImagenTemp> arrayResultados;
	private Long fechaDespuesDe, fechaAntesDe;
	
	private JButton botonBuscar;
	
	private int numPagina, numTotalPaginas;
	
	private JButton anterior, siguiente;
	
	
	public ControladorResultados(VistaResultados vistaResultados, ModeloPrincipal modeloPrincipal) {
		
		this.vistaResultados = vistaResultados;
		this.modeloPrincipal = modeloPrincipal;
		
		arrayEtiquetasBusqueda = new ArrayList<Etiquetas>();
		
		botonBuscar = vistaResultados.getBotonBuscar(); 
		botonBuscar.addActionListener(this);
		vistaResultados.getOpBuscarSinEtiqueta().addActionListener(this);
		
		anterior = vistaResultados.getAnterior();
		anterior.addActionListener(this);
		siguiente = vistaResultados.getSiguiente();
		siguiente.addActionListener(this);
		
		//Limita el mayor año de búsqueda como el año actual, siendo el primero 1970
		int anoActual = LocalDate.now().getYear();
		for(int i=anoActual; i>1969; i--) {
			vistaResultados.getOpAnoAntes().addItem(i+"");
			vistaResultados.getOpAnoDespues().addItem(i+"");
		}
		vistaResultados.getOpAnoDespues().setSelectedIndex(vistaResultados.getOpAnoAntes().getItemCount()-1);
		vistaResultados.getOpAnoAntes().setSelectedIndex(0);
		
		
	}
	
	public JButton getBotonBuscar() {
		return botonBuscar;
	}
	
	public void busquedaParaActualizarImagenTempUpdateada(ObjectId idMongoImagenTempActualizada) {
		
		empezarBusqueda(!vistaResultados.getOpBuscarSinEtiqueta().isSelected());
		
		int numeroContiene = -1;
		
		//Se busca en qué posición del array resultados actuales está la imagen actualizada
		for(int i=0; i<vistaResultados.getArrayResultadosActuales().length; i++) {
			if(vistaResultados.getArrayResultadosActuales()[i].getIdMongo().equals(idMongoImagenTempActualizada)) {
				numeroContiene=i;
				break;
			}
		}
		
		//Si no está, se vacía la imagen ampliada (panel este)
		if(numeroContiene == -1) vistaResultados.vaciarImagenGrande();
		//Si está, se actualiza
		else vistaResultados.cambiarImagenGrande(vistaResultados.getArrayResultadosActuales()[numeroContiene]);
		
		
	}
	
	public void empezarBusqueda(boolean buscarConEtiquetas) {//TODO sincronizar y hacer a thread de búsqueda acceder aquí?
		
		numPagina = 0;//Se vuelve a la página 1 (por si el usuario había buscado mirando una página posterior)
		
		if(buscarConEtiquetas) arrayResultados = modeloPrincipal.getArrayResultados(arrayEtiquetasBusqueda,fechaDespuesDe,fechaAntesDe);
		else arrayResultados = modeloPrincipal.getArrayResultados(null, fechaDespuesDe,fechaAntesDe);

		//Línea alternativa a las dos anteriores:
		//modeloPrincipal.getArrayResultados((buscarConEtiquetas ? arrayEtiquetasBusqueda : null), fechaDespuesDe, fechaAntesDe)
		
		mostrarResultados();
		
		
	}
	
	
	private void mostrarResultados(){
		
		//Número de filas del panel resultados central
		int maxPorPagina = vistaResultados.getNumeroFilas();
		
		numTotalPaginas = 0;
		
		//Suma una página si %!=0
		numTotalPaginas = (arrayResultados.size() % maxPorPagina == 0) ?  arrayResultados.size() / maxPorPagina : (arrayResultados.size() / maxPorPagina) + 1;
		
		//Ternario para que retorna[x] valga null en las posiciones que se salgan del array (última página si hay resultados colganderos)
		//TODO extrapolar retorna a maxPorPagina distintos (dar a retorna.length el valor de maxPorPagina y meter en for)
		ImagenTemp[] retorna = {((numPagina*maxPorPagina)+0)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+0),
								((numPagina*maxPorPagina)+1)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+1),
								((numPagina*maxPorPagina)+2)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+2),
								((numPagina*maxPorPagina)+3)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+3)};
		
		vistaResultados.anadirResultados(retorna, numPagina+1, numTotalPaginas);
		
		//Activa/desactiva botones anterior/siguiente en función a las páginas que haya
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
		
		if(ev.getSource()==botonBuscar) {
			
			boolean erroresFormulario = false;
			
			//Comprueba que la fecha sea correcta (eg !31feb)
			if(vistaResultados.getOpFechaDespues().isSelected()){
				fechaDespuesDe = getFechaDesdeTexto(vistaResultados.getFechaDespues());
				if(fechaDespuesDe==null) erroresFormulario = true;//TODO mostrar tooltip
			}else {
				fechaDespuesDe = null;
			}
			
			//Otra fecha correcta
			if(vistaResultados.getOpFechaAntes().isSelected()) {
				fechaAntesDe = getFechaDesdeTexto(vistaResultados.getFechaAntes());
				if(fechaAntesDe==null) erroresFormulario = true;//TODO mostrar tooltip
			}else {
				fechaAntesDe = null;
			}
			
			//Si está activada la búsqueda sin etiquetas
			if(vistaResultados.getOpBuscarSinEtiqueta().isSelected()) {
				
				if(!erroresFormulario) empezarBusqueda(false);
				else System.out.println("errores en el formulario");
			
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
					//TODO más adelante, no buscar si la fecha NO ha cambiado, para optimizar recursos
					empezarBusqueda(true);
					
				
				}
				
				
				
			}
			
		}else if(ev.getSource()==anterior) {
			
			numPagina--;
			mostrarResultados();
			
		}else if(ev.getSource()==siguiente) {
			
			numPagina++;
			mostrarResultados();
			
		
		//Si se pulsa en la X de una etiqueta buscada	
		}else if(ev.getSource() instanceof Etiquetas) {
			
			//Si no se ha seleccionado Búsqueda sin etiquetas (si se selecciona se inhabilitan las etiquetas)
			if(!vistaResultados.getOpBuscarSinEtiqueta().isSelected()) {
				
				//Si el array de etiquetas contiene esa etiqueta
				if(arrayEtiquetasBusqueda.contains(ev.getSource())) {
					
					//Se borra la etiqueta del array
					arrayEtiquetasBusqueda.remove(ev.getSource());
					vistaResultados.getPanelEtiquetas().remove((Etiquetas)ev.getSource());
					
					//Si el array queda vacío, se vacía el panel
					if(arrayEtiquetasBusqueda.isEmpty()) {
						vistaResultados.anadirResultados(null, 0, 0);//TODO crear método de vaciar vista en vez de hacerlo así cutre??
						
					//Si quedan etiquetas
					}else {
						
						//Si la etiqueta borrada no tenía 0 resultados se empieza la búsqueda
						if(((Etiquetas)ev.getSource()).getCuenta() != 0) {
							empezarBusqueda(true);//TODO controlar el parámetro en caso de que sea posible borrar etiquetas con la opción busqueda sin etiquetas seleccionada
						}
						
					}
					
					vistaResultados.revalidate();
					vistaResultados.repaint();
					
				}else {
					System.out.println("ERROR, se ha dado a borrar una etiqueta que no estaba en arrayEtiquetasBusqueda (controladorResultados 249)");
				}
				
			}
			
		}
		
	}

}
