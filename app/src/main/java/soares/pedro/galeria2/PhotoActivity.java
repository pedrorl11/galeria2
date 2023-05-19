package soares.pedro.galeria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {

    String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = findViewById(R.id.tbPhoto); // pego a Toolbar
        setSupportActionBar(toolbar); // Definindo como ActionBar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // Habilito o botão de voltar

        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path"); // Obtendo o caminho da foto

        Bitmap bitmap = Util.getBitmap(photoPath);
        ImageView imPhoto = findViewById(R.id.imPhoto); // pego o ImageView
        imPhoto.setImageBitmap(bitmap); // Definindo o bitmap no ImageView para exibir a foto
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater(); // Crio um inflador
        inflater.inflate(R.menu.photo_activity_tb, menu); // Inflo o menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opShare:
                sharePhoto(); // chamo o metodo de compartilhar foto
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void sharePhoto() {
        Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this, "soares.pedro.galeria2.fileprovider", new File(photoPath)); // pego a uri da foto
        Intent i = new Intent(Intent.ACTION_SEND); // Criando uma intent para compartilhamento
        i.putExtra(Intent.EXTRA_STREAM, photoUri);
        i.setType("image/jpeg"); // Defino a imagem como JPEG
        startActivity(i);
    }
}