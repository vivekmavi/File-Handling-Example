package com.example.paras.filehandling;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity
{

    EditText uName , pWord;
    Button save , next ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uName = (EditText) findViewById(R.id.username_ET);
        pWord = (EditText) findViewById(R.id.password_ET);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            Toast.makeText(this, "Permission checking", Toast.LENGTH_SHORT).show();
            checkPermission();
        }

    }
    public void save(View view)
    {
        String userNAme = uName.getText().toString();
        String passWord = pWord.getText().toString();
        String data = userNAme+" "+passWord+" ";

//        Toast.makeText(this, "sd card mounted state"+isExternalStorageReadable(), Toast.LENGTH_SHORT).show();




        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//        File folder = getExternalCacheDir();
        // creates a folder transport at /data/data/package.name/transport
        File myFile = new File(folder,"data");
        myFile.mkdir();

        myFile.setExecutable(true);
        myFile.setReadable(true);
        myFile.setWritable(true);

        String permissions = String.valueOf(myFile.canWrite())+myFile.canRead()+myFile.canRead();
        Toast.makeText(this, permissions, Toast.LENGTH_SHORT).show();

        File textFile = new File(myFile.getAbsolutePath()+"/data.txt");
        textFile.setExecutable(true);
        textFile.setReadable(true);
        textFile.setWritable(true);

        permissions = String.valueOf("textfile"+textFile.canWrite())+textFile.canRead()+textFile.canRead();

        Toast.makeText(this, permissions, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, myFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        if (textFile.isDirectory())
            Toast.makeText(this, "textfile is directory", Toast.LENGTH_SHORT).show();

        if (textFile.isFile())
            Toast.makeText(this, "textfile is file", Toast.LENGTH_SHORT).show();

        if (!myFile.mkdirs()) {
            Toast.makeText(this, "Directory not created", Toast.LENGTH_SHORT).show();
        }

        if(textFile.exists())
            Toast.makeText(this, "file exist", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "file does not exist", Toast.LENGTH_SHORT).show();

        writeData(textFile,data);
    }
    public void next(View view)
    {
        Intent intent = new Intent(MainActivity.this,LoadData.class);
        startActivity(intent);
    }
    protected void writeData(File myFile , String data)
    {
        FileOutputStream fileOutputStream = null;
        try
        {    if(!myFile.exists())
            {
                myFile.createNewFile();
            }

            fileOutputStream = openFileOutput( "data.txt" , Context.MODE_APPEND);
            BufferedWriter bfWritter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bfWritter.write(data);
            bfWritter.flush();
            Toast.makeText(this, "written at "+myFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                if(fileOutputStream!=null)
                    fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Checks if external storage is available to at least read */
    public String isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) &&
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return "read only";
        }
        else if (Environment.MEDIA_MOUNTED.equals(state))
        {
            return  "only mounted";
        }
        return "not mounted";
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    123);

        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case 123: {


                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)     {
                    //Peform your task here if any
                } else {

                    checkPermission();
                }
                return;
            }
        }
    }

}
