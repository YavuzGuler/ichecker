
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
        //ppKey.createKeyPair("prikey","cert");
        byte [] sign=ppKey.encrypt("selamin aleyküm".getBytes(StandardCharsets.UTF_8),ppKey.readCertificate("cert"));
        System.out.println(new String(sign));
        sign=ppKey.decryption(sign,ppKey.readPrivateKey("prikey"));
        System.out.println(new String(sign));

        //ppKey.createKeyPair("prikey","cert");
       // readpathandhashinside();

    }

    public static void readpathandhashinside(){
        String path = "/Users/aosmankocaman/Developer/ichecker/dummies";
        File[] files = finder(path);
        ArrayList<String> allfiles = new ArrayList<String>();

        for(int i=0;i<files.length;i++){
            String[] array1 = files[i].toString().split("/");
            allfiles.add(array1[array1.length-1]);


        }

        Collections.sort(allfiles);

        for(int i=0;i<allfiles.size();i++){

            String initpath = path+ "/" + allfiles.get(i);
            System.out.println(path+ "/" + allfiles.get(i));


            File myObj = new File(initpath);
            FileInputStream fileInputStream;
            File outputfile = new File("output.txt");


            try {
                fileInputStream = new FileInputStream(myObj);
                byte[] crunchifyValue = new byte[(int) myObj.length()];
                fileInputStream.read(crunchifyValue);
                fileInputStream.close();




                String fileContent = new String(crunchifyValue, "UTF-8");
                HashOperations hasher = new HashOperations();
                byte[] hashedvalues = hasher.MD5(fileContent);

                for(int t=0;t<hashedvalues.length;t++){
                    appendStrToFile(outputfile,hashedvalues[t]+" ");
                    System.out.print(hashedvalues[t]+" ");
                }
                appendStrToFile(outputfile,"\n");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }

    }


    public static void appendStrToFile(File file,
                                       String str)
    {
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(file, true));
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    public static File[] finder(String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".txt"); }
        } );

    }






}
