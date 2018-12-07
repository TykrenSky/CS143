//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #8 Bonus
//
//A HuffmanTree2 constructs and maintains a Huffman tree to encode and decode text files
//by using the frequency of all the characters to represent frequent characters with fewer 
//bits than less frequent characters. This version does not use a code file, instead able to 
//encode its code tree into the file to be encoded

import java.util.*;
import java.io.*;

public class HuffmanTree2 {
   private HuffmanNode overallRoot;
   
   //pre: BitStreamInput should contain a properly formatted HuffmanTree2
   //post: Creates a new HuffmanTree2 that represents the tree in the input
   public HuffmanTree2(BitInputStream input) {
      overallRoot = process(input);
   }
   
   //recursive method for rebuilding the tree
   private HuffmanNode process(BitInputStream input) {
      boolean isBranch = input.readBit() == 0;
      if (isBranch) {
         HuffmanNode zero = process(input);
         HuffmanNode one = process(input);
         return new HuffmanNode(zero, one);
      } else {
         int character = read9(input);
         return new HuffmanNode(character, 0);
      }
   }
   
   //pre: the given array should contain all null
   //post: assigns the code for every character in this tree to an index that is 
   //      equal to its character value
   public void assign(String[] codes) {
      traverse(codes, overallRoot, "");
   }
   
   //recursive method for assigning codes
   private void traverse(String[] codes, HuffmanNode root, String path) {
      if (root.zero != null) {
         traverse(codes, root.zero, path + "0");
         traverse(codes, root.one, path + "1");
      } else {
         codes[root.character] = path;
      }
   }
   
   //post: Writes the tree in standard format to the given BitOutputStream for later
   //      reconstruction with the BitInputStream constructor
   public void writeHeader(BitOutputStream output) {
      writePre(output, overallRoot);
   }
   
   //recursive for printing the tree
   private void writePre(BitOutputStream output, HuffmanNode root) {
      if (root.zero != null) {
         output.writeBit(0);
         writePre(output, root.zero);
         writePre(output, root.one);
      } else {
         output.writeBit(1);
         write9(output, root.character);
      }
   }

    // pre : an integer n has been encoded using write9 or its equivalent
    // post: reads 9 bits to reconstruct the original integer
    private int read9(BitInputStream input) {
        int multiplier = 1;
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += multiplier * input.readBit();
            multiplier = multiplier * 2;
        }
        return sum;
    }

    // pre : 0 <= n < 512
    // post: writes a 9-bit representation of n to the given output stream
    private void write9(BitOutputStream output, int n) {
        for (int i = 0; i < 9; i++) {
            output.writeBit(n % 2);
            n = n / 2;
        }
    }
    
    //pre: The index should represent the character value, and the value at
   //     the index should be the frequency
   //post: Constructs a new HuffmanTree using a given int array that contains the 
   //      frequency of the characters in the document the HuffmanTree will be used 
   //      to encode. 
   public HuffmanTree2(int[] counts) {
      Queue<HuffmanNode> builderQueue = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < counts.length; i++) {
         if (counts[i] > 0) {
            builderQueue.add(new HuffmanNode(i, counts[i]));
         }
      }
      builderQueue.add(new HuffmanNode(counts.length, 1));
      while (builderQueue.size() > 1) {
         HuffmanNode zero = builderQueue.remove();
         HuffmanNode one = builderQueue.remove();
         builderQueue.add(new HuffmanNode(zero, one));
      }
      overallRoot = builderQueue.remove();
   }
   
   //pre: The given Scanner should represent a code file that contains a HuffmanTree
   //     in standard format
   //post: constructs a HuffmanTree using the given Scanner as a code file for
   //      the tree
   public HuffmanTree2(Scanner input) {
      overallRoot = new HuffmanNode(0, 0, null, null);
      while (input.hasNextLine()) {
         int character = Integer.parseInt(input.nextLine());
         String path = input.nextLine();
         HuffmanNode current = overallRoot;
         while (!path.equals("")) {
            if (path.charAt(0) == '0') {
               if (current.zero == null) {
                  current.zero = new HuffmanNode(0, 0, null, null);
               }
               current = current.zero;
            } else {
               if (current.one == null) {
                  current.one = new HuffmanNode(0, 0, null, null);
               }
               current = current.one;
            }
            path = path.substring(1);
         }
         current.character = character;
      }
   }
   
   //post: Writes the current HuffmanTree to a given PrintStream in the form
   //      of a standard code file, composed of a character value followed by
   //      its code in bits
   public void write(PrintStream output) {
      write(output, overallRoot, "");
   }
   
   //recursive method for writing a code file
   private void write(PrintStream output, HuffmanNode root, String path) {
      if (root.zero != null) {
         write(output, root.zero, path + "0");
         write(output, root.one, path + "1");
      } else {
         output.println(root.character);
         output.println(path);
      }
   }
   
   //pre: the BitInputStream should contain a legal encoding of characters for this
   //     tree's code
   //post: reads bits from the given input, and uses this tree's codes to reconstruct the
   //      original file that the BitInputStream represents to the given PrintStream, 
   //      terminating when it reaches the specified end of file character value (eof)
   public void decode(BitInputStream input, PrintStream output, int eof) {
      boolean done = false;
      HuffmanNode current = overallRoot;
      while (!done) {
         if (current.zero == null) {
            if (current.character != eof) {
               output.write(current.character);
               current = overallRoot;
            } else {
               done = true;
            }
         } else {
            int bit = input.readBit();
            if (bit == 0) {
               current = current.zero;
            } else {
               current = current.one;
            }
         }
      }
   }
}
