//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #4
//
//A HangmanManager class manages a game of "evil" hangman. It only selects a word when
//it is forced to, and also tries to maximize its options to defeat the player. It 
//keeps track of the players guesses, where their correct letters are in the word,
//and a list of possible words that narrows as the player guesses.


import java.util.*;

public class HangmanManager {
   private String pattern;
   private int guessesLeft;
   private Set<String> words;
   private SortedSet<Character> guesses;
   
   //pre: length >= 1 and max >= 0 
   //    (Throws IllegalArgumentException if not)
   //post: Constructs a new HangmanManager using a provided dictionary of words, 
   //      starting a game with words of the given length and a maximum number of
   //      wrong guesses
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      guessesLeft = max;
      words = new TreeSet<String>();
      for (String word : dictionary) {
         if (word.length() == length) {
            words.add(word);
         }
      }
      pattern = "-";
      for (int i = 0; i < length - 1; i++) {
         pattern += "-";
      }
      guesses = new TreeSet<Character>();
   }
   
   //post: returns the Set of words being considered by the manager
   public Set<String> words() {
      return words;
   }
   
   //post: returns the number of wrong guesses left
   public int guessesLeft() {
      return guessesLeft;
   }
   
   //pre: The set of words cannot be empty
   //       (Throws IllegalStateException if words is empty)
   //post: returns the pattern of the word, with all of the letters the player guessed
   //filled in and all others letters are "-"
   public String pattern() {
      if (words.isEmpty()) {
         throw new IllegalStateException();
      }
      String result = "" + pattern.charAt(0);
      for (int i = 1; i < pattern.length(); i++) {
         result += " " + pattern.charAt(i);
      }
      return result;
   }
   
   //post: returns the SortedSet of all guesses the player has already made
   public SortedSet<Character> guesses() {
      return guesses;
   }
   
   //pre: The set of words cannot be empty and the player must have guesses left
   //       (Throws IllegalStateException otherwise) 
   //     The guess given must not have already been guessed
   //       (Throws IllegalArgumentException otherwise)
   //post: Takes a guessed letter and decides uses it to decide on a new group of words
   //      to consider going forward. It returns the number of times the guess shows up
   //      in the new pattern, returning zero and removing a guess if the letter is
   //      not in the new pattern
   public int record(char guess) {
      if (words.isEmpty() || guessesLeft < 1) {
         throw new IllegalStateException();
      }
      if (guesses.contains(guess)) {
         throw new IllegalArgumentException();
      }
      Map<String, Set<String>> possiblePatterns = getPossiblePatterns(guess);
      String finalPattern = getFinalPattern(possiblePatterns);
      words = possiblePatterns.get(finalPattern);
      updatePattern(finalPattern);
      if (finalPattern.indexOf(guess) == -1) {
         guessesLeft--;
         return 0;
      } else {
         int letters = 0;
         for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == guess) {
               letters++;
            }
         }
         return letters;
      }
   }
   
   //post: uses a character guess to seperate the set of words into groups
   //      based on pattern, returning the Map of possibilities
   private Map<String, Set<String>> getPossiblePatterns(char guess) {
      Map<String, Set<String>> possiblePatterns = new TreeMap<String, Set<String>>();
      guesses.add(guess);
      for (String word : words) {
         String thisPattern = "";
         for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
               thisPattern += guess;
            } else {
               thisPattern += "-";
            }
         }
         if (!possiblePatterns.containsKey(thisPattern)) {
            possiblePatterns.put(thisPattern, new TreeSet<String>());
         }
         possiblePatterns.get(thisPattern).add(word);
      }
      return possiblePatterns;
   }
   
   //post: goes through the map of possibile patterns and returns the pattern
   //      key that has the largest Set associated with it
   private String getFinalPattern(Map<String, Set<String>> possiblePatterns) {
      String finalPattern = null;
      for (String aPattern : possiblePatterns.keySet()) {
         if (finalPattern == null) {
            finalPattern = aPattern;
         } else {
            if (possiblePatterns.get(finalPattern).size() <
                  possiblePatterns.get(aPattern).size()) {
               finalPattern = aPattern;
            }
         }
      }
      return finalPattern;
   }
   
   //post: uses the newPattern(which contains only the most recent guess and dashes)
   //      and the old pattern to update the pattern field to be accurate for the 
   //      next section of the game
   private void updatePattern(String newPattern) {
      String result = "";
      for (int i = 0; i < pattern.length(); i++) {
         if (newPattern.charAt(i) != '-') {
            result += newPattern.charAt(i);
         } else {
            result += pattern.charAt(i);
         }
      }
      pattern = result;
   }
}