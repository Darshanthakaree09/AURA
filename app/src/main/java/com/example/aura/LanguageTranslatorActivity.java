package com.example.aura;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.google.mlkit.common.model.DownloadConditions;
//import com.google.mlkit.nl.translate.Translator;
//import com.google.mlkit.nl.translate.TranslatorOptions;

public class LanguageTranslatorActivity extends AppCompatActivity {

    private EditText edtLanguage;
    private TextView translateLanguageTV;
    private Button translateLanguageBtn;

//    private Translator englishGermanTranslator;
//
//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_translator);
//
//        TranslatorOptions options = new TranslatorOptions.Builder()
//                .setSourceLanguage(com.google.mlkit.nl.translate.TranslateLanguage.ENGLISH)
//                .setTargetLanguage(com.google.mlkit.nl.translate.TranslateLanguage.GERMAN)
//                .build();
//
//        englishGermanTranslator = com.google.mlkit.nl.translate.Translation.getClient(options);
//
//        edtLanguage = findViewById(R.id.idEdtLanguage);
//        translateLanguageTV = findViewById(R.id.idTVTranslatedLanguage);
//        translateLanguageBtn = findViewById(R.id.idBtnTranslateLanguage);
//
//        translateLanguageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String textToTranslate = edtLanguage.getText().toString();
//                if (!textToTranslate.isEmpty()) {
//                    downloadModelAndTranslate(textToTranslate);
//                } else {
//                    Toast.makeText(LanguageTranslatorActivity.this, "Please enter text to translate", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
//
//    private void downloadModelAndTranslate(String text) {
//        DownloadConditions conditions = new DownloadConditions.Builder()
//                .requireWifi()
//                .build();
//
//        englishGermanTranslator.downloadModelIfNeeded(conditions)
//                .addOnSuccessListener(unused -> translateText(text))
//                .addOnFailureListener(e -> Toast.makeText(LanguageTranslatorActivity.this, "Model download failed", Toast.LENGTH_SHORT).show());
//    }
//
//    private void translateText(String text) {
//        englishGermanTranslator.translate(text)
//                .addOnSuccessListener(translatedText -> translateLanguageTV.setText(translatedText))
//                .addOnFailureListener(e -> Toast.makeText(LanguageTranslatorActivity.this, "Translation failed", Toast.LENGTH_SHORT).show());
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (englishGermanTranslator != null) {
//            englishGermanTranslator.close(); // Close the translator to free up resources
//        }
//    }
}
