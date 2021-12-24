/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/11/21, 4:32 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/11/21, 12:54 AM
 */

package com.gwabs.GOLDEN_ODDS.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.gwabs.GOLDEN_ODDS.R;

public class LoginAndSignUp extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_sign_up);
        progressDialog = new ProgressDialog(LoginAndSignUp.this);
        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.UserName);
        edtPassword = findViewById(R.id.password);

        Button btnLogin = findViewById(R.id.btnlgin);
        Button btnSignup = findViewById(R.id.btnsignup);
        TextView txtForgetPassword = findViewById(R.id.TxtForgetPasword);

        txtForgetPassword.setOnClickListener(this::onClick);


        btnLogin.setOnClickListener(v -> {

            if (TextUtils.isEmpty(edtEmail.getText().toString())) {

                edtEmail.setError("please enter your email");

            } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {

                edtEmail.setError("please enter your password");

            } else if (TextUtils.isEmpty(edtEmail.getText().toString()) && TextUtils.isEmpty(edtPassword.getText().toString())) {

                edtEmail.setError("Enter your user name and password to signup");


            } else if (edtPassword.getText().length() < 4) {

                edtPassword.setError("Password should be more than 4 characters");

            } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {

                edtEmail.setError("Enter a Valid Email");

            } else {

                String Email, password;
                Email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                progressDialog.setMessage(Email + " Please wait..."); // Setting Message
                progressDialog.setTitle("Logging"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                SignInUser(Email, password);
            }


        });


        btnSignup.setOnClickListener(v -> {

            if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {

                Toast.makeText(getApplicationContext(), "please enter your email and password", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(edtEmail.getText().toString()) && TextUtils.isEmpty(edtPassword.getText().toString())) {

                Toast.makeText(getApplicationContext(), "Enter your user name and password to Sign Up", Toast.LENGTH_SHORT).show();
            } else if (edtPassword.getText().length() < 4 && !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                Toast.makeText(LoginAndSignUp.this, "Enter a Valid Email," + "\nPassword should be more than 4 characters", Toast.LENGTH_SHORT).show();
            } else if (edtPassword.getText().length() < 4) {
                Toast.makeText(LoginAndSignUp.this, "Password can't be less then 4 characters", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                Toast.makeText(LoginAndSignUp.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            } else {
                confirmPassword();
            }

        });


    }


    private void SignInUser(String Email, String Password) {

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(task -> {


                    if (task.isSuccessful()) {
                        Toast.makeText(LoginAndSignUp.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginAndSignUp.this, HOME.class);
                        startActivity(intent);
                        finish();

                    } else {
                        progressDialog.dismiss();


                    }


                }).addOnFailureListener(e -> Toast.makeText(LoginAndSignUp.this, "Login failed " + e.getMessage(), Toast.LENGTH_SHORT).show());

    }


    private void SignUpNewUser(String Email, String Password) {

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Signup Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), HOME.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //  Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(e -> Toast.makeText(LoginAndSignUp.this, "failed to Signup " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void sendPasswordRequest() {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Password Reset");
        builder.setMessage("Enter your Registerd Email and click send to send a password reset link");
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.custumdialogpreset,
                        null);
        builder.setView(customLayout);
        builder.setCancelable(true);
        EditText Email = customLayout.findViewById(R.id.resetEmail);
        builder.setPositiveButton("Send Password Reset", (dialog, which) -> {
            if (TextUtils.isEmpty(Email.getText().toString())) {
                Toast.makeText(getApplicationContext(), "please enter your email", Toast.LENGTH_SHORT).show();
            } else if ((!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches())) {
                Toast.makeText(LoginAndSignUp.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            } else {
                String emailAddress = Email.getText().toString();
                mAuth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Password reset link has been sent to your email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No User record found", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
        AlertDialog dialog
                = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg);
    }

    private void confirmPassword() {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Password");

        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.confirmpassword,
                        null);
        builder.setView(customLayout);
        builder.setCancelable(true);
        EditText cpssword = customLayout.findViewById(R.id.cPassword);
        builder.setPositiveButton("OK", (dialog, which) -> {
            if (TextUtils.isEmpty(cpssword.getText().toString())) {

                Toast.makeText(LoginAndSignUp.this, "please enter the password you just created", Toast.LENGTH_SHORT).show();

            } else if (!cpssword.getText().toString().equals(edtPassword.getText().toString())) {

                Toast.makeText(LoginAndSignUp.this, "Password Not Match", Toast.LENGTH_SHORT).show();

            } else {
                String Email, password;
                Email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                progressDialog.setMessage(Email + " Please wait..."); // Setting Message
                progressDialog.setTitle("Signing Up"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                SignUpNewUser(Email, password);
            }
        });
        AlertDialog dialog
                = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg);


    }

    private void onClick(View v) {

        //noinspection CatchMayIgnoreException
        try {
            sendPasswordRequest();
        } catch (Exception e) {
            //noinspection ResultOfMethodCallIgnored
            e.getMessage();
        }
    }
}