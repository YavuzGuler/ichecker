public class Main {

    public static void main(String[] args) {

        String prikey = "";
        String keycert = "";
        String regFile = "";
        String logfile = "";
        String hash = "";
        String path = "";

        if(args[0].equals("createCert")){
            if(args[1].equals("-k")){
                prikey = args[2];
                keycert = args[4];
            }
            else{
                keycert = args[2];
                prikey = args[4];
            }
            PPKey ppKey=new PPKey();
            ppKey.createKeyPair(prikey,keycert);
        }
        else if (args[0].equals("createReg")){
            int i=0;

            for(i=1;i<args.length;i+=2){

                switch (args[i]) {
                    case "-r":

                        regFile = args[i+1];
                        break;
                    case "-p":

                        path = args[i+1];
                        break;
                    case "-l":

                        logfile = args[i+1];
                        break;
                    case "-h":

                        hash = args[i+1];
                        if(!hash.equals("MD5")&&!hash.equals("SHA-256"))
                            System.exit(0);
                        break;
                    case "-k":
                        prikey = args[i+1];
                        break;
                }

            }
            FileReadingOperations fileReadingOperations=new FileReadingOperations();
            fileReadingOperations.signFile(regFile,path,logfile,hash,prikey);
        }
        else {
            for (int i=1;i<args.length;i+=2){
                    switch (args[i]) {
                        case "-r":
                            regFile = args[i+1];
                            break;
                        case "-p":
                            path = args[i+1];
                            break;
                        case "-l":
                            logfile = args[i+1];
                            break;
                        case "-h":
                            hash = args[i+1];
                            break;
                        case "-c":
                            keycert = args[i+1];
                            break;
                    }
            }
            FileReadingOperations fileReadingOperations=new FileReadingOperations();
            fileReadingOperations.signVerification(regFile,path,logfile,hash,keycert);
        }
    }

}
