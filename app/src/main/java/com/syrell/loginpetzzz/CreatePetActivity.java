package com.syrell.loginpetzzz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class CreatePetActivity extends AppCompatActivity {

    Button btn_Add;
    EditText name,race,specie;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);

        this.setTitle("Registrar mascota");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_pet");
        mfirestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.nombre);
        race = findViewById(R.id.raza);
        specie = findViewById(R.id.especie);
        btn_Add = findViewById(R.id.btn_Add);

        if (id == null  || id == ""){
            btn_Add.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    String namepet = name.getText().toString().trim();
                    String racepet = race.getText().toString().trim();
                    String speciepet = specie.getText().toString().trim();

                    if(namepet.isEmpty() && racepet.isEmpty() && speciepet.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    } else {
                        postPet(namepet,racepet,speciepet);
                    }

                }


            });
        }else{
            btn_Add.setText("Update");
            getPet(id);
            btn_Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String namepet = name.getText().toString().trim();
                    String racepet = race.getText().toString().trim();
                    String speciepet = specie.getText().toString().trim();

                    if(namepet.isEmpty() && racepet.isEmpty() && speciepet.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    } else {
                        updatePet(namepet,racepet,speciepet,id);
                    }
                }
            });
        }



    }

    private void updatePet(String namepet, String racepet, String speciepet, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("name",namepet);
        map.put("race",racepet);
        map.put("specie",speciepet);

        mfirestore.collection("pet").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postPet(String namepet, String racepet, String speciepet) {

        Map<String, Object> map = new HashMap<>();
        map.put("name",namepet);
        map.put("race",racepet);
        map.put("specie",speciepet);


        mfirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "La mascota ha sido registrada", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getPet(String id){
        mfirestore.collection("pet").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String namePet = documentSnapshot.getString("name");
                String racePet = documentSnapshot.getString("race");
                String speciePet = documentSnapshot.getString("specie");

                name.setText(namePet);
                race.setText(racePet);
                specie.setText(speciePet);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public  boolean onSupportNavigateUp(){
        onBackPressed();
        return false;
    }
}