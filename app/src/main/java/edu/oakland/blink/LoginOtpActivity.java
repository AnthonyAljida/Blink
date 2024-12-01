package edu.oakland.blink;

import android.annotation.SuppressLint;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Calendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import edu.oakland.blink.utils.AndroidUtil;

public class LoginOtpActivity extends AppCompatActivity {
        String phoneNumber;
        Long timeoutSeconds = 60L;
        EditText otpInput;
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


          otpInput = findViewById(R.id.login_otp_code_input);
          ContinueBtn = findViewById(R.id.login_otp_continue_button);
          resentOptTextView = findViewById(R.id.login_otp_resend_code_button);

          phoneNumber = Objects.requireNonNull(getIntent().getExtras()).getString("phone");
          sendotp(phoneNumber, false);

            ContinueBtn.setOnClickListener(view -> {

                 String enteredOtp = otpInput.getText().toString();
                 PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationCode, enteredOtp);
                signIn(credential);
                    });


            resentOptTextView.setOnClickListener((view)->{
                sendotp(phoneNumber,true);
            });
            return insets;




        });
  }


  void sendotp(String phoneNumber, boolean isResend) {
startResendTimer();
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



    void signIn(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    Intent intend = new Intent(LoginOtpActivity.this, LoginUsernameActivity.class);
                    intend.putExtra("phone", phoneNumber);
                    startActivity(intend);
                }
                else{
                    AndroidUtil.showToast(getApplicationContext(), "OTP Verification failed");
                }
            }
        });
    }
  @SuppressLint("DiscouragedApi")
  void startResendTimer() {
      resentOptTextView.setEnabled(false);

      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
              timeoutSeconds--;
              resentOptTextView.setText("Resend OTP in " +timeoutSeconds +" seconds");

              if(timeoutSeconds <= 0) {
                  timeoutSeconds = 60L;
                  timer.cancel();
                  runOnUiThread(()-> {
                      resentOptTextView.setEnabled(true);


                  } );
              }
          }
      },0, 1000);
    }
}
