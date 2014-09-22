package tools.zipDecrypt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
	public static void main(String[] args) throws IOException {
		// password-protected zip file I need to read
		InputStream in = Main.class.getResourceAsStream("test.zip");
		// wrap it in the decrypt stream
		ZipDecryptInputStream zdis = new ZipDecryptInputStream(in, "111111");
		// wrap the decrypt stream by the ZIP input stream
		ZipInputStream zis = new ZipInputStream(zdis);
		// read all the zip entries and save them as files
		ZipEntry ze;
		while ((ze = zis.getNextEntry()) != null) {
			FileOutputStream fos = new FileOutputStream(ze.getName());
			int b;
			while ((b = zis.read()) != -1) {
				fos.write(b);
			}
			fos.close();
			zis.closeEntry();
		}
		zis.close();
	}
}
