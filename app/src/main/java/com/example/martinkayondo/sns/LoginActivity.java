package com.example.martinkayondo.sns;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView needNewAccountLink;
    private EditText emailTextEdit, passwordTextEdit;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private FirebaseAuth firebaseAuth;
    private ImageView facebookLoginImage;
    private ImageView twitterLoginImage;
    private ImageView googleLoginImage;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        needNewAccountLink = findViewById(R.id.no_account_text);
        loginButton = findViewById(R.id.login_button);
        emailTextEdit = findViewById(R.id.login_email);
        passwordTextEdit = findViewById(R.id.login_password);
        loadingBar = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        twitterLoginImage = findViewById(R.id.twitter_login_button);
        facebookLoginImage = findViewById(R.id.facebook_login_button);
        googleLoginImage = findViewById(R.id.google_login_button);

        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithEmail();
            }
        });
        
        View.OnClickListener snsLogin = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loginWithSns();
            }
        };
        
        facebookLoginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    private void loginWithSns() {
    }


    private void loginWithEmail(){
        String email = emailTextEdit.getText().toString();
        String pwd = passwordTextEdit.getText().toString();



        if(email.isEmpty() || pwd.isEmpty()){
            Toast.makeText(LoginActivity.this, "Invalid Credentials...", Toast.LENGTH_SHORT).show();
            return;
        }else{

            loadingBar.setTitle("Login");
            loadingBar.setMessage("Signing in...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);


            firebaseAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Signed in as: ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                goToMainActivity();

                            }else{

                                String message = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"Login failed: "+message,Toast.LENGTH_SHORT);
                                loadingBar.dismiss();
                                return;
                            }
                        }
                    });
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
