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
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;



    public class PhotoActivity extends AppCompatActivity {
    String photoPath;

        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.opShare:
                    sharePhoto();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_photo);
            Toolbar toolbar = findViewById(R.id.tbPhoto);
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            Intent i = getIntent();
            photoPath = i.getStringExtra("photo_path");
            Bitmap bitmap = Util.getBitmap(photoPath);
            ImageView imPhoto = findViewById(R.id.imPhoto);
            imPhoto.setImageBitmap(bitmap);
        }
        void sharePhoto() {
            Uri photoUri = FileProvider.getUriForFile(this,"soares.pedro.galeria2.fileprovider", new File(photoPath));
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_STREAM, photoUri);
            i.setType("image/jpeg");
            startActivity(i);
        }
    }

