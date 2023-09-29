package com.imagee.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView.OnCloseListener;
import android.Manifest;
import android.view.View.OnClickListener;
import android.view.View;
import android.os.Build;
import android.content.pm.PackageManager;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
	
	ImageView mImageView;
	Button mChoosebtn;
	
	private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        //VIEWS
		mImageView = findViewById(R.id.image_view);
		mChoosebtn = findViewById(R.id.choose_image_btn);
		
		//handle button click
		mChoosebtn.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				//check runtime permission
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
				{
					if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
						//permission not granted, request it.
						String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
						//show popup for runtime permission
						requestPermissions(permissions, PERMISSION_CODE);
					}
					else {
						//permission already granted
						pickImageFromGallery();
					}
				}
				else {
					//system os is less than marshmallow
				}
			}

		});
		}
		
	private void pickImageFromGallery() {
		//intent to pick image
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, IMAGE_PICK_CODE);
		
	}

	//handle result of runtime permission
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	  switch (requestCode) {
		  case PERMISSION_CODE:{
			  if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
				  //permission was granted
				  pickImageFromGallery();
			  }
			  else {
				  //permission was denied
				  Toast.makeText(this,"Permission denied...!", Toast.LENGTH_SHORT).show();
			  }
		  }
	  }
	}

	//handle result of picked image
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
		//set image to image view
		mImageView.setImageURI(data.getData());
	}
	}
	
	}
	   
