package controlador;

import java.util.ArrayList;

import modelo.ModeloPrincipal;

public class Hasheador implements Runnable{
	
	private ArrayList<ImagenTemp> arrayImagenTemp;
	private ControladorAnadir controladorAnadir;
	private ModeloPrincipal modeloPrincipal;
	
	public Hasheador(ArrayList<ImagenTemp> arrayImagenTemp, ControladorAnadir controladorAnadir, ModeloPrincipal modeloPrincipal) {
		
		this.arrayImagenTemp = arrayImagenTemp;
		this.controladorAnadir = controladorAnadir;
		this.modeloPrincipal = modeloPrincipal;
		
	}

	@Override
	public void run() {
		
		//ojo !
		if(!arrayImagenTemp.isEmpty()) {
			
			
			
			for(ImagenTemp it : arrayImagenTemp) {
				
				long tiempo = System.currentTimeMillis();
				
				it.setPHash(ProxyHash.getHash(it.getImagen()));
				it.setArrayComparaciones(modeloPrincipal.getArrayComparaciones(it, 80));
				if(it.getArrayComparaciones().isEmpty()) it.setAnadirIgualmente(true);
				it.setTiempoEnHashear(System.currentTimeMillis()-tiempo);
				
				if(controladorAnadir.getImagenTempActual() == it) {
					controladorAnadir.actualizacionHash(it);
				}
				
			}
			
		}
		
		
	}

}
