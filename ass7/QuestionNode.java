//Tieran Rashid
//CSE143 AN with Michael Sulistio
//Homework #7
//A QuestionNode stores either an answer or a question and its answers



public class QuestionNode {
   public boolean isQuestion; //whether or not this is a question
   public String data; //answer or question itself
   public QuestionNode yes; //yes answer
   public QuestionNode no; //no answer
   
   //Constucts a QuestionNode with the data string as an answer
   public QuestionNode(String data) {
      this(data, false, null, null);
   }
   
   //Constructs a QuestionNode with a given data, question and answer nodes
   public QuestionNode(String data, boolean isQuestion, QuestionNode yes, QuestionNode no) {
      this.data = data;
      this.isQuestion = isQuestion;
      this.yes = yes;
      this.no = no;
   }
}