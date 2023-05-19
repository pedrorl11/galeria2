package soares.pedro.galeria2;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    MainActivity mainActivity;
    List<String> photos; // lista das foto

    public MainAdapter(MainActivity mainActivity, List<String> photos) {
        this.mainActivity = mainActivity;
        this.photos = photos;
    }

    // Método para inflar o item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.list_item, parent, false); // Infla o layout do item
        return new MyViewHolder(v); // Retorna a view do item inflado
    }

    //itens da lista
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imPhoto; // ImageView do item

        public MyViewHolder(View itemView) {
            super(itemView);
            imPhoto = itemView.findViewById(R.id.imItem);
        }
    }

    // Método para exibir os dados nos itens da lista
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        View v = holder.itemView; // pego a view do item
        ImageView imPhoto = v.findViewById(R.id.imItem); // Obtém a referência do ImageView do item
        int w = (int) mainActivity.getResources().getDimension(R.dimen.itemWidth);
        int h = (int) mainActivity.getResources().getDimension(R.dimen.itemHeight);
        Bitmap bitmap = Util.getBitmap(photos.get(position), w, h);
        imPhoto.setImageBitmap(bitmap); // Define o bitmap no ImageView
        imPhoto.setOnClickListener(new View.OnClickListener() { // adiciono um ouvidor de clicks
            @Override
            public void onClick(View view) {
                mainActivity.startPhotoActivity(photos.get(position)); //mostro a foto
            }
        });
    }

    // Método do número de itens na lista
    @Override
    public int getItemCount() {
        return photos.size();
    }
}