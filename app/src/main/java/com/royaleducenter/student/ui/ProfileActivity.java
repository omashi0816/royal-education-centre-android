package com.royaleducenter.student.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.royaleducenter.student.R;
import com.royaleducenter.student.models.Profile;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout profileContainer;
    private TextView tvName, tvStudentCode;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvName = findViewById(R.id.tvName);
        tvStudentCode = findViewById(R.id.tvStudentCode);
        profileContainer = findViewById(R.id.profileContainer);
        progressBar = findViewById(R.id.progressBar);

        loadMockProfile();
    }

    private void loadMockProfile() {
        progressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            
            // seed.sql දත්ත අනුව:
            Profile p = new Profile();
            p.fullName = "Amal Wijesinghe";
            p.studentCode = "S0001";
            p.email = "amal@royaledu.com";
            p.phone = "+94 77 666 7777";
            p.dateOfBirth = "2006-05-15";
            p.gender = "male";
            p.address = "123 Temple Road, Colombo";
            p.city = "Colombo";
            p.school = "Royal College";
            p.educationLevel = "A/L";
            p.guardianName = "Sumith Wijesinghe";
            p.guardianPhone = "+94 77 111 0000";
            p.bloodGroup = "A+";
            p.nationality = "Sri Lankan";

            showProfile(p);
        }, 500);
    }

    private void showProfile(Profile p) {
        tvName.setText(p.fullName);
        tvStudentCode.setText(p.studentCode);

        profileContainer.removeAllViews();
        addField("Email", p.email);
        addField("Phone", p.phone);
        addField("Date of Birth", p.dateOfBirth);
        addField("Gender", "Male");
        addField("Address", p.address);
        addField("City", p.city);
        addField("School", p.school);
        addField("Education Level", p.educationLevel);
        addField("Guardian Name", p.guardianName);
        addField("Guardian Phone", p.guardianPhone);
        addField("Blood Group", p.bloodGroup);
        addField("Nationality", p.nationality);
    }

    private void addField(String label, String value) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_profile_field, profileContainer, false);
        ((TextView) view.findViewById(R.id.tvLabel)).setText(label);
        ((TextView) view.findViewById(R.id.tvValue)).setText(value != null ? value : "-");
        profileContainer.addView(view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
