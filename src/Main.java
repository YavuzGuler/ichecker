import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
    HashOperations sa=new HashOperations();
        PPKey ppKey=new PPKey();
        //ppKey.createKeyPair("prikey","cert");
        byte [] sign=ppKey.encrypt("selamin aleyküm".getBytes(StandardCharsets.UTF_8),ppKey.readCertificate("cert"));
        System.out.println(new String(sign));
        sign=ppKey.decryption(sign,ppKey.readPrivateKey("prikey"));
        System.out.println(new String(sign));
    }
}
