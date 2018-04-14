package markintoch.rentcar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button registro,login;
    FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        registro = (Button) findViewById(R.id.BotonRegister);
        login = (Button) findViewById(R.id.BotonLogin);

        //----------------------AUTENTICACION DE USUARIOS DE FIREBASE-----------------------
        mAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = firebaseAuth.getCurrentUser(); //Se verifica el estado de la sesion
                if(usuario != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Desea continuar como "+usuario.getEmail()).setTitle("Inicio de sesión");
                    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(LoginActivity.this,SearchActivity.class);
                            startActivity(i);
                            android.os.Process.killProcess(android.os.Process.myPid()); //Mata el proceso actual, cierra el activity
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(LoginActivity.this, "La sesión ha sido cerrada", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        };

        //----------------------INICIO DE SESION DE USUARIOS DE FIREBASE-----------------------
        FirebaseAuth.getInstance().addAuthStateListener(authListener); //Establece que existe una autenticacion activa de acuerdo si existe un usuario
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean camposLlenos = validate(new EditText[]{email,password}); //Funcion que valida que todos los campos esten llenos
                if(camposLlenos){
                    String user = email.getText().toString();
                    String contra = password.getText().toString();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(user,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){//Si se cumplio la tarea exitosamente, regresara un true y se concluye que se registro el usuario
                                Toast.makeText(getApplicationContext(),"Sesion iniciada correctamente",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this,SearchActivity.class);
                                startActivity(i);
                                finish();
                            }else{ //En caso contrario, regresara un false | Con la funcion .getMessage() se puede acceder al mensaje del error
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean validate(EditText[] fields){
        for(int i=0; i<fields.length; i++){
            EditText currentField=fields[i];
            if(currentField.getText().toString().equals("")){
                currentField.setError("Este campo es requerido");
                return false;
            }
        }
        return true;
    }
}
