package bdpaquiz.andersen.stanford.bdpamilwaukeequiz;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Stan on 11/3/2014.
 */
    public class DatabaseQuestion {


        /**
         *
         */
        private String question;
        private ArrayList<String> options;
        private String[] answer;
        private String reason;
        private boolean vocab;
        private String chapter;
        private int keyID;

        public DatabaseQuestion(String questionUnparsed, Boolean vocab, String chapter, int keyID) {

            this.keyID = keyID;
            this.chapter = chapter;
            splitQuestion(questionUnparsed, vocab);
        }

    private void splitQuestion(String nonParsed,boolean vocab){
        String[] holder1; String[] holder2;
        holder1 =nonParsed.split("\\[o\\]|\\[O\\]");
        String answer = "";
        String reason = "";
        for(int i = 0; i < holder1.length;i++){
            holder2 = holder1[i].split("\\[a\\]|\\[A\\]");
            holder1[i] = holder2[0];
            if(holder2.length>1){
                answer=holder2[1];
            }
            holder2 = holder1[i].split("\\[r\\]|\\[R\\]");
            holder1[i] = holder2[0];
            if(holder2.length>1){
                reason=holder2[1];
            }
        }
        holder2 = answer.split("\\[r\\]|\\[R\\]");
        if(holder2.length>1){
            answer = holder2[0];
            reason = holder2[1];
        }
        ArrayList<String> options = new ArrayList<String>();
        if(holder1.length>1){
            for(int i=1; i<holder1.length; i++){
                options.add(holder1[i]);
            }
        }
        this.question = holder1[0];
        this.answer = new String[] {answer};
        this.reason = reason;
        this.vocab = vocab;
        this.options= options;

    }


    public int getKeyID(){
        return this.keyID;
    }
    public String getChapter(){
        return this.chapter;
    }

    public String getQuestion() {
            return this.question;
        }

        public String[] getAnswer() {
            return this.answer;
        }

        public String[] getOptionsArray() {
            return this.options.toArray(new String[options.size()]);
        }

        public ArrayList<String> getOptions() {
            return this.options;
        }

        public String getReason() {
            return this.reason;
        }

        public boolean isVocab() {
            return this.vocab;
        }

        public int allOptionsLength() {
            return answer.length + options.size();
        }

 /**       public String print(int answerLocation) {
            String temp = this.question + "\n";
            ArrayList<String> shuffle = new ArrayList<String>();
            if (options.size() > 0) {
                for (int i = options.size(); i > 0; i--) {
                    shuffle.add(options.remove(rand.nextInt(i)));
                }
                this.options = shuffle;
            }
            int tempAlphabet = 0;
            for (int i = 0; i < this.options.size(); i++) {
                if (answerLocation == i) {
                    temp = temp + this.alphabet[tempAlphabet] + this.answer + "\n";
                    tempAlphabet++;
                }
                temp = temp + this.alphabet[tempAlphabet] + this.options.get(i) + "\n";
                tempAlphabet++;
            }
            if (answerLocation >= this.options.size()) {
                temp = temp + this.alphabet[tempAlphabet] + this.answer + "\n";
            }
            return temp;
        }
*/
        public String print() {
            String temp = question.concat("\n");
            if (options.size() > 0) {
                for (int i = 0; i < options.size(); i++) {
                    temp = temp + options.get(i) + "\n";
                }
            }
            return temp + this.answer;
        }


    }
