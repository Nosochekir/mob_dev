package ru.mirea.perovka.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences secureSharedPreferences;
    private EditText poetEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poetEditText = findViewById(R.id.poetEditText);
        Button saveButton = findViewById(R.id.saveSecureButton);

        try {
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            poetEditText.setText(secureSharedPreferences.getString("POET", ""));
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        saveButton.setOnClickListener(v -> {
            try {
                secureSharedPreferences.edit()
                        .putString("POET", poetEditText.getText().toString())
                        .apply();
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}