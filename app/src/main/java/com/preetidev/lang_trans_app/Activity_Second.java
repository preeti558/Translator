package com.preetidev.lang_trans_app;

import static com.google.mlkit.nl.translate.Translator.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;


public class Activity_Second extends AppCompatActivity {

    //Initialize widgets
    private Spinner fromSpinner,toSpinner;
    private EditText sourceEdt;
    private Button btn;
    private TextView translatedTV;

    //Source Array of Strings - Spinners data
    String[] fromLanguages={
            "from","English","Spanish","Arabic","Japanese","Hindi","Chinese","Macedonian"
            ,"Tamil","Greek","Portuguese","German","Italian","Turkish","Urdu","Dutch","Telgu"
    };
    String[] toLanguages={
            "to","English","Spanish","Arabic","Japanese","Hindi","Chinese","Macedonian"
            ,"Tamil","Greek","Portuguese","German","Italian","Turkish","Urdu","Dutch","Telgu"
    };

    //permissions
    private static final int REQUEST_CODE=1;
    String languageCode, fromLanguageCode ,toLanguageCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        fromSpinner=findViewById(R.id.idFromSpinner);
        toSpinner=findViewById(R.id.idToSpinner);
        sourceEdt=findViewById(R.id.Input);
        translatedTV=findViewById(R.id.output);
        btn=findViewById(R.id.transBtn);

        //spinner 1
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromLanguageCode=GetLanguageCode(fromLanguages[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter fromAdapter=new ArrayAdapter(this,
                R.layout.spinner_item, fromLanguages);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);

        //spinner 2
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toLanguageCode=GetLanguageCode(toLanguages[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter toAdapter=new ArrayAdapter(this,
                R.layout.spinner_item, toLanguages);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translatedTV.setText("");
                if(sourceEdt.getText().toString().isEmpty()){
                    Toast.makeText(Activity_Second.this,"Please Enter Your text",Toast.LENGTH_SHORT).show();

                } else if (fromLanguageCode.isEmpty()) {
                    Toast.makeText(Activity_Second.this,"Please Select Source Language",Toast.LENGTH_SHORT).show();

                } else if (toLanguageCode.isEmpty()) {
                    Toast.makeText(Activity_Second.this,"Please Select the Target Language",Toast.LENGTH_SHORT).show();

                }else {
                    Translatetext(fromLanguageCode,toLanguageCode,sourceEdt.getText().toString());
                }
            }
        });
    }

    private void Translatetext(String fromLanguageCode, String toLanguageCode, String src) {

        translatedTV.setText("Downloading Language Model....");
        try {
            TranslatorOptions options=new TranslatorOptions.Builder().setSourceLanguage(fromLanguageCode)
                    .setTargetLanguage(toLanguageCode).build();

            Translator translator= Translation.getClient(options);
            DownloadConditions conditions=new DownloadConditions.Builder().build();

            translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    translatedTV.setText("Translating....");
                    translator.translate(src).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            translatedTV.setText(s);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Activity_Second.this,"Fail to Translate",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Activity_Second.this,"Fail to Download the language",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private String GetLanguageCode(String language){
        String LanguageCode;

        //Arabic","Japanese","Hindi","Chinese","Macedonian "
        //            ,"Tamil","Greek","Portuguese","German","Italian","Turkish","Urdu","Dutch","Telgu"
        switch (language){
            case "English":
                languageCode= TranslateLanguage.ENGLISH;
                break;
            case "Spanish":
                languageCode=TranslateLanguage.SPANISH;
                break;
            case "Arabic":
                languageCode=TranslateLanguage.ARABIC;
                break;
            case "Japanese":
                languageCode=TranslateLanguage.JAPANESE;
                break;
            case "Hindi":
                languageCode=TranslateLanguage.HINDI;
                break;
            case "Chinese":
                languageCode=TranslateLanguage.CHINESE;
                break;
            case "Macedonian":
                languageCode=TranslateLanguage.MACEDONIAN;
                break;
            case "Tamil":
                languageCode=TranslateLanguage.TAMIL;
                break;
            case "Greek":
                languageCode=TranslateLanguage.GREEK;
                break;
            case "Portuguese":
                languageCode=TranslateLanguage.PORTUGUESE;
                break;
            case "German":
                languageCode=TranslateLanguage.GERMAN;
                break;
            case "Italian":
                languageCode=TranslateLanguage.ITALIAN;
                break;
            case "Turkish":
                languageCode=TranslateLanguage.TURKISH;
                break;
            case "Urdu":
                languageCode=TranslateLanguage.URDU;
                break;
            case "Telgu":
                languageCode=TranslateLanguage.TELUGU;
                break;
            case "Dutch":
                languageCode=TranslateLanguage.DUTCH;
                break;
            default:
                languageCode="";
        }


    return languageCode;
    }
}