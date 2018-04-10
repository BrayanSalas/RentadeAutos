package markintoch.rentcar.Recycler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import markintoch.rentcar.Objetos.Carro;
import markintoch.rentcar.R;

public class RecyclerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Carro> carros;
    private Adaptador adaptador;
    Context con;

    DatabaseReference referencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        con = this;
        //////////////////////////////////////////////////////////
        int pos = getIntent().getExtras().getInt("position");
        String newfechaInicio = getIntent().getExtras().getString("newfechaInicio");
        String newfechaDevolucion = getIntent().getExtras().getString("newfechaDevolucion");
        String newhoraInicio = getIntent().getExtras().getString("newhoraInicio");
        String newhoraDevolucion = getIntent().getExtras().getString("newhoraDevolucion");
        String establecimiento = getIntent().getExtras().getString("establecimiento");
        if(pos == 0){
            referencia = database.getReference("establecimientos/escobedo");
        }else if(pos == 1){
            referencia = database.getReference("establecimientos/san nicolas");
        }else if(pos == 2){
            referencia = database.getReference("establecimientos/apodaca");
        }else{
            referencia = database.getReference("establecimientos/santa catarina");
        }
        /////////////////////////////////////////////////////////////

        carros = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adaptador = new Adaptador(carros, con, newfechaInicio, newfechaDevolucion, newhoraInicio, newhoraDevolucion, establecimiento);
        recyclerView.setAdapter(adaptador); //Se usar el adaptador en el RecyclerView
        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carros.clear();
                for (DataSnapshot Q : dataSnapshot.getChildren()){
                    Carro c = Q.getValue(Carro.class);
                    carros.add(c);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
