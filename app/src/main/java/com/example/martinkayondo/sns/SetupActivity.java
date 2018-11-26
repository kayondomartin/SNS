package com.example.martinkayondo.sns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {
    private EditText userName, fullName, countryName;
    private Button saveInfoButton;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        userName = findViewById(R.id.setup_username);
        fullName = findViewById(R.id.setup_user_full_name);
        countryName = findViewById(R.id.setup_country);
        saveInfoButton = findViewById(R.id.setup_information_buttom);
        profileImage = findViewById(R.id.setup_profile_image);
    }
}
