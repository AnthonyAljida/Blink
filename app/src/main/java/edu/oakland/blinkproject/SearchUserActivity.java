package edu.oakland.blinkproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.oakland.blinkproject.adapter.SearchUserRecyclerAdapter;

public class SearchUserActivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;

    SearchUserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchInput = findViewById(R.id.search_username_input);
        searchButton = findViewById(R.id.search_user_btn);
        backButton = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.search_user_recycler_view);

        searchInput.requestFocus();

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        // handles inputting a query for a username search
        searchButton.setOnClickListener(v -> {
            String searchQuery = searchInput.getText().toString();
            if (searchQuery.isEmpty()) {
                searchInput.setError("Enter a username");
                return;
            } else if (searchQuery.length() < 3 ) {
                searchInput.setError("Username too short!");
            }
            setupSearchRecyclerView(searchQuery);
        });
    }

    // TODO: Use Firebase to query the db and get usernames for
    void setupSearchRecyclerView(String searchQuery) {
        

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }

}