package markintoch.rentcar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Carro> carros;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MyAdapter(List<Carro> carros, int layout, OnItemClickListener listener) {
        this.carros = carros;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @Override//INFLAR LA VISTA
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(carros.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return carros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageViewPoster;

        public ViewHolder(View itemView){
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewTitle);
            this.imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
        }

        public void bind(final Carro carros, final OnItemClickListener listener){
            //Renderizar objetos de nuestro RecyclerView
            textViewName.setText(carros.getMarca());
            Picasso.with(context).load(carros.getPoster()).into(imageViewPoster);
            //imageViewPoster.setImageResource(movies.getPoster());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(carros, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Carro carros, int position);
    }
}
