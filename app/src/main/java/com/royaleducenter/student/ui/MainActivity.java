package com.royaleducenter.student.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.royaleducenter.student.R;
import com.royaleducenter.student.api.ApiClient;
import com.royaleducenter.student.api.ApiService;
import com.royaleducenter.student.models.ApiResponse;
import com.royaleducenter.student.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNav;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(sessionManager.getFullName());
        toolbar.setSubtitle(sessionManager.getStudentCode());
        setSupportActionBar(toolbar);

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(this::onNavItemSelected);

        if (savedInstanceState == null) {
            loadFragment(new DashboardFragment(), getString(R.string.nav_dashboard));
        }
    }

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {
            loadFragment(new DashboardFragment(), getString(R.string.nav_dashboard));
        } else if (id == R.id.nav_courses) {
            loadFragment(new CoursesFragment(), getString(R.string.nav_courses));
        } else if (id == R.id.nav_timetable) {
            loadFragment(new TimetableFragment(), getString(R.string.nav_timetable));
        } else if (id == R.id.nav_more) {
            loadFragment(new MoreFragment(), getString(R.string.nav_more));
        }
        return true;
    }

    private void loadFragment(Fragment fragment, String title) {
        toolbar.setTitle(title);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public void showLogoutConfirm() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.yes, (d, w) -> performLogout())
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void performLogout() {
        ApiService apiService = ApiClient.getService(this);
        apiService.logout().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                doLogout();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                doLogout();
            }
        });
    }

    private void doLogout() {
        sessionManager.logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
