package io.gresse.hugo.tp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Chris on 04/12/2017.
 */

public class LoginActivity extends AppCompatActivity
{
    public static final String TAG = LoginActivity.class.getSimpleName();

    EditText mInputName;
    EditText mInputEmail;
    Button mSendButton;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputName = findViewById(R.id.editText);
        mInputEmail = findViewById(R.id.editText2);
        mSendButton = findViewById(R.id.button2);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                UserStorage.saveUserInfo(getApplicationContext(),mInputName.getText().toString(),mInputEmail.getText().toString());
                connect();
            }

        });

    }

    public void connect(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }


}
