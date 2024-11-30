package edu.oakland.blink;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import edu.oakland.blink.utils.AndroidUtil;

public class LoginOtpActivity extends AppCompatActivity {
        String phoneNumber;
        Long timeoutSeconds = 60L;
        EditText optInput;
        Button ContinueBtn;
        TextView resentOptTextView;
        String VerificationCode;
        PhoneAuthProvider.ForceResendingToken ResendingToken;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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


          optInput = findViewById(R.id.login_otp_code_input);
          ContinueBtn = findViewById(R.id.login_otp_continue_button);
          resentOptTextView = findViewById(R.id.login_otp_resend_code_button);

          phoneNumber = Objects.requireNonNull(getIntent().getExtras()).getString("phone");
          sendotp(phoneNumber, false);


          return insets;




        });
  }


  void sendotp(String phoneNumber, boolean isResend) {

      PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
              .setPhoneNumber(phoneNumber)
              .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
      .setActivity(this)
     .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


          @Override
          public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    signIn(phoneAuthCredential);
          }

          @Override
          public void onVerificationFailed(@NonNull FirebaseException e) {

              AndroidUtil.showToast(getApplicationContext(), "OTP Verification failed");

          }


         @Override
         public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
             super.onCodeSent(s, forceResendingToken);
             VerificationCode = s;
             ResendingToken = forceResendingToken;
             AndroidUtil.showToast(getApplicationContext(), "OTP sent successfully");

         }
     });
if(isResend) {
    PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(ResendingToken).build());
} else
{
    PhoneAuthProvider.verifyPhoneNumber(builder.build());
}


  }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {


    }
}
