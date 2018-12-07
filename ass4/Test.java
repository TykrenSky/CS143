import java.util.*;
import java.io.*;

public class Test {
   public static void main(String[] args) throws FileNotFoundException {
      List<String> list = Arrays.asList("Hi", "yo", "yi");
      HangmanManager hang = new HangmanManager2(list, 2, 2);
      Set<String> s1 = hang.words();
      Set<String> s2 = hang.words();
      hang.record('i');
      Set<String> s3 = hang.words();
      System.out.print((s1 == s2) + " " + (s1 == s3));
   }
}