package edu.oakland.blink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import edu.oakland.blink.utils.FirebaseUtil;

public class SplashActivity extends AppCompatActivity {

  Button StartMsg;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_splash);
    ViewCompat.setOnApplyWindowInsetsListener(
        findViewById(R.id.splash_root),
        (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

          StartMsg = findViewById(R.id.splash_start_messaging);
          StartMsg.setOnClickListener(
              (view) -> {
                if (FirebaseUtil.isLoggedIn()) {

                  startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

                startActivity(new Intent(SplashActivity.this, LoginPhoneNumberActivity.class));
              });

          return insets;
        });
  }
}
