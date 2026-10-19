package com.royaleducenter.student.ui;

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

import com.royaleducenter.student.R;
import com.royaleducenter.student.models.TimetableSlot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TimetableFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout timetableContainer;
    private TextView tvEmpty;
    private ProgressBar progressBar;

    private static final String[] DAYS = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        timetableContainer = view.findViewById(R.id.timetableContainer);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        progressBar = view.findViewById(R.id.progressBar);

        swipeRefresh.setOnRefreshListener(this::loadMockTimetable);
        loadMockTimetable();

        return view;
    }

    private void loadMockTimetable() {
        progressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            List<TimetableSlot> mockSlots = new ArrayList<>();
            // Database එකේ ඇති දත්ත වලට අනුකූලව:
            mockSlots.add(new TimetableSlot("Mathematics", "Sunil Silva", "02:00 PM - 04:00 PM", "Room A1", "monday"));
            mockSlots.add(new TimetableSlot("Economics", "Kamal Perera", "09:00 AM - 11:00 AM", "Room B1", "monday"));
            mockSlots.add(new TimetableSlot("Physics", "Kamal Perera", "02:00 PM - 04:00 PM", "Room A1", "tuesday"));
            mockSlots.add(new TimetableSlot("Chemistry", "Nimali Fernando", "02:00 PM - 04:00 PM", "Room A2", "wednesday"));
            mockSlots.add(new TimetableSlot("Accounting", "Kamal Perera", "09:00 AM - 11:00 AM", "Room B1", "wednesday"));

            showTimetable(mockSlots);
        }, 500);
    }

    private void showTimetable(List<TimetableSlot> slots) {
        timetableContainer.removeAllViews();
        if (slots.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        tvEmpty.setVisibility(View.GONE);
        Map<String, List<TimetableSlot>> dayMap = new LinkedHashMap<>();
        for (String day : DAYS) dayMap.put(day, new ArrayList<>());
        for (TimetableSlot slot : slots) {
            String day = slot.dayOfWeek != null ? slot.dayOfWeek.toLowerCase() : "";
            if (dayMap.containsKey(day)) dayMap.get(day).add(slot);
        }

        for (String day : DAYS) {
            List<TimetableSlot> daySlots = dayMap.get(day);
            if (daySlots == null || daySlots.isEmpty()) continue;

            View dayView = getLayoutInflater().inflate(R.layout.item_day_timetable, timetableContainer, false);
            ((TextView) dayView.findViewById(R.id.tvDay)).setText(day.substring(0, 1).toUpperCase() + day.substring(1));
            LinearLayout slotsContainer = dayView.findViewById(R.id.slotsContainer);

            for (TimetableSlot slot : daySlots) {
                View slotView = getLayoutInflater().inflate(R.layout.item_timetable_slot, slotsContainer, false);
                ((TextView) slotView.findViewById(R.id.tvTime)).setText(slot.timeString != null ? slot.timeString : slot.getFormattedTime());
                ((TextView) slotView.findViewById(R.id.tvSubject)).setText(slot.subjectName);
                ((TextView) slotView.findViewById(R.id.tvTeacher)).setText(slot.teacherName);
                ((TextView) slotView.findViewById(R.id.tvRoom)).setText("Room: " + slot.room);
                slotsContainer.addView(slotView);
            }
            timetableContainer.addView(dayView);
        }
    }
}
