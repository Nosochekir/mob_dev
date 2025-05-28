package ru.mirea.perovka.internalfilestorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText dateEditText, descriptionEditText;
    private Button saveButton;
    private final String FILENAME = "historic_date.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateEditText = findViewById(R.id.dateEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToInternalStorage();
            }
        });
    }

    private void saveToInternalStorage() {
        String date = dateEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (date.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        String content = "Дата: " + date + "\nОписание: " + description;

        try (FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
            Toast.makeText(this, "Данные сохранены во внутреннее хранилище", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
        }
    }
}