package com.example.project_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button btnLogout,btnWrite;
    FirebaseAuth FBauth;
    FileList FL;
    RecyclerView ReView;
    RecyclerView.Adapter RAdapter;
    RecyclerView.LayoutManager LayoutManager;
    private String File_Name_List = "save.txt";

    ArrayList<String> FileNameList=new ArrayList<String>();
    ArrayList<String> FileUIDList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FL=(FileList)getApplicationContext();

        btnLogout = findViewById(R.id.BTNlogoutMain);
        btnWrite = findViewById(R.id.BTNFileMain);
        FBauth = FirebaseAuth.getInstance();
        ReView = (RecyclerView) findViewById(R.id.RVFilesMain);

        ReView.setHasFixedSize(true);

        LayoutManager = new LinearLayoutManager(this);
        ReView.setLayoutManager(LayoutManager);
        load();
        trimonlySameID();
        Log.d(TAG, "onCreate: fileList" + FileNameList.size());
        RAdapter=new FileRVAdapter(this,FileNameList,FileUIDList);
        ReView.setAdapter(RAdapter);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FBauth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xintent =new Intent(getApplicationContext(), FileView.class);
                xintent.putExtra("FileName","NewFile.txt");
                xintent.putExtra("FileColor","#F3ECDF");
                startActivity(xintent);
            }
        });

    }

    public void load(){
        Log.d(TAG, "load");
        FileInputStream fis = null;

        try {
            fis = openFileInput(File_Name_List);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            int counter=1;
            while((text = br.readLine())!= null){
                Log.d(TAG, "load: "+ counter+" " +text);
                if(counter%2==1)FileNameList.add(text);
                else if(counter%2==0)FileUIDList.add(text);
                sb.append(text).append("\n");
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
    }

    public void delete123(View v){
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
                if(counter==1){}
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
    }

    private void trimonlySameID(){
        int leng=FileNameList.size();
        for(int i=0;i<leng;i++){
            if(!FileUIDList.get(i).equals(FBauth.getCurrentUser().getUid().toString())){
                FileUIDList.remove(i);
                FileNameList.remove(i);
                i--;
                leng--;
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }
}
