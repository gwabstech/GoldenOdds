/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 3:24 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:22 PM
 */

package com.gwabs.GOLDEN_ODDS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    sendPasswordRequest();
                }catch (Exception e){
                    e.getMessage().toString();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())){

                    Toast.makeText(getApplicationContext(),"please enter your email and password" ,Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(edtEmail.getText().toString())&& TextUtils.isEmpty(edtPassword.getText().toString())){

                    Toast.makeText(getApplicationContext(),"Enter your user name and password to signU",Toast.LENGTH_SHORT).show();
                }
                else if (edtPassword.getText().length()<4 && !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                    Toast.makeText(LoginAndSignUp.this, "Enter a Valid Email,"
                            +"\nPassword should be more than 4 characters", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
                    Toast.makeText(LoginAndSignUp.this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show();
                }
                else if (edtPassword.getText().length()<4){
                    Toast.makeText(LoginAndSignUp.this,"Password can't be less then 4 characters",Toast.LENGTH_SHORT).show();
                }
                else{

                    String Email,password;
                    Email = edtEmail.getText().toString();
                    password = edtPassword.getText().toString();
                    progressDialog.setMessage(Email+" Please wait..."); // Setting Message
                    progressDialog.setTitle("Logging"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    SignInUser(Email,password);
                }


            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())){

                    Toast.makeText(getApplicationContext(),"please enter your email and password" ,Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(edtEmail.getText().toString())&& TextUtils.isEmpty(edtPassword.getText().toString())){

                    Toast.makeText(getApplicationContext(),"Enter your user name and password to Sign Up",Toast.LENGTH_SHORT).show();
                }

                else if (edtPassword.getText().length()<4 && !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
                    Toast.makeText(LoginAndSignUp.this,"Enter a Valid Email,"+"\nPassword should be more than 4 characters",Toast.LENGTH_SHORT).show();
                }

                else if (edtPassword.getText().length()<4){
                    Toast.makeText(LoginAndSignUp.this,"Password can't be less then 4 characters",Toast.LENGTH_SHORT).show();
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
                    Toast.makeText(LoginAndSignUp.this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show();
                }

                else{
                    confirmPassword();
                }

            }
        });


    }


    private void SignInUser(String Email,String Password){

        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()){
                                Toast.makeText(LoginAndSignUp.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginAndSignUp.this,HOME.class);
                                startActivity(intent);
                                finish();

                            }else{
                                progressDialog.dismiss();
                              //  Toast.makeText(LoginAndSignUp.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                              //  Log.i("fl",task.getException().toString());

                            }



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginAndSignUp.this,"Login failed "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void SignUpNewUser(String Email,String Password){

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Signup Successfully",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(),HOME.class);
                            startActivity(intent);
                            finish();
                        }else {
                          //  Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginAndSignUp.this,"failed to Signup "+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private  void sendPasswordRequest(){
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
        builder.setPositiveButton("Send Password Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(Email.getText().toString())){
                    Toast.makeText(getApplicationContext(),"please enter your email" ,Toast.LENGTH_SHORT).show();
                }
                else if ((!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches())){
                    Toast.makeText(LoginAndSignUp.this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show();
                }
                else {
                    String emailAddress = Email.getText().toString();
                    mAuth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Password reset link has been sent to your email", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "No User record found", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
        AlertDialog dialog
                = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg);
    }

    private void confirmPassword(){
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
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(cpssword.getText().toString())){

                    Toast.makeText(LoginAndSignUp.this, "please enter the password you just created", Toast.LENGTH_SHORT).show();

                }else if (!cpssword.getText().toString().equals(edtPassword.getText().toString())){

                    Toast.makeText(LoginAndSignUp.this, "Password Not Match", Toast.LENGTH_SHORT).show();

                }else{
                    String Email,password;
                    Email = edtEmail.getText().toString();
                    password = edtPassword.getText().toString();
                    progressDialog.setMessage(Email+" Please wait..."); // Setting Message
                    progressDialog.setTitle("Signing Up"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    SignUpNewUser(Email,password);
                }
            }
        });
        AlertDialog dialog
                = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg);



    }
}