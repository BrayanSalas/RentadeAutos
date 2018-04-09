package markintoch.rentcar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetallesActivity extends AppCompatActivity {
    private Context context;
    TextView textViewTitleD, textViewDescription;
    ImageView imageViewPosterD;
    Button elegirCarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        textViewTitleD = findViewById(R.id.textViewTitleD);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewPosterD = findViewById(R.id.imageViewPosterD);
        elegirCarro = findViewById(R.id.elegirCarro);

        final String marca = getIntent().getExtras().getString("marca");
        String poster = getIntent().getExtras().getString("poster");
        String puertas = getIntent().getExtras().getString("puertas");
        String color = getIntent().getExtras().getString("color");
        String modelo = getIntent().getExtras().getString("modelo");
        String ac = getIntent().getExtras().getString("ac");
        String transmision = getIntent().getExtras().getString("transmision");
        String capacidad = getIntent().getExtras().getString("capacidad");
        String descripcionAdicional = getIntent().getExtras().getString("descripcionAdicional");
        final String precio = getIntent().getExtras().getString("precio");



        textViewTitleD.setText(marca);
        textViewDescription.setText("Puertas: "+puertas+"\n"+"Color: "+color+"\n"+"Modelo: "+modelo+"\n"+"AC: "+ac+"\n"+"Transmision: "+transmision+"\n"+"Capacidad: "+capacidad+"\n"+"Descripcion Adicional: "+descripcionAdicional+"\n"+"Precio: $"+precio);
        Picasso.with(context).load(poster).into(imageViewPosterD);

        elegirCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetallesActivity.this);
                builder.setMessage("Desea elegir el coche "+marca+"\n"+"por el precio de: $"+precio).setTitle("Elección del Carro");
                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Codigo de firebase
                        Toast.makeText(DetallesActivity.this, "Se ha hecho el pedido de manera exitosa", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(DetallesActivity.this, "No se ha podido realizar la peticion", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    /*private List<Carro> getAllCarro(final String marca, final String poster, final String puertas, final String color, final String modelo, final String ac, final String transmision, final String capacidad, final String descripcionAdicional, final String precio){
        return new ArrayList<Carro>(){{
            add(new Carro(marca, poster, puertas, color, modelo, ac, transmision, capacidad, descripcionAdicional, precio));

        }};
    }*/
}
