package bdpaquiz.andersen.stanford.bdpamilwaukeequiz;

/**
 * Created by Stan on 8/13/2014.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Chapter implements Serializable{

    /**
     *
     */
    private String name;
    private ArrayList<Question>	questions;
    private ArrayList<Question>	vocab;

    private Random rand = new Random();

    public Chapter() {
        questions=new ArrayList<Question>();
        vocab = new ArrayList<Question>();
        name = "";

    }
    public Chapter(String name) {
        questions=new ArrayList<Question>();
        vocab = new ArrayList<Question>();
        this.name = name;

    }

    public ArrayList<Question> getAllQuestionAndVocab(){
        ArrayList<Question> temp= (ArrayList<Question>) this.questions.clone();
        temp.addAll((ArrayList<Question>) this.vocab);
        return temp;
    }
    public void changeName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void addQuestion(Question question){
        if(question.isVocab()){
            this.vocab.add(question);
        }else{
            this.questions.add(question);
        }
    }
    public void addQuestion(Question question, boolean vocab){
        if(vocab){
            this.vocab.add(question);
        }else{
            this.questions.add(question);
        }
    }
    public void addVocab(Question question){
        this.vocab.add(question);
    }
    public void removeQuestion(Question question){
        this.questions.remove(question);
        this.vocab.remove(question);
    }
    public boolean hasQuestion( Question question){
        return this.questions.contains(question) || this.vocab.contains(question);
    }

    public Question getQuestion(){
        if(vocab.size()==0){
            return this.getQuestionOnly();
        }else if( questions.size() == 0){
            return this.getVocab();
        }else{
            int location = rand.nextInt(vocab.size()+questions.size());
            if(location < questions.size()){
                return questions.get(location);
            }else{
                return vocab.get(location-questions.size());
            }
        }
    }
    public Question getQuestion(char questionPool){
        switch(questionPool){
            case 'q': return this.getQuestion();
            case 'v': return this.getVocab();
            default: return this.getQuestion();
        }
    }

    public Question getVocab(){
        if(vocab.size() == 0){
            return null;
        }else{
            return this.vocab.get(rand.nextInt(vocab.size()));
        }
    }
    public Question getQuestionOnly(){
        if(questions.size()== 0){
            return null;
        }else{
            return this.questions.get(rand.nextInt(questions.size()));
        }
    }
    public int questionNumber(){
        return vocab.size()+questions.size();
    }
    public int questionNumber(char mnenonic){
        switch(mnenonic){
            case 'q': return questions.size();
            case 'v': return vocab.size();
            default: return questions.size()+vocab.size();

        }
    }

    public int questionOnlyNumber(){
        return questions.size();
    }
    public int vocabNumber(){
        return vocab.size();
    }
    @SuppressWarnings("unchecked")
    public ArrayList<Question> allQuestionsForPrint(){
        ArrayList<Question> tempArrayList = (ArrayList<Question>) this.questions.clone();
        for(int i=0; i<this.vocab.size();i++){
            tempArrayList.add(this.vocab.get(i));
        }
        return tempArrayList;
    }


}


