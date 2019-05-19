package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;

import modelo.ModeloPrincipal;
import vista.Etiquetas;
import vista.VistaResultados;

public class ControladorResultados implements ActionListener {
	
	private VistaResultados vistaResultados;
	private ModeloPrincipal modeloPrincipal;
	
	private ArrayList<Etiquetas> arrayEtiquetasBusqueda;
	private ArrayList<ImagenTemp> arrayResultados;
	
	private int numPagina, numTotalPaginas;
	
	private JButton anterior, siguiente;
	
	
	public ControladorResultados(VistaResultados vistaResultados, ModeloPrincipal modeloPrincipal) {
		
		this.vistaResultados = vistaResultados;
		this.modeloPrincipal = modeloPrincipal;
		
		arrayEtiquetasBusqueda = new ArrayList<Etiquetas>();
		
		vistaResultados.getBotonBuscar().addActionListener(this);
		
		anterior = vistaResultados.getAnterior();
		anterior.addActionListener(this);
		siguiente = vistaResultados.getSiguiente();
		siguiente.addActionListener(this);
		
		int anoActual = LocalDate.now().getYear();
		String[] anosComboBox = new String[anoActual-1969];
		for(int i=anoActual; i>1969; i--) {
			//System.out.println(anoActual-i + "-" + i);
			//anosComboBox[anoActual-1] = i+"";
			vistaResultados.getOpAnoAntes().addItem(i+"");
			vistaResultados.getOpAnoDespues().addItem(i+"");
		}
		vistaResultados.getOpAnoDespues().setSelectedIndex(vistaResultados.getOpAnoAntes().getItemCount()-1);
		vistaResultados.getOpAnoAntes().setSelectedIndex(0);
		
		
	}
	
	
	
	public void empezarBusqueda() {//sincronizar y hacer a thread de búsqueda acceder aquí?
		//TODO orden para empezar la búsqueda en bd y tal
		
		System.out.println("buscar");
		
		numPagina = 0;
		arrayResultados = modeloPrincipal.getArrayResultados(arrayEtiquetasBusqueda);
		
		mostrarResultados();
		
		
	}
	
	private void mostrarResultados(){
		
		int maxPorPagina = vistaResultados.getNumeroFilas();//Número de filas del panel resultados central
		
		//numPagina = 0;//declarar como campo de clase (empieza en cero)
		numTotalPaginas = 0;
		
		//Suma una página si %!=0
		numTotalPaginas = (arrayResultados.size() % maxPorPagina == 0) ?  arrayResultados.size() / maxPorPagina : (arrayResultados.size() / maxPorPagina) + 1;
		
		//Ternario para que retorna[x] valga null en las posiciones que se salgan del array (última página si hay resultados colganderos)
		ImagenTemp[] retorna = {((numPagina*maxPorPagina)+0)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+0),
								((numPagina*maxPorPagina)+1)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+1),
								((numPagina*maxPorPagina)+2)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+2),
								((numPagina*maxPorPagina)+3)>= arrayResultados.size() ? null : arrayResultados.get((numPagina*maxPorPagina)+3)};
		
		vistaResultados.anadirResultados(retorna, numPagina+1, numTotalPaginas);
		
		anterior.setEnabled(false);
		if(numTotalPaginas==1) siguiente.setEnabled(false);
		else siguiente.setEnabled(true);
		
		
	}
	
	
	private static boolean esFechaValida(String input) {
        //String formatString = "MM/dd/yyyy";

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            format.parse(input);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (ParseException e) {
			return false;
		}
        return true;
    }



	@Override
	public void actionPerformed(ActionEvent ev) {

		if(ev.getSource()==vistaResultados.getBotonBuscar()) {
			
			/*
				TODO primero, comprobar que la fecha esté bien y la etiqueta no esté repe (si procede)
				después, comprobar si la caja de texto está vacía
					si tiene texto, adelante
					si está vacía, comprobar si la fecha es distinta, y entonces adelante
			*/
			
			//Si la barra de búsqueda NO está vacía
			if(!vistaResultados.getBarraBusqueda().getText().isEmpty()) {
//				vistaResultados.getPanelEtiquetas().add(new Etiquetas(vistaResultados.getBarraBusqueda().getText(),8));
//				vistaResultados.getPanelEtiquetas().revalidate();
				
				Etiquetas temp = new Etiquetas(vistaResultados.getBarraBusqueda().getText(),modeloPrincipal.getCountEtiquetas(vistaResultados.getBarraBusqueda().getText()));
				temp.anadirListenerEliminar(this);
				
				//Si la etiqueta ya está
				if(arrayEtiquetasBusqueda.contains(temp)) {
					//System.out.println("la contiene");//test
					//TODO borrar texto de barra y hacer relucir la etiqueta en especial
					
				}else {
					//System.out.println("no la contiene");
					arrayEtiquetasBusqueda.add(temp);//Añade etiqueta al arraylist
					vistaResultados.getPanelEtiquetas().add(arrayEtiquetasBusqueda.get(arrayEtiquetasBusqueda.size()-1));//Añade etiqueta a la vista
					vistaResultados.getBarraBusqueda().setText("");//Vacía caja de texto de búsqueda
					empezarBusqueda();
					vistaResultados.getPanelEtiquetas().revalidate();
					
				}
				
			}
			
		}else if(ev.getSource()==anterior) {
			
			numPagina--;
			mostrarResultados();
			if(!siguiente.isEnabled()) siguiente.setEnabled(true);
			if(numPagina==1) anterior.setEnabled(false); 
			
		}else if(ev.getSource()==siguiente) {
			//System.out.println("clic");
			numPagina++;
			mostrarResultados();
			if(!anterior.isEnabled()) anterior.setEnabled(true);
			if(numPagina == numTotalPaginas-1) siguiente.setEnabled(false);
		}
		
		
		if(ev.getSource().getClass().getSimpleName().equals("Etiquetas")) {
			
			if(arrayEtiquetasBusqueda.contains(ev.getSource())) {
				
				arrayEtiquetasBusqueda.remove(ev.getSource());
				vistaResultados.getPanelEtiquetas().remove((Etiquetas)ev.getSource());
				if(((Etiquetas)ev.getSource()).getCuenta()==0) {
					vistaResultados.getPanelEtiquetas().revalidate();
					vistaResultados.getPanelEtiquetas().repaint();
				}else {
					empezarBusqueda();
					vistaResultados.getPanelEtiquetas().repaint();
				}
				
			}
			
		}
		
	}

}
