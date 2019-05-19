package programa;


import modelo.*;
import vista.*;
import controlador.*;

public class Main {

	public static void main(String[] args) {
		
		ModeloPrincipal modeloPrincipal = new ModeloPrincipal();
		VistaPrincipal vistaPrincipal = new VistaPrincipal();
		
		ControladorPrincipal controladorPrincipal = new ControladorPrincipal(modeloPrincipal, vistaPrincipal);
		
		vistaPrincipal.setVisible(true);
		
		

	}

}
