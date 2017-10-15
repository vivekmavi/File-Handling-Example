package com.example.paras.filehandling;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.R.attr.bufferType;
import static android.R.attr.data;

public class LoadData extends AppCompatActivity {

    TextView sDataTextView ;
    Button loadData , addMore ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);

        sDataTextView = (TextView) findViewById(R.id.showData_TV);
    }
    public void load(View view)
    {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//        File folder = getExternalCacheDir();
        File myFile = new File(folder,"data");
        String permissions = String.valueOf(myFile.canWrite())+myFile.canRead()+myFile.canRead();
        Toast.makeText(this, permissions, Toast.LENGTH_SHORT).show();
        File textFile = new File(myFile.getAbsolutePath()+"/data.txt");
        permissions = String.valueOf(textFile.canWrite())+textFile.canRead()+textFile.canRead();
        Toast.makeText(this, permissions, Toast.LENGTH_SHORT).show();
        if(textFile.exists())
            Toast.makeText(this, "file exist", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "file does not exist", Toast.LENGTH_SHORT).show();
        String readData = read(textFile);
        sDataTextView.setText(readData);
    }
    public void addMore(View view)
    {
        Intent intent = new Intent(LoadData.this,MainActivity.class);
        startActivity(intent);
    }
    protected String read(File myFile)
    {
        StringBuffer stringBuffer = null;
        FileInputStream fileInputStream = null;

        try
        {
            fileInputStream = new FileInputStream(myFile);
            int read=-1;
            stringBuffer = new StringBuffer();

            while ((read = fileInputStream.read())!=-1)
            {
                stringBuffer.append((char) read);
            }
            return stringBuffer.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            if(fileInputStream!=null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

        return "no data";

    }
}
