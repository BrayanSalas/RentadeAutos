package markintoch.rentcar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    EditText nombre, correo, usuario, password;
    Button terminarRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nombre = (EditText) findViewById(R.id.nombre);
        correo = (EditText) findViewById(R.id.correo);
        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.passwordRegister);
        final FirebaseDatabase rentCar = FirebaseDatabase.getInstance();//Haciendo conexion con nuestra Database
        final DatabaseReference usuarioDatabase = rentCar.getReference("usuarios"); //Similar a una tabla en SQL
        terminarRegistro = (Button) findViewById(R.id.BotonRegisterComplete);
        terminarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean camposLlenos = validate(new EditText[]{correo, nombre, usuario, password});
                if (camposLlenos){
                    final String newEmail = correo.getText().toString();
                final String newPassword = password.getText().toString();
                final String newUsuario = usuario.getText().toString();
                final String newNombre = nombre.getText().toString();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(newEmail, newPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Usuario newUser = new Usuario(newNombre, newEmail, newPassword, newUsuario);
                            usuarioDatabase.push().setValue(newUser); //En la referencia (o tabla) usuarios hace un push de los datos del objeto Usuario
                            Toast.makeText(getApplicationContext(), "Usuario registrado existosamente", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            if (task.getException().getMessage() == "The email address is already in use by another account.") {
                                Toast.makeText(getApplicationContext(), "Este usuario ya existe", Toast.LENGTH_SHORT).show(); //En caso de que el email ya este en uso en la base de datos
                            } else {// Aparecera un toast con el mensaje de que ya existe un usuario registrado con este email
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }else{
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
            }
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
