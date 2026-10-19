package com.royaleducenter.student.api;

import com.royaleducenter.student.models.ApiResponse;
import com.royaleducenter.student.models.DashboardData;
import com.royaleducenter.student.models.EnrollRequest;
import com.royaleducenter.student.models.LoginRequest;
import com.royaleducenter.student.models.LoginResponse;
import com.royaleducenter.student.models.PaymentRequest;
import com.royaleducenter.student.models.Payment;
import com.royaleducenter.student.models.Course;
import com.royaleducenter.student.models.Enrollment;
import com.royaleducenter.student.models.TimetableSlot;
import com.royaleducenter.student.models.AttendanceData;
import com.royaleducenter.student.models.Exam;
import com.royaleducenter.student.models.Notice;
import com.royaleducenter.student.models.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("logout")
    Call<ApiResponse> logout();

    @GET("dashboard")
    Call<ApiResponse<DashboardData>> getDashboard();

    @GET("enrollments")
    Call<ApiResponse<List<Enrollment>>> getEnrollments();

    @GET("courses")
    Call<ApiResponse<List<Course>>> getAvailableCourses();

    @POST("enroll")
    Call<ApiResponse> enroll(@Body EnrollRequest request);

    @GET("timetable")
    Call<ApiResponse<List<TimetableSlot>>> getTimetable();

    @GET("attendance")
    Call<ApiResponse<AttendanceData>> getAttendance();

    @GET("exams")
    Call<ApiResponse<List<Exam>>> getExams();

    @GET("payments")
    Call<ApiResponse<List<Payment>>> getPayments();

    @POST("payments/submit")
    Call<ApiResponse> submitPayment(@Body PaymentRequest request);

    @GET("notices")
    Call<ApiResponse<List<Notice>>> getNotices();

    @GET("profile")
    Call<ApiResponse<Profile>> getProfile();
}
