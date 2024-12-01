package edu.oakland.blinkproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import edu.oakland.blinkproject.model.ChatMessageModel;
import edu.oakland.blinkproject.model.ChatroomModel;
import edu.oakland.blinkproject.model.UserModel;
import edu.oakland.blinkproject.utils.AndroidUtil;

public class ChatActivity extends AppCompatActivity {

    UserModel otherUser;
    String chatroomId;
    ChatroomModel chatroomModel;

    EditText messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // gets the user model from AndroidUtil
        otherUser = AndroidUtil.getUserModelFromIntent(getIntent());
        chatroomId = null; // TODO: change this to get chatroom id from Firebase

        messageInput = findViewById(R.id.chat_message_input);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        backBtn = findViewById(R.id.back_btn);
        otherUsername = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.chat_recycler_view);

        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        otherUsername.setText(otherUser.getUsername());

        sendMessageBtn.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if (message.isEmpty()) {
                return;
            }
            else {
                sendMessageToUser(message);
            }
        }));

        getOrCreateChatroomModel();
    }

    // TODO: setup firebaseutil so that the currentUserId can get obtained through firebase
    void sendMessageToUser(String message) {

        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());

        // TODO: add way to set the chatroomModel from firebase using the chatroomId so below can work

        ChatMessageModel chatMessageModel = new ChatMessageModel(message, FirebaseUtil.currentUserId(), Timestamp.now());





    }

    // TODO: Use Firebase to
    void getOrCreateChatroomModel() {

    }


}