package bdpaquiz.andersen.stanford.bdpamilwaukeequiz;
    
import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Random;


public class MyActivity extends Activity {
    private ArrayList<MenuItem> chaptersMenu = new ArrayList<MenuItem>();
//    private static boolean firstCreation = true;
    private int buttonsPerRow=5;
    private RadioButton[] radioButtons=new RadioButton[5];
    private Button submitRadioButtons;
    private Button submitCheckableButtons;
    private CheckBox[] checkableButtons=new CheckBox[5];
    private LinearLayout answerButtonsLayout;
    //may be unnecessary in future
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    //    private ScrollView manyButtons;
 //   private CheckBox[] manyButtons = new CheckBox[1];

//    private Button submitManyButtons; //could create  a dialog but will instead create a new layout
//    private LinearLayout manyButtonsLinearLayout;
    private ArrayList<Integer> answerLocations=new ArrayList<Integer>();
    private String[] chapterNames;
    private DatabaseHandler dbHandler;
    private DatabaseQuestion question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        answerButtonsLayout = (LinearLayout) findViewById(R.id.subLinear);
        this.dbHandler = new DatabaseHandler(this);
        this.chapterNames = dbHandler.getAllChapterNames();

       if(savedInstanceState==null) {


// Makes DATABASE and fills it with questions
//        DatabaseHandler dbHandler = ParseTxts2.parseChapters(this);

//           int temp = dbHandler.activeQuestionNumber("chapter 0");
            this.question = dbHandler.getQuestion(chapterNames[0]);
            //createCheckableButtons(null, this.question, null);
            createCheckableButtons(null, this.question, null);

       }else{
            this.question = dbHandler.getQuestion(chapterNames[0]);
           createCheckableButtons(null,question,null);
//           chapterManager = (ChapterManager) savedInstanceState.getSerializable("chapterManager");
/**
            if(savedInstanceState.getBoolean("screensQuestion")   ){
                returnToQuestion();
            }else{
                answer();
            }
       }
*/
//         Intent intent;
//       intent = new Intent(this, dbactivate.class);
//       startActivity(intent);

       }


    }
/**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);

        SubMenu submenu = menu.addSubMenu("ChapterSelect");
        boolean[] checkedMenu = chapterManager.getIncludedChapters();
        for(int i=0;i<chapterManager.chapterNumber();i++) {
            chaptersMenu.add(submenu.add(chapterManager.getChapterName(i)));
            chaptersMenu.get(i).setCheckable(true);
            chaptersMenu.get(i).setChecked(checkedMenu[i]);
        }

        return true;
     }
*/
        /**
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        /**
        switch(chapterManager.getMode()){
            case 'v': ((MenuItem) menu.findItem(R.id.vocabMode)).setChecked(true);
                break;
            case 'q': ((MenuItem) menu.findItem(R.id.questionMode)).setChecked(true);
                break;
        }

        switch(chapterManager.getQuestionPool()){
            case 'q': ((MenuItem)  menu.findItem(R.id.questionPool)).setChecked(true);
                break;
            case 'v': ((MenuItem)  menu.findItem(R.id.vocabPool)).setChecked(true);
                break;
            case 'a': ((MenuItem)  menu.findItem(R.id.bothQandV)).setChecked(true);
                break;
        }

        return true;
    }
*/
    /**
    public void setEnabled(){

        boolean[] enable = chapterManager.chapterThatNeedsEnabling();
        for(int i=0;i<enable.length;i++){
            chaptersMenu.get(i).setEnabled(enable[i]);

         }

    }
*/
        /**
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /**
        int id = item.getItemId();
        switch(id) {
            case R.id.vocabMode: chapterManager.setMode('v');
                item.setChecked(true);
                break;
            case R.id.questionMode:
                chapterManager.setMode('q');
                item.setChecked(true);
                break;
            case R.id.bothQandV: chapterManager.changeQuestionPool('a');
                    setEnabled();
                item.setChecked(true);
                break;
            case R.id.questionPool: chapterManager.changeQuestionPool('q');
                    setEnabled();
                item.setChecked(true);
                break;
            case R.id.vocabPool: chapterManager.changeQuestionPool('v');
                    setEnabled();
                item.setChecked(true);
                break;
        }
        for(int i=0;i<chaptersMenu.size();i++) {
            if (this.chaptersMenu.get(i).equals(item)) {
                item.setChecked(!item.isChecked());
                chapterManager.changeIncludedChapters(this.chaptersMenu.indexOf(item), item.isChecked()) ;
            }

        }
        if(findViewById(R.id.answerScreenLayout)==null){
           returnToQuestion();
        }
        if(chapterManager.isActiveChaptersEmpty()){
            answer();
        }

        if (id == R.id.action_settings) {
            return true;
        }

         return super.onOptionsItemSelected(item);

     }
*/
/**
    public void answer(int ans) {
        if(chapterManager.answer(ans)){
            setEnabled();
        }
        answer();
    }
    public void answer(){
        setContentView(R.layout.activity_change_to_answer_screen);
        Button button = (Button) findViewById(R.id.button);
        button.setMovementMethod(new ScrollingMovementMethod());
        button.setText(chapterManager.printAnswer());
    }




        /*Intent intent = new Intent(this, ChangeToAnswerScreen.class);
        startActivity(intent);*/

