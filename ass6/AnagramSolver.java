//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #6
//
//An AnagramSolver takes a given word and finds all of the anagrams of that word using a 
//provided dictionary.

import java.util.*;

public class AnagramSolver {
   private Map<String, LetterInventory> dictionary;
   
   //post: Constructs an AnagramSolver that uses the provided list as its dictionary without
   //      changing the list
   public AnagramSolver(List<String> list) {
      dictionary = new HashMap<String, LetterInventory>();
      for (String word : list) {
         dictionary.put(word, new LetterInventory(word));
      }
   }
   
   //pre: max >= 0 
   //       (Throws IllegalArgumentException if not)
   //post: Takes a String s and prints all of the anagrams of the word that can be made from
   //      words in the AnagramSolver's dictionary
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      SortedMap<String, LetterInventory> reducedDictionary = new TreeMap<String, LetterInventory>();
      LetterInventory sInv = new LetterInventory(s);
      for (String word : dictionary.keySet()) {
         LetterInventory result = sInv.subtract(dictionary.get(word));
         if (result != null) {
            reducedDictionary.put(word, dictionary.get(word));
         }
      }
      explore(sInv, new ArrayList<String>(), max, reducedDictionary);
   }
   
   //private method for recursive calls
   private void explore(LetterInventory s, List<String> path, 
                        int max, SortedMap<String, LetterInventory> dictionary) {
      if (s.isEmpty()) {
         System.out.println(path);
      } else if (max == 0 || path.size() < max) {
         for (String word : dictionary.keySet()) {
            LetterInventory result = s.subtract(dictionary.get(word));
            if (result != null) {
               path.add(word);
               explore(result, path, max, dictionary);
               path.remove(path.size() - 1);
            }
         }
      }
   }
}