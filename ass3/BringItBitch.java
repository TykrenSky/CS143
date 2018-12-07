import java.util.*;

public class BringItBitch {
   public static void main(String[] args) {
      int[] numbers = new int[100000];
      Random rand = new Random();
      for (int i = 0; i < numbers.length; i++) {
         numbers[i] = rand.nextInt(100000);
      }
      numbers[numbers.length - 1] = 100000;
      Arrays.sort(numbers);
      System.out.println(contains(numbers, 624));
   }
   
   private static boolean contains(int[] toSearch, int val) {
      int maxPoint = toSearch.length;
      int minPoint = 0;
      while (maxPoint - minPoint != 1 && val != toSearch[(maxPoint + minPoint)/2]) {
         if (val > toSearch[(maxPoint + minPoint) / 2]) {
            minPoint = (maxPoint + minPoint) / 2;
         } else {
            maxPoint = (maxPoint + minPoint) / 2;
         }
      }
      return val == toSearch[(maxPoint + minPoint) / 2];
   }
}