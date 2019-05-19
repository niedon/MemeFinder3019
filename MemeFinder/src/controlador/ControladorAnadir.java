package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.*;

import modelo.ModeloPrincipal;
import vista.Etiquetas;
import vista.VistaAnadir;

public class ControladorAnadir implements ActionListener, MouseListener{
	
	private VistaAnadir vistaAnadir;
	private ModeloPrincipal modeloPrincipal; 
	
	//private JTextField barraDireccion;//DEPRECATED?
	private JButton botonExaminar;
	private JFileChooser elegidor;
	private JTextField barraEtiquetas;
	private JTextField barraNombreImagen;
	private JButton botonEnviar;
	
	private Hasheador hasheador;
	private Thread thread;
	
	private JLabel flechaIzq;
	private JLabel flechaDer;
	
	private ArrayList<ImagenTemp> arrayListImagenes;
	
	private static final String rutaTemp = "/home/basi/eclipse-workspace/MemeFinder/src/imagenesDB/temp/"; //Poner en clase principal
	
	private int contadorImagenActual;
	
	public ControladorAnadir(VistaAnadir vistaAnadir, ModeloPrincipal modeloPrincipal) {
		
		contadorImagenActual=0;
		
		this.vistaAnadir = vistaAnadir;
		this.modeloPrincipal = modeloPrincipal;
		
		botonEnviar = vistaAnadir.getBotonEnviar();
		
		
		//<test>
		
//		barraDireccion = vistaAnadir.getBarraDireccion();
//		barraDireccion.addActionListener(this);
		barraNombreImagen = vistaAnadir.getBarraNombreImagen();
		barraNombreImagen.addActionListener(this);
		botonExaminar = vistaAnadir.getBotonExaminar();
		botonExaminar.addActionListener(this);
		
		barraEtiquetas = vistaAnadir.getBarraEtiquetas();
		barraEtiquetas.addActionListener(this);
		
		botonEnviar = vistaAnadir.getBotonEnviar();
		botonEnviar.addActionListener(this);
		
		flechaIzq = vistaAnadir.getFlechaIzq();
		flechaIzq.addMouseListener(this);
		flechaDer = vistaAnadir.getFlechaDer();
		flechaDer.addMouseListener(this);
		
		//</test>
		
		arrayListImagenes = new ArrayList<ImagenTemp>();
		
	}
	
	public void actualizacionHash(ImagenTemp imagenTemp) {
		
		//TODO cuando acaba el array coincidencias se llama a este método, ver qué hace el método, y en caso de que
		//no haga nada relevante, modificar el redibujado de vistaañadir con las cosas del hashing y las
		//coincidencias (además del enlace a vistacoincidencias, ojú)
		vistaAnadir.setNuevaImagen(imagenTemp,contadorImagenActual+1, arrayListImagenes.size());
		
//		//si el objeto está en la lista Y es el que se visualiza ahora
//		if(arrayListImagenes.contains(imagenAVisualizar) && arrayListImagenes.get(contadorImagenActual)==imagenAVisualizar) {
//			//cambiar en interfaz
//		}
		
	}
	
