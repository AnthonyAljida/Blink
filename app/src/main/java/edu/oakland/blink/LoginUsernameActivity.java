package edu.oakland.blink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import edu.oakland.blink.models.UserModel;
import edu.oakland.blink.utils.FirebaseUtil;

public class LoginUsernameActivity extends AppCompatActivity {
    EditText username;
    Button save;
    String phoneNumber;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_username);
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.login_username_main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

        username = findViewById(R.id.login_username_input);
        save = findViewById(R.id.login_username_save_button);

        phoneNumber = getIntent().getExtras().getString("phone");
        getUsername();

        save.setOnClickListener((v -> {
            setUsername();
        }));
    }

    void setUsername() {
        String usernameText = username.getText().toString();
        if (usernameText.isEmpty() || usernameText.length() < 3) {
            username.setError("Username length should be at least 3 chars");
            return;
        }

        if (userModel != null) {
            userModel.setUsername(usernameText);
        } else {
            userModel = new UserModel(phoneNumber, usernameText, Timestamp.now(), FirebaseUtil.currentUserId());
        }

        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginUsernameActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    void getUsername() {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    userModel = task.getResult().toObject(UserModel.class);
                    if (userModel != null) {
                        username.setText(userModel.getUsername());
                    }
                }
            }
        });
    }
}