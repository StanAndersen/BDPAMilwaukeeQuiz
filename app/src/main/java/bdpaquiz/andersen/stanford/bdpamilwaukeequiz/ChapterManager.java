package bdpaquiz.andersen.stanford.bdpamilwaukeequiz;

import android.view.MenuItem;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Stan on 8/15/2014.
 */
public class ChapterManager implements Serializable {
    private int correctTotal = 0;
    private int incorrectTotal = 0;
    private ArrayList<Chapter> chapters = null;
    private ArrayList<Chapter> activeChapters = null;
    private int chapterLocation = 0;
    private Chapter currentChapter = new Chapter();
    private char questionPool = 'a'; // a is for all q is for questions only and v is for vocab
    private char mode = 'q'; //q for question or 'v' for vocab mode

    private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private Random rand = new Random();
    private Question currentQuestion = new Question();
    private int answerLocation = 0;
    private String fullQuestionString = "";
    private boolean enableChange = false;
    private String reason = "";
    private boolean[] includedChapters = new boolean[1]; //Chapters that would be checked

    public ChapterManager() {
        //should not need this constructor

    }

    public ChapterManager(ArrayList<Chapter> allChapters) {
        this.chapters = allChapters;
        this.activeChapters = (ArrayList<Chapter>) allChapters.clone();
        includedChapters = new boolean[chapters.size()];
        for (int i = 0; i < includedChapters.length; i++) {
            includedChapters[i] = true;
        }
        changeCurrentQuestion();
    }

    public boolean[] getIncludedChapters(){
        return  this.includedChapters;
    }
    public void setMode(char mode){
        this.mode=mode;
        makeCurrentQuestionPrint();
    }
    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }

    public boolean isActiveChaptersEmpty() {
        return activeChapters.isEmpty();
    }
    private void makeCurrentQuestionPrint(){
        if (activeChapters.isEmpty()) {
            fullQuestionString = "There are no more questions active \n correct=" + correctTotal
                    + "\n  incorrect:" + incorrectTotal;
        }else {
            if (mode == 'q') {
                fullQuestionString = currentQuestion.print(answerLocation);
            } else if (mode == 'v') {
                fullQuestionString = currentQuestion.getQuestion();
            } else {
                fullQuestionString = "Failed to find a question mode";
            }
        }

    }

    public void changeCurrentQuestion() {
        boolean done = false;
        fullQuestionString = "";
        if (activeChapters.isEmpty()) {

        } else {
            while (!done) {
                chapterLocation = rand.nextInt(activeChapters.size());
                currentChapter = activeChapters.get(chapterLocation);
                if (currentChapter.questionNumber(questionPool) > 0) {
                    currentQuestion = activeChapters.get(chapterLocation).getQuestion(questionPool);
                    answerLocation = rand.nextInt(currentQuestion.allOptionsLength());
                    done=true;
                } else {
                    //chaptersMenu.get(chapters.indexOf(currentChapter)).setEnabled(false);
                    activeChapters.remove(currentChapter);
                    if (activeChapters.isEmpty()) {
                        done=true;
                    }
                    //System.out.println(activeChapters.size());
                }
            }
        }
        makeCurrentQuestionPrint();

    }

    public String printCurrentQuestion() {
        return fullQuestionString;
    }

    public int chapterNumber() {
        return this.chapters.size();
    }

    public boolean answer(int ans) {
        this.reason = "intialize question";
        boolean changeEnableNeeded = false;
        if (mode == 'q') {
            if (ans == answerLocation) {
                correctTotal++;
                //correct.setText(Integer.toString(correctTotal));
                currentChapter.removeQuestion(currentQuestion);
                if (currentChapter.questionNumber(this.questionPool) == 0) {
                    //  chaptersMenu.get(chapters.indexOf(currentChapter)).setEnabled(false);
                    activeChapters.remove(currentChapter);
                    changeEnableNeeded=true;
                }
                reason = "Correct!\n\n" + currentQuestion.getQuestion() +
                        "\n\n the answer was: \n" + currentQuestion.getAnswer() + "\n \n";
            } else {
                incorrectTotal++;
                //incorrect.setText(Integer.toString(incorrectTotal));
                reason = "Incorrect.\n" +
                        "\n" +
                        "\n" + currentQuestion.getQuestion() +
                        "\n \n the answer was: \n" + currentQuestion.getAnswer() + "\n \n";
            }
            reason = reason + "This is from:  " + currentChapter.getName() + " \n"
                    + currentQuestion.getReason();
            reason+= "\n Correct: "+ correctTotal + " Incorrect: " +incorrectTotal;
        } else if (mode == 'v') {
            reason = currentQuestion.getAnswer() + "\nThis is from:  " + currentChapter.getName() + "\n \n" + currentQuestion.getReason();
        }
        return changeEnableNeeded;
    }
    public String getChapterName(int i){
        return this.chapters.get(i).getName();
    }
    public char getQuestionPool(){
        return this.questionPool;
    }
    public char getMode(){
        return this.mode;
    }
    public String printAnswer(){
        if(this.activeChapters.isEmpty()) {
            if (this.mode == 'v') {
                this.reason = "There are no more questions active";
            } else {
                this.reason = "There are no more questions active \n correct=" + correctTotal
                        + "\n  incorrect:" + incorrectTotal;

            }
        }
        return this.reason;
    }

    public String getReasonString() {
        return this.reason;
    }

    public boolean changeEnabledChapterNecesaryFromAnswer() {
        return this.enableChange;
    }

    public boolean[] chapterThatNeedsEnabling() {
        this.enableChange = false;
        boolean[] chapterThatNeedEnabling = new boolean[chapters.size()];
        for (int i = 0; i < chapters.size(); i++) {
            if (this.chapters.get(i).questionNumber(this.questionPool) > 0) {
                chapterThatNeedEnabling[i] = true;
            }
        }
        return chapterThatNeedEnabling;
    }

    public boolean changeQuestionPool(char mnenonic) { //if question needs to be changed
        char formerMnenonic = mnenonic;
        this.questionPool = mnenonic;
        activeChapters.clear();
        boolean[] chapterThatNeedEnabling = chapterThatNeedsEnabling();
        for (int i = 0; i < chapters.size(); i++) {
            if (this.includedChapters[i] && chapterThatNeedEnabling[i]) {
                activeChapters.add(chapters.get(i));
            }
        }
        if (currentQuestion.isVocab() && ('q' == mnenonic) || (!currentQuestion.isVocab()) && ('v' == mnenonic)) {
            changeCurrentQuestion();
            return true;
        }
        return false;
    }
    public boolean changeIncludedChapters(int location,boolean include){ //if current question if from off chapter or new chapter
        this.includedChapters[location] = include;
        if(include){
            activeChapters.add(chapters.get(location));
        }else{
            activeChapters.remove(chapters.get(location));
        }
        if(include && activeChapters.size()==1){
            changeCurrentQuestion();
            return true;
        }else if(activeChapters.size()==0){
            changeCurrentQuestion();
            return true;
        }else if(!include && currentChapter == chapters.get(location)){
            changeCurrentQuestion();
            return true;
        }
        return false;
    }
}

