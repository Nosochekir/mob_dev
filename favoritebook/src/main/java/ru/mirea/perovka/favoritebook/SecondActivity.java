package ru.mirea.perovka.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private EditText editBook;
    private EditText editQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editBook = findViewById(R.id.editBook);
        editQuote = findViewById(R.id.editQuote);

        TextView textBook = findViewById(R.id.textBook);
        TextView textQuote = findViewById(R.id.textQuote);

        Intent intent = getIntent();
        if (intent != null) {
            String book = intent.getStringExtra(MainActivity.BOOK_NAME_KEY);
            String quote = intent.getStringExtra(MainActivity.QUOTES_KEY);
            textBook.setText("Любимая книга: " + book);
            textQuote.setText("Цитата из книги: " + quote);
        }
    }

    public void sendBack(View view) {
        String book = editBook.getText().toString();
        String quote = editQuote.getText().toString();
        String result = String.format("Название Вашей любимой книги: %s. Цитата: %s", book, quote);

        Intent intent = new Intent();
        intent.putExtra(MainActivity.USER_MESSAGE, result);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}