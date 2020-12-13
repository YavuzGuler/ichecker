import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
public class HashOperations {
    public StringBuilder byteArrayToHexDecimal(byte [] bytes){
        StringBuilder sb=new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb;
    }
    public byte[] MD5(String text){
        byte[] output=null;
        try {
            MessageDigest md5=null;
            md5=MessageDigest.getInstance("MD5");
            md5.update(text.getBytes(StandardCharsets.UTF_8));
            output=md5.digest();
            return output;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] MD5(byte[] in){
        byte[] output=null;
        try {
            MessageDigest md5=null;
            md5=MessageDigest.getInstance("MD5");
            md5.update(in);
            output=md5.digest();
            return output;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] SHA256(String text){
        byte[] hash=null;
        try {
            MessageDigest sha256;
            sha256=MessageDigest.getInstance("SHA-256");
            sha256.update(text.getBytes(StandardCharsets.UTF_8));
            return sha256.digest();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] SHA256(byte[] in){
        byte[] hash=null;
        try {
            MessageDigest sha256;
            sha256=MessageDigest.getInstance("SHA-256");
            sha256.update(in);
            return sha256.digest();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
