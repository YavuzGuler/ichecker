
public class Main {

    public static void main(String[] args) {
    HashOperations sa=new HashOperations();
       System.out.println( sa.byteArrayToHexDecimal(sa.SHA256("sas")));
        PPKey ppKey=new PPKey();
        ppKey.createKeyPair("prikey","cert");
    }
}
