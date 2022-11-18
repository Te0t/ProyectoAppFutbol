package com.example.futbolpersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText jetcodigo,jetnombre,jetciudad;
    RadioButton jrbprofecional,jrbascenso,jrbaficionado;
    CheckBox jcbactivo;
    String codigo,nombre,ciudad,categoria,activo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ocultar barra  de titulo y asociar objetos de java con xml
        getSupportActionBar().hide();
        jetciudad=findViewById(R.id.etciudad);
        jetcodigo=findViewById(R.id.etcodigo);
        jetnombre=findViewById(R.id.etnombre);
        jrbprofecional=findViewById(R.id.rbprofesional);
        jrbascenso=findViewById(R.id.rbascenso);
        jrbaficionado=findViewById(R.id.rbaficionado);
        jcbactivo=findViewById(R.id.cbactivo);

    }
    public void Adicionar(View view){
        codigo=jetcodigo.getText().toString();
        nombre=jetnombre.getText().toString();
        ciudad=jetciudad.getText().toString();
        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty()){
            Toast.makeText(this, "todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }
        else {
            if (jrbprofecional.isChecked())
                categoria="profesional";
            else {
                if(jrbascenso.isChecked())
                    categoria="ascenso";
                else
                    categoria="aficionado";
            }
            // Create a new user with a first and last name
            Map<String, Object> equipo = new HashMap<>();
            equipo.put("Codigo", codigo);
            equipo.put("Nombre", nombre);
            equipo.put("Cuidad", ciudad);
            equipo.put("Categoria", categoria);
            equipo.put("Activo", activo);

            // Add a new document with a generated ID
            db.collection("campeonato")
                    .add(equipo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(MainActivity.this, "Documento Adicionado", Toast.LENGTH_SHORT).show();
                            limpiar_campos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Log.w(TAG, "Error adding document", e);
                            Toast.makeText(MainActivity.this, "error  adicionando documento", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
    private void limpiar_campos(){
        jetnombre.setText("");
        jetcodigo.setText("");
        jetciudad.setText("");
        jrbprofecional.setChecked(true);
        jcbactivo.setChecked(false);
        jetcodigo.requestFocus();
    }
}