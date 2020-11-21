package com.example.bccprojecttest3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.images.ImageRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class userInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CHOOSE_IMAGE = 101;

    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageUrl;

    CircleImageView imageView;
    EditText namaText, dpText,kotaText;
    TextView tglLahir;


    FirebaseAuth mAuth;
    Calendar calendar;
    DatePickerDialog datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mAuth = FirebaseAuth.getInstance();

        namaText = findViewById(R.id.namaText);
        dpText = findViewById(R.id.dpText);
        kotaText = findViewById(R.id.kotaText);

        findViewById(R.id.saveInfoButton).setOnClickListener(this);
        findViewById(R.id.tglButton).setOnClickListener(this);

        tglLahir = findViewById(R.id.tglLahir);
        progressBar = findViewById(R.id.progressBar);
        imageView = (CircleImageView) findViewById(R.id.profile_image);
        //============================= Saat user klik gambar=======================
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showImageChooser();
            }
        });
        //===========================================================================
    }

    private void showImageChooser(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImagetoFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void uploadImagetoFirebaseStorage(){
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");

        if(uriProfileImage != null){
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);

                            profileImageUrl = profileImageRef.getDownloadUrl().toString();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(userInfoActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void pilihTgl() {
        calendar = Calendar.getInstance();

//        calendar.add(Calendar.MONTH,1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        int year = calendar.get(Calendar.YEAR);

        datePicker= new DatePickerDialog(userInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tglLahir.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        },day,month,year);
        datePicker.show();
    }

    private void saveInfo(){
        String displayName = dpText.getText().toString().trim();
        String nama = namaText.getText().toString().trim();
        String kota = kotaText.getText().toString().trim();
        String tanggalLhr = tglLahir.getText().toString();

        if(displayName.isEmpty()){
            dpText.setError("Kolom ini tidak boleh kosong");
            dpText.requestFocus();
            return;
        }
        if(nama.isEmpty()){
            namaText.setError("Kolom ini tidak boleh kosong");
            namaText.requestFocus();
            return;
        }
        if(kota.isEmpty()){
            kotaText.setError("Kolom ini tidak boleh kosong");
            kotaText.requestFocus();
            return;
        }
        if(tglLahir.equals("dd/mm/yyyy")){
            tglLahir.setError("Tolong masukan tanggal lahir");
            tglLahir.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            //==========================Saving DP and Photo UTI to FB Storage===========================================
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName).setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(userInfoActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //=========================Saving the other userinfo to FB DB==============================================
            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

            Users pengguna = new Users(nama,tanggalLhr,kota);
            current_user_db.setValue(pengguna);

        }
        Intent intent = new Intent(userInfoActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveInfoButton:
                saveInfo();
                break;
            case R.id.tglButton:
                pilihTgl();
                break;
        }
    }

}