/**
    public void answer1(android.view.View view) {
        answer(0);
    }

    public void answer2(android.view.View view) {
        answer(1);
    }

    public void answer3(android.view.View view) {
        answer(2);
    }

    public void answer4(android.view.View view) {
        answer(3);
    }

    public void answer5(android.view.View view) {
        answer(4);
    }

    public void returnToQuestion(android.view.View view) {
        if(chapterManager.isActiveChaptersEmpty()){
            answer();
        }else {
            chapterManager.changeCurrentQuestion();
            returnToQuestion();
        }
    }


    public void returnToQuestion(){
        setContentView(R.layout.activity_my);
        TextView question =(TextView) findViewById(R.id.textView);
        question.setMovementMethod(new ScrollingMovementMethod());
        question.setText(chapterManager.printCurrentQuestion());
    }
*/
    /**
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_my);
            //do other initialization
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout-land.activity_my);
            //do other initialization
        }I certify this submission as my own orginal work completed in accordance with the Coursera Honor Code.
    }
    */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
      /**
        savedInstanceState.putSerializable("chapterManager",chapterManager);
        if(findViewById(R.id.answerScreenLayout)==null){
            savedInstanceState.putBoolean("screensQuestion",true);
        }else{
            savedInstanceState.putBoolean("screensQuestion",false);
        }
*/

    }


    public void createCheckableButtons(int[] order, DatabaseQuestion question, boolean[] intialState){
        int numberOfButtons = question.allOptionsLength();
        String[] optionsToDisplay = new String[numberOfButtons];
        checkableButtons = new CheckBox[numberOfButtons];
        if(intialState==null){
            intialState=new boolean[numberOfButtons];
        }
        if( order == null){
            order = new int[numberOfButtons];
            for(int i = 0; i<numberOfButtons; i++){
                order[i] = i;
            }
        }
        this.answerLocations.clear();
        String[] tempAnswer= question.getAnswer();
        for(int i=0; i<question.getAnswer().length; i++){
            answerLocations.add(order[i]);
            optionsToDisplay[order[i]]=tempAnswer[i];
        }
        String[] tempOption=question.getOptionsArray();
        for(int i=0; i<tempOption.length; i++){
            optionsToDisplay[order[i+tempAnswer.length]]=tempOption[i];
        }

        setContentView(R.layout.activity_my);
        answerButtonsLayout=(LinearLayout) findViewById(R.id.subLinear);
        if(answerButtonsLayout != null){
            answerButtonsLayout.removeAllViews();
        }
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1);

        //Investigate TableView and ListView

        for(int i=0;i<numberOfButtons;i++){
            //build checkable buttons for multiple answers
            CheckBox box1=new CheckBox(this);
            if(i<alphabet.length) {
                box1.setText(Character.toString(alphabet[i]).concat(")")+optionsToDisplay[i]);
            }else{
                box1.setText(Integer.toString(i).concat(") ")+optionsToDisplay[i]);
            }
            box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(buttonView.isChecked()){
                        buttonView.setBackgroundColor(Color.GREEN);
                    }else{
                        buttonView.setBackgroundColor(Color.TRANSPARENT);//Color.parseColor()
                    }
                }
            });
            /**
            box1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    CheckBox check=(CheckBox) v;
                    if(check.isChecked()){
                        check.setBackgroundColor(0x0000FF00);
                    }else{
                        check.setBackgroundColor(Color.TRANSPARENT);
                    }
                }

            });
             */
            box1.setLayoutParams(params);
            box1.setChecked(intialState[i]);
            checkableButtons[i] = box1;
            answerButtonsLayout.addView(box1);
        }
