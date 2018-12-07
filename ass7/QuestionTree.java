//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #7
//
//A QuestionTree stores a set of questions that branch out into more questions, attempting
//to narrow in on an object that the user selects by using the users answers. if wrong,
//the QuestionTree will update itself based on a user provided question and answer.
//Can also read a previous set of questions from a file and answers and write its current 
//set after the program ends

import java.util.*;
import java.io.*;

public class QuestionTree {
   private QuestionNode overallRoot;
   private Scanner console;
   
   //Constructs a new QuestionTree that only knows "computer" as an answer
   public QuestionTree() {
      overallRoot = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
   
   //post: Uses its set of questions to try and guess what object the user is thinking of,
   //      and if it guesses wrong it updates itself with the users object and a new 
   //      question
   public void askQuestions() {
      overallRoot = ask(overallRoot);
   }
   
   //post: runs the main body of askQuestions
   private QuestionNode ask(QuestionNode root) {
      if (root.isQuestion) {
         boolean answer = yesTo(root.data);
         if (answer) {
            root.yes = ask(root.yes);
         } else {
            root.no = ask(root.no);
         }
         return root;
      } else {
         boolean answer = yesTo("Would your object happen to be " + root.data + "?");
         if (answer) {
            System.out.println("Great, I got it right!");
            return root;
         } else {
            return modify(root);
         }
      }
   }
   
   //post: updates the set of questions to include the users object and a new
   //      distinguishing question
   private QuestionNode modify(QuestionNode root) {
      System.out.print("What is the name of your object? ");
      String object = console.nextLine();
      System.out.println("Please give me a yes/no question that");
      System.out.println("distinguishes between your object");
      System.out.print("and mine--> ");
      String question = console.nextLine();
      boolean answer = yesTo("And what is the answer for your object?");
      if (answer) {
         return new QuestionNode(question, true, new QuestionNode(object), root);
      } else {
         return new QuestionNode(question, true, root, new QuestionNode(object));
      }
   }
   
   //pre: the Scanner input must link to a file in the proper format
   //post: Uses the information in the file the Scanner is linked to 
   //      to replace the current set of questions and answers with the one in the
   //      file
   public void read(Scanner input) {
      overallRoot = readDown(input);
   }
   
   //post: runs the body of read
   private QuestionNode readDown(Scanner input) {
      String type = input.nextLine();
      if (type.equals("Q:")) {
         String data = input.nextLine();
         QuestionNode yes = readDown(input);
         QuestionNode no = readDown(input);
         return new QuestionNode(data, true, yes, no);
      } else {
         String data = input.nextLine();
         return new QuestionNode(data);
      }
   }
   
   //pre: The PrintStream must be open for writing
   //post: writes the current set of questions and answers to the given PrintStream
   //      in the proper format for later use with the read method
   public void write(PrintStream output) {
      write(output, overallRoot);
   }
   
   //post: runs the main body of write
   private void write(PrintStream output, QuestionNode root) {
      if (root.isQuestion) {
         output.println("Q:");
         output.println(root.data);
         write(output, root.yes);
         write(output, root.no);
      } else {
         output.println("A:");
         output.println(root.data);
      }
   }
}