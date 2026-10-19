package com.royaleducenter.student.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.royaleducenter.student.R;
import com.royaleducenter.student.models.Payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PaymentsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout paymentsContainer;
    private TextView tvEmpty;
    private ProgressBar progressBar;
    private MaterialAutoCompleteTextView spPaymentType, spPaymentMethod;
    private TextInputEditText etAmount, etRemarks;
    private MaterialButton btnSubmit;

    private static final List<String> PAYMENT_TYPES = Arrays.asList("registration", "course_fee", "monthly_fee", "exam_fee", "other");
    private static final List<String> PAYMENT_METHODS = Arrays.asList("cash", "online", "bank_transfer", "card");
    private static final Map<String, String> TYPE_LABELS = Map.of(
            "registration", "Registration Fee", "course_fee", "Course Fee",
            "monthly_fee", "Monthly Fee", "exam_fee", "Exam Fee", "other", "Other");
    private static final Map<String, String> METHOD_LABELS = Map.of(
            "cash", "Cash", "online", "Online", "bank_transfer", "Bank Transfer", "card", "Card");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Payments");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        swipeRefresh = findViewById(R.id.swipeRefresh);
        paymentsContainer = findViewById(R.id.paymentsContainer);
        tvEmpty = findViewById(R.id.tvEmpty);
        progressBar = findViewById(R.id.progressBar);
        spPaymentType = findViewById(R.id.spPaymentType);
        spPaymentMethod = findViewById(R.id.spPaymentMethod);
        etAmount = findViewById(R.id.etAmount);
        etRemarks = findViewById(R.id.etRemarks);
        btnSubmit = findViewById(R.id.btnSubmit);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                PAYMENT_TYPES.stream().map(TYPE_LABELS::get).toArray(String[]::new));
        spPaymentType.setAdapter(typeAdapter);

        ArrayAdapter<String> methodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                PAYMENT_METHODS.stream().map(METHOD_LABELS::get).toArray(String[]::new));
        spPaymentMethod.setAdapter(methodAdapter);

        btnSubmit.setOnClickListener(v -> submitMockPayment());
        swipeRefresh.setOnRefreshListener(this::loadMockPayments);
        loadMockPayments();
    }

    private void submitMockPayment() {
        if (TextUtils.isEmpty(etAmount.getText())) {
            etAmount.setError("Amount is required");
            return;
        }
        btnSubmit.setEnabled(false);
        btnSubmit.setText("Submitting...");
        new android.os.Handler().postDelayed(() -> {
            Toast.makeText(this, "Payment Request Submitted Successfully (Mock)", Toast.LENGTH_SHORT).show();
            btnSubmit.setEnabled(true);
            btnSubmit.setText("Submit Payment");
            etAmount.setText("");
            etRemarks.setText("");
            loadMockPayments();
        }, 1000);
    }

    private void loadMockPayments() {
        progressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);

            List<Payment> mockPayments = new ArrayList<>();
            mockPayments.add(new Payment("registration", 5000.00, "2024-01-10", "completed", "cash"));
            mockPayments.add(new Payment("course_fee", 50000.00, "2024-01-10", "completed", "cash"));
            mockPayments.add(new Payment("monthly_fee", 7000.00, "2024-02-01", "completed", "cash"));
            mockPayments.add(new Payment("monthly_fee", 8000.00, "2024-03-01", "completed", "cash"));

            showPayments(mockPayments);
        }, 500);
    }

    private void showPayments(List<Payment> payments) {
        paymentsContainer.removeAllViews();
        if (payments.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        tvEmpty.setVisibility(View.GONE);

        for (Payment p : payments) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_payment, paymentsContainer, false);
            String typeLabel = TYPE_LABELS.getOrDefault(p.paymentType, p.paymentType);
            ((TextView) view.findViewById(R.id.tvPaymentType)).setText(typeLabel);
            ((TextView) view.findViewById(R.id.tvAmount)).setText(String.format("LKR %,.2f", p.finalAmount));
            ((TextView) view.findViewById(R.id.tvDate)).setText(p.paymentDate + " - " + METHOD_LABELS.getOrDefault(p.paymentMethod, p.paymentMethod));
            
            TextView tvStatus = view.findViewById(R.id.tvStatus);
            tvStatus.setText(p.status.substring(0, 1).toUpperCase() + p.status.substring(1));
            tvStatus.setBackgroundColor(p.status.equals("completed") ? 0xFF4CAF50 : 0xFFFF9800);
            paymentsContainer.addView(view);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
