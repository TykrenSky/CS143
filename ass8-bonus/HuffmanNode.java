public class HuffmanNode implements Comparable<HuffmanNode> {
   public int character;
   public int frequency;
   public HuffmanNode zero;
   public HuffmanNode one;
   
   public HuffmanNode(int character, int frequency) {
      this(character, frequency, null, null);
   }
   
   public HuffmanNode(HuffmanNode zero, HuffmanNode one) {
      this(0, zero.frequency + one.frequency, zero, one);
   }
   
   public HuffmanNode(int character, int frequency, HuffmanNode zero, HuffmanNode one) {
      this.character = character;
      this.frequency = frequency;
      this.zero = zero;
      this.one = one;
   }
   
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }
}