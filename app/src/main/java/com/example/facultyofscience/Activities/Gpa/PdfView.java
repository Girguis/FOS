package com.example.facultyofscience.Activities.Gpa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.R;
import com.pdfview.PDFView;

public class PdfView extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view_layout);
        getSupportActionBar().hide();
        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset(getIntent().getExtras().getString("fileName")).show();
    }
}