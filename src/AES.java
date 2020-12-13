import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AES {


    public byte[] encryption(String Key, byte[] plainText){
    try {
        HashOperations hashOperations=new HashOperations();
        Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey=new SecretKeySpec(hashOperations.MD5(Key),"AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return cipher.doFinal(plainText);
    }catch (Exception e){
       return null;
    }
}

public byte[] decryption(String Key,byte[] cipherText){
    try {
        HashOperations hashOperations=new HashOperations();
        Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey=new SecretKeySpec(hashOperations.MD5(Key),"AES");
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        return cipher.doFinal(cipherText);
    }catch (Exception e){
        return null;
    }
}
}
