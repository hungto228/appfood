package com.example.phantom_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phantom_project.Common.Common;
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

public class SignIn extends AppCompatActivity {
    private Button mSignInBtn;
    private MaterialEditText mPhoneNumberEdt, mPassWordEdt;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng nhập");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E9AFE")));
        //initlization
        mPhoneNumberEdt = findViewById(R.id.edt_Phonenumber);
        mPassWordEdt = findViewById(R.id.edt_Password);
        mSignInBtn = findViewById(R.id.btn_Signin);


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Hãy đợi...");
                progressDialog.show();
                final String phoneNumber=mPhoneNumberEdt.getText().toString();
                final String passWord=mPassWordEdt.getText().toString();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(phoneNumber).exists()) {
                            progressDialog.dismiss();
                            ModelUsers modelUsers = dataSnapshot.child(phoneNumber).getValue(ModelUsers.class);
                            modelUsers.setPhone(phoneNumber);
                            if (modelUsers.getPassword().equals(passWord)) {
                                Intent intent = new Intent(SignIn.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Common.CurrentUsers=modelUsers;
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Số điện thoại hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignIn.this, "Hãy đăng ký trước", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
