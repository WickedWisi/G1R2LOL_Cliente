package cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class AsimetricC {

   //private static final String OUTPUT_PATH = "C:\\Cifrado\\UserCredentialC.properties";
    private static final String PUBLIC_KEY_PATH = "C:\\Cifrado\\publicKey.der";

    public PublicKey loadPublicKey() {
        // Load Public Key from file
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public void keyGenerator() {
        Chiper.GenerarClaves gc = new Chiper.GenerarClaves();
        gc.keyGenerator("C:\\Cifrado");
    }*/
    public byte[] encryptAndSaveData(String message, PublicKey publicKey) {
        byte[] encryptedData = null;
        try {
            String folderPath = "C:\\Cifrado";
            File folder = new File(folderPath);

            if (!folder.exists()) {
                if (folder.mkdir()) {
                    System.out.println("Carpeta creada exitosamente en C:");
                } else {
                    System.err.println("Error al crear la carpeta");

                    //return;
                }
            } else {
                System.err.println("La carpeta ya existe");
            }
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = cipher.doFinal(message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    private byte[] fileReader(String path) {
        byte[] ret = null;
        try {
            ret = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
