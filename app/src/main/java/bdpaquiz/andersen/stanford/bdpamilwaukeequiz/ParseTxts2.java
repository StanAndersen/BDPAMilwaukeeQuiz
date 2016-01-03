package bdpaquiz.andersen.stanford.bdpamilwaukeequiz;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Stan on 11/3/2014.
 */
public class ParseTxts2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        ArrayList<Chapter> chapters = parseChapters();
//        System.out.println(chapters.get(0).getName());
    }


    public static DatabaseHandler parseChapters(Context context){
        AssetManager assetManager = context.getAssets();
        String[] tempfile = null;
        try {
            tempfile = assetManager.list("");
        }catch(IOException e1){
            e1.printStackTrace();
        }
        String filePath="";
        ArrayList<Chapter> allChapters = new ArrayList<Chapter>();
        String longquestion = "";
        //File[] tempfile= new File("../BDPAGame").listFiles();
//        File[] tempfile= new File[fileNames.length];


        //InputStream tempfile= new InputStream("../Files0");
        ArrayList<String> location= new ArrayList<String>();
        System.out.println((tempfile.length));
        for(int i=0;i<tempfile.length;i++){
            //String t=tempfile[i].toString();
            String t = tempfile[i];
            //	System.out.println(tempfile[i]);
            if(t.toLowerCase().endsWith(".txt")){
                location.add(t);
            }
        }
        // find all txts in current folder w/o using a full path name  no

        DatabaseHandler dbHandler=new DatabaseHandler(context);


        for(int i=0;i<location.size();i++){
            longquestion = "";
            boolean turn=false;  //used to remove text before first question
            String[] holder;
            boolean prevocab=false;
            boolean vocab=false;
            //File file = new File(location.get(i));
            String currentChapter = location.get(i).substring(0,location.get(i).length()-4);
            dbHandler.addChapter(currentChapter);

            try {
                InputStream file= assetManager.open(location.get(i));
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if(!turn){
                        if(line.contains("[q]") || line.contains("[Q]")||line.contains("[v]") || line.contains("[V]")){
                            vocab=true;
                            if(line.contains("[q]") || line.contains("[Q]")){
                                vocab=false;
                            }
                            holder =line.split("\\[q\\]|\\[Q\\]|\\[v\\]|\\[V\\]");
                            turn=true;
                            longquestion = holder[0];
                            if(holder.length > 1){
                                longquestion=holder[1];
                            }
                        }
                    }else{
                        if(line.contains("[q]") || line.contains("[Q]")||line.contains("[v]") || line.contains("[V]")){
                            prevocab=vocab;
                            vocab=true;
                            if(line.contains("[q]") || line.contains("[Q]")){
                                vocab=false;
                            }
                            holder =line.split("\\[q\\]|\\[Q\\]|\\[v\\]|\\[V\\]");
                            longquestion=longquestion+holder[0];
                            // holdChapter.addQuestion( splitQuestion(longquestion,prevocab),prevocab );

                            dbHandler.addQuestion(longquestion, currentChapter, prevocab);

                            longquestion = holder[0];
                            if( holder.length > 1){
                                longquestion = holder[1];
                            }

                        }else{
                            longquestion= longquestion+ " "+line;

                        }

                    }

                }
                scanner.close();
                if(turn){
                    dbHandler.addQuestion(longquestion, currentChapter, prevocab);

                }


            }catch(  //FileNotFoundException e){
                    //System.out.println(file + "Not Found");
                    IOException e1){
                e1.printStackTrace();
            }
            if(dbHandler.isEmpty(currentChapter)){
                dbHandler.deleteChapter(currentChapter);
            }

        }



        return dbHandler;
    }

}


