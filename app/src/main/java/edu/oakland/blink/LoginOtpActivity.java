package edu.oakland.blink;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginOtpActivity extends AppCompatActivity {
        String phoneNumber;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_login_otp);
    ViewCompat.setOnApplyWindowInsetsListener(
        findViewById(R.id.login_otp_main),
        (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


          phoneNumber = getIntent().getExtras().getString("phone");
          Toast.makeText(getApplicationContext(), phoneNumber, Toast.LENGTH_LONG).show();


          return insets;




        });
  }
}
