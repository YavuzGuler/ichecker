import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class FileReadingOperations {

    public void readpathandhashinside(String path, String outfilename){

        File[] files = finder(path);
        ArrayList<String> allfiles = new ArrayList<String>();


        //reads all filenames on specified path
        for(int i=0;i<files.length;i++){
            String[] array1 = files[i].toString().split("/");
            allfiles.add(array1[array1.length-1]);
        }

        //sorts filename array
        Collections.sort(allfiles);



        for(int i=0;i<allfiles.size();i++){

            //specifies full path to file
            String initpath = path+ "/" + allfiles.get(i);


            File myObj = new File(initpath);
            FileInputStream fileInputStream;
            File outputfile = new File(outfilename);

            //reads specific file and hashes, writes hash values to output file
            try {
                fileInputStream = new FileInputStream(myObj);
                byte[] crunchifyValue = new byte[(int) myObj.length()];
                fileInputStream.read(crunchifyValue);
                fileInputStream.close();

                String fileContent = new String(crunchifyValue, "UTF-8");
                HashOperations hasher = new HashOperations();
                byte[] hashedvalues = hasher.MD5(fileContent);

                for(int t=0;t<hashedvalues.length;t++){
                    //appends MD5 values to output file.
                    appendStrToFile(outputfile,hashedvalues[t]+" ");

                }
                //creates new line at output file
                appendStrToFile(outputfile,"\n");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }

    }

    //function for appending new lines to output file.
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

    //finder function for txt files in given directory.
    public static File[] finder(String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".txt"); }
        } );

    }


}
