/* This is the Room class that contains the
different questions for the room, and occupied status
Author: Steven Zuelke
 */

import QuestionTypes.Question;
import javafx.geometry.Point2D;

import java.io.Serializable;

public class Room implements Serializable {

    //QuestionTypes.Question[0] is up and increments Clockwise
    private Question[] Questions;
    private Boolean Occupied;

    //Constructor

    public Room(Question[] questions, Boolean occupied){

        Questions = questions;
        Occupied = occupied;

    }//end Constructor

    //Getters for specific question and Occupied status

    public Boolean GetOccupied(){ return Occupied; }

    public void SetOccupied(Boolean bool){Occupied = bool;}

    //Returns null if No question at that index

    public Question GetQuestion(int index){

        Question q;
        if((q = Questions[index]) != null){

            return q;

        }//end if q
        return null;

    }//end GetQuestion

    public boolean getTopLocked() {

        boolean topLocked = Questions[0].GetLocked();
        return topLocked;

    }//end getTopLocked

    public boolean getBottomLocked() {

        boolean bottomLocked = Questions[2].GetLocked();
        return bottomLocked;

    }//end getBottomLocked

    public boolean getLeftLocked() {

        boolean leftLocked = Questions[3].GetLocked();
        return leftLocked;

    }//end getLeftLocked

    public boolean getRightLocked() {

        boolean rightLocked = Questions[1].GetLocked();
        return rightLocked;

    }//end getRightLocked()

}//end class
