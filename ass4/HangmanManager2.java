//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #4 Bonus
//
//A HangmanManager2 is an improvement on HangmanManager that returns unmodifiable objects and 
//tries to make the player lose even faster than before


import java.util.*;

public class HangmanManager2 extends HangmanManager {
   private Set<String> oldSetWords;
   private Set<String> unmodifiableSetWords;
   private SortedSet<Character> oldSetGuesses;
   private SortedSet<Character> unmodifiableSetGuesses;
   
   //Follows the same constructor behavior as HangmanManager
   public HangmanManager2(Collection<String> dictionary, int length, int max) {
      super(dictionary, length, max);
      oldSetWords = super.words();
      unmodifiableSetWords = Collections.unmodifiableSet(super.words());
      oldSetGuesses = super.guesses();
      unmodifiableSetGuesses = Collections.unmodifiableSortedSet(super.guesses());
   }
   
   //If the user only has one guess left the HangmanManager2 attempts to force them to lose
   //instead of carrying on the game, otherwise it behaves just like HangmanManager
   public int record(char guess) {
      if (guessesLeft() == 1) {
         String word = null;
         Iterator<String> itr = words().iterator();
         while (word == null && itr.hasNext()) {
            String test = itr.next();
            if (test.indexOf(guess) == -1) {
               word = test;
            }
         }
         if (word != null) {
            super.words().clear();
            super.words().add(word);
         }
      }
      return super.record(guess);
   }
   
   //Returns an unmodifiable Set of the considered words
   public Set<String> words() {
      if (oldSetWords != super.words()) {
         oldSetWords = super.words();
         unmodifiableSetWords = Collections.unmodifiableSet(super.words());
      }
      return unmodifiableSetWords;
   }
   
   //Returns an unmodifiable SortedSet of past guesses
   public SortedSet<Character> guesses() {
      if (oldSetGuesses != super.guesses()) {
         oldSetGuesses = super.guesses();
         unmodifiableSetGuesses = Collections.unmodifiableSortedSet(super.guesses());
      }
      return unmodifiableSetGuesses;
   }
}