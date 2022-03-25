package com.example.facultyofscience;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyofscience.Adapters.GpaAdapter;
import com.example.facultyofscience.Models.SubjectGrade;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GpaCalcActivity extends AppCompatActivity {
    EditText hoursTxt,oldGpa,oldGpaHours,subName;
    TextView wantCalcCGpa,semsterGpa,cGpa;
    Button addBtn,calculateGPA;
    Spinner gradesList;
    RecyclerView enteredGradesLayout;
    Switch isCGpa;
    GridLayout gridLayout;
    ArrayList<SubjectGrade> subjectGrades =new ArrayList<SubjectGrade>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpa_layout);
        getSupportActionBar().hide();
        enteredGradesLayout=findViewById(R.id.enteredGradesLayout);
        GpaAdapter adapter=new GpaAdapter(this,subjectGrades);
        enteredGradesLayout.setAdapter(adapter);
        enteredGradesLayout.setLayoutManager(new LinearLayoutManager(this));
        hoursTxt=findViewById(R.id.hours);
        subName=findViewById(R.id.subName);
        addBtn=findViewById(R.id.addBtn);
        gradesList=findViewById(R.id.gradesList);
        calculateGPA=findViewById(R.id.calculateGPA);
        wantCalcCGpa=findViewById(R.id.wantCalcCGpa);
        oldGpa=findViewById(R.id.oldGpa);
        oldGpaHours=findViewById(R.id.oldGpaHours);
        isCGpa=findViewById(R.id.isCGpa);
        semsterGpa=findViewById(R.id.semsterGpa);
        cGpa=findViewById(R.id.cGpa);
        gridLayout=findViewById(R.id.gridLayout);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hoursTxt.getText().toString().isEmpty())
                    Toast.makeText(GpaCalcActivity.this,"برجاء ادخال عدد الساعات",Toast.LENGTH_SHORT).show();
                else
                {
                    String grade=gradesList.getSelectedItem().toString();
                    String name=subName.getText().toString();
                    int hours= Integer.parseInt(hoursTxt.getText().toString().isEmpty()?"0":hoursTxt.getText().toString());
                    subjectGrades.add(new SubjectGrade(hours,grade,name));
                }
                if(subjectGrades.size()>0) {
                    calculateGPA.setVisibility(View.VISIBLE);
                    wantCalcCGpa.setVisibility(View.VISIBLE);
                    isCGpa.setVisibility(View.VISIBLE);
                }
                else {
                    calculateGPA.setVisibility(View.INVISIBLE);
                    wantCalcCGpa.setVisibility(View.INVISIBLE);
                    isCGpa.setVisibility(View.INVISIBLE);
                    oldGpa.setVisibility(View.GONE);
                    oldGpaHours.setVisibility(View.GONE);
                    gridLayout.setVisibility(View.INVISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        });
        calculateGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    float _oldGpa=0;
                    int _hours=0;
                    if(isCGpa.isChecked())
                    {
                        if(oldGpaHours.getText().toString().isEmpty())
                        {
                            Toast.makeText(GpaCalcActivity.this,"برجاء ادخال عدد ساعات GPA التراكمى",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(oldGpa.getText().toString().isEmpty()) {
                            Toast.makeText(GpaCalcActivity.this, "برجاء ادخال GPA التراكمى", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(isCGpa.isChecked()&&!isGpaInRange(oldGpa)){
                            Toast.makeText(GpaCalcActivity.this,"المعدل التراكمى يجب ان يكون بين 0 ل 4",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            _oldGpa= Float.parseFloat(oldGpa.getText().toString().isEmpty()?"0":oldGpa.getText().toString());
                            _oldGpa=convertToFloatFixedDigits(_oldGpa,3);
                            _hours= Integer.parseInt(oldGpaHours.getText().toString().isEmpty()?"0":oldGpaHours.getText().toString());
                        }
                    }
                    int newHours=0;
                    float newGrades=0;
                    for (int i=0;i<subjectGrades.size();i++)
                    {
                        SubjectGrade sg=subjectGrades.get(i);
                        newGrades+=(GetGradeForGPA(sg.getGrade())*sg.getHours());
                        newHours+=sg.getHours();
                    }
                    float gpa =(float)(newGrades/(newHours*1.0));
                    gridLayout.setVisibility(View.VISIBLE);
                    String g="التقدير الفصلى "+convertToFloatFixedDigits(gpa,3) +" ("+GetGradeSymboleForGPA(gpa)+")";
                    semsterGpa.setText(g);
                    float c=calcCGPABySGPA(gpa,_oldGpa,newHours,_hours);
                    g="التقدير العام "+ c +" ("+GetGradeSymboleForGPA(c)+")";
                    cGpa.setText(g);
                    cGpa.setVisibility(View.VISIBLE);
                }

        });
        isCGpa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(!isChecked) {
                    oldGpa.setVisibility(View.GONE);
                    oldGpaHours.setVisibility(View.GONE);
                }
                else
                {
                    oldGpa.setVisibility(View.VISIBLE);
                    oldGpaHours.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private boolean isGpaInRange(EditText t)
    {
        float gpa=Float.parseFloat(t.getText().toString());
        return gpa>=0.0&&gpa<=4.0f;
    }
    private float convertToFloatFixedDigits(float number, int digits)
    {
        DecimalFormat df=new DecimalFormat();
        df.setMaximumFractionDigits(digits);
        return Float.parseFloat(df.format(number));
    }
    private float calcCGPABySGPA(float gpa,float cGpa,int hours,int cHours) {
        float g= (float) ((cGpa * cHours + gpa * hours) / (hours*1.0 + cHours));
        return convertToFloatFixedDigits(g,3);
    }
    private String GetGradeSymboleForGPA(float gpa) {
        if (gpa == 4.00f)
            return "A";
        else if (gpa >= 3.67f)
            return "A-";
        else if (gpa >= 3.33f)
            return "B+";
        else if (gpa >= 3.00f)
            return "B";
        else if (gpa >= 2.67f)
            return "C+";
        else if (gpa >= 2.33f)
            return "C";
        else if (gpa >= 2.00f)
            return "D";
        else
            return "F";
    }
    private float GetGradeForGPA(String grade) {
        if (grade.equals("A"))
            return 4.0f;
        else if (grade.equals("A-"))
            return  3.67f;
        else if (grade.equals("B+"))
            return 3.33f;
        else if (grade.equals("B"))
            return 3.0f;
        else if (grade.equals("C+"))
            return 2.67f;
        else if (grade.equals("C"))
            return 2.33f;
        else if (grade.equals("D"))
            return 2.0f;
        else
            return 0;
    }
}
