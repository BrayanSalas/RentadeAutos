package markintoch.rentcar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
    TextView textViewTitleR, textViewDescriptionR, estadoCarro;
    ImageView imageViewPosterR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentados);

        textViewTitleR = findViewById(R.id.textViewTitleR);
        textViewDescriptionR = findViewById(R.id.textViewDescriptionR);
        estadoCarro = findViewById(R.id.estadoCarro);
        imageViewPosterR = findViewById(R.id.imageViewPosterR);
        context = this;


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
                            rentarCarr.child("peticion").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Buscar buscar = dataSnapshot.getValue(Buscar.class);
                                    try {

                                    String marca = buscar.getMarca();
                                    String poster = buscar.getPoster();
                                    String fechaInicio = buscar.getFechaInicio();
                                    String fechaFin = buscar.getFechaDevolucion();
                                    String horaInicio = buscar.getHoraInicio();
                                    String horaFin = buscar.getHoraFin();
                                    String establecimiento = buscar.getEstablecimiento();
                                    String precio = buscar.getPrecio();
                                    System.out.println(precio);

                                        estadoCarro.setText("Carro rentado");
                                        textViewTitleR.setText(marca);
                                        textViewDescriptionR.setText("Establecimiento: " + establecimiento + "\n" + "Fecha de Inicio: " + fechaInicio + "\n" + "Fecha de devolucion: " + fechaFin + "\n" + "Hora de Inicio: " + horaInicio + "\n" + "Hora de Devolucion: " + horaFin + "\n" + "Precio: $" + precio);
                                        Picasso.with(context).load(poster).into(imageViewPosterR);

                                    }catch(Exception e){
                                        estadoCarro.setText("No ha hecho petición de carro");
                                        textViewTitleR.setText("");
                                        textViewDescriptionR.setText("");
                                        Picasso.with(context).load("https://img.memecdn.com/404-car-not-found_o_6767609.jpg").into(imageViewPosterR);
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            //Si no existe la referencia
                    }//if user
                }//for
            }//onDataPrincipal

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RentadosActivity.this, "No se ha podido hacer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
