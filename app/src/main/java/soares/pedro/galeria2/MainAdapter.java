package soares.pedro.galeria2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

    public class MainAdapter extends RecyclerView.Adapter {

    MainActivity mainActivity;
    List<String> photos;

    public MainAdapter(MainActivity mainActivity, List<String> photos) {
        this.mainActivity = mainActivity;
        this.photos = photos;

    }

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
        @Override
        public int getItemCount() {
            return photos.size();
        }

        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            View v = holder.itemView; // Obtém a view do item
            ImageView imPhoto = holder.itemView.findViewById(R.id.imItem);
            int w = (int) mainActivity.getResources().getDimension(R.dimen.itemWidth); // Obtém a largura do item
            int h = (int) mainActivity.getResources().getDimension(R.dimen.itemHeight); // Obtém a altura do item

            Bitmap bitmap = Util.getBitmap(photos.get(position), w, h);
            imPhoto.setImageBitmap(bitmap);
            imPhoto.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    mainActivity.startPhotoActivity(photos.get(position));
                }
            });
        }
    }
