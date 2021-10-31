
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.commons.io.FilenameUtils;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Arrays;


public class PDFname {
    String fileName = "";
    public static void main(String[] args) {
        System.out.println("Enter the path of desired location: ");
        Scanner scan = new Scanner(System.in);
        String path = scan.next();
        //Creating a File object for directory
        File directoryPath = new File(path);
        //List of all files and directories
        String contents[] = directoryPath.list();
        System.out.println("Total Files: " + contents.length );
        for(int i=0; i<contents.length; i++) {
            // System.out.println(contents[i]);
            String ext1 = FilenameUtils.getExtension(contents[i]);
            if (ext1.equals("pdf")){
                try {
                    PDFrenamer(path, contents[i]);
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void PDFrenamer(String path, String file) throws IOException{
        
        String fileName ="";
        
        File oldFile = new File(path + file);
        System.out.print("Replaced Names: " + file + "   ---------->   ");
        PDDocument doc = PDDocument.load(oldFile);

        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(doc);
        // System.out.print(text);
        String[] lines = text.split("\\r\\n");
         
        if (lines.length >= 5){

            String fileName1 = lines[1];
            String fileName2 = lines[3].substring(7);
            fileName = fileName1 + " (" + fileName2 + ")" +".pdf";
            
          
        }else if (lines.length == 3) {
            fileName = lines[0] + " (Barcode).pdf";
        }else if (lines.length == 0){
            fileName = "empty.pdf";
        }else{
            fileName = lines[0] + ".pdf";
        }
        

        doc.close();

        System.out.println(" " + fileName);

        File newFile = new File(path+ fileName);
        // System.out.println(path+fileName);
        oldFile.renameTo(newFile);
        // System.out.println(oldFile.renameTo(newFile));
        
    }
}