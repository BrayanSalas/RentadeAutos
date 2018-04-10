package markintoch.rentcar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import markintoch.rentcar.Objetos.Buscar;
import markintoch.rentcar.Objetos.Usuario;

public class RentadosActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseDatabase rentCar = FirebaseDatabase.getInstance();
    DatabaseReference referencia;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference rentarCarr;
    String email;
    String username;
    Context context;
    TextView textViewTitleR, textViewDescriptionR;
    ImageView imageViewPosterR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentados);

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
                        rentarCarr = rentCar.getReference("usuarios/" + username + "/peticion");
                        rentarCarr.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                                Buscar post = dataSnapshot.getValue(Buscar.class);
                                String poster = post.poster;
                                String marca = post.marca;
                                String establecimiento = post.establecimiento;
                                String fechaInicio = post.fechaInicio;
                                String fechaFin = post.fechaDevolucion;
                                String horaInicio = post.horaInicio;
                                String horaFin = post.horaFin;
                                //poner en un textview
                                textViewTitleR.setText(marca);
                                textViewDescriptionR.setText("Establecimiento: "+establecimiento+"\n"+"Fecha de Inicio: "+fechaInicio+"\n"+"Fecha de devolucion: "+fechaFin+"\n"+"Hora de Inicio: "+horaInicio+"\n"+"Hora de Devolucion: "+horaFin);
                                Picasso.with(context).load(poster).into(imageViewPosterR);


                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {}

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RentadosActivity.this, "The read failed: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
