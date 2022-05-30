package Entities;

import java.io.Serializable;

public class Survey implements Serializable{
private int id;

private String content ;
private String[] questionArray=new String[6];
private int[] answersArray= new int[6];
public int[] getAnswers() {
	// TODO Auto-generated method stub
	return answersArray;
}
public String[] getQuestions() {
	// TODO Auto-generated method stub
	return questionArray;
} 
@Override
	public String toString() {
	String string="";
		for (String s : questionArray) {
			string=string+s;
		}
		return string;
	}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
}
