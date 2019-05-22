package controlador;

import java.util.ArrayList;

import modelo.ModeloPrincipal;
import vista.VistaAnadir;

public class Hasheador implements Runnable{
	
	private ArrayList<ImagenTemp> arrayImagenTemp;
	private ControladorAnadir controladorAnadir;
	private VistaAnadir vistaAnadir;
	private ModeloPrincipal modeloPrincipal;
	
	public Hasheador(ArrayList<ImagenTemp> arrayImagenTemp, ControladorAnadir controladorAnadir, VistaAnadir vistaAnadir, ModeloPrincipal modeloPrincipal) {
		
		this.arrayImagenTemp = arrayImagenTemp;
		this.controladorAnadir = controladorAnadir;
		this.vistaAnadir = vistaAnadir;
		this.modeloPrincipal = modeloPrincipal;
		
	}

	@Override
	public void run() {
		
		//ojo !
		if(!arrayImagenTemp.isEmpty()) {
			
			long tiempo = System.currentTimeMillis();
			
			for(int i=0;i<arrayImagenTemp.size();i++) {
//				System.out.println("procesando en hasheador imagentemp " + i);
				arrayImagenTemp.get(i).setPHash(ProxyHash.getHash(arrayImagenTemp.get(i).getImagen()));
//				System.out.println(i + " con phash: " + arrayImagenTemp.get(i).getPHash());
				//TODO comparaciones y cambiar para que no se actualice nada hasta que no acabe esto
				//(i.e. en controladoranadir puede haber fallo con el texto a poner en el hashing
				//puesto que pone si phash=="" poner texto por defecto o algo así)
//				System.out.println("tiempo en hacer hash: " + (System.currentTimeMillis()-tiempo));
				
				//TODO ver cómo gestionar el número máx, quizás desde una constante en controlador principal
				arrayImagenTemp.get(i).setArrayComparaciones(modeloPrincipal.getArrayComparaciones(arrayImagenTemp.get(i), 80));
				
				arrayImagenTemp.get(i).setTiempoEnHashear(System.currentTimeMillis()-tiempo);
				//TODO meter el porcentaje de getArrayComparaciones en opciones, alguna mierda tipo "precisión del algoritmo buscador de imágenes"
				
				
				if(controladorAnadir.getImagenTempActual() == arrayImagenTemp.get(i)) {
					
					controladorAnadir.actualizacionHash(arrayImagenTemp.get(i));
					
					//vistaAnadir.setNuevaImagen(arrayImagenTemp.get(i).getImagen(), num1, num2);
					
					//TODO quizás mandar directamente a redibujar, sin bool en el método y con el hash ya hecho?
					//meter igualmente en el objeto imagentemp las coincidencias (array de ID de la bd??)
				}
				
				
			}
		}
		
		
	}

}
