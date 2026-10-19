package com.royaleducenter.student.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveLoginData(String token, int userId, String fullName,
                              String studentCode, String email, String phone, String avatar) {
        editor.putString(Constants.KEY_TOKEN, token);
        editor.putInt(Constants.KEY_USER_ID, userId);
        editor.putString(Constants.KEY_FULL_NAME, fullName);
        editor.putString(Constants.KEY_STUDENT_CODE, studentCode);
        editor.putString(Constants.KEY_EMAIL, email);
        editor.putString(Constants.KEY_PHONE, phone);
        editor.putString(Constants.KEY_AVATAR, avatar);
        editor.apply();
    }

    public String getToken() {
        return prefs.getString(Constants.KEY_TOKEN, null);
    }

    public String getFullName() {
        return prefs.getString(Constants.KEY_FULL_NAME, "Student");
    }

    public String getStudentCode() {
        return prefs.getString(Constants.KEY_STUDENT_CODE, "");
    }

    public String getEmail() {
        return prefs.getString(Constants.KEY_EMAIL, "");
    }

    public String getPhone() {
        return prefs.getString(Constants.KEY_PHONE, "");
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
