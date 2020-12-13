import java.nio.charset.StandardCharsets;
public class Main {

    public static void main(String[] args) {

    HashOperations sa=new HashOperations();
        PPKey ppKey=new PPKey();
        FileReadingOperations fileReadingOperations=new FileReadingOperations();
        fileReadingOperations.readpathandhashinside("D:\\bbm465\\ichecker\\dummies","output.txt","MD5");
    }

}
