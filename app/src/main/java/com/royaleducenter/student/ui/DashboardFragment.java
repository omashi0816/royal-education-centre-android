package com.royaleducenter.student.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.royaleducenter.student.R;
import com.royaleducenter.student.models.DashboardData;
import com.royaleducenter.student.models.Exam;
import com.royaleducenter.student.models.Notice;
import com.royaleducenter.student.models.TimetableSlot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private TextView tvActiveCourses, tvAttendanceRate, tvPendingAmount;
    private LinearLayout todayClassesContainer, resultsContainer, noticesContainer;
    private TextView tvNoClasses, tvNoResults, tvNoNotices;
    private ProgressBar progressBar;
    private MaterialButton btnPayNow;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        tvActiveCourses = view.findViewById(R.id.tvActiveCourses);
        tvAttendanceRate = view.findViewById(R.id.tvAttendanceRate);
        tvPendingAmount = view.findViewById(R.id.tvPendingAmount);
        todayClassesContainer = view.findViewById(R.id.todayClassesContainer);
        resultsContainer = view.findViewById(R.id.resultsContainer);
        noticesContainer = view.findViewById(R.id.noticesContainer);
        tvNoClasses = view.findViewById(R.id.tvNoClasses);
        tvNoResults = view.findViewById(R.id.tvNoResults);
        tvNoNotices = view.findViewById(R.id.tvNoNotices);
        progressBar = view.findViewById(R.id.progressBar);
        btnPayNow = view.findViewById(R.id.btnPayNow);

        btnPayNow.setOnClickListener(v -> startActivity(new Intent(requireContext(), PaymentsActivity.class)));

        view.findViewById(R.id.tvViewAllNotices).setOnClickListener(v -> 
            startActivity(new Intent(requireContext(), NoticesActivity.class)));

        swipeRefresh.setOnRefreshListener(this::loadMockDashboard);
        loadMockDashboard();

        return view;
    }

    private void loadMockDashboard() {
        progressBar.setVisibility(View.VISIBLE);
        
        // පද්ධතිය පරීක්ෂා කිරීමට සර්වර් එක වෙනුවට පරීක්ෂණ දත්ත ලබා දෙමු
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            DashboardData data = new DashboardData();
            data.stats = new DashboardData.Stats();
            data.stats.activeEnrollments = 2;
            data.stats.attendancePresent = 18;
            data.stats.attendanceTotal = 20;
            data.stats.pendingAmount = 7500.00;

            data.todayClasses = new ArrayList<>();
            data.todayClasses.add(new TimetableSlot("Combined Mathematics", "Kamal Perera", "08:00 AM - 10:00 AM", "Room A1"));
            data.todayClasses.add(new TimetableSlot("Physics", "Sunil Silva", "10:30 AM - 12:30 PM", "Room B2"));

            data.recentResults = new ArrayList<>();
            Exam e1 = new Exam("Unit Test 01", "Combined Maths", 85.0, 100);
            e1.grade = "A";
            data.recentResults.add(e1);

            data.notices = new ArrayList<>();
            data.notices.add(new Notice("Center Holiday", "The center will be closed on Poya day.", "2024-07-15"));
            data.notices.add(new Notice("Exam Fee Notice", "Please pay your mid-term exam fees before Friday.", "2024-07-18"));

            populateDashboard(data);
        }, 500);
    }

    private void populateDashboard(DashboardData data) {
        tvActiveCourses.setText(String.valueOf(data.stats.activeEnrollments));
        int rate = data.stats.attendanceTotal > 0 ? (data.stats.attendancePresent * 100 / data.stats.attendanceTotal) : 0;
        tvAttendanceRate.setText(rate + "%");
        tvPendingAmount.setText(String.format("LKR %,.2f", data.stats.pendingAmount));
        btnPayNow.setVisibility(data.stats.pendingAmount > 0 ? View.VISIBLE : View.GONE);

        todayClassesContainer.removeAllViews();
        if (!data.todayClasses.isEmpty()) {
            tvNoClasses.setVisibility(View.GONE);
            for (TimetableSlot slot : data.todayClasses) todayClassesContainer.addView(createClassItemView(slot));
        } else tvNoClasses.setVisibility(View.VISIBLE);

        resultsContainer.removeAllViews();
        if (!data.recentResults.isEmpty()) {
            tvNoResults.setVisibility(View.GONE);
            for (Exam exam : data.recentResults) resultsContainer.addView(createResultItemView(exam));
        } else tvNoResults.setVisibility(View.VISIBLE);

        noticesContainer.removeAllViews();
        if (!data.notices.isEmpty()) {
            tvNoNotices.setVisibility(View.GONE);
            for (Notice notice : data.notices) noticesContainer.addView(createNoticeItemView(notice));
        } else tvNoNotices.setVisibility(View.VISIBLE);
    }

    private View createClassItemView(TimetableSlot slot) {
        View view = getLayoutInflater().inflate(R.layout.item_class, todayClassesContainer, false);
        ((TextView) view.findViewById(R.id.tvSubject)).setText(slot.subjectName);
        ((TextView) view.findViewById(R.id.tvTime)).setText(slot.timeString != null ? slot.timeString : slot.getFormattedTime());
        ((TextView) view.findViewById(R.id.tvTeacher)).setText(slot.teacherName);
        ((TextView) view.findViewById(R.id.tvRoom)).setText("Room: " + slot.room);
        return view;
    }

    private View createResultItemView(Exam exam) {
        View view = getLayoutInflater().inflate(R.layout.item_result, resultsContainer, false);
        ((TextView) view.findViewById(R.id.tvExamTitle)).setText(exam.title);
        ((TextView) view.findViewById(R.id.tvSubject)).setText(exam.subjectName);
        ((TextView) view.findViewById(R.id.tvMarks)).setText(exam.marksObtained.intValue() + "/" + exam.totalMarks);
        ((TextView) view.findViewById(R.id.tvGrade)).setText(exam.grade);
        return view;
    }

    private View createNoticeItemView(Notice notice) {
        View view = getLayoutInflater().inflate(R.layout.item_notice, noticesContainer, false);
        ((TextView) view.findViewById(R.id.tvNoticeTitle)).setText(notice.title);
        ((TextView) view.findViewById(R.id.tvNoticeContent)).setText(notice.content);
        ((TextView) view.findViewById(R.id.tvNoticeDate)).setText(notice.postedAt);
        return view;
    }
}
