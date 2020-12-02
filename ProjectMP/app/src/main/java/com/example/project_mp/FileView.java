package com.example.project_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FileView extends AppCompatActivity {
    private static final String TAG = "FileView";
    private String File_Name = "";
    private String File_Name_List = "save.txt";
    private String userId = "none";

    FirebaseAuth FBauth;
    EditText ET_files,ET_filename;
    Button btnSave,btnLoad;
    LinearLayout LayoutAll;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);
        ET_files= findViewById(R.id.ET_files);
        ET_filename=findViewById(R.id.ETfileName);
        LayoutAll=findViewById(R.id.FileViewLayout);

        FBauth= FirebaseAuth.getInstance();
        btnLoad=findViewById(R.id.BTNLoadFile);
        btnSave=findViewById(R.id.BTNSaveFile);

        ET_filename.setText(File_Name);
        getIncomingIntent();
        if(FBauth.getCurrentUser()!=null){
            userId=FBauth.getUid();
        }
    }
    
    public void save(View v){
        String text = ET_files.getText().toString();
        FileOutputStream fos = null;
        String SavedFileName;
        File file=getBaseContext().getFileStreamPath(File_Name);
        if(!File_Name.equals(ET_filename.getText().toString()) && file.exists()){
            SavedFileName = ET_filename.getText().toString();
            File directory = getFilesDir();
            File old = new File(directory,File_Name);
            File neew = new File(directory,SavedFileName);
            old.renameTo(neew);
        }else{
            SavedFileName=File_Name;
        }

        try {
            fos = openFileOutput(SavedFileName, MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "saved to " + getFilesDir() + "/" + SavedFileName, Toast.LENGTH_LONG).show();
            }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FileOutputStream fos2=null;
        ArrayList<String> FileString = new ArrayList<>();
        Log.d(TAG, "load");
        boolean hasOldFile = false;
        FileInputStream fis = null;
        try {
            Log.d(TAG, "load2: ");
            fis = openFileInput(File_Name_List);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String textL;
            int counter=1;
            Log.d(TAG, "load3: ");
            while((textL = br.readLine())!= null){
                Log.d(TAG, "load3.1: "+ counter+" " +textL);
                if(textL.equals(File_Name)){
                    FileString.add(SavedFileName);
                    hasOldFile=true;
                }
                else if(counter%2==1)FileString.add(textL);
                else if(counter%2==0)FileString.add(textL);
                sb.append(textL).append("\n");
                counter++;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!hasOldFile){
            FileString.add(SavedFileName);
            FileString.add(FBauth.getCurrentUser().getUid().toString());
        }

        try {
            fos2 = openFileOutput(File_Name_List,MODE_PRIVATE);
            String TextSave = "";
            for(int i=0;i<FileString.size();i++){
                Log.d(TAG, "save: 321"+FileString.get(i));
                TextSave+=FileString.get(i);
                TextSave+="\n";
                Log.d(TAG, "save: 3211"+TextSave);
            }
            Log.d(TAG, "save 3232: " + TextSave + FileString.size());
            fos2.write(TextSave.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                fos2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File_Name=SavedFileName;
    }

    public void load(View v){
        FileInputStream fis = null;

        try {
            fis = openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine())!= null){
                sb.append(text).append("\n");
            }
            ET_files.setText("");
            ET_files.setText(sb.toString());
        }catch (FileNotFoundException e){
            e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void delete(View v){
        FileOutputStream fos2=null;
        ArrayList<String> FileString = new ArrayList<>();
        boolean hasOldFile = false;
        FileInputStream fis = null;
        try {
            fis = openFileInput(File_Name_List);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String textL;
            int counter=1;
            boolean skipnext=false;
            while((textL = br.readLine())!= null){
                if(skipnext){
                    skipnext=false;
                }
                else if(textL.equals(File_Name)){
                    skipnext=true;
                }
                else if(counter%2==1)FileString.add(textL);
                else if(counter%2==0)FileString.add(textL);
                sb.append(textL).append("\n");
                counter++;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            fos2 = openFileOutput(File_Name_List,MODE_PRIVATE);
            String TextSave = "";
            for(int i=0;i<FileString.size();i++){
                Log.d(TAG, "save: 321"+FileString.get(i));
                TextSave+=FileString.get(i);
                TextSave+="\n";
                Log.d(TAG, "save: 3211"+TextSave);
            }
            Log.d(TAG, "save 3232: " + TextSave + FileString.size());
            fos2.write(TextSave.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                fos2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file = new File(getFilesDir(),File_Name);
        file.delete();
        finish();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: Start");
        if(getIntent().hasExtra("FileName") && getIntent().hasExtra("FileColor")){
                File_Name=getIntent().getStringExtra("FileName");
                ET_filename.setText(File_Name);
                if(File_Name.equals("NewFile.txt")){File_Name="";}
            Log.d(TAG, "getIncomingIntent: got extra " + File_Name);
                load(null);
                LayoutAll.setBackgroundColor(Color.parseColor(getIntent().getStringExtra("FileColor")));
        }
    }
}
