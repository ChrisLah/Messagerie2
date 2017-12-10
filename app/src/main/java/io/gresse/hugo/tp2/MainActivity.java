package io.gresse.hugo.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ValueEventListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    EditText       mInputEditText;
    Button         mSendButton;
    MessageAdapter mMessageAdapter;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(UserStorage.getUserName(this).isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        Menu menu = findViewById(R.id.action_logout);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        mInputEditText = findViewById(R.id.inputEditText);
        mSendButton = findViewById(R.id.sendButton);

        mMessageAdapter = new MessageAdapter(new ArrayList<Message>());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMessageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        connectAndListenToFirebase();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mInputEditText.getText().toString().isEmpty()){
                    return;
                }
                DatabaseReference newData = mDatabaseReference.push();
                newData.setValue(
                        new Message(mInputEditText.getText().toString(), UserStorage.getUserName(getApplicationContext()),UserStorage.getUserEmail(getApplicationContext()), (new Date()).getTime() ));
                mInputEditText.setText("");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabaseReference.removeEventListener(this);
    }

    private void connectAndListenToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("chat/messages");

        mDatabaseReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "dataChange : " + dataSnapshot);
        List<Message> items = new ArrayList<>();
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
            items.add(messageSnapshot.getValue(Message.class));
        }
        mMessageAdapter.setData(items);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
