import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 * This class has the main method that is used to initiate the compression and decompression of the desired file.
 * 
 * @authors Yuki Suzuki, Leo Molitor
 * @version 7.2.18
 */
public class Compressor {
	
	/**
	 * As Compressor's constructor, it creates a new instance of Compressor.
	 * @throws FileNotFoundException if the the user selects a file that deosn't exist.
	 * @param inFile The desired file to be compressed
	 * @param outFile The output file name after being decompressed
 	 */
	public Compressor(File inFile, String outFile) throws FileNotFoundException {
		//getting absolute path of the file to be compressed.
		String fileName = inFile.getAbsolutePath();
		String head = (String)inFile.getName().subSequence(0, inFile.getName().indexOf('_'));
		//calling huffman
		Huffman huffman = new Huffman();
		huffman.buildDictionary( fileName );
		
		//getting file names for both compressed's and decompressed's
		String out = inFile.getParent() + inFile.separator + outFile;
		String orig = inFile.getParent() + inFile.separator + head + "_inflated.txt";
		
		//making both compressed's and decompressed's in the same directory of passed File.
		huffman.encode( fileName, out );
		huffman.decode( out, orig );
		
		//showing result (table of binary representation of each character, and compression ratio.)
		huffman.printDictionary();
		System.out.println( "Compressing data/"+inFile.getName() +" into data/"+ head +".huff" );
		System.out.println( "Complete (compression ratio: " + huffman.getCompressionRatio() +")\n" );
		System.out.println( "Inflating "+ head +".huff" +" into " + head + "_inflated.txt" );
		System.out.println( "Complete." );
	}//constructor
	
	/**
	 * @param args Arguments that main will use when running
	 * @throws FileNotFoundException if the the user selects a file that doesn't exist
 	 */
	public static void main(String[] args) throws FileNotFoundException {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(" Select the file you wish to be compressed ");
        try {   
            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
                throw new Error("Input file not selected");
            
            //gets desired input and ouput file
            File file = chooser.getSelectedFile();
            String path = JOptionPane.showInputDialog("Enter the name you wish for outputted compressed file");
            
            //calls compressor
    		Compressor Compressor = new Compressor(file, path);
    		
        } catch (FileNotFoundException e) {
            System.err.println("Data file not found.");
        } catch (Exception e) {
            System.err.println("A mysterious error occurred.");
            e.printStackTrace(System.err);
        }
	}//main
	
	
}//class
