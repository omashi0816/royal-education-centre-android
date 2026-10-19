package com.royaleducenter.student.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.royaleducenter.student.R;
import com.royaleducenter.student.models.Course;
import com.royaleducenter.student.models.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private TabLayout tabLayout;
    private FrameLayout tabContent;
    private ProgressBar progressBar;

    private static final int TAB_AVAILABLE = 0;
    private static final int TAB_ENROLLED = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabContent = view.findViewById(R.id.tabContentContainer);
        progressBar = view.findViewById(R.id.progressBar);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.available_courses));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.my_enrollments));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadCurrentTab();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        swipeRefresh.setOnRefreshListener(this::loadCurrentTab);
        loadMockAvailableCourses();

        return view;
    }

    private void loadCurrentTab() {
        if (tabLayout.getSelectedTabPosition() == TAB_AVAILABLE) loadMockAvailableCourses();
        else loadMockEnrollments();
    }

    private void loadMockAvailableCourses() {
        progressBar.setVisibility(View.VISIBLE);
        tabContent.removeAllViews();
        
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            List<Course> mockCourses = new ArrayList<>();
            mockCourses.add(new Course(1, "Advanced Level Science Stream", "AL Science 2024", 50000, 8000, 20, 40));
            mockCourses.add(new Course(2, "Advanced Level Commerce Stream", "AL Commerce 2024", 45000, 7000, 15, 50));
            mockCourses.add(new Course(4, "O/L Revision Class", "O/L Revision 2024", 25000, 5000, 80, 80));
            mockCourses.add(new Course(6, "ICT Certificate Course", "ICT Batch Jan-2024", 30000, 5000, 10, 25));

            showAvailableCourses(mockCourses);
        }, 500);
    }

    private void showAvailableCourses(List<Course> courses) {
        LinearLayout scrollContainer = new LinearLayout(requireContext());
        scrollContainer.setOrientation(LinearLayout.VERTICAL);
        tabContent.addView(scrollContainer);

        if (courses.isEmpty()) {
            showEmpty(R.string.no_courses_available);
            return;
        }

        for (Course course : courses) {
            View itemView = getLayoutInflater().inflate(R.layout.item_course, scrollContainer, false);
            ((TextView) itemView.findViewById(R.id.tvCourseName)).setText(course.courseName);
            ((TextView) itemView.findViewById(R.id.tvBatchName)).setText(course.batchName);
            ((TextView) itemView.findViewById(R.id.tvDescription)).setText(course.courseDescription != null ? course.courseDescription : "");
            ((TextView) itemView.findViewById(R.id.tvFees)).setText(String.format("Course: LKR %,.0f | Monthly: LKR %,.0f", course.courseFee, course.monthlyFee));
            ((TextView) itemView.findViewById(R.id.tvEnrolledCount)).setText(getString(R.string.enrolled_count, course.enrolledCount, course.maxStudents));

            MaterialButton btnEnroll = itemView.findViewById(R.id.btnEnroll);
            if (course.enrolledCount >= course.maxStudents) {
                btnEnroll.setText(R.string.batch_full);
                btnEnroll.setEnabled(false);
            } else {
                btnEnroll.setOnClickListener(v -> {
                    Toast.makeText(requireContext(), "Enrollment request sent (Mock)", Toast.LENGTH_SHORT).show();
                    btnEnroll.setEnabled(false);
                    btnEnroll.setText("Enrolled");
                });
            }
            scrollContainer.addView(itemView);
        }
    }

    private void loadMockEnrollments() {
        progressBar.setVisibility(View.VISIBLE);
        tabContent.removeAllViews();

        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            List<Enrollment> mockEnrollments = new ArrayList<>();
            mockEnrollments.add(new Enrollment("Advanced Level Science Stream", "AL Science 2024", "2024-01-15", "2025-12-31", "active"));
            
            showEnrollments(mockEnrollments);
        }, 500);
    }

    private void showEnrollments(List<Enrollment> enrollments) {
        LinearLayout scrollContainer = new LinearLayout(requireContext());
        scrollContainer.setOrientation(LinearLayout.VERTICAL);
        tabContent.addView(scrollContainer);

        if (enrollments.isEmpty()) {
            showEmpty(R.string.no_enrollments);
            return;
        }

        for (Enrollment en : enrollments) {
            View itemView = getLayoutInflater().inflate(R.layout.item_enrollment, scrollContainer, false);
            ((TextView) itemView.findViewById(R.id.tvCourseName)).setText(en.courseName);
            ((TextView) itemView.findViewById(R.id.tvBatchName)).setText(en.batchName);
            ((TextView) itemView.findViewById(R.id.tvDates)).setText(en.startDate + " to " + en.endDate);
            TextView tvStatus = itemView.findViewById(R.id.tvStatus);
            tvStatus.setText(en.status.substring(0, 1).toUpperCase() + en.status.substring(1));
            tvStatus.setBackgroundColor(en.status.equals("active") ? 0xFF4CAF50 : 0xFFFF9800);
            tvStatus.setTextColor(0xFFFFFFFF);
            scrollContainer.addView(itemView);
        }
    }

    private void showEmpty(int messageId) {
        tabContent.removeAllViews();
        TextView tv = new TextView(requireContext());
        tv.setText(messageId);
        tv.setTextColor(0xFF757575);
        tv.setGravity(android.view.Gravity.CENTER);
        tv.setPadding(48, 48, 48, 48);
        tabContent.addView(tv);
    }
}
