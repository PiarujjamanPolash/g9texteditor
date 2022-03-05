package com.cse11th.oop.g9editor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class createFile extends AppCompatActivity {

    EditText et_name, et_content;
    Button b_save;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_file);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        et_name = findViewById(R.id.et_name);
        et_content = findViewById(R.id.et_content);
        b_save = findViewById(R.id.b_save);

        //button functions
        b_save.setOnClickListener(view -> {
            String filename = et_name.getText().toString();
            String content = et_content.getText().toString();

            if (!filename.equals("") && !content.equals("")) {
                try {
                    saveTextAsFile(filename, content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveTextAsFile(String filename, @NonNull String content) throws IOException {
        String FileName = filename + ".txt";

        // Creating File
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FileName);

        // Writing in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this,"Yeah!!! We have Saved it", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"File Not Found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"OOPS ! We Can Not Save.", Toast.LENGTH_SHORT).show();
        }
    }

    // Taking File System Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}