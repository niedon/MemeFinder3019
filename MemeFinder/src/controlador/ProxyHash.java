package controlador;

import java.io.File;
import java.io.FileInputStream;

public class ProxyHash {
	
	private static ImagePHash iph = new ImagePHash(32,16);
	
	public static String getHash(File fi) {
		try {
			return iph.getHash(new FileInputStream(fi));
		} catch (Exception e) {
			return "";
		}
	}
	
	public static int getDistancia(String hash1, String hash2) {
		return iph.distance(hash1, hash2);
	}

}
