package com.syrell.loginpetzzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btn_Register;
    EditText name, email, password;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        name = findViewById(R.id.nombreR);
        email = findViewById(R.id.correoR);
        password = findViewById(R.id.contraseñaR);
        btn_Register = findViewById(R.id.btnRegistro);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser= name.getText().toString().trim();
                String emailUser =  email.getText().toString().trim();
                String passUser =  password.getText().toString().trim();

                if (nameUser.isEmpty()&& emailUser.isEmpty()&&passUser.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(nameUser,emailUser,passUser);
                }
            }
        });
    }

    private void registerUser(String nameUser, String emailUser, String passUser) {
mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        String id= mAuth.getCurrentUser().getUid();
         Map<String,Object> map=new HashMap<>();
         map.put("id",id);
         map.put("name", nameUser);
         map.put("email", emailUser);
         map.put("password", passUser);

         mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void unused) {
                 finish();
                 startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                 Toast.makeText(RegisterActivity.this, "El usuario ha sido registrado", Toast.LENGTH_SHORT).show();

             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(RegisterActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
             }
         });

    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(RegisterActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
    }
});
    }
}