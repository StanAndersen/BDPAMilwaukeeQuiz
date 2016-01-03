package bdpaquiz.andersen.stanford.bdpamilwaukeequiz;

/**
 * Created by Stan on 8/13/2014.
 */

 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Random;

public class Question implements Serializable{

    /**
     *
     */
    private String question;
    private ArrayList<String> options;
    private String answer;
    private String reason;
    private boolean vocab;
    private Random rand = new Random();
    private String[] alphabet = {"A) ", "B) ","C) ","D) ","E) ","F) ","H) ","I) "};

    public Question() {
        question = "";
        options= new ArrayList<String>();
        answer = "";
        reason= "";
        vocab = false;

    }
    /**
     *
     * @param question
     * @param answer
     * @param reason
     * @param vocab
     * @param options
     */
    public Question(String question, String answer, String reason, boolean vocab, ArrayList<String> options){
        this.question = question;
        this.answer = answer;
        this.reason=reason;
        this.vocab=vocab;
        this.options=options;
    }

    public Question(String question, String answer, String reason, boolean vocab, String[] options){
        this.question = question;
        this.answer = answer;
        this.reason=reason;
        this.vocab=vocab;
        this.options=new ArrayList<String>();
        if(options.length>0){
            for(int i = 0; i< options.length;i++){
                this.options.add(options[i]);
            }
        }
    }

    public String getQuestion(){
        return this.question;
    }
    public String getAnswer(){
        return this.answer;
    }
    public String[] getOptionsArray(){
        return this.options.toArray(new String[options.size()]);
    }
    public ArrayList<String> getOptions(){
        return this.options;
    }
    public String getReason(){
        return this.reason;
    }
    public boolean isVocab(){
        return this.vocab;
    }

    public int allOptionsLength(){
        return 1+options.size();
    }
    public String print(int answerLocation){
        String temp = this.question + "\n";
        ArrayList<String> shuffle = new ArrayList<String>();
        if(options.size()>0){
            for(int i=options.size();i>0;i-- ){
                shuffle.add(options.remove(rand.nextInt(i)));
            }
            this.options=shuffle;
        }
        int tempAlphabet = 0;
        for(int i=0; i<this.options.size();i++){
            if(answerLocation == i){
                temp = temp + this.alphabet[tempAlphabet] +this.answer +"\n";
                tempAlphabet++;
            }
            temp=temp +this.alphabet[tempAlphabet]+this.options.get(i)+"\n";
            tempAlphabet++;
        }
        if(answerLocation>=this.options.size()){
            temp=temp+this.alphabet[tempAlphabet] +this.answer +"\n";
        }
        return temp;
    }

    public String print(){
        String temp = question.concat("\n");
        if( options.size() > 0){
            for(int i = 0; i< options.size();i++){
                temp = temp + options.get(i) + "\n";
            }
        }
        return temp + this.answer;
    }
}
