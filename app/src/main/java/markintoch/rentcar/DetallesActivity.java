package markintoch.rentcar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import markintoch.rentcar.Objetos.Buscar;
import markintoch.rentcar.Objetos.Carro;
import markintoch.rentcar.Objetos.Usuario;
import markintoch.rentcar.Recycler.RecyclerActivity;


public class DetallesActivity extends AppCompatActivity {
    private Context context;
    private String email;
    String username;
    TextView textViewTitleD, textViewDescription;
    ImageView imageViewPosterD;
    Button elegirCarro;
    FirebaseDatabase rentCar = FirebaseDatabase.getInstance();
    DatabaseReference referencia;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        textViewTitleD = findViewById(R.id.textViewTitleD);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewPosterD = findViewById(R.id.imageViewPosterD);
        elegirCarro = findViewById(R.id.elegirCarro);


        //CARACTERISTICAS DEL AUTO
        final String marca = getIntent().getExtras().getString("marca");
        final String poster = getIntent().getExtras().getString("poster");
        String puertas = getIntent().getExtras().getString("puertas");
        String color = getIntent().getExtras().getString("color");
        String modelo = getIntent().getExtras().getString("modelo");
        String ac = getIntent().getExtras().getString("ac");
        String transmision = getIntent().getExtras().getString("transmision");
        String capacidad = getIntent().getExtras().getString("capacidad");
        String descripcionAdicional = getIntent().getExtras().getString("descripcionAdicional");
        final String precio = getIntent().getExtras().getString("precio");

        //DATOS DEL PETICION
        final String newfechaInicio = getIntent().getExtras().getString("newfechaInicio");
        final String newfechaDevolucion = getIntent().getExtras().getString("newfechaDevolucion");
        final String newhoraInicio = getIntent().getExtras().getString("newhoraInicio");
        final String newhoraDevolucion = getIntent().getExtras().getString("newhoraDevolucion");
        final String establecimiento = getIntent().getExtras().getString("establecimiento");



        textViewTitleD.setText(marca);
        textViewDescription.setText("Descripcion: "+descripcionAdicional+"\n"+"Puertas: "+puertas+"                        "+"Color: "+color+"\n"+"Modelo: "+modelo+"                  "+"AC: "+ac+"\n"+"Transmision: "+transmision+"     "+"Capacidad: "+capacidad+"\n"+"Precio: $"+precio);
        Picasso.with(context).load(poster).into(imageViewPosterD);

        elegirCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                AlertDialog.Builder builder = new AlertDialog.Builder(DetallesActivity.this);
                builder.setMessage("Desea elegir el coche " + marca + "\n" + "por el precio de: $" + precio).setTitle("Elección del Carro");
                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TRAE TODOS LOS USUARIOS
                        referencia = database.getReference("usuarios");
                        referencia.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot Q : dataSnapshot.getChildren()) {
                                    Usuario usuario = Q.getValue(Usuario.class);
                                    if (user != null) {
                                        email = user.getEmail();
                                    }
                                    if (usuario.getCorreo().equals(email)) {
                                        username = Q.getKey();
                                        DatabaseReference rentarCarr = rentCar.getReference("usuarios/" + username);
                                        Buscar peticion = new Buscar(newfechaInicio, newfechaDevolucion, newhoraInicio, newhoraDevolucion, establecimiento, marca, precio, poster);
                                        rentarCarr.child("peticion").setValue(peticion);
                                        Toast.makeText(DetallesActivity.this, "Se ha hecho el pedido de manera exitosa", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                Intent i = new Intent(DetallesActivity.this, RentadosActivity.class);
                                startActivity(i);
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(DetallesActivity.this, "No se ha podido hacer", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(DetallesActivity.this, "No se ha podido realizar la peticion", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }catch(Exception e){
                    Toast.makeText(context, "Existe un error del email en la base de datos, revise con soporte tecnico", Toast.LENGTH_SHORT).show();
                }
            }//fin del onclick
        });
    }
}
