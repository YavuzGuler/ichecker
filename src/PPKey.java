
import sun.security.mscapi.CPublicKey;
import sun.security.tools.keytool.*;

import sun.security.x509.X500Name;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;

import java.util.*;


public class PPKey {
    public static String DEMO_PWD = "changeit";
    public static String DEMO_KEYSTTORE = ".keystore";
    public byte[] encrypt(byte[] plainText,PublicKey publicKey){
        try {
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
    public void createKeyPair(String privateKeyPath,String publicKeyCertificatePath){
       try {
           CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA256WithRSA");
           X500Name x500Name = new X500Name("Yavuz-Aliosman", "IT", "BigSmile Cooperation", "Ankara", "Mamak", "Turkey");
           keypair.generate(2048);
           PrivateKey privKey = keypair.getPrivateKey();
           X509Certificate[] chain = new X509Certificate[1];
           chain[0] = keypair.getSelfCertificate(x500Name, new Date(), (long) 365 * 24 * 60 * 60);
           FileWriter fileWriter=new FileWriter(new File(publicKeyCertificatePath));
           fileWriter.write(certToString(chain[0]));
           fileWriter.close();
           fileWriter=new FileWriter(new File(privateKeyPath));
           AES aes=new AES();
           System.out.println("Password: ");
           Scanner scanner =new Scanner(System.in);

           fileWriter.write(new String(Base64.getEncoder().encode(privKey.getEncoded())));
           fileWriter.close();


       }catch (Exception e){
           e.printStackTrace();
       }


    }
}
