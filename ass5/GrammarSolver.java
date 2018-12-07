//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #5
//
//A GrammarSolver takes a list of rules in Backus-Naur Form and is then able to
//create random elements of the grammar based on those rules

import java.util.*;

public class GrammarSolver {
   private SortedMap<String, List<String>> rules;
   
   //pre: The grammar parameter is not empty, or if there are two or more entries
   //     for the same non-terminal
   //       (Throws IllegalArgumentException if either not)
   //post: Creates a new GrammarSolver that uses the rules provided in the 
   //      grammar parameter without altering that list
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      rules = new TreeMap<String, List<String>>();
      for (String line : grammar) {
         String[] parts = line.split("::=");
         if (rules.containsKey(parts[0])) {
            throw new IllegalArgumentException();
         }
         String[] ruleSet = parts[1].split("[|]");
         rules.put(parts[0], new ArrayList<String>());
         for (String rule : ruleSet) {
            rules.get(parts[0]).add(rule.trim());
         }
      }
   }
   
   //post: returns true if the symbol parameter is a non-terminal in this grammar
   public boolean grammarContains(String symbol) {
      return rules.containsKey(symbol);
   }
   
   //pre: the symbol parameter must be a non-terminal, and times >= 0
   //       (throws IllegalArgumentException if either not)
   //post: randomly generates an occurence of the given symbol a given number of times,
   //      returning the set of occurences in an array. Each rule of a non-terminal is
   //      applied with equal probability. The terminals are all seperated by a single
   //      space, with no leading or trailing whitespace
   public String[] generate(String symbol, int times) {
      if (!grammarContains(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] result = new String[times];
      for (int i = 0; i < times; i++) {
         result[i] = generate(symbol).trim();
      }
      return result;
   }
   
   //post: returns a String representing the possible non-terminals in the grammar
   //      in a sorted, comma-seperated list enclosed in square brackets
   public String getSymbols() {
      return rules.keySet().toString();
   }
   
   //post: Generates a single occurence of the given symbol
   private String generate(String symbol) {
      if (grammarContains(symbol)) {
         int size = rules.get(symbol).size();
         String rule = rules.get(symbol).get((int)(Math.random() * size));
         String[] ruleSet = rule.split("[ \t]+");
         String result = "";
         for (String part : ruleSet) {
            result += generate(part);
         }
         return result;
      } else {
         return symbol + " ";
      }
   }
}