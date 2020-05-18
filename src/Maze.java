/* This is the maze class that will contain all Rooms
and the Gameplay called by Main
Author: Steven Zuelke
 */

import QuestionTypes.Question;
import javafx.geometry.Point2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Maze implements Serializable {

    Room[][] Rooms;
    DataAccess DataAccess;

    //Constructor

    public Maze(){

        Rooms = new Room[3][3];
        //SetupRooms();
        DataAccess = new DataAccess();

    }//end Constructor

    //Method to try and move the player to a new room
    //Index parameter at 0 is UP and increments Clockwise

    public Boolean ChangeRooms(int index){

        Boolean moved = false;

        return moved;

    }//end ChangeRooms

    //Method to see if the character has no way to win (aka lost)
    //Recursively exhaust every path, and if no possible path contains the end,
    //then you lost and returns true

    public boolean CheckLoss(int r, int c, ArrayList<Point2D> previousRooms){

        boolean loss = false;

        previousRooms.add(new Point2D(r, c));
        if(r == Rooms.length - 1 && c == Rooms[0].length - 1)
            return false; //Base case (can reach the end)
        //end if
        if(r != Rooms.length - 1 && //Not at right side
            !previousRooms.contains(new Point2D(r + 1, c)) && //Haven't already been there
            !Rooms[r][c].GetQuestion(1).GetLocked()) {//Door isn't locked

            loss = CheckLoss(r + 1, c, previousRooms);
            if(!loss) return false;//early bailout

        }
        //end if

        if(c != Rooms[0].length - 1 && //Not at bottom yet
            !previousRooms.contains(new Point2D(r, c + 1)) && //Haven't already been there
            !Rooms[r][c].GetQuestion(2).GetLocked()) {//Door isn't locked

            loss = CheckLoss(r, c + 1, previousRooms);
            if(!loss) return false;//early bailout

        }//end if

        if(r != 0 && //Not at left yet
            !previousRooms.contains(new Point2D(r - 1, c)) &&//Haven't already been there
            !Rooms[r][c].GetQuestion(3).GetLocked()){ //Door isn't locked

            loss = CheckLoss(r - 1, c, previousRooms);
            if(!loss) return false;//early bailout

        }//end if

        if(c != 0 && //Not at top yet
            !previousRooms.contains(new Point2D(r, c - 1)) &&//Haven't already been there
            !Rooms[r][c].GetQuestion(0).GetLocked()){ //Door isn't locked

            loss = CheckLoss(r, c - 1, previousRooms);
            if(!loss) return false;//early bailout

        }//end if

        //if reached this point, there is no way past current point
        previousRooms.remove(new Point2D(r, c));
        loss = true;
        return loss;

    }//end CheckLoss

    //Method to see if the character has won the game after any move

    public boolean CheckWin(){

        Boolean won = false;
        if(Rooms[Rooms.length - 1][Rooms[0].length - 1].GetOccupied()) won = true;
        return won;

    }//end CheckWin

    //Method to Pick a question from database that isn't already in use
    //Waiting on questions in the database
    private Question PickQuestion(ArrayList<Question> used){

        Question question = null;
        ArrayList<Question> allQuestions = DataAccess.GetQuestions();
        do{

            int index = (new Random()).nextInt() % allQuestions.size();
            question = allQuestions.get(index);

        }while(allQuestions.contains(question));
        //end do while

        return question;

    }//end PickQuestion

    //Method to set up all rooms for beginning of game

    private void SetupRooms(){

        ArrayList<Question> used = new ArrayList<Question>();
        for(int i = 0; i < Rooms.length; i++){

            for(int j = 0; j < Rooms[0].length; j++){

                Question[] questions = new Question[4];
                Boolean occupied = false;
                //for each room fill appropriate question list
                //Top
                if(i != 0){

                    questions[0] = PickQuestion(used);
                    used.add(questions[0]);

                }//end if 0

                else questions[0] = null;
                //Right
                if(j != Rooms[0].length - 1){

                    questions[1] = PickQuestion(used);
                    used.add(questions[1]);

                }//end if 1

                else questions[1] = null;
                //Bottom
                if(i != Rooms.length - 1){

                    questions[2] = PickQuestion(used);
                    used.add(questions[2]);

                }//end if 2

                else questions[2] = null;
                //Left
                if(j != 0){

                    questions[3] = PickQuestion(used);
                    used.add(questions[3]);

                }//end if 3

                else questions[3] = null;

                //Put character in top left
                if(i == 0 && j == 0) occupied = true;
                Rooms[i][j] = new Room(questions, occupied);

            }//end for j

        }//end for i

    }//end SetupRooms

}
