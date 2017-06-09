package copyAndMove;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class copyAndMove {

    public static byte[] buf = new byte[100];
    public static byte[] data = null;
    public static int dataIdx = 0;
    
    public static void write(byte[] bs, String outPath, String fileIn, String opType) {
    	try {
            RandomAccessFile f = new RandomAccessFile(outPath, "rw");
            f.write(bs);
            f.close();
            
            //delete old file
            if(opType.equals("mv")){
            	System.out.println("Here");
            	Path path = Paths.get(fileIn);
            	Files.delete(path);
            }
        } catch (IOException ex) {
        	System.out.println(ex);
        }
    }
    public static void read(String filePath) throws IOException {
    	RandomAccessFile f = new RandomAccessFile(filePath, "r");
        data = new byte[(int) f.length()];
        while (true) {
            int nBytes = f.read(buf);
            if (nBytes == -1) {// read returns -1 when end of file
            	break;
            }
            for (int i = 0; i < nBytes; i++) {
                data[dataIdx] = buf[i];
                dataIdx++;
            }
        }
        f.close();
        Charset cs = Charset.forName("UTF-8");
        String s = new String(data, cs);
        System.out.println(s);
    }
    
    public static void makeDir(String outputFile) throws IOException{
    	Path path = Paths.get(outputFile);
    	Files.createDirectories(path);
    }
    
    public static void main(String[] args) {
        String fileIn = null;
        String fileOut = null;
        String opType = null;
        
        if (args.length == 3) {
            opType = args[0];
        	fileIn = args[1];
            fileOut = args[2];
        }
      
        else{ System.out.println("Please specify two parameters, Inputfile and OutputFile");}
            
        try {
        	String directories = fileOut.substring(0, fileOut.lastIndexOf("/"));
      
        	copyAndMove.read(fileIn);
        	makeDir(directories);	
        	
            copyAndMove.write(data, fileOut, fileIn, opType);
            System.out.println(opType);
            
        } catch (FileNotFoundException ex) {
            System.out.println("File was not found, please verify " + fileIn + " exists.");
        } catch (IOException ex) {
                System.out.println(ex);
        }
    }
}