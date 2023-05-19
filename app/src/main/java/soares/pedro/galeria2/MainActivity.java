package soares.pedro.galeria2;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.FileProvider;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import android.Manifest;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.widget.Toast;
        import java.io.File;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;

    static int RESULT_REQUEST_PERMISSION = 2;

    String currentPhotoPath; // Caminho da foto atual

    List<String> photos = new ArrayList<>(); // Lista de fotos

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbMain); // Obtém a Toolbar do layout
        setSupportActionBar(toolbar); // Define a Toolbar como ActionBar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // Habilita o botão de voltar na ActionBar

        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Imagens armazenadas
        File[] files = dir.listFiles(); // Lista dos arquivos
        for (int i = 0; i < files.length; i++) {
            photos.add(files[i].getAbsolutePath()); // Adiciona os caminhos da fotos
        }
        mainAdapter = new MainAdapter(MainActivity.this, photos);

        RecyclerView rvGallery = findViewById(R.id.rvGallery); // pega a RecyclerView do layout
        rvGallery.setAdapter(mainAdapter);

        float w = getResources().getDimension(R.dimen.itemWidth); // Largura dos itens
        int numberOfColumns = Util.calculateNoOfColumns(MainActivity.this, w); // Número de colunas com base na largura dos itens
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns); // Cria o layout
        rvGallery.setLayoutManager(gridLayoutManager); // Define o layout da RecyclerView

        List<String> permissions = new ArrayList<>(); // Lista de permissões
        permissions.add(Manifest.permission.CAMERA); // Adiciona a permissão de acesso à câmera

        checkForPermissions(permissions); // Verifica as permissões
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater(); // Cria o inflater
        inflater.inflate(R.menu.main_activity_tb, menu); // Infla o menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) { // Verifica qual item foi selecionado
            case R.id.opCamera:
                dispatchTakePictureIntent(); // Inicia a captura de foto
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startPhotoActivity(String photoPath) {
        Intent i = new Intent(MainActivity.this, PhotoActivity.class);
        i.putExtra("photo_path", photoPath); // Inicia a atividade de visualização da foto com o caminho da foto como extra
        startActivity(i);
    }

    private void dispatchTakePictureIntent() {
        File f = null;
        try {
            f = createImageFile(); // Cria o arquivo de imagem
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        currentPhotoPath = f.getAbsolutePath(); // Obtém o caminho da imagem

        if (f != null) {
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "soares.pedro.galeria2.fileprovider", f); // Obtém a URI do arquivo de imagem
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
            startActivityForResult(i, RESULT_TAKE_PICTURE); // Inicia a captura de foto
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //define como nome o momento que a foto foi tirada
        String imageFileName = "JPEG_" + timeStamp; // acrescenta JPEG_ antes do timestamp
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f; // Cria um arquivo de imagem jpg com o nome definido
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                photos.add(currentPhotoPath); // Adiciona a foto capturada à lista de fotos
                mainAdapter.notifyItemInserted(photos.size() - 1); // Notifica a adição de um novo item
            } else {
                File f = new File(currentPhotoPath);
                f.delete(); // Exclui o arquivo de imagem criado
            }
        }
    }

    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                permissionsNotGranted.add(permission);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), RESULT_REQUEST_PERMISSION);
            }
        }
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsNotGranted = new ArrayList<>();

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                permissionsNotGranted.add(permissions[i]);
            }
        }

        if (requestCode == RESULT_REQUEST_PERMISSION) {
            if (permissionsNotGranted.isEmpty()) {
                // Permissões concedidas
            } else {
                // Permissões negadas
            }
        }
    }
}