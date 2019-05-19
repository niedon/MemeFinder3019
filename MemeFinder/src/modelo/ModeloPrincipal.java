package modelo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import controlador.ImagenTemp;
import controlador.ProxyHash;
import vista.Etiquetas;

public class ModeloPrincipal {
	
	private MongoClient mc;
	private MongoDatabase mdb;
	private MongoCollection tablaImagenes, tablaEtiquetas;
	
	//------CAMPOS
	
	private static final String _ID = "_id";
	private static final String NOMBRE = "nombre";
	private static final String ETIQUETAS = "etiquetas";
	private static final String URL = "url";
	private static final String PHASH = "phash";
	private static final String CATEGORIAS = "categorias";
	private static final String FECHA = "fecha";
	
	private static final String ETNOMBRE = "etnombre";
	private static final String ETCOUNT = "etcount";
	
	
	private static final String rutaBD = "/home/basi/eclipse-workspace/MemeFinder/src/imagenesDB/"; //Poner en clase principal
	
	
	public ModeloPrincipal() {
		
		try {
			
			//TODO drop de las tablas y cambiar por las buenas cuando el programa esté así como que funcional
			
			mc = new MongoClient("localhost", 27017);
			mdb = mc.getDatabase("MemeFinder3019");
			tablaImagenes = mdb.getCollection("tablaImagenesTEST");
			tablaEtiquetas = mdb.getCollection("tablaEtiquetasTEST");
			//System.out.println(mdb.getName());
			//System.out.println(mdb.getCollection("tablaImagenesTEST").countDocuments());
			//mc.close();
			
//			DBCursor cur = tablaImagenes.find();
			
			//Document document = new Document();
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	 public boolean anadirImagen(ImagenTemp imagenTemp) {
		 
		 //Modifica tablaImagenes
		 
//		 File temp = null;
//			do {
//				temp = new File(rutaTemp + Math.round((Math.random()*1000)) + archivosElegidos[i].getName().substring(archivosElegidos[i].getName().lastIndexOf('.')));
//				
//			}while(Files.exists(temp.toPath()));
//			
//			try {
//				Files.copy(archivosElegidos[i].toPath(), temp.toPath());
		 
		 File temp = null;
		 String nombreExt = "";
		 do {
			 temp = new File(rutaBD + Math.round(Math.random()*1000000) + imagenTemp.getExtension());
		 }while(Files.exists(temp.toPath()));
		 
		 try {
			 Files.copy(imagenTemp.getImagen().toPath(), temp.toPath());
		 }catch(Exception e) {
			 System.out.println("Error copiando el archivo fuera de /temp/");
		 }
		 
		 Document entrada = new Document(NOMBRE, (imagenTemp.getNombre()+imagenTemp.getExtension()));
		 
		 entrada.append(ETIQUETAS, Arrays.asList(imagenTemp.etiquetasAArrayDeString()));//TODO comprobar si hay error si etiquetas==null
		 //entrada.append(URL, temp.getAbsolutePath().substring(temp.getAbsolutePath().lastIndexOf('/')+1));
		 entrada.append(URL, temp.getAbsolutePath());
		 entrada.append(PHASH, imagenTemp.getPHash());
		 entrada.append(FECHA, imagenTemp.getFecha());
		 
		 tablaImagenes.insertOne(entrada);
		 
		 imagenTemp.getImagen().delete();
		 
		 //Modifica tablaEtiquetas
		 
		 for(Etiquetas e : imagenTemp.getArrayEtiquetas()) {
			 
			 //Si no existe esa etiqueta en BD
			 if(tablaEtiquetas.find(new Document(_ID,e.getTexto())).first() == null) {
				 System.out.println("etiquetas null");
			 //if(tablaEtiquetas.countDocuments(new Document(_ID,e.getTexto())) == 0) {
				 
				 Document doc = new Document();
				 doc.append(_ID, e.getTexto());
				 doc.append(ETCOUNT, 1);
				 
				 tablaEtiquetas.insertOne(doc);
				 
				 System.out.println("count0: " + tablaEtiquetas.find(new Document(_ID,e.getTexto())).first());
				 
			 }else {
				 System.out.println("etiquetas no null");
				 Document newDocument = new Document();
				 newDocument.append("$inc", new BasicDBObject().append(ETCOUNT, 1));
								
						tablaEtiquetas.updateOne(new Document().append(_ID, e.getTexto()), newDocument);
						
						System.out.println("count+: " + tablaEtiquetas.find(new Document(_ID,e.getTexto())).first());
				 
			 }
			 
			 
			 
			 
		 }
		 
		 
		 return false;
	 }
	 
	 public int getCountEtiquetas(String str) {
		 
		 Document retorna = (Document) tablaEtiquetas.find(new Document(_ID,str)).first();
		 
		 if(retorna==null) {
			 return 0;
		 }else {
			 return retorna.getInteger(ETCOUNT, 90);
			 
		 }
		 
		 
	 }
	 
	 
	 //TODO método para comparar imágenes almacenadas, quizás devolver string[] o imagentemp[] para procesar en vistacoincidencias
	 
	 public ArrayList<ImagenTemp> getArrayComparaciones(ImagenTemp it, int max) {
		 
		 ArrayList<ImagenTemp> arrayRetorna = new ArrayList<ImagenTemp>();
		 String hashOriginal = it.getPHash();
		 FindIterable<Document> iterable = tablaImagenes.find();
		 System.out.println(iterable.toString());
		 FindIterable<BasicDBObject> caca = tablaImagenes.find();
		 
		 for(Document doc : iterable) {
			 System.out.println("comprado con: " + ((String)doc.get(NOMBRE)));
			 int compara = ProxyHash.getDistancia((String)doc.get(PHASH), hashOriginal);
			 System.out.println("compara: " + compara);
			 if(compara < max) {
				 
				 ImagenTemp temp = convierteDocObjeto(doc);
				 temp.setIndiceParecido(compara);
				 arrayRetorna.add(temp);
			 }
		 }
		 
		 return arrayRetorna;
		 
	 }
	 
	 public ArrayList<ImagenTemp> getArrayResultados(ArrayList<Etiquetas> arrayEtiquetas){
		 
		 Document doc = new Document();
		 
		 //Añade a etiquetasNoCero los textos de etiquetas que tienen resultados en la bd
		 ArrayList<String> etiquetasNoCero = new ArrayList<String>();		 
		 for(Etiquetas e : arrayEtiquetas) {
			 if(getCountEtiquetas(e.getTexto()) != 0) {
				 etiquetasNoCero.add(e.getTexto());
			 }
		 }
		 
		 //<test>
		 System.out.print("etiquetas nonull: ");
		 for(String s : etiquetasNoCero) { System.out.print(s + "-"); }
		 System.out.println();
		 //</test>
		 
		 /*
		  * aquí va la magia
		  */
		 
//		 ArrayList<String> ttt= new ArrayList<String>();
//		 ttt.add("aaa");
//		 ttt.add("test");
		 
		 //Añade las condiciones (resultados que tengan en ETIQUETAS (al menos) todas las que estén en etiquetasNoCero)
		 doc.append(ETIQUETAS, new Document("$all", etiquetasNoCero));
		 
		 
		 
		 FindIterable<Document> resultado = tablaImagenes.find(doc);
		 MongoCursor<Document> iter = resultado.iterator();
		 
		 ArrayList<ImagenTemp> retorna = new ArrayList<ImagenTemp>();
//		 int contador = 0;//test
		 
		 //Parseador
		 while(iter.hasNext()) {
			 retorna.add(convierteDocObjeto(iter.next()));
//			 System.out.println("tiene");//test
//			 contador++;//test
		 }
//		 System.out.println(contador);
		 
//		 for(ImagenTemp i : retorna) {//test
//			 System.out.println(i.getNombre()+i.getExtension() + "  --  " + i.getImagen().getAbsolutePath());
//		 }
		 
		 return retorna;
		 
		 //---------------------------DEPRECATED?

//		 //doc.append("$all", new Document(ETIQUETAS,etiquetasNoCero));
//		 ////doc.append(ETIQUETAS, new Document("$all",Arrays.asList(etiquetasNoCero)));
//		 
//		 
//		 FindIterable<Document> resultado = tablaImagenes.find(doc);
//		 ArrayList<ImagenTemp> retorna = new ArrayList<ImagenTemp>();
//		 int contador = 0;
//		 MongoCursor<Document> iter = resultado.iterator();
//		 
//		 while(iter.hasNext()) {
//			 
//			 retorna.add(convierteDocObjeto(iter.next()));
//			 System.out.println("tiene");
//			 contador++;
//		 }
//		 System.out.println("tamaño; " + contador);
//		 
////		 for(Document docum : resultado) {
////			 retorna.add(convierteDocObjeto(doc));
////		 }
//		 
//		 for(ImagenTemp i : retorna) {
//			 System.out.println(i.getNombre()+i.getExtension() + "  --  " + i.getImagen().getAbsolutePath());
//		 }
//		 
//		 
//		 
//		 return retorna;
	 }
	 
	 
	 
	 
	 private ImagenTemp convierteDocObjeto(Document d) {
		 
		 String url = (String) d.get(URL);
		 System.out.println("la url es " + url);
		 String nombreFull = (String) d.get(NOMBRE);
//		 //ArrayList<Etiquetas> etiquetas = (ArrayList<Etiquetas>) d.get(ETIQUETAS);
//		 ArrayList<Etiquetas> etiquetas = new ArrayList<Etiquetas>();
//		 for(String s : (String[]) d.get(ETIQUETAS)) {
//			 etiquetas.add(new Etiquetas(s,getCountEtiquetas(s)));
//		 }
		 String pHash = (String) d.get(PHASH);
		 long fecha = (long) d.get(FECHA);
		 
		 ImagenTemp retorna = new ImagenTemp(url,nombreFull);
		 
		 retorna.setIdMongo((ObjectId)d.get(_ID));
		 ObjectId id = new ObjectId();
		 
		 for(String s : (ArrayList<String>) d.get(ETIQUETAS)) {
			 retorna.anadirEtiqueta(new Etiquetas(s,getCountEtiquetas(s)));
		 }
		 retorna.setPHash(pHash);
		 retorna.setFecha(fecha);
		 
		 
		 return retorna;
	 }
	 
}
