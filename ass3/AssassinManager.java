//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #3
//
//An AssassinManager keeps track of a ring of people stalking one another in a circle, 
//and a graveyard that keeps track of who has died. The manager can also handle
//one player killing the person they were stalking and if the game is over

import java.util.*;

public class AssassinManager {
   private AssassinNode killFront;
   private AssassinNode graveFront;
   
   //pre: The list must contain at least one name 
   //          (Throws IllegalArgumentException if not)
   //post: Constructs a new AssassinManager using a List of names, with the first name stalking the 
   //      second name and so on, with the last name stalking the first
   public AssassinManager(List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      }
      for (int i = names.size() - 1; i >= 0; i--) {
         killFront = new AssassinNode(names.get(i), killFront);
      }
   }
   
   //post: Prints the current kill ring, detailing who is stalking who to the console
   //      if there is only one player remaining, they will be stalking themselves
   public void printKillRing() {
      AssassinNode current = killFront;
      while (current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + killFront.name);
   }
   
   //post: Prints all of the players who have died, along with their killers
   //      with the most recent kill at the top
   //      will print nothing if no players have died
   public void printGraveyard() {
      AssassinNode current = graveFront;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }
   
   //post: returns true if the given name is in the kill ring
   public boolean killRingContains(String name) {
      return contains(killFront, name);
   }
   
   //post: returns true if the given name is in the graveyard
   public boolean graveyardContains(String name) {
      return contains(graveFront, name);
   }
   
   //post: returns true if there is only one player left
   public boolean gameOver() {
      return killFront.next == null;
   }
   
   //post: Returns the name of the last remaining player if the game is over, otherwise 
   //      it returns null
   public String winner() {
      if (!gameOver()) {
         return null;
      }
      return killFront.name;
   }
   
   //pre: The game must not be over (throws IllegalStateException if not), 
   //     and the kill ring must contain the given name 
   //       (Throws IllegalArgumentException if not)
   //post: Kills the player with the given name, moving them out of the kill ring and into
   //      the graveyard, recording the person who was stalking them as their killer,
   //      and making their killer's new target be the victim's old target
   public void kill(String name) {
      if (gameOver()) {
         throw new IllegalStateException();
      }
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      AssassinNode current = killFront;
      while (current.next != null 
            && !current.next.name.toLowerCase().equals(name.toLowerCase()) ) {
         current = current.next;
      }
      AssassinNode victim = null;
      if (current.next == null) {
         victim = killFront;
         killFront = killFront.next;
      } else {
         victim = current.next;
         current.next = current.next.next;
      }
      victim.next = graveFront;
      graveFront = victim;
      graveFront.killer = current.name;
   }
   
   //Checks to see if the kill ring or graveyard with the given start node contains a 
   //specific name
   private boolean contains(AssassinNode front, String name) {
      name = name.toLowerCase();
      AssassinNode current = front;
      while (current != null) {
         if (current.name.toLowerCase().equals(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
}