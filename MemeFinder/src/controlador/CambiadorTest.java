//package controlador;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import vista.VistaPrincipal;
//import vista.VistaResultados;
//
//public class CambiadorTest implements ActionListener{
//	
//	private VistaPrincipal vistaPrincipal;
//	
//	public CambiadorTest(VistaPrincipal vistaPrincipal) {
//		
//		this.vistaPrincipal = vistaPrincipal;
//		
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		
//		if(vistaPrincipal.getTextoBarraBusqueda().equals("") || vistaPrincipal.getTextoBarraBusqueda().equals(null)) {
//			System.out.println("buscar principal vacío");
//		}else {
//			vistaPrincipal.getVistaResultados().setTextoBarra(vistaPrincipal.getTextoBarraBusqueda());
//			
//			vistaPrincipal.cambiaTest("PANELRESULTADOS");
//			System.out.println("cambiado a PANELRESULTADOS");
//		}
//		
//
//		
//	}
//
//}
