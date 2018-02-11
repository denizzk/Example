package com.example.example;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends Activity {

	ImageView imgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		setTitle(getIntent().getExtras().getString("date"));

		imgView = (ImageView) findViewById(R.id.imageView2);
		Cursor c = MainActivity.database.getData("SELECT IMAGE FROM '" + getIntent().getExtras().getString("name")
				+ "'WHERE ID ='"+ getIntent().getExtras().getInt("id")+"'");
		if (c.moveToNext()) {
			byte[] image = c.getBlob(0);
			Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);
			imgView.setImageBitmap(b);
		}
	}
}
