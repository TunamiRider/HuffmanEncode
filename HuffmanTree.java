import java.io.File;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.PriorityQueue;

public class HuffmanTree<HuffData> extends BinaryTree<HuffData>{
	private PriorityQueue<BinaryTree<HuffData>> theQueue;
	private PriorityQueue<HuffData> theQhuff;
	
	public HuffmanTree(File file){
		theQueue = new PriorityQueue<BinaryTree<HuffData>>( new CompareHuffmanTrees() );
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		theQhuff = new PriorityQueue<HuffData>();
		char ch;
        String str;
        HuffData huff;
        BinaryTree<HuffData> left;
        BinaryTree<HuffData> right;
        BinaryTree<HuffData> newTree;
		//making map
		try {
            FileInputStream reader = new FileInputStream(file);
            int i = reader.read();
            
            while(i!=-1) {
            	ch = (char)i;
            	if(ch == 13) {
            		i = reader.read();
            	}
            	ch = (char)i;
                
            	switch(ch) {
            		case '\n':
            			str = "\\n";
            			break;
            		case '\t':
            			str = "\\t";
            			break;
            		default:
            			str = Character.toString(ch);
            	}
            	
            	if( !map.containsKey(str) ) {
            		map.put(str, 1);
            	}else {
            		map.replace(str, map.get(str), map.get(str)+1);
            	}
            	i = reader.read();
            }
            reader.close();
		}catch(Exception e){System.out.println(e);} 
		
        //making big one binaryTree.
        for (Entry<String, Integer> entry : map.entrySet()) {
        	huff = new HuffData( entry.getKey(), entry.getValue() );
        	theQhuff.offer(huff);
        	theQueue.add( new BinaryTree<HuffData>( huff, null, null) );
        }
		while(theQueue.size()>1) {
			left = theQueue.poll();
			right = theQueue.poll();
			newTree = merge(left, right);
			theQueue.offer(newTree);
		}
	}
	public BinaryTree<HuffData> getBinaryTree(){
		return this.theQueue.poll();
	}
	public PriorityQueue<HuffData> getHuffDataMap(){
		return this.theQhuff;
	}
	private class CompareHuffmanTrees implements Comparator<BinaryTree<HuffData>>{
		public int compare(BinaryTree<HuffData> o1, BinaryTree<HuffData> o2) {
				return Integer.compare(o1.getData().occurence, o2.getData().occurence);
			}
	}
	private BinaryTree<HuffData> merge(BinaryTree<HuffData> left, BinaryTree<HuffData> right){
		int sumo = left.root.data.occurence + right.root.data.occurence;
		String all = left.root.data.symbol + right.root.data.symbol;
		return new BinaryTree<HuffData>(new HuffData(all, sumo), left, right);
	}
	private class HuffData implements Comparable<HuffData>{
		int occurence;
		String symbol;
		
		public HuffData(String symbol, int occurence) {
			this.occurence = occurence;
			this.symbol = symbol;
		}
		public String toString() {
			return this.symbol;
		}
		public int hashCode() {
			return this.occurence;
		}
		@Override
		public int compareTo(HuffData o) {
			return o.occurence - this.occurence;
		}	
	}
}//class