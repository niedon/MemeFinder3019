package controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.bson.types.ObjectId;

import vista.Etiquetas;


//Clase que reúne los datos a pasar a la BD mientras se está operando con ellos (mientras se añaden mediante el pograma)
public class ImagenTemp {
	
	private ObjectId idMongo;

	private File imagen;
	private BufferedImage bImagen;
	private ArrayList<Etiquetas> arrayEtiquetas;
	private String nombre;
	private String extension;
	//private String pHash;
	private Boolean[] pHash;
	private long fecha;
	
	private String textoTempDelHash;
	private float indiceParecido;
	private ArrayList<ImagenTemp> arrayComparaciones;
	private long tiempoEnHashear;
	
	
	public ImagenTemp(String url, String nombreYExtension) {
		
		this.imagen = new File(url);
		try {
			this.bImagen = ImageIO.read(imagen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.arrayEtiquetas = new ArrayList<Etiquetas>();
		//this.pHash = "";
		this.pHash = null;
		this.nombre = nombreYExtension.substring(0,nombreYExtension.lastIndexOf('.'));
		this.extension = nombreYExtension.substring(nombreYExtension.lastIndexOf('.'));
		this.fecha = System.currentTimeMillis();
		
		
	}

	public boolean anadirEtiqueta(Etiquetas etiqueta) {
		
		if(arrayEtiquetas.contains(etiqueta)) {
			return false;
		}else {
			arrayEtiquetas.add(etiqueta);
			return true;
		}
		
	}
	
	public boolean borrarEtiqueta(Etiquetas etiqueta) {
		
		if(arrayEtiquetas.contains(etiqueta)) {
			arrayEtiquetas.remove(etiqueta);
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public ObjectId getIdMongo() {
		return idMongo;
	}

	public void setIdMongo(ObjectId idMongo) {
		this.idMongo = idMongo;
	}
	
	public File getImagen() {
		return imagen;
	}
	
	public BufferedImage getbImagen() {
		return bImagen;
	}

	public void setbImagen(BufferedImage bImagen) {
		this.bImagen = bImagen;
	}

	public ArrayList<Etiquetas> getArrayEtiquetas() {
		return arrayEtiquetas;
	}
	
	public Boolean[] getPHash() {
		return pHash;
	}
	
//	public Boolean[] getPHashWrap() {
//		Boolean[] retorna = new Boolean[pHash.length];
//		for(int i=0; i<retorna.length; i++) {
//			retorna[i] = pHash[i];
//		}
//		return retorna;
//	}
	
	public void setPHash(Boolean[] pHash) {
		this.pHash = pHash;
		//textoTempDelHash = "Hashing completito, shurmanus";
//		for(Boolean b : pHash) {
//			System.out.print(b + "-");
//		}
		
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public long getFecha() {
		return fecha;
	}
	
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
	
	public String[] etiquetasAArrayDeString() {
		String[] retorna = new String[arrayEtiquetas.size()];
		for(int i=0; i<retorna.length; i++) {
			retorna[i] = arrayEtiquetas.get(i).getTexto();
		}
		return retorna;
	}
	
	
	
	public String getTextoHashing() {
		return textoTempDelHash;
	}
	
	public float getIndiceParecido() {
		return this.indiceParecido;
	}
	
	public void setIndiceParecido(float indiceParecido) {
		this.indiceParecido = indiceParecido;
	}
	
	public ArrayList<ImagenTemp> getArrayComparaciones() {
		return arrayComparaciones;
	}

	public void setArrayComparaciones(ArrayList<ImagenTemp> arrayComparaciones) {
		this.arrayComparaciones = arrayComparaciones;
	}
	
	public long getTiempoEnHashear() {
		return tiempoEnHashear;
	}

	public void setTiempoEnHashear(long tiempoEnHashear) {
		this.tiempoEnHashear = tiempoEnHashear;
	}
	
	
	


}
