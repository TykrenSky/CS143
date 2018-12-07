import java.util.*;

public class Test {
   public static void main(String[] args) {
      String[] start = {"Athos", "Porthos", "Yeet"};
      List<String> names = new ArrayList<String>();
      for (String name : start) {
         names.add(name);
      }
      AssassinManager manager = new AssassinManager(names);
      manager.printKillRing();
      System.out.println(manager.killRingContains("athoS"));
      System.out.println(manager.gameOver());
   }
}