// Submit button
        submitCheckableButtons = new Button(this);
        submitCheckableButtons.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkableButtonsAnswer(v);
            }
        });

        submitCheckableButtons.setText("Submit");
        submitCheckableButtons.setLayoutParams(params);

        LinearLayout submitButtonLayout = (LinearLayout) findViewById(R.id.submit);
        if(submitButtonLayout != null){
            submitButtonLayout.removeAllViews();
        }
        submitButtonLayout.addView(submitCheckableButtons);

        TextView questionView = (TextView) findViewById(R.id.textView);
        questionView.setMovementMethod(new ScrollingMovementMethod());
        questionView.setText(question.getQuestion());
    }

    public void createRadioButtons(int[] order, DatabaseQuestion question, boolean[] intialState){
        int numberOfButtons = question.allOptionsLength();
        String[] optionsToDisplay = new String[numberOfButtons];

        radioButtons = new RadioButton[numberOfButtons];
        if(intialState==null){
            intialState=new boolean[numberOfButtons];
        }
        if( order == null){
            order = new int[numberOfButtons];
            for(int i = 0; i<numberOfButtons; i++){
                order[i] = i;
            }
        }
        this.answerLocations.clear();
        String[] tempAnswer= question.getAnswer();
        for(int i=0; i<question.getAnswer().length; i++){
            answerLocations.add(order[i]);
            optionsToDisplay[order[i]]=tempAnswer[i];
        }
        String[] tempOption=question.getOptionsArray();
        for(int i=0; i<tempOption.length; i++){
            optionsToDisplay[order[i+tempAnswer.length]]=tempOption[i];
        }

        setContentView(R.layout.activity_my);
        answerButtonsLayout=(LinearLayout) findViewById(R.id.subLinear);
        if(answerButtonsLayout != null){
            answerButtonsLayout.removeAllViews();
        }
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        for(int i=0;i<numberOfButtons;i++){
            //build Radio buttons for multiple answers
            RadioButton box1=new RadioButton(this);
            if(i<alphabet.length) {
                box1.setText(Character.toString(alphabet[i]).concat(")")+optionsToDisplay[i]);
            }else{
                box1.setText(Integer.toString(i).concat(") ")+optionsToDisplay[i]);
            }
            box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(buttonView.isChecked()){
                        buttonView.setBackgroundColor(Color.GREEN);
                    }else{
                        buttonView.setBackgroundColor(Color.TRANSPARENT);//Color.parseColor()
                    }
                }
            });
            /**
             box1.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v){
             CheckBox check=(CheckBox) v;
             if(check.isChecked()){
             check.setBackgroundColor(0x0000FF00);
             }else{
             check.setBackgroundColor(Color.TRANSPARENT);
             }
             }

             });
             */
            radioGroup.addView(box1);
            box1.setLayoutParams(params);
            box1.setChecked(intialState[i]);
            radioButtons[i] = box1;
//            answerButtonsLayout.addView(box1);
        }
        answerButtonsLayout.addView(radioGroup);
// Submit button
        submitRadioButtons = new Button(this);
        submitRadioButtons.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                radioButtonsAnswer(v);
            }
        });

        submitRadioButtons.setText("Submit");
        submitRadioButtons.setLayoutParams(params);

        LinearLayout submitButtonLayout = (LinearLayout) findViewById(R.id.submit);
        if(submitButtonLayout != null){
            submitButtonLayout.removeAllViews();
        }
        submitButtonLayout.addView(submitRadioButtons);

        TextView questionView = (TextView) findViewById(R.id.textView);
        questionView.setMovementMethod(new ScrollingMovementMethod());
        questionView.setText(question.getQuestion());
    }




    public void checkableButtonsAnswer(View v){
    boolean correct = true;
    ArrayList<Integer> correctAnswers = new ArrayList<Integer>();
    for(int i = 0; i < checkableButtons.length; i++){
        if(checkableButtons[i].isChecked()){
            if(answerLocations.contains( i)){
               //do nothing yet
            }else{
                correct = false;
            }
        }else{
            if(answerLocations.contains(i)){
                correct = false; //this is an answer where there should not be
            }else{
                //do nothing yet
            }
        }
    }

    respondToAnswer(correct);
}

    public void radioButtonsAnswer(View v) {

        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isChecked()) {
                if (answerLocations.contains(i)) {
                    respondToAnswer(true);            //only time answer is correct
                    return;
                } else {
                    respondToAnswer(false);
                    return;
                }
            }
        }
        respondToAnswer(false);

    }


public void respondToAnswer(Boolean correct){
    setContentView(R.layout.activity_change_to_answer_screen);
    TextView answerView = (TextView) findViewById(R.id.answerTextView);
    String answer = "";
    //TODO modify database structures
    if(correct){
        answer=answer+"Correct \n";
    }else{
        answer=answer+"InCorrect \n";
    }
    answer= answer+"The answer to:  \n"+ this.question.getQuestion()+"\n\n " +
            "is:  ";
    String[] answersTemp = this.question.getAnswer();
    for(int i=0; i<answersTemp.length; i++){
        answer= answer+answersTemp[i] +"\n";
    }
    String reason= this.question.getReason();
    if(reason.length()>0){
        answer=answer+"The reason is: "+reason+"\n";
    }
    answerView.setText(answer);

}

    public void returnToQuestion(View view){
        this.question= dbHandler.getQuestion(this.chapterNames[0]);
        //createCheckableButtons(null, this.question, null);
        createRadioButtons(null,this.question, null);
    }
}