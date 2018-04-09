package markintoch.rentcar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maste on 09/04/2018.
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.CarrosViewHolder> {

    private List<Carro> carros;
    Context con;

    public Adaptador(List<Carro> carros, Context con){
        this.con = con;
        this.carros = carros;

    }

    @Override
    public Adaptador.CarrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarrosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(Adaptador.CarrosViewHolder holder, int position) {
        Carro carro = carros.get(position);
        Picasso.with(con).load(carro.getPoster()).into(holder.imagen);
        holder.nombre.setText(carro.getMarca());

    }

    @Override
    public int getItemCount() {
        return carros.size();
    }

    public class CarrosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre;
        ImageView imagen;

        public CarrosViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.textViewTitle);
            imagen = (ImageView) itemView.findViewById(R.id.imageViewPoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(con, DetallesActivity.class);
            Carro carroObj = carros.get(getPosition());
            intent.putExtra("marca", carroObj.getMarca());
            intent.putExtra("poster", carroObj.getPoster());
            intent.putExtra("puertas", carroObj.getPuertas());
            intent.putExtra("color", carroObj.getColor());
            intent.putExtra("modelo", carroObj.getModelo());
            intent.putExtra("ac", carroObj.getAc());
            intent.putExtra("transmision", carroObj.getTransmision());
            intent.putExtra("capacidad", carroObj.getCapacidad());
            intent.putExtra("descripcionAdicional", carroObj.getDescripcionAdicional());
            intent.putExtra("precio", carroObj.getPrecio());
            con.startActivity(intent);
        }
    }
}
