package controlador;

import java.io.File;
import java.io.FileInputStream;

public class ProxyHash {
	
	private static ImagePHash iph = new ImagePHash(32,16);//OJO al cambiar esto, podría explotar todo en ImagePHash (punto 6 getHash())
	
	public static Boolean[] getHash(File fi) {
		try {
			Boolean[] retorna = iph.getHash(new FileInputStream(fi));
			System.out.println("hash que sale del gethash: " + retorna);
			return retorna;
		} catch (Exception e) {
			System.out.println("hay excepción en proxyhash");
			e.printStackTrace();
			return null;
		}
	}
	
	public static float getDistancia(Boolean[] hash1, Boolean[] hash2) {
		return iph.distanceFraction(hash1, hash2);
	}

}
