package com.royaleducenter.student.api;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.royaleducenter.student.ui.LoginActivity;
import com.royaleducenter.student.utils.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final SessionManager sessionManager;
    private final Context appContext;

    public AuthInterceptor(Context context) {
        this.sessionManager = new SessionManager(context);
        this.appContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String token = sessionManager.getToken();
        okhttp3.Request.Builder builder = chain.request().newBuilder();
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Response response = chain.proceed(builder.build());

        if (response.code() == 401) {
            sessionManager.logout();
            Intent intent = new Intent(appContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            appContext.startActivity(intent);
        }
        return response;
    }
}
