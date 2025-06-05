package ru.mirea.perovka.mireaproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileWork extends Fragment {

    private static final String EMPTY_FILENAME_MESSAGE = "Введите имя файла";
    private static final String SAVE_SUCCESS_MESSAGE = "Файл сохранён";
    private static final String SAVE_ERROR_MESSAGE = "Ошибка при сохранении";
    private static final String LOAD_SUCCESS_MESSAGE = "Файл загружен";
    private static final String LOAD_ERROR_MESSAGE = "Ошибка при чтении файла";
    private static final String FILE_NOT_FOUND_MESSAGE = "Файл не найден или ошибка чтения.";

    private EditText filenameInput;
    private EditText contentInput;
    private TextView fileContentDisplay;
    private Button saveButton;
    private Button loadButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file_work, container, false);
        initializeViews(rootView);
        setupButtonListeners();
        return rootView;
    }

    private void initializeViews(View view) {
        filenameInput = view.findViewById(R.id.editFileName);
        contentInput = view.findViewById(R.id.editTextContent);
        fileContentDisplay = view.findViewById(R.id.tvFileContent);
        saveButton = view.findViewById(R.id.btnSaveToFile);
        loadButton = view.findViewById(R.id.btnLoadFromFile);
    }

    private void setupButtonListeners() {
        saveButton.setOnClickListener(v -> handleFileSave());
        loadButton.setOnClickListener(v -> handleFileLoad());
    }

    private void handleFileSave() {
        String filename = filenameInput.getText().toString().trim();
        String content = contentInput.getText().toString();

        if (filename.isEmpty()) {
            showToast(EMPTY_FILENAME_MESSAGE);
            return;
        }

        try (FileOutputStream fos = requireContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            showToast(SAVE_SUCCESS_MESSAGE);
        } catch (IOException e) {
            showToast(SAVE_ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void handleFileLoad() {
        String filename = filenameInput.getText().toString().trim();

        if (filename.isEmpty()) {
            showToast(EMPTY_FILENAME_MESSAGE);
            return;
        }

        try (FileInputStream fis = requireContext().openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder contentBuilder = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }

            String fileContent = contentBuilder.toString();
            fileContentDisplay.setText(fileContent);
            contentInput.setText(fileContent);
            showToast(LOAD_SUCCESS_MESSAGE);

        } catch (IOException e) {
            fileContentDisplay.setText(FILE_NOT_FOUND_MESSAGE);
            showToast(LOAD_ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}