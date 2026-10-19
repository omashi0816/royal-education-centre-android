package com.royaleducenter.student.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.royaleducenter.student.R;

public class MoreFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        view.findViewById(R.id.cardAttendance).setOnClickListener(v ->
                startActivity(new Intent(getContext(), AttendanceActivity.class)));

        view.findViewById(R.id.cardExams).setOnClickListener(v ->
                startActivity(new Intent(getContext(), ExamsActivity.class)));

        view.findViewById(R.id.cardPayments).setOnClickListener(v ->
                startActivity(new Intent(getContext(), PaymentsActivity.class)));

        view.findViewById(R.id.cardNotices).setOnClickListener(v ->
                startActivity(new Intent(getContext(), NoticesActivity.class)));

        view.findViewById(R.id.cardProfile).setOnClickListener(v ->
                startActivity(new Intent(getContext(), ProfileActivity.class)));

        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showLogoutConfirm();
            }
        });

        return view;
    }
}
