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
        FileReadingOperations fileReadingOperations=new FileReadingOperations();
        //fileReadingOperations.readpathandhashinside("D:\\bbm465\\ichecker\\dummies","output.txt","MD5");

        String prikey = "";
        String keycert = "";
        String regFile = "";
        String logfile = "";
        String hash = "";
        String path = "";

        System.out.println("Argument count: " + args.length);
        if(args[0].equals("createCert")){
            System.out.println("BASARAMADIK ABI");
            if(args[1].equals("-k")){
                prikey = args[2];
                keycert = args[4];
            }
            else{
                keycert = args[2];
                prikey = args[4];
            }
        }
        else if (args[0].equals("createReg")){
            int i=0;
            while (i <= args.length){
                i++;
                if(args[i].equals("-r")){
                    i++;
                    regFile = args[i];
                }
                else if(args[i].equals("-p")){
                    i++;
                    path = args[i];
                }
                else if(args[i].equals("-l")){
                    i++;
                    logfile = args[i];
                }
                else if(args[i].equals("-h")){
                    i++;
                    hash = args[i];
                }
                else if(args[i].equals("-k")){
                    prikey = args[i];
                }

            }

            //createReg fonksiyonlari
        }
        else {
            int i = 0;
            while (i < args.length) {
                i++;
                try {
                    if (args[i].equals("-r")) {
                        i++;
                        regFile = args[i];
                    } else if (args[i].equals("-p")) {
                        i++;
                        path = args[i];
                    } else if (args[i].equals("-l")) {
                        i++;
                        logfile = args[i];
                    } else if (args[i].equals("-h")) {
                        i++;
                        hash = args[i];
                    } else if (args[i].equals("-c")) {
                        i++;
                        keycert = args[i];
                    }
                }
                catch (Exception e){
                    break;
                }


            }
            System.out.println("check while out");
            //check fonksiyonlari

        }
    }

}
