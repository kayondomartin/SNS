package com.example.martinkayondo.sns;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText userEmail, userPassword, userPasswordConfirmation;
    private Button registerButton;
    private ProgressDialog loadingBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userEmail = findViewById(R.id.register_email);
        userPassword = findViewById(R.id.register_password);
        userPasswordConfirmation = findViewById(R.id.register_confirm_password);
        registerButton = findViewById(R.id.create_user_button);
        loadingBar = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPwd = userPasswordConfirmation.getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            Toast.makeText(RegisterActivity.this, "Please Enter valid email Address", Toast.LENGTH_SHORT).show();
            return;
        }else if(password.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Please Enter password", Toast.LENGTH_SHORT).show();
            return;
        }else if(confirmPwd.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Please confirm password", Toast.LENGTH_SHORT).show();
            return;
        }else if(!password.equals(confirmPwd)){
            Toast.makeText(RegisterActivity.this, "Password confirmation invalid", Toast.LENGTH_SHORT).show();
            return;
        }


        loadingBar.setTitle("Creating New Account");
        loadingBar.setMessage("Please wait...");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            goToSetup();
                        }else{
                            String message = task.getException().getMessage();
                            Toast.makeText(RegisterActivity.this, "Error occured: "+message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });

    }

    private void goToSetup(){
        Intent intent = new Intent(RegisterActivity.this, SetupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
