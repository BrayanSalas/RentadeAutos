package markintoch.rentcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import markintoch.rentcar.Objetos.Buscar;
import markintoch.rentcar.Recycler.RecyclerActivity;

@SuppressWarnings("serial")
public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Serializable {
    private FirebaseAuth mAuth;
    String estab[];
    int position;
    Button buttonBuscar;
    EditText fechaInicio;
    EditText fechaDevolucion;
    EditText horaInicio;
    EditText horaDevolucion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mAuth = FirebaseAuth.getInstance();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        estab = getResources().getStringArray(R.array.establecimientos);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.establecimientos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        fechaInicio = findViewById(R.id.recogerFecha);
        fechaDevolucion = findViewById(R.id.devolucionFecha);
        horaInicio = findViewById(R.id.recogerHora);
        horaDevolucion = findViewById(R.id.devolucionHora);
        buttonBuscar = findViewById(R.id.buttonBuscar);


        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean camposLlenos = validate(new EditText[]{fechaInicio, fechaDevolucion, horaInicio, horaDevolucion});
                if (camposLlenos) {
                    String newfechaInicio = fechaInicio.getText().toString();
                    String newfechaDevolucion = fechaDevolucion.getText().toString();
                    String newhoraInicio = horaInicio.getText().toString();
                    String newhoraDevolucion = horaDevolucion.getText().toString();

                    //Comandos a utilizar cuando la persona confirme que quiere utilizar el carro, pero deberá ser en ventanas posteriores
                    //igual adjunto lo que deberia ir en la parte final de la app

                    //final FirebaseDatabase rentCar = FirebaseDatabase.getInstance();//Haciendo conexion con nuestra Database
                    //final DatabaseReference usuarioDatabase = rentCar.getReference("establecimientos"); //Similar a una tabla en SQL
                    //Buscar newBuscar = new Buscar(newfechaInicio, newfechaDevolucion, newhoraInicio, newhoraDevolucion, estab[position]);
                    //usuarioDatabase.push().setValue(newBuscar);

                    Intent intent = new Intent(SearchActivity.this, RecyclerActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("newfechaInicio", newfechaInicio);
                    intent.putExtra("newfechaDevolucion", newfechaDevolucion);
                    intent.putExtra("newhoraInicio", newhoraInicio);
                    intent.putExtra("newhoraDevolucion", newhoraDevolucion);
                    intent.putExtra("establecimiento", estab[position]);
                    startActivity(intent);

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mAuth.signOut();
                Toast.makeText(this, "La sesión ha sido cerrada", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SearchActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //La posicion se actualiza cada que elige un diferente establecimiento
        position = pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