	public ImagenTemp getImagenTempActual() {
		return arrayListImagenes.get(contadorImagenActual);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getSource().equals(botonExaminar)) {
			
			elegidor = new JFileChooser();
			elegidor.setMultiSelectionEnabled(true);
			//TODO filtro de imágenes
			if(elegidor.showDialog(null, "Elegir") == JFileChooser.APPROVE_OPTION) {
				
				File[] archivosElegidos = elegidor.getSelectedFiles();
				
				for(int i = 0; i<archivosElegidos.length; i++) {
					//String urlObjetivo = "";
					File temp = null;
					do {
						temp = new File(rutaTemp + Math.round((Math.random()*1000)) + archivosElegidos[i].getName().substring(archivosElegidos[i].getName().lastIndexOf('.')));
						
					}while(Files.exists(temp.toPath()));
					
					try {
						Files.copy(archivosElegidos[i].toPath(), temp.toPath());
						//ImagenTemp imgTemporal = new ImagenTemp(temp.getAbsolutePath(),archivosElegidos[i].getName());
						arrayListImagenes.add(new ImagenTemp(temp.getAbsolutePath(),archivosElegidos[i].getName()));
						System.out.println("nombre: " + archivosElegidos[i].getName());
						System.out.println("añadido archivo a array, ruta: " + temp.getAbsolutePath());
					}catch(Exception e) {
						System.out.println("no se ha copiado el archivo a temp");
						e.printStackTrace();
					}
				}
				
				/*
				TODO empezar aquí con los threads?
				
				meter bool a imagentemp para que no lo meta en la base de datos
				hasta que no se saque como mínimo el hash
				
				posteriormente gestionar similitud de imágenes (comparaciones de hash) en caso de que tarden mucho,
				dejarlas para luego con la imagen ya metida en bbdd si tardan mucho las comparaciones
				
				----nuevo
				método en esta clase sync (para que solo pueda acceder el main o el hilo de hashing simultaneamente) que compruebe jlabel de
				hashing y si el botón debe estar habilitado o inhabilitado (main debería pasar siempre por aquí) para que no haya interferencias
				en cambiar de pantalla y el hashing
				
				solo permitir enviar imagen si el hashing está completo
				esto permite aumentar el tiempo de hashing, comprobar cuánto se tarda en leer bbdd
				*/
				

				//Carga en vista
				vistaAnadir.setNuevaImagen(arrayListImagenes.get(0), contadorImagenActual+1, arrayListImagenes.size());
				
				
				//esto se llama cuando se han instanciado todas las ImagenTemp, luego no hay conflicto del String pHash entre el constructor y el setter 
				hasheador = new Hasheador((ArrayList<ImagenTemp>) arrayListImagenes.clone(), this,vistaAnadir, this.modeloPrincipal);
				(new Thread(hasheador)).start();
				
				
				
				
				
//				//Test comparación imágenes
//				for(int i=0; i<arrayListImagenes.size(); i++) {
//					for(int j=i; j<arrayListImagenes.size(); j++) {
//						if(j!=i) System.out.println((i+1) + "-" + (j+1) + ": " + ProxyHash.getDistancia(arrayListImagenes.get(i).getPHash(), arrayListImagenes.get(j).getPHash()));
//					}
//				}
				
				
			}

			
		}else if(ev.getSource().equals(barraEtiquetas)) {
			
			
			//OJO a los !
			if(!barraEtiquetas.getText().equals("") && !arrayListImagenes.isEmpty()) {
				
				Etiquetas temporal = new Etiquetas(barraEtiquetas.getText(),modeloPrincipal.getCountEtiquetas(barraEtiquetas.getText()));
				if(arrayListImagenes.get(contadorImagenActual).anadirEtiqueta(temporal)) {
					temporal.anadirListenerEliminar(this);
					vistaAnadir.anadirEtiqueta(temporal);
					barraEtiquetas.setText("");
					//TODO dar retroalimentación al usuario en caso de que imagentemp contenga ya la etiqueta
				}
				
				
				
				
				
			}
		}else if(ev.getSource().equals(botonEnviar)) {
			
			//En cristiano, si el nombre (sin extensión) del objeto ImageTemp es DISTINTO al string de la barra Y el string de la barra NO es ""
			if(!arrayListImagenes.get(contadorImagenActual).getNombre().equals(barraNombreImagen.getText()) && !barraNombreImagen.getText().equals("")) {
				arrayListImagenes.get(contadorImagenActual).setNombre(barraNombreImagen.getText());
			}
			
			System.out.println("Imagen añadida:");
			
			System.out.println("nombre: " + arrayListImagenes.get(contadorImagenActual).getNombre() + arrayListImagenes.get(contadorImagenActual).getExtension());
			
			if(arrayListImagenes.get(contadorImagenActual).getArrayEtiquetas().isEmpty()) {
				System.out.println("No hay etiquetas");
			}else {
				System.out.println("Etiquetas:");
				for(Etiquetas e : arrayListImagenes.get(contadorImagenActual).getArrayEtiquetas()) {
					System.out.println("array: " + e.getTexto());
				}
			}
			
			//TODO ventana emergente diciendo que hay imagenes similares (si procede)
			modeloPrincipal.anadirImagen(arrayListImagenes.get(contadorImagenActual));
			
			arrayListImagenes.remove(contadorImagenActual);
			
			if(arrayListImagenes.isEmpty()) {
				vistaAnadir.vaciar();
			}else{
				
				//Retrocede una posición si la imagen enviada es la última
				if(contadorImagenActual==arrayListImagenes.size()) {
					contadorImagenActual--;
				}
				
				vistaAnadir.setNuevaImagen(arrayListImagenes.get(contadorImagenActual), contadorImagenActual+1, arrayListImagenes.size());
				
			}
			

		}
		
		
		
		if(ev.getSource().getClass().getSimpleName().equals("Etiquetas")) {
			
			vistaAnadir.quitarEtiqueta((Etiquetas)ev.getSource());
			arrayListImagenes.get(contadorImagenActual).getArrayEtiquetas().remove(ev.getSource());
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent ev) {
		if(ev.getSource()==flechaIzq && !arrayListImagenes.isEmpty()) {
			
			if(contadorImagenActual!=0) {
				arrayListImagenes.get(contadorImagenActual).setNombre(barraNombreImagen.getText());
				contadorImagenActual--;
				vistaAnadir.setNuevaImagen(arrayListImagenes.get(contadorImagenActual), contadorImagenActual+1, arrayListImagenes.size());
			}
			
		}else if(ev.getSource()==flechaDer && !arrayListImagenes.isEmpty()) {
			
			if(contadorImagenActual != (arrayListImagenes.size()-1)) {
				arrayListImagenes.get(contadorImagenActual).setNombre(barraNombreImagen.getText());
				contadorImagenActual++;
				vistaAnadir.setNuevaImagen(arrayListImagenes.get(contadorImagenActual), contadorImagenActual+1, arrayListImagenes.size());
			}
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent ev) {
		//TODO aquí y en el siguiente virguerías gráficas de opacidad de las flechas
		
		
	}

	@Override
	public void mouseExited(MouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
