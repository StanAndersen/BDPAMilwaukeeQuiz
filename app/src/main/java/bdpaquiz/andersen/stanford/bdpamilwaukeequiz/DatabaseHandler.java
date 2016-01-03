package bdpaquiz.andersen.stanford.bdpamilwaukeequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

/**
 * Created by Stan on 8/18/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "Chapters";

    // Contacts table name
    private ArrayList<String> tableChapter = new ArrayList<String>();
    private ArrayList<String> tableChapterBackup=new ArrayList<String>();

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String QUESTION = "questionStringToBeParsed";
    private static final String VOCAB="vocab";
    private static  final String DONE="done";
    private static final String ATTEMPTS="attempts";
    private static final String CORRECT ="CORRECT";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
        tableChapter.add("Remove");
        String CREATE_TABLE_CHAPTERS = "CREATE TABLE " + tableChapter + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + QUESTION + " TEXT,"
                + ANSWER + " TEXT," + OPTIONS + "TEXT," +VOCAB+"TEXT,"+REASON+"TEXT,"+ ")";
        db.execSQL(CREATE_TABLE_CHAPTERS);
        */
    }

    // Creating Tables
    public void addChapter(String currentChapter) {
        currentChapter = modifyString(currentChapter);
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " +currentChapter);
//        db.execSQL("DROP TABLE IF EXISTS " + "\""+currentChapter.concat("Backup")+"\"");

        tableChapter.add(currentChapter);
        String CREATE_TABLE_CHAPTERS = "CREATE TABLE " +currentChapter +
                " ( "+ KEY_ID + " INTEGER PRIMARY KEY, "
                + QUESTION + " TEXT, "
                + VOCAB+" TEXT, "
                +DONE+" TEXT, "
                +ATTEMPTS+  " INTEGER, "
                +CORRECT+" TEXT " +" )";
        db.execSQL(CREATE_TABLE_CHAPTERS);
 //       tableChapterBackup.add(currentChapter.concat("Backup"));
  //      String CREATE_TABLE_CHAPTERS_BACKUP = "CREATE TABLE " + "\""+ currentChapter.concat("Backup")+"\"" + "("
  //              + KEY_ID + " INTEGER PRIMARY KEY," + QUESTION + " TEXT,"
//                + ANSWER + " TEXT," + OPTIONS + "TEXT," +VOCAB+"TEXT,"+REASON+"TEXT"+ ")";
//        db.execSQL(CREATE_TABLE_CHAPTERS_BACKUP);
        db.close();

    }
    /**
    public void onCreate(SQLiteDatabase db, ArrayList<String> currentChapters){
        for(int i =0; i<currentChapters.size(); i++) {
            onCreate(db, currentChapters.get(i));
        }

    }
     */

    /**
    public void addChapter(SQLiteDatabase db, String currentChapter){
        tableChapter.add(currentChapter);
        String CREATE_TABLE_CHAPTERS = "CREATE TABLE " + tableChapter + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + QUESTION + " TEXT,"
                + ANSWER + " TEXT," + OPTIONS + "TEXT," +VOCAB+"TEXT,"+REASON+"TEXT,"+ ")";
        db.execSQL(CREATE_TABLE_CHAPTERS);
        tableChapterBackup.add(currentChapter.concat("Backup"));
        String CREATE_TABLE_CHAPTERS_BACKUP = "CREATE TABLE " + tableChapterBackup + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + QUESTION + " TEXT,"
                + ANSWER + " TEXT," + OPTIONS + "TEXT," +VOCAB+"TEXT,"+REASON+"TEXT,"+ ")";
        db.execSQL(CREATE_TABLE_CHAPTERS_BACKUP);
    }
     */
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
/**
        for(int i=0;i<tableChapter.size();i++ ){
            db.execSQL("DROP TABLE IF EXISTS " + tableChapter.get(i));
            // Create tables again
            onCreate(db);
        }
*/
        //get table names

        ArrayList<String> table=new ArrayList<String>();
        String query= "SELECT * FROM sqlite_master WHERE type='table'"; //look into DUMP
        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            table.add(cursor.getString(cursor.getColumnIndex("name")));
            cursor.moveToNext();
        }
        //remove android_metadata
        table.remove("android_metadata");
        for(int i = 0; i<table.size(); i++){
            db.execSQL("DROP TABLE IF EXISTS "+modifyString(table.get(i)));
        }

