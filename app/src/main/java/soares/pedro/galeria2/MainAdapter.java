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
    MainActivity mainActivity; // Referência para a MainActivity
    List<String> photos; // Lista de URLs das fotos

    public MainAdapter(MainActivity mainActivity, List<String> photos) {
        this.mainActivity = mainActivity; // Atribui a MainActivity passada como argumento
        this.photos = photos; // Atribui a lista de URLs de fotos passada como argumento
    }

    // Método chamado para inflar o layout do item da lista
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity); // Cria um LayoutInflater a partir da MainActivity
        View v = inflater.inflate(R.layout.list_item, parent, false); // Infla o layout do item da lista
        return new MyViewHolder(v); // Retorna um novo objeto MyViewHolder com a view do item inflado
    }

    // Classe interna que representa os itens da lista
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imPhoto; // Referência para o ImageView do item da lista

        public MyViewHolder(View itemView) {
            super(itemView);
            imPhoto = itemView.findViewById(R.id.imItem); // Obtém a referência do ImageView do layout do item
        }
    }

    // Método chamado para exibir os dados nos itens da lista
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        View v = holder.itemView; // Obtém a view do item
        ImageView imPhoto = v.findViewById(R.id.imItem); // Obtém a referência do ImageView do item
        int w = (int) mainActivity.getResources().getDimension(R.dimen.itemWidth); // Obtém a largura do item
        int h = (int) mainActivity.getResources().getDimension(R.dimen.itemHeight); // Obtém a altura do item
        Bitmap bitmap = Util.getBitmap(photos.get(position), w, h); // Obtém o bitmap da foto na posição atual
        imPhoto.setImageBitmap(bitmap); // Define o bitmap no ImageView
        imPhoto.setOnClickListener(new View.OnClickListener() { // Define um listener de clique para o ImageView
            @Override
            public void onClick(View view) {
                mainActivity.startPhotoActivity(photos.get(position)); // Inicia a atividade de exibição da foto
            }
        });
    }

    // Método chamado para obter o número de itens na lista
    @Override
    public int getItemCount() {
        return photos.size(); // Retorna o tamanho da lista de fotos
    }
}