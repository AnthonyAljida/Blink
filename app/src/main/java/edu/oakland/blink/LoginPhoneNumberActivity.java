package edu.oakland.blink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class LoginPhoneNumberActivity extends AppCompatActivity {
   CountryCodePicker Country_Code_Picker;
   EditText phone_number_input;
   Button phone_continue_button;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_login_phone_number_activity);
    ViewCompat.setOnApplyWindowInsetsListener(
        findViewById(R.id.login_phone_main),
        (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

           Country_Code_Picker = findViewById(R.id.login_phone_country_code_picker);
           phone_number_input = findViewById(R.id.login_phone_number_input);
            phone_continue_button = findViewById(R.id.login_phone_continue_button);



            Country_Code_Picker.registerCarrierNumberEditText(phone_number_input);
            phone_continue_button.setOnClickListener((view) -> {
                if(!Country_Code_Picker.isValidFullNumber()) {
                    phone_number_input.setError("Phone number not valid");
                    return;
                }
                Intent intend = new Intent(LoginPhoneNumberActivity.this, LoginOtpActivity.class);
                intend.putExtra("phone", Country_Code_Picker.getFullNumberWithPlus());
                startActivity(intend);



            });






            return insets;




        });
  }
}
