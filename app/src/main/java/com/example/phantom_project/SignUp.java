package com.example.phantom_project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phantom_project.Model.ModelUsers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private Button mSignUpBtn;
    private MaterialEditText mPhoneNumberEdt, mNameUserEdt, mPassWordEdt;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng ký");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //initlization
        mPhoneNumberEdt = findViewById(R.id.edt_Phonenumber);
        mNameUserEdt = findViewById(R.id.edt_NameUser);
        mPassWordEdt = findViewById(R.id.edt_Password);

        mSignUpBtn = findViewById(R.id.btn_Signup);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Hãy đợi...");
                progressDialog.show();
                final String phoneNumber=mPhoneNumberEdt.getText().toString();
                final String nameUser=mNameUserEdt.getText().toString();
                final String passWord=mPassWordEdt.getText().toString();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(phoneNumber).exists()){
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this,"Người dùng đã tồn tại!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressDialog.dismiss();
                            ModelUsers modelUsers = new ModelUsers(nameUser,passWord);
                            reference.child(nameUser).setValue(modelUsers);
                            Toast.makeText(SignUp.this,"Đăng ký thành công! " ,Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
