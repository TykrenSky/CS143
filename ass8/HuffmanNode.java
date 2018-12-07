//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #8
//
//A HuffmanNode stores a character and its frequencies or two other HuffmanNodes and
//their combined frequencies for use with a HuffmanTree. Can be compared to other HuffmanNodes

public class HuffmanNode implements Comparable<HuffmanNode> {
   public int character;
   public int frequency;
   public HuffmanNode zero;
   public HuffmanNode one;
   
   //post: constructs a leaf node with the given character value and frequency value
   public HuffmanNode(int character, int frequency) {
      this(character, frequency, null, null);
   }
   
   //post: constructs a branch node a given zero and one node(left and right) and a 
   //      frequency equal to the sum of the frequencies of the two given nodes
   public HuffmanNode(HuffmanNode zero, HuffmanNode one) {
      this(0, zero.frequency + one.frequency, zero, one);
   }
   
   //post: constructs a HuffmanNode with the specified character and frequency values, and
   //      the given zero and one HuffmanNodes
   public HuffmanNode(int character, int frequency, HuffmanNode zero, HuffmanNode one) {
      this.character = character;
      this.frequency = frequency;
      this.zero = zero;
      this.one = one;
   }
   
   //post: returns an int representing which HuffmanNode has the larger frequency,
   //      returning a negative value if this node is smaller than the other, and a 
   //      positive value if this node is larger. returns zero if equivalent
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }
}