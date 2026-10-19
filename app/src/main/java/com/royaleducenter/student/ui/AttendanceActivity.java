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
import com.royaleducenter.student.models.AttendanceData;
import com.royaleducenter.student.models.AttendanceRecord;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout recordsContainer;
    private TextView tvEmpty, tvPresent, tvLate, tvAbsent;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Attendance History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        swipeRefresh = findViewById(R.id.swipeRefresh);
        recordsContainer = findViewById(R.id.recordsContainer);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvPresent = findViewById(R.id.tvPresent);
        tvLate = findViewById(R.id.tvLate);
        tvAbsent = findViewById(R.id.tvAbsent);
        progressBar = findViewById(R.id.progressBar);

        swipeRefresh.setOnRefreshListener(this::loadMockAttendance);
        loadMockAttendance();
    }

    private void loadMockAttendance() {
        progressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            AttendanceData data = new AttendanceData();
            // Updated to use the correct inner class name: AttendanceStats
            data.stats = new AttendanceData.AttendanceStats();
            data.stats.present = 18;
            data.stats.late = 1;
            data.stats.absent = 1;

            data.records = new ArrayList<>();
            data.records.add(new AttendanceRecord("2024-07-01", "Combined Mathematics", "present"));
            data.records.add(new AttendanceRecord("2024-07-02", "Physics", "present"));
            data.records.add(new AttendanceRecord("2024-07-03", "Chemistry", "late"));

            populateAttendance(data);
        }, 500);
    }

    private void populateAttendance(AttendanceData data) {
        if (data.stats != null) {
            tvPresent.setText(String.valueOf(data.stats.present));
            tvLate.setText(String.valueOf(data.stats.late));
            tvAbsent.setText(String.valueOf(data.stats.absent));
        }

        recordsContainer.removeAllViews();
        if (data.records == null || data.records.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        tvEmpty.setVisibility(View.GONE);

        for (AttendanceRecord record : data.records) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_attendance, recordsContainer, false);
            ((TextView) view.findViewById(R.id.tvDate)).setText(record.date);
            ((TextView) view.findViewById(R.id.tvSubject)).setText(record.subjectName);
            TextView tvStatus = view.findViewById(R.id.tvStatus);
            
            String status = record.status != null ? record.status : "";
            if (!status.isEmpty()) {
                tvStatus.setText(status.substring(0, 1).toUpperCase() + status.substring(1));
            } else {
                tvStatus.setText("-");
            }

            int color = 0xFF757575;
            if (status.equals("present")) color = 0xFF4CAF50;
            else if (status.equals("late")) color = 0xFFFF9800;
            else if (status.equals("absent")) color = 0xFFF44336;
            tvStatus.setBackgroundColor(color);
            recordsContainer.addView(view);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
