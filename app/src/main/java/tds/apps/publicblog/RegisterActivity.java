package tds.apps.publicblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameField,mEmailField,mPasswordField;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailField = (EditText) findViewById(R.id.emailField);
        mNameField = (EditText) findViewById(R.id.nameField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mRegisterBtn = (Button) findViewById(R.id.registerBtn);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

    }

    private void startRegister() {

        final String name = mNameField.getText().toString().trim();
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)||
                password.length()>5){

            mProgress.setMessage("Signing up");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("image").setValue("default");


                        mProgress.dismiss();

                        Intent i = new Intent(RegisterActivity.this , MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }

                }
            });

        }
        else {
            mPasswordField.setHint("You must have x characters in your password");
            mEmailField.setHint("You must have to fill email");
            mNameField.setHint("You must have to fill Name");
            mProgress.dismiss();

        }


    }
}
