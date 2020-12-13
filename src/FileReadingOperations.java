import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileReadingOperations {

    private HashOperations hasher;
    public FileReadingOperations(){
         this.hasher= new HashOperations();
    }
    public String timeReturner(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public byte[] readAllBytesFromFile(String filePath){
        try {
            return Files.readAllBytes(Paths.get(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
return null;
    }
    private ArrayList<String> getSignature(String regFile){
        try {
           Scanner scanner=new Scanner(new File(regFile));
           ArrayList<String> list=new ArrayList<String>();
           String line=null;
           while (scanner.hasNextLine()){

               line=scanner.nextLine();
               list.add(line);
           }
           scanner.close();
            return list;
        }catch (Exception e){
        e.printStackTrace();
            }
return null;
    }
    public void signFile(String regFile,String path,String logFile,String hash,String privateKeyFile){
        PPKey ppKey=new PPKey();
        PrivateKey privateKey=ppKey.readPrivateKey(privateKeyFile);
        if(privateKey==null){
            appendStrToFile(new File(logFile),timeReturner()+": Wrong password attempt!",1);
            System.exit(0);
        }
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(new File(regFile), false));
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
       String pathFiles= readpathandhashinside(path,regFile,hash,logFile,1);
        byte[] hashedvalues;
        hashedvalues=(hash.equals("MD5"))? this.hasher.MD5(pathFiles.getBytes(StandardCharsets.UTF_8)):this.hasher.SHA256(pathFiles.getBytes(StandardCharsets.UTF_8));
        hashedvalues=ppKey.sign(hashedvalues,privateKey);
        appendStrToFile(new File(regFile),pathFiles,1);
        appendStrToFile(new File(regFile),new String(Base64.getEncoder().encode(hashedvalues)),0);

    }
    public void signVerification(String regFile,String path, String logFile, String hash,String publicKeyCertificate){
        PPKey ppKey=new PPKey();
        PublicKey publicKey=ppKey.readCertificate(publicKeyCertificate);
        String pathFiles=readpathandhashinside(path,regFile,hash,logFile,0);
        byte[] hashedvalues;
        hashedvalues=(hash.equals("MD5"))? this.hasher.MD5(pathFiles.getBytes(StandardCharsets.UTF_8)):this.hasher.SHA256(pathFiles.getBytes(StandardCharsets.UTF_8));
        ArrayList<String> regFileList=getSignature(regFile);
        if(regFileList==null)
            System.exit(-1);
        if(!Arrays.equals(hashedvalues, ppKey.signVerification(Base64.getDecoder().decode(regFileList.get(regFileList.size()-1).getBytes(StandardCharsets.UTF_8)), publicKey))){
            appendStrToFile(new File(logFile),timeReturner()+": Registry file verification failed!",1);
            System.exit(0);
        }
        if (checkFiles(regFile, path, logFile, hash,regFileList) == 0) {
            appendStrToFile(new File(logFile), timeReturner() + ": The directory is checked and no change is detected!", 1);
        }
    }
    public int checkFiles(String regFile,String path,String logFile,String hash,ArrayList<String> regFileList){
        ArrayList<String> pathList = new ArrayList<String>(Arrays.asList(readpathandhashinside(path, regFile, hash, logFile, 0).split("\n")));
        
        return 0;
    }

    public  String readpathandhashinside(String path, String regFile,String hash,String logFile,int sign){
        //reads all filenames on specified path
        ArrayList<File> allfiles = new ArrayList<File>(Arrays.asList(finder(path)));

        //sorts filename array
        Collections.sort(allfiles);
        appendStrToFile(new File(logFile),timeReturner()+": Registry file is created at "+ logFile+"!",1);

        StringBuilder returnValue=new StringBuilder();
        for(int i=0;i<allfiles.size();i++){

            //reads specific file and hashes, writes hash values to output file
            byte[] crunchifyValue =readAllBytesFromFile(allfiles.get(i).getAbsolutePath());
            byte[] hashedvalues;
            hashedvalues=(hash.equals("MD5"))? this.hasher.MD5(crunchifyValue):this.hasher.SHA256(crunchifyValue);
            returnValue.append(allfiles.get(i).getAbsolutePath()).append(" ").append(new String(this.hasher.byteArrayToHexDecimal(hashedvalues))).append((i != allfiles.size() - 1) ? "\n" : "");
            if (sign == 1) {
                appendStrToFile(new File(logFile), timeReturner() + ": " + allfiles.get(i).getAbsolutePath() + " is added to registry.",1);
            }
        }
        if(sign==1){
        appendStrToFile(new File(logFile),timeReturner()+": "+Integer.toString(allfiles.size())+" files are added to the registry and registry" +
                " creation is finished! ",1);
        }

        return returnValue.toString();
    }

    //function for appending new lines to output file.
    public  void appendStrToFile(File file,String str,int newLine)
    {
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(file, true));

            out.write(str+((newLine==1)?"\n":""));
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
