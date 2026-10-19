package com.royaleducenter.student.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.royaleducenter.student.R;
import com.royaleducenter.student.models.Notice;

import java.util.ArrayList;
import java.util.List;

public class NoticesActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout noticesContainer;
    private TextView tvEmpty;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("All Notices");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        swipeRefresh = findViewById(R.id.swipeRefresh);
        noticesContainer = findViewById(R.id.noticesContainer);
        tvEmpty = findViewById(R.id.tvEmpty);
        progressBar = findViewById(R.id.progressBar);

        swipeRefresh.setOnRefreshListener(this::loadMockNotices);
        loadMockNotices();
    }

    private void loadMockNotices() {
        progressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            List<Notice> mockNotices = new ArrayList<>();
            // seed.sql දත්ත අනුව:
            mockNotices.add(new Notice("Welcome to Royal Education Center", "We are pleased to welcome all new students to our institution. Please check your timetables and attend classes regularly.", "2024-07-01", true));
            mockNotices.add(new Notice("Mid-Term Examination Schedule", "Mid-term examinations will begin on July 25th. Please check the exam schedule on your dashboard.", "2024-07-05", true));
            mockNotices.add(new Notice("Holiday Notice", "The center will be closed on July 15th for a public holiday.", "2024-07-10", false));
            mockNotices.add(new Notice("Physics Extra Class", "An extra physics revision class will be conducted on Saturday at 2 PM for AL Science batch.", "2024-07-12", false));

            showNotices(mockNotices);
        }, 500);
    }

    private void showNotices(List<Notice> notices) {
        noticesContainer.removeAllViews();
        if (notices.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        tvEmpty.setVisibility(View.GONE);

        for (Notice notice : notices) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_notice_full, noticesContainer, false);
            ((TextView) view.findViewById(R.id.tvNoticeTitle)).setText(notice.title);
            ((TextView) view.findViewById(R.id.tvNoticeDate)).setText(notice.postedAt);
            ((TextView) view.findViewById(R.id.tvNoticeContent)).setText(notice.content);
            view.findViewById(R.id.ivPin).setVisibility(notice.isPinned ? View.VISIBLE : View.GONE);
            noticesContainer.addView(view);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
