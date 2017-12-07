package fys.luggagem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import org.apache.pdfbox.util.Hex;

/**
 *
 * @author jordan
 */
public class Encryptor {

    public static String[] encrypt(String input) {
        String salt = null;
        String[] output = new String[2];
        if (null == input) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[32];
            random.nextBytes(bytes);
            StringBuilder saltbuilder = new StringBuilder();
            for (byte b : bytes) {
                saltbuilder.append(String.format("%02X", b));
            }
            salt = saltbuilder.toString();
            input += salt;
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b));
            }
            output[0] = sb.toString();
            output[1] = salt;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public static String encrypt(String input, String salt) {
        String SHA256 = null;
        if (null == input) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            input += salt;
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b));
            }
            SHA256 = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SHA256;
    }
}
