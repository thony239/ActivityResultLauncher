package com.example.practica_clase;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.practica_clase.databinding.ActivityAceptadoBinding;
import com.example.practica_clase.databinding.ActivityMainBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
static float valoracion=0;
ActivityMainBinding binding;
Bitmap bitmap;

ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegistrar.setOnClickListener(view -> {
            String nombre = binding.txtNombre.getText().toString();
            String clave = binding.txtClave.getText().toString();
            String clave2= binding.txtClave2.getText().toString();
            String email = binding.txtGma.getText().toString();
            String email2 = binding.txtGma2.getText().toString();
            String rol = binding.txtRol.getText().toString();

            Context context = MainActivity.this;
            if (nombre.isEmpty()){
                Toast.makeText(MainActivity.this,"Campo nombre vacio, llenar por favor",Toast.LENGTH_SHORT).show();
            }else if (clave.isEmpty()||clave.length()<5){
                Toast.makeText(MainActivity.this,"Campo clave vacio, llenar por favor, mayor a 5",Toast.LENGTH_SHORT).show();
            }else if (clave2.isEmpty()||clave2.length()<5){
                Toast.makeText(MainActivity.this,"Campo repetir clave vacio, llenar por favor, mayor a 5",Toast.LENGTH_SHORT).show();
            }else if (email.isEmpty()){
                Toast.makeText(MainActivity.this,"Campo email vacio, llenar por favor",Toast.LENGTH_SHORT).show();
            }else if (email2.isEmpty()) {
                Toast.makeText(MainActivity.this, "Campo repetir email vacio, llenar por favor", Toast.LENGTH_SHORT).show();
            }else if (rol.isEmpty()) {
                Toast.makeText(MainActivity.this, "Campo rol vacio, llenar por favor", Toast.LENGTH_SHORT).show();
            }

            if (clave.equals(clave2)){
                int cont1 = clave.length();
                char[] conteo = clave.toCharArray();
                int cont=0;
                for(int i=0; i<conteo.length; i++){
                    if( (conteo[i] >=33 && conteo[i]<=47) || (conteo[i] >=58 && conteo[i]<=64) || (conteo[i] >=91 && conteo[i]<=96) || (conteo[i] >=123 && conteo[i]<=126) ){
                        cont++;
                    }
                }
                if (cont>=4 && cont1>=12){
                    valoracion=5;
                }else if(cont>=2 && cont1>=10){
                    valoracion=4;
                }else if(cont>=1 && cont1>=8) {
                    valoracion = 3;
                }else if(cont1>=8) {
                    valoracion = 2;
                }else{
                    valoracion=1;
                }
                Pattern pattern = Pattern
                        .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                if (email.equals(email2)){
                    Matcher mather = pattern.matcher(email);

                    if (mather.find() == true) {
                        Toast.makeText(MainActivity.this,"El email ingresado es válido.",Toast.LENGTH_SHORT).show();
                        if (rol.equals("admin")||rol.equals("ADMIN")||rol.equals("invitado")||rol.equals("INVITADO")){
                            Toast.makeText(MainActivity.this,"USTED INGRESO CORRECTEMENTE",Toast.LENGTH_SHORT).show();
                            abrirActividaddetalle(nombre,clave,email,rol,valoracion);
                        }else {
                            Toast.makeText(MainActivity.this,"INGRESE ADMIN O INVITADO",Toast.LENGTH_SHORT).show();
                            binding.txtRol.setText("");
                        }
                    } else {
                        Toast.makeText(MainActivity.this,"El email ingresado es invalido.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Los email no coinciden",Toast.LENGTH_SHORT).show();
                    binding.txtGma2.setText("");
                }


            }else{
                Toast.makeText(MainActivity.this,"Las claves no coinciden",Toast.LENGTH_SHORT).show();
                binding.txtClave2.setText("");
            }

        });
        activity_launcher();
        binding.foto.setOnClickListener(view -> {
            abrircamara();
        });

    }
    private void abrircamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(camaraIntent);
        // startActivityForResult(camaraIntent, 1000);
        activityResultLauncher.launch((camaraIntent));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK && requestCode==1000){
            if (data != null){
                bitmap =data.getExtras().getParcelable("data");
                binding.foto.setImageBitmap(bitmap);
            }
        }
    }

    private void abrirActividaddetalle(String nom,String clave,String email, String rol,float valoracion){
        Intent intents = new Intent(this,Aceptado.class);
        Usuario usu = new Usuario(nom,clave,email,rol,valoracion);
        intents.putExtra(Aceptado.USUARIO_KEY,usu);
        intents.putExtra(Aceptado.BITMAP_KEY,bitmap);
        startActivity(intents);
    }
    public void activity_launcher(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        bitmap = result.getData().getExtras().getParcelable("data");
                        binding.foto.setImageBitmap(bitmap);
                    }
                }
            }

        });

    }

}