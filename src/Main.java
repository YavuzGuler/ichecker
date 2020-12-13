import java.nio.charset.StandardCharsets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        HashOperations sa=new HashOperations();
        PPKey ppKey=new PPKey();
        byte [] sign=ppKey.encrypt("selamin aleyküm".getBytes(StandardCharsets.UTF_8),ppKey.readCertificate("cert"));
        System.out.println(new String(sign));
        sign=ppKey.decryption(sign,ppKey.readPrivateKey("prikey"));
        System.out.println(new String(sign));
        

    }





}