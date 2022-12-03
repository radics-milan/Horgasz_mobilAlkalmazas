package com.example.horgszmobilalkalmazs;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView catchDateTextView;
    ImageView catchImageView;
    ImageView selectPhotoImageView;
    ImageView takePhotoImageView;
    EditText catchSizeEditText;
    EditText catchWeightEditText;
    EditText catchBaitEditText;
    EditText catchLocationEditText;
    Spinner catchSpeciesSpinner;
    ImageView backImageView;
    Button saveCatchButton;
    Button cancelCatchButton;
    TextView catchFlagTextView;
    TextView catchSizeFlagTextView;
    TextView catchDateFlagTextView;
    SimpleDateFormat sdf;
    ArrayList<String> filterNames = new ArrayList<>();
    ArrayList<String> errors = new ArrayList<>();
    ClassFish catchFish = null;

    String catchDate = null;
    String catchName = null;
    String catchImage = null;
    int catchSize = 0;
    float catchWeight = 0;
    String catchBait = null;
    String catchLocation = null;


    Uri catchImageUri = null;
    String photographedImagePath = null;

    private static final int SELECT_PHOTO_CODE = 1000;
    private static final int TAKE_PHOTO_CODE = 1001;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 123;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 124;

    String[] Errors = {
        "Nincs kép kiválasztva!",
        "Nincs faj kiválasztva!",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catch);

        catchDateTextView = findViewById(R.id.catchDateTextView);
        catchImageView = findViewById(R.id.catchImageView);
        selectPhotoImageView = findViewById(R.id.selectPhotoImageView);
        takePhotoImageView = findViewById(R.id.takePhotoImageView);
        catchSizeEditText = findViewById(R.id.catchSizeEditText);
        catchWeightEditText = findViewById(R.id.catchWeightEditText);
        catchBaitEditText = findViewById(R.id.catchBaitEditText);
        catchLocationEditText = findViewById(R.id.catchLocationEditText);
        catchSpeciesSpinner = findViewById(R.id.catchSpeciesSpinner);
        backImageView = findViewById(R.id.backImageView);
        saveCatchButton = findViewById(R.id.saveCatchButton);
        cancelCatchButton = findViewById(R.id.cancelCatchButton);
        catchFlagTextView = findViewById(R.id.catchFlagTextView);
        catchSizeFlagTextView = findViewById(R.id.catchSizeFlagTextView);
        catchDateFlagTextView = findViewById(R.id.catchDateFlagTextView);

        if (savedInstanceState != null) {
            catchImageUri =  Uri.parse(savedInstanceState.getString("image"));
        }

        sdf = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss", Locale.getDefault());
        catchDateTextView.setText( sdf.format(new Date()));

        catchDateTextView.setOnClickListener(o -> selectDate());
        backImageView.setOnClickListener(o -> finish());
        cancelCatchButton.setOnClickListener(o -> finish());
        saveCatchButton.setOnClickListener(o -> saveCatch());

        filterNames.add("Válassz fajt!");
        for (ClassFish fish: new DatabaseFish(this).getAllDataFromLocalStore()) {
            filterNames.add(fish.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catchSpeciesSpinner.setAdapter(adapter);
        catchSpeciesSpinner.setSelection(0);
        catchSpeciesSpinner.setOnItemSelectedListener(this);

        selectPhotoImageView.setOnClickListener(o -> checkReadExternalStoragePermission());
        takePhotoImageView.setOnClickListener(o -> checkWriteExternalStoragePermission());

        if (catchImageUri == null){
            errors.add(Errors[0]);
        }


        catchSizeEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                String text = mEdit.toString();
                if (text.equals("")){
                    catchSizeFlagTextView.setVisibility(View.GONE);
                }
                if (catchFish != null && !text.equals("") ){
                    int size = Integer.parseInt(text);
                    String date = catchDateTextView.getText().toString().substring(5, 10);
                    if (!catchFish.isCloseSeasonAtDate(date) && catchFish.getMinimumCatchSize() != 0){
                        if (!catchFish.isBiggerThanMinimumCatchSize(size)){
                            String catchSizeFlagText = "Méreten aluli (" + catchFish.getMinimumCatchSize() +" cm alatti) hal nem tartható meg!";
                            catchSizeFlagTextView.setText(catchSizeFlagText);
                            catchSizeFlagTextView.setVisibility(View.VISIBLE);
                        } else {
                            catchSizeFlagTextView.setVisibility(View.GONE);
                        }
                    } else if (catchFish.isCloseSeasonAtDate(date) && catchFish.getMinimumCatchSizeInCloseSeason() != 0){
                        if (!catchFish.isBiggerThanMinimumCatchSizeInCloseSeason(size)){
                            String catchSizeFlagText = "Méreten aluli (" + catchFish.getMinimumCatchSizeInCloseSeason() +" cm alatti) hal nem tartható meg!";
                            catchSizeFlagTextView.setText(catchSizeFlagText);
                            catchSizeFlagTextView.setVisibility(View.VISIBLE);
                        } else {
                            catchSizeFlagTextView.setVisibility(View.GONE);
                        }
                    }

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (catchImageUri != null){
            catchImageView.setImageURI(catchImageUri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getItemAtPosition(i).toString().equals(filterNames.get(0))){
            errors.add(Errors[1]);
        } else {
            catchFish = new DatabaseFish(this).getHalByNev(adapterView.getItemAtPosition(i).toString());
            removeError(Errors[1]);

            catchSizeEditText.setText("");

            String date = catchDateTextView.getText().toString().substring(5, 10);
            if (catchFish.isCloseSeasonAtDate(date)){
                String catchDateFlagText;
                if (catchFish.getName().equals("Harcsa")){
                    catchDateFlagText = catchFish.getName() + " tilalmi időszak van! Csak a legalább 100 cm-es példány tartható meg!";
                } else {
                    catchDateFlagText = catchFish.getName() + " tilalmi időszak van! Tilos a megtartása!";
                } catchDateFlagTextView.setText(catchDateFlagText);
                catchDateFlagTextView.setVisibility(View.VISIBLE);

            } else {
                catchDateFlagTextView.setVisibility(View.GONE);
            }
            String catchFlagText;
            switch (catchFish.getType()){
                case "Őshonos, időszakos felmentéssel fogható faj":
                    catchFlagText = "A hal megtartásához engedély szükséges!";
                    catchFlagTextView.setText(catchFlagText);
                    catchFlagTextView.setVisibility(View.VISIBLE);
                    break;
                case "Védett faj":
                    catchFlagText = "Védett halat tilos megtartani!";
                    catchFlagTextView.setText(catchFlagText);
                    catchFlagTextView.setVisibility(View.VISIBLE);
                    break;
                case "Idegenhonos, invazív faj":
                    catchFlagText = "Invazív halfajt tilos visszaengedni!";
                    catchFlagTextView.setText(catchFlagText);
                    catchFlagTextView.setVisibility(View.VISIBLE);
                    break;
                default:
                    catchFlagTextView.setText("");
                    catchFlagTextView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void removeError(String errorText){
        for (int i = 0; i < errors.size() ; i++) {
            if (errors.get(i).equals(errorText)){
                errors.remove(i);
                break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (catchImageUri != null){
            outState.putString("image", catchImageUri.toString());
        }

    }

    private void saveCatch() {
        if (errors.size() == 0){
            catchDate = catchDateTextView.getText().toString();
            catchImage = catchImageUri.toString();
            catchName = catchFish.getName();
            if (!catchSizeEditText.getText().toString().equals("")){
                catchSize = Integer.parseInt(catchSizeEditText.getText().toString());
            }
            if (!catchWeightEditText.getText().toString().equals("")){
                catchWeight = Float.parseFloat(catchWeightEditText.getText().toString());
            }
            if (!catchBaitEditText.getText().toString().equals("")){
                catchBait = catchBaitEditText.getText().toString();
            }
            if (!catchLocationEditText.getText().toString().equals("")){
               catchLocation = catchLocationEditText.getText().toString();
            }
            DatabaseCatch databaseCatch = new DatabaseCatch(this);
            databaseCatch.addCatch(new ClassCatch(catchDate, catchName, catchImage, catchSize, catchWeight, catchBait, catchLocation));

            catchDateTextView.setText(sdf.format(new Date()));
            catchSizeEditText.setText("");
            catchWeightEditText.setText("");
            catchBaitEditText.setText("");
            catchLocationEditText.setText("");
            catchSpeciesSpinner.setSelection(0);
            catchDateFlagTextView.setVisibility(View.GONE);
            catchFlagTextView.setVisibility(View.GONE);
            catchFish = null;
            Toast.makeText(this, "Sikeres hozzáadás! (" + catchName + ")", Toast.LENGTH_SHORT).show();
        } else {
            showErrors();
        }
    }

    private void showErrors(){
        StringBuilder error = new StringBuilder();
        for (int i = 0; i < errors.size() ; i++) {
            if (i < errors.size() -1){
                error.append(errors.get(i)).append("\n");
            } else {
                error.append(errors.get(i));
            }
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void selectDate() {
        Calendar date;
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(AddCatchActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
            date.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(AddCatchActivity.this, (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                catchDateTextView.setText(sdf.format(date.getTime()));
                if (!errors.contains(Errors[1])) {
                    String selectedDate = catchDateTextView.getText().toString().substring(5, 10);
                    if (catchFish.isCloseSeasonAtDate(selectedDate)){
                        String catchDateFlagText;
                        if (catchFish.getName().equals("Harcsa")){
                            catchDateFlagText = catchFish.getName() + " tilalmi időszak van! Csak a legalább 100 cm-es példány tartható meg!";
                        } else {
                            catchDateFlagText = catchFish.getName() + " tilalmi időszak van! Tilos a megtartása!";
                        } catchDateFlagTextView.setText(catchDateFlagText);
                        catchDateFlagTextView.setVisibility(View.VISIBLE);

                    } else {
                        catchDateFlagTextView.setVisibility(View.GONE);
                    }
                }

            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    private void checkReadExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
            } else {
                selectPhoto();
            }
        }
    }

    private void checkWriteExternalStoragePermission(){
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
            } else{
                takePhoto();
            }
        }
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO_CODE);
    }

    public void takePhoto(){
        String fileName = "photo";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
            photographedImagePath = imageFile.getAbsolutePath();
            Uri imageUri = FileProvider.getUriForFile(this, "com.example.horgszmobilalkalmazs.fileprovider", imageFile);
            catchImageUri = imageUri;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case READ_EXTERNAL_STORAGE_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    selectPhoto();
                } else {
                    Toast.makeText(this, "Nincs megadva engedély fénykép kiválasztásához", Toast.LENGTH_SHORT).show();
                }
                break;
            case WRITE_EXTERNAL_STORAGE_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePhoto();
                } else {
                    Toast.makeText(this, "Nincs megadva engedély fénykép készítéshez", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO_CODE && resultCode == RESULT_OK){
            catchImageUri = data.getData();
            catchImageView.setImageURI(catchImageUri);
            removeError(Errors[0]);
        } else if(requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK){
            catchImageView.setImageURI(catchImageUri);
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(), photographedImagePath, sdf.format(new Date()), "kep");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            removeError(Errors[0]);
        }
    }

}