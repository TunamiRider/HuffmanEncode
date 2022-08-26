import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * This class implements CodeDictionary, and is used to look up the code of a character, or look up 
 * the corresponding caracter of a code. It also gets the result of compression, including the frequencies and codes.
 * @authors Yuki Suzuki, Leo Molitor
 * @version 7.2.18
 */
public class HuffmanDictionary<HuffData> implements CodeDictionary{
	//binary tree representing frequencies of all characters
	private BinaryTree<HuffData> binaryTree;
	//Queue holding instances of HuffData
	private PriorityQueue<HuffData> theDataList;
	
	/**
	 * This is the constructor for the HuffmanDictionary class. It instantiates binaryTree and theDataList.
	 * @param inFile The file wished to be compressed
 	 */
	public HuffmanDictionary(String inFile){
			HuffmanTree<HuffData> tree;
            tree = new HuffmanTree<HuffData>(new File(inFile));
            binaryTree = (BinaryTree<HuffData>) tree.getBinaryTree();
            theDataList = (PriorityQueue<HuffData>) tree.getHuffDataMap();
	}
	/**
	 * This finds the code that corresponds with a given character
	 * @param key The character whose code is to be returned
	 * @returns code Code of the desired character
	 */
	@Override
	public String lookup(String key) {
		//this adjusts with huffman implementation for new line.
		if(key.equals("\\n")) {
			key = "\n";
		}
		try {
			return lookup(key, binaryTree);
		}catch(Exception ex) {throw new IllegalArgumentException("Targeted key is not found");}
	}
	private String lookup(String key, BinaryTree<HuffData> binaryTree) {
		String code = "";
		if( binaryTree.isLeaf() ){
			return code;
		}
		if( binaryTree.getRightSubtree()!=null  && binaryTree.getRightSubtree().getData().toString().indexOf(key)!=-1 ) {
			return code += '1' + lookup(key, binaryTree.getRightSubtree());
		}
		if(binaryTree.getLeftSubtree()!=null && binaryTree.getLeftSubtree().getData().toString().indexOf(key)!=-1 ) {
			return code += '0' + lookup(key, binaryTree.getLeftSubtree());
		}
		return code;
	}
	/**
	 * This finds the character that corresponds with a given code
	 * @param value represents the path of binary tree.
	 * @returns character key
	 */
	@Override
	public String reverseLookup(String code) {
		try {
			return reverseLookup(code, binaryTree);
		}catch(Exception ex) {throw new IllegalArgumentException("Targeted key is not found");}
	}
	private String reverseLookup(String code, BinaryTree<HuffData> binaryTree) {
		String character = "";
		if(code.length()==0 && binaryTree.isLeaf()) {
			return binaryTree.getData().toString();
		}
		if(code.charAt(0)=='0' && binaryTree.getLeftSubtree()!=null ) {
			return reverseLookup(code.substring(1), binaryTree.getLeftSubtree());
		}
		if(code.charAt(0)=='1' && binaryTree.getRightSubtree()!=null ) {
			return reverseLookup(code.substring(1), binaryTree.getRightSubtree());
		}
		return character;
	}
	/**
	 * This prints compression info of the file. (Characters, codes frequencies)
	 * @returns An empty string. The printing of the compression info has already been done.
	 */
	public String toString() {
		HuffData huff;
		String ch;
		String freq;
		int size = binaryTree.getData().hashCode();
		System.out.println("Term	Freq		Code\n");
		
		while(!theDataList.isEmpty()) {
			huff = theDataList.poll();
			freq = String.valueOf( (float)huff.hashCode()/(float)size );
			
			ch = huff.toString();
			if( ch.equals("\n") ) {
				ch = "\\n";
			}
			System.out.print( ch + "	" + freq + "	" + this.lookup(huff.toString()) +"\n");
		}
		return "";
	}
	
}//class
