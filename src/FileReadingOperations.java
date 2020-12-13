import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FileReadingOperations {
    public byte[] readAllBytesFromFile(String filePath){
        try {
            return Files.readAllBytes(Paths.get(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
return null;
    }

    public  void readpathandhashinside(String path, String outfilename,String hash){
        //reads all filenames on specified path
        ArrayList<File> allfiles = new ArrayList<File>(Arrays.asList(finder(path)));

        //sorts filename array
        Collections.sort(allfiles);
        File outputfile=null;
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(new File(outfilename), false));
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0;i<allfiles.size();i++){
             outputfile = new File(outfilename);
            //reads specific file and hashes, writes hash values to output file
            byte[] crunchifyValue =readAllBytesFromFile(allfiles.get(i).getAbsolutePath());
            HashOperations hasher = new HashOperations();
            byte[] hashedvalues;
            hashedvalues=(hash.equals("MD5"))? hasher.MD5(crunchifyValue):hasher.SHA256(crunchifyValue);
            appendStrToFile(outputfile,allfiles.get(i).getAbsolutePath()+" ");
            appendStrToFile(outputfile,new String(hasher.byteArrayToHexDecimal(hashedvalues)));
            appendStrToFile(outputfile,"\n");
        }
    }

    //function for appending new lines to output file.
    public  void appendStrToFile(File file,String str)
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

    //finder function for txt files in given directory.
    public  File[] finder(String dirName){
        File dir = new File(dirName);
        return dir.listFiles();

    }


}
