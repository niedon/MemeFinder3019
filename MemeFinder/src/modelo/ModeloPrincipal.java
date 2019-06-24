package modelo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
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
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;

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
	
	
	//private static final String rutaBD = "/home/basi/eclipse-workspace/MemeFinder/src/imagenesDB/"; //Poner en clase principal
	private static final String rutaBD = "/home/basi/Imágenes/imgBD/";
	
	
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
		 //List<Boolean> hash = Arrays.asList(imagenTemp.getPHashWrap());
		 entrada.append(PHASH, Arrays.asList(imagenTemp.getPHash()));
		 entrada.append(FECHA, imagenTemp.getFecha());
		 
		 tablaImagenes.insertOne(entrada);
		 
		 imagenTemp.getImagen().delete();
		 
		 //Modifica tablaEtiquetas
		 
		 //TODO comprobar que no falla si getArrayEtiquetas().isEmpty()
		 for(Etiquetas e : imagenTemp.getArrayEtiquetas()) {
			 
			 anadirEtiqueta(e.getTexto());
			 
//			 //Si no existe esa etiqueta en BD
//			 if(tablaEtiquetas.find(new Document(_ID,e.getTexto())).first() == null) {
////				 System.out.println("etiquetas null");
//			 //if(tablaEtiquetas.countDocuments(new Document(_ID,e.getTexto())) == 0) {
//				 
//				 Document doc = new Document();
//				 doc.append(_ID, e.getTexto());
//				 doc.append(ETCOUNT, 1);
//				 
//				 tablaEtiquetas.insertOne(doc);
//				 
////				 System.out.println("count0: " + tablaEtiquetas.find(new Document(_ID,e.getTexto())).first());
//				 
//			 }else {
//				 System.out.println("etiquetas no null");
//				 Document newDocument = new Document();
//				 newDocument.append("$inc", new BasicDBObject().append(ETCOUNT, 1));
//								
//						tablaEtiquetas.updateOne(new Document().append(_ID, e.getTexto()), newDocument);
//						
////						System.out.println("count+: " + tablaEtiquetas.find(new Document(_ID,e.getTexto())).first());
//				 
//			 }
			 
			 
			 
			 
		 }
		 
		 
		 return false;
	 }
	 
	 public void updateImagen(ImagenTemp it, String nuevoNombre, ArrayList<Etiquetas> arrayEtiquetasNuevas, ArrayList<Etiquetas> arrayetiquetasBorradas) {
		 
		 ObjectId tempId = it.getIdMongo();
		 
		 
		 //ojo !
		 if(!nuevoNombre.isEmpty()) {
			 System.out.println("entraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			 
			 UpdateResult aaa = tablaImagenes.updateOne(eq(_ID,tempId), Updates.set(NOMBRE, (nuevoNombre + it.getExtension())));
			 System.out.println("matches: " + aaa.getMatchedCount());
			 System.out.println("modificados: " + aaa.getModifiedCount());
			 
		 }
		 
		//ojo !
		 if(!arrayetiquetasBorradas.isEmpty() && !(arrayetiquetasBorradas==null)) {
			 for(int i=0; i<arrayetiquetasBorradas.size(); i++) {
				 tablaImagenes.updateOne(eq(_ID,tempId), Updates.pull(ETIQUETAS, arrayetiquetasBorradas.get(i).getTexto()));
				 borrarEtiqueta(arrayetiquetasBorradas.get(i).getTexto());
			 }
		 }
		 
		//ojo !
		 if(!arrayEtiquetasNuevas.isEmpty() && !(arrayEtiquetasNuevas==null)) {
			 for(int i=0; i<arrayEtiquetasNuevas.size(); i++) {
				 tablaImagenes.updateOne(eq(_ID,tempId), Updates.push(ETIQUETAS, arrayEtiquetasNuevas.get(i).getTexto()));
				 anadirEtiqueta(arrayEtiquetasNuevas.get(i).getTexto());
			 }
		 }
		 
	 }
	 
//	 public void updateImagen(ImagenTemp it, String nuevoNombre, ArrayList<Etiquetas> nuevoArrayEtiquetas) {
//		 
//		 //obsoleto?
//		 
//		 //ojo !
//		 if(!it.getNombre().equals(nuevoNombre)) {
//			 tablaImagenes.updateOne(eq(_ID,it.getIdMongo()), Updates.set(NOMBRE, nuevoNombre));
//		 }
//		 
//		 //bool[] con length del array etiquetas antiguo, será false solo en las etiquetas eliminadas de la imagen
//		 boolean[] arrayIndicesEtBorradas = new boolean[it.getArrayEtiquetas().size()];
//		 for(int i=0; i<arrayIndicesEtBorradas.length; i++) arrayIndicesEtBorradas[i]=false;
//		 
//		 for(Etiquetas et : nuevoArrayEtiquetas) {
//			 
//			 //Si arrayviejo contiene la etiqueta et (de arraynuevo), se marca true en el bool[]
//			 if(it.getArrayEtiquetas().contains(et)) {
//				 arrayIndicesEtBorradas[it.getArrayEtiquetas().indexOf(et)] = true;
//			
//			 //Si arraynuevo contiene una et que arrayviejo no, se añade la etiqueta	 
//			 }else {
//				 anadirEtiqueta(et.getTexto());
//			 }
//		 }
//		 
//		 //Se recorre el bool[], y en los casos false, se borra la etiqueta, porque serán
//		 //los únicos en los que no han sido cambiados a true por el foreach anterior
//		 for(int i=0; i<it.getArrayEtiquetas().size(); i++) {
//			 if(arrayIndicesEtBorradas[i] == false) {
//				 borrarEtiqueta(it.getArrayEtiquetas().get(i).getTexto());
//			 }
//		 }
//		 
//	 }
	 
	 public boolean borrarImagen(ObjectId id) {
		 
		 try {
			 
			 Document doc = (Document)tablaImagenes.find(new Document(_ID,id)).first();
			 
			 for(String s : (ArrayList<String>) doc.get(ETIQUETAS)) {
				 
				 borrarEtiqueta(s);
				 
//				 if(getCountEtiquetas(s) == 1) {
//					 tablaEtiquetas.deleteOne(eq(_ID,s));
//				 }else {
//					 tablaEtiquetas.updateOne(eq(_ID,s),Updates.inc(ETCOUNT, -1));
//				 }
	 
			 }
			 
			 tablaImagenes.deleteOne(new Document(_ID,id));
			 
			 return true;
			 
		 }catch(Exception e) {
			 System.out.println("----------------EXCEPCIÓN BORRANDO UNA IMAGEN ModeloPricipal borrarImagen()");
			 return false;
		 }
		 
		 
	 }
	 
	 private void anadirEtiqueta(String strEt) {
		 
		 //Si no existe esa etiqueta en BD
		 if(tablaEtiquetas.find(new Document(_ID,strEt)).first() == null) {
//			 System.out.println("etiquetas null");
		 //if(tablaEtiquetas.countDocuments(new Document(_ID,e.getTexto())) == 0) {
			 
			 Document doc = new Document();
			 doc.append(_ID, strEt);
			 doc.append(ETCOUNT, 1);
			 
			 tablaEtiquetas.insertOne(doc);
			 
//			 System.out.println("count0: " + tablaEtiquetas.find(new Document(_ID,e.getTexto())).first());
			 
		 }else {
			 System.out.println("etiquetas no null");
			 Document newDocument = new Document();
			 newDocument.append("$inc", new BasicDBObject().append(ETCOUNT, 1));
							
					tablaEtiquetas.updateOne(new Document().append(_ID, strEt), newDocument);
					
//					System.out.println("count+: " + tablaEtiquetas.find(new Document(_ID,e.getTexto())).first());
			 
		 }
		 
	 }
	 
	 //No borra per se, resta 1 de la tabla tablaEtiquetas y borra si getCount==1
	 private void borrarEtiqueta(String strEt) {
		 
		 if(getCountEtiquetas(strEt) == 1) {
		 tablaEtiquetas.deleteOne(eq(_ID,strEt));
	 }else {
		 tablaEtiquetas.updateOne(eq(_ID,strEt),Updates.inc(ETCOUNT, -1));
	 }
		 
	 }
	 
	 public int getCountEtiquetas(String strEt) {
		 
		 Document retorna = (Document) tablaEtiquetas.find(new Document(_ID,strEt)).first();
		 
		 if(retorna==null) {
			 return 0;
		 }else {
			 return retorna.getInteger(ETCOUNT, 90);
			 
		 }
		 
		 
	 }
	 
	 
	 public ArrayList<ImagenTemp> getArrayComparaciones(ImagenTemp it, int minPorcentaje) {
		 
		 ArrayList<ImagenTemp> arrayRetorna = new ArrayList<ImagenTemp>();
		 //String hashOriginal = it.getPHash();
		 Boolean[] hashOriginal = it.getPHash();
		 
//		 System.out.println();
//		 System.out.println("hash original:  ");
//		 for(int i=0; i<hashOriginal.length; i++) {
//			 System.out.print(hashOriginal[i] + "-");
//		 }
//		 System.out.println();
		 
		 FindIterable<Document> iterable = tablaImagenes.find();
//		 System.out.println("iterable: " + iterable.toString());
		 FindIterable<BasicDBObject> caca = tablaImagenes.find();
		 
		 for(Document doc : iterable) {
			 
			 ArrayList<Boolean> arb = (ArrayList<Boolean>)doc.get(PHASH);
			 Boolean[] hashMeCagoEnLaHostia = new Boolean[arb.size()];
			 for(int i=0; i<arb.size(); i++) {
				 
				 hashMeCagoEnLaHostia[i] = arb.get(i);
				 
			 }
			 
//			 System.out.println("comprado con: " + ((String)doc.get(NOMBRE)));
			 //int compara = ProxyHash.getDistancia((Boolean[])(((ArrayList<Boolean>)doc.get(PHASH)).toArray()), hashOriginal);
//			 int compara = ProxyHash.getDistancia(
//					 (Boolean[])(doc.getList(PHASH,
//					 Boolean.class).toArray()), hashOriginal);
			 //investiga(doc.get(PHASH));
			 float compara = ProxyHash.getDistancia(hashMeCagoEnLaHostia,
					 hashOriginal);
//			 System.out.println("compara: " + compara);
			 if(compara > (((float)minPorcentaje)/((float)100))) {
				 ImagenTemp temp = convierteDocObjeto(doc);
				 temp.setIndiceParecido(compara);
				 arrayRetorna.add(temp);
			 }
		 }
		 
		 return arrayRetorna;
		 
	 }
	 
	 
	 public ArrayList<ImagenTemp> getArrayResultados(ArrayList<Etiquetas> arrayEtiquetas, Long despuesDe, Long antesDe){
		 
		 Bson filtroFecha = null;
		 FindIterable<Document> resultado = null;
		 
		 if(despuesDe!= null || antesDe!=null) {
			 
			 if(despuesDe!= null && antesDe!=null) {
				 filtroFecha = and(gte(FECHA,despuesDe),lte(FECHA,antesDe));
			 }else if(despuesDe!=null) {
				 filtroFecha=gte(FECHA,despuesDe);
			 }else {
				 filtroFecha=lte(FECHA,antesDe);
			 }
		 }
		 
		 
		 if(arrayEtiquetas != null) {
			 
			 ArrayList<String> etiquetasNoCero = new ArrayList<String>();
			 for(Etiquetas e : arrayEtiquetas) {
				 if(getCountEtiquetas(e.getTexto()) != 0) {
					 etiquetasNoCero.add(e.getTexto());
				 }
			 }
			 
			 if(etiquetasNoCero.isEmpty()) {
				 
				 return new ArrayList<ImagenTemp>();
				 
			 }else {
				 
				 if(filtroFecha != null) {
					 
					 resultado = tablaImagenes.find(and(all(ETIQUETAS,etiquetasNoCero),filtroFecha));
					 
				 }else {
					 
					 resultado = tablaImagenes.find(all(ETIQUETAS,etiquetasNoCero));
					 
				 }
			 }
			 
		 }else {
			 
			 if(filtroFecha != null) {
				 
				 resultado = tablaImagenes.find(and(new Document(ETIQUETAS,new ArrayList<String>()),filtroFecha));
				 
			 }else {
				 
				 resultado = tablaImagenes.find(new Document(ETIQUETAS,new ArrayList<String>()));
				 
			 }
			 
		 }
		 
		 MongoCursor<Document> iter = resultado.iterator();
		 
		 ArrayList<ImagenTemp> retorna = new ArrayList<ImagenTemp>();
		 
		 while(iter.hasNext()) {
			 retorna.add(convierteDocObjeto(iter.next()));
		 }
		 
		 return retorna;
		 
	 }
	 
	 public ImagenTemp getResultadoUnico(ObjectId idMongo) {
		 
		 ImagenTemp it = convierteDocObjeto((Document)tablaImagenes.find(eq(_ID,idMongo)).first()); 
		 System.out.println("nombre de resultadounico: " + it.getNombre());
		 
		 return it;
	 }
	 
	 
	 
	 
	 private ImagenTemp convierteDocObjeto(Document d) {
		 
		 String url = (String) d.get(URL);
//		 System.out.println("la url es " + url);
		 String nombreFull = (String) d.get(NOMBRE);
//		 System.out.println("nombrefull: " + nombreFull);
//		 //ArrayList<Etiquetas> etiquetas = (ArrayList<Etiquetas>) d.get(ETIQUETAS);
//		 ArrayList<Etiquetas> etiquetas = new ArrayList<Etiquetas>();
//		 for(String s : (String[]) d.get(ETIQUETAS)) {
//			 etiquetas.add(new Etiquetas(s,getCountEtiquetas(s)));
//		 }
		 //String pHash = (String) d.get(PHASH);
		 
//		 Boolean[] pHash = (Boolean[])d.get(PHASH);
		 
//		 ArrayList<Boolean> arb = (ArrayList<Boolean>)doc.get(PHASH);
//		 Boolean[] hashMeCagoEnLaHostia = new Boolean[arb.size()];
//		 for(int i=0; i<arb.size(); i++) {
//			 
//			 hashMeCagoEnLaHostia[i] = arb.get(i);
//			 
//		 }
		 ArrayList<Boolean> boolTemporal = (ArrayList<Boolean>) d.get(PHASH);
		 Boolean[] pHash = new Boolean[boolTemporal.size()];
		 for(int i=0; i<boolTemporal.size(); i++) pHash[i] = boolTemporal.get(i);
		 
		 
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
