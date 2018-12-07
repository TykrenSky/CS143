//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #8
//
//A HuffmanTree constructs and maintains a Huffman tree to encode and decode text files
//by using the frequency of all the characters to represent frequent characters with fewer 
//bits than less frequent characters. This object can make a code file that tells how
//certain charecters should be encoded, and decode an previously encoded file into its 
//original text

import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallRoot;
   
   //pre: The index should represent the character value, and the value at
   //     the index should be the frequency
   //post: Constructs a new HuffmanTree using a given int array that contains the 
   //      frequency of the characters in the document the HuffmanTree will be used 
   //      to encode. 
   public HuffmanTree(int[] counts) {
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
   public HuffmanTree(Scanner input) {
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