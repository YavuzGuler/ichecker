
import sun.security.tools.keytool.*;

import sun.security.x509.X500Name;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.net.ssl.X509KeyManager;
import javax.xml.bind.DatatypeConverter;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;


public class PPKey {
    private AES aes;

    public PPKey(){
         this.aes=new AES();
    }

    public byte[] encrypt(byte[] plainText,PublicKey publicKey){
        try {
            if(publicKey==null){
                System.out.println("Password wrong");
                System.exit(0);
            }
            Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            return cipher.doFinal(plainText);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] decryption(byte[] cipherText,PrivateKey privateKey){
        try {
            if(privateKey==null){
                System.out.println("Password wrong");
                System.exit(0);
            }
            Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(cipherText);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] sign(byte[] bytes,PrivateKey privateKey){
        try {
            if(privateKey==null){
                System.out.println("Password wrong");
                System.exit(0);
            }
            Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private String certToString(X509Certificate certificate){
        StringWriter stringWriter= new StringWriter();
        try {
            stringWriter.write("-----BEGIN CERTIFICATE-----\n");
            stringWriter.write(DatatypeConverter.printBase64Binary(certificate.getEncoded()).replaceAll("(.{64})", "$1\n"));
            stringWriter.write("\n-----END CERTIFICATE-----\n");
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
    private void writePrivateKey(String privateKeyPath,PrivateKey privateKey){
        try {
            FileWriter fileWriter=new FileWriter(new File(privateKeyPath));

            System.out.print("Password: ");
            Scanner scanner =new Scanner(System.in);
            String password=scanner.nextLine();
            byte [] aesEncryption=(new String(Base64.getEncoder().encode(privateKey.getEncoded()))+" Only-dead-people-can-see!").getBytes(StandardCharsets.UTF_8);
            aesEncryption=this.aes.encryption(password,aesEncryption);
            fileWriter.write(new String(Base64.getEncoder().encode(aesEncryption)));
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public PrivateKey readPrivateKey(String privateKeyPath){
        try {
            System.out.print("Password: ");
            Scanner scanner =new Scanner(System.in);
            String password=scanner.nextLine();
            scanner=new Scanner(new File(privateKeyPath));

            byte[] aesDecryptionBytes=this.aes.decryption(password,Base64.getDecoder().decode(scanner.nextLine().getBytes(StandardCharsets.UTF_8)));
            if(aesDecryptionBytes==null){
                return null;
            }
            String aesDecryption=new String(aesDecryptionBytes);


            if(aesDecryption.split(" ")[1].equals("Only-dead-people-can-see!")){
                KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                PrivateKey key= keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(aesDecryption.split(" ")[0])));

                return key;
            }
            else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public PublicKey readCertificate(String certificatePath){
        try {
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            FileInputStream is = new FileInputStream (certificatePath);
            X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
            return cer.getPublicKey();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private void writeCertificate(String certificatePath, X509Certificate certificate){
        try {
            FileWriter fileWriter=new FileWriter(new File(certificatePath));
            fileWriter.write(certToString(certificate));
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void createKeyPair(String privateKeyPath,String publicKeyCertificatePath){
       try {
           CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA256WithRSA");
           X500Name x500Name = new X500Name("Yavuz-Aliosman", "IT", "BigSmile Cooperation", "Ankara", "Mamak", "Turkey");
           keypair.generate(2048);

           PrivateKey privateKey = keypair.getPrivateKey();
           X509Certificate[] chain = new X509Certificate[1];

           chain[0] = keypair.getSelfCertificate(x500Name, new Date(), (long) 365 * 24 * 60 * 60);
           writeCertificate(publicKeyCertificatePath,chain[0]);
           writePrivateKey(privateKeyPath,privateKey);
       }catch (Exception e){
           e.printStackTrace();
       }


    }
}