//        db.execSQL("DROP TABLE");

    }

    //
    public void addQuestion(String question, String chapter, Boolean vocab) {
        question = modifyString(question);
        chapter = modifyString(chapter);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.QUESTION, question); // Question
        values.put(this.VOCAB, modifyString(Boolean.toString(vocab)));
        values.put(this.DONE, modifyString("FALSE"));
        values.put(this.ATTEMPTS, 0);
        values.put(this.CORRECT, modifyString("FALSE"));

        // Inserting Row
       db.insert(chapter,null,values);
       /** db.execSQL("INSERT INTO "+chapter+ " (KEY_ID, QUESTION, ANSWER, OPTIONS, VOCAB, REASON) "+
                "VALUES (" + KEY_ID + ", " + QUESTION+ ", " + ANSWER + ", " + OPTIONS + ", " + VOCAB + ", " + REASON + ")");
        */
        //db.insert(chapter.concat("Backup"), null, values);
        db.close(); // Closing database connection
    }

    // Get items in chapter
    public boolean isEmpty(String chapter) {
        chapter=modifyString(chapter);
        String countQuery = "SELECT  COUNT(*) FROM "+ chapter+" WHERE "+this.DONE+" = 'FALSE'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount()>0){
            return false;
        }else{
            return true;
        }

    }

    public void deleteChapter(String currentChapter){
        currentChapter=modifyString(currentChapter);
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + currentChapter);
        db.close();
    }


    public int totalQuestions(String chapter){
        chapter=modifyString(chapter);
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT  COUNT(*) FROM " + chapter;
        Cursor cursor = db.rawQuery(query, null);
        int temp=cursor.getCount();
        db.close();
        return temp;
    }

    public DatabaseQuestion getQuestion(String chapter){
        chapter=modifyString(chapter);
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT * FROM " + chapter+
                " WHERE "+this.DONE+" = ?"+
                "ORDER BY RANDOM()" +
                "LIMIT 1";
        Cursor cursor = db.rawQuery(query,new String[] {"'FALSE'"});
        //Random rand=new Random();

        if(cursor.getCount() == 0){
            db.close();
            return null;
        }else{
            //cursor.moveToPosition(rand.nextInt(cursor.getCount()));
            cursor.moveToFirst();
            int keyID = cursor.getInt(cursor.getColumnIndex(this.KEY_ID));
            String question = cursor.getString(cursor.getColumnIndex(this.QUESTION));
            boolean vocab = Boolean.parseBoolean( cursor.getString(cursor.getColumnIndex(this.VOCAB)));
            db.close();
            return new DatabaseQuestion( question, vocab, chapter, keyID);

        }
    }

    public void deactivateQuestion(DatabaseQuestion question){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(this.DONE, "1");
        db.update(question.getChapter(),args,this.KEY_ID+"=" + question.getKeyID(),null);

        db.close();
    }

    public String[] getAllChapterNames(){
        ArrayList<String> table= new ArrayList<String>();
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM sqlite_master WHERE type='table'"; //look into DUMP
        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            table.add(cursor.getString(cursor.getColumnIndex("name")));
            cursor.moveToNext();
        }
        db.close();
        table.remove("android_metadata");
        String[] temp = new String[table.size()];
        temp = table.toArray(temp);
        return temp;
    }

    private String modifyString(String input){
        input = DatabaseUtils.sqlEscapeString(input);
        return input;
    }

    public int activeQuestionNumber(String chapter){
        chapter = modifyString(chapter);
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT COUNT(*) FROM "+chapter + " WHERE "+this.DONE+ " = 'FALSE'"; //look into DUMP
        Cursor cursor = db.rawQuery(query,null);
        int temp =cursor.getCount();
        db.close();
        return temp;
    }
}
