package com.royaleducenter.student.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.royaleducenter.student.R;
import com.royaleducenter.student.models.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout examsContainer;
    private TextView tvEmpty;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Exams & Results");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        swipeRefresh = findViewById(R.id.swipeRefresh);
        examsContainer = findViewById(R.id.examsContainer);
        tvEmpty = findViewById(R.id.tvEmpty);
        progressBar = findViewById(R.id.progressBar);

        swipeRefresh.setOnRefreshListener(this::loadMockExams);
        loadMockExams();
    }

    private void loadMockExams() {
        progressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            List<Exam> mockExams = new ArrayList<>();
            // seed.sql දත්ත අනුව:
            Exam e1 = new Exam("Mathematics Mid-Term", "Combined Maths", 85.0, 100);
            e1.examDate = "2024-07-25 14:00";
            e1.grade = "A";
            e1.resultStatus = "pass";
            mockExams.add(e1);

            Exam e2 = new Exam("Physics Mid-Term", "Physics", 72.0, 100);
            e2.examDate = "2024-07-26 14:00";
            e2.grade = "B";
            e2.resultStatus = "pass";
            mockExams.add(e2);

            Exam e3 = new Exam("Economics Mid-Term", "Economics", 0.0, 75);
            e3.examDate = "2024-07-27 09:00";
            e3.resultStatus = "pending";
            mockExams.add(e3);

            showExams(mockExams);
        }, 500);
    }

    private void showExams(List<Exam> exams) {
        examsContainer.removeAllViews();
        if (exams.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        tvEmpty.setVisibility(View.GONE);

        for (Exam exam : exams) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_exam, examsContainer, false);
            ((TextView) view.findViewById(R.id.tvExamTitle)).setText(exam.title);
            ((TextView) view.findViewById(R.id.tvSubject)).setText(exam.subjectName);
            ((TextView) view.findViewById(R.id.tvExamDate)).setText(exam.examDate);

            TextView tvStatus = view.findViewById(R.id.tvStatus);
            TextView tvMarks = view.findViewById(R.id.tvMarks);
            TextView tvGrade = view.findViewById(R.id.tvGrade);

            if (!"pending".equals(exam.resultStatus)) {
                tvMarks.setText("Marks: " + exam.marksObtained.intValue() + "/" + exam.totalMarks);
                tvGrade.setText(exam.grade);
                tvStatus.setText("Passed");
                tvStatus.setBackgroundColor(0xFF4CAF50);
            } else {
                tvMarks.setText("Marks: -");
                tvGrade.setText("-");
                tvStatus.setText("Pending");
                tvStatus.setBackgroundColor(0xFFFF9800);
            }
            examsContainer.addView(view);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
