package com.example.example;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class CategoriesActivity extends Activity {

	GridView gridView;
	ArrayList<Image> list;
	ImageListAdapter adapter = null;

	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		gridView = (GridView) findViewById(R.id.gridView);
		list = new ArrayList<Image>();
		adapter = new ImageListAdapter(this, R.layout.custom_layout, list);
		gridView.setAdapter(adapter);

		// get all data from database
		Cursor cursor = MainActivity.database.getData("SELECT * FROM IMAGES ORDER BY ID DESC");
		list.clear();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			String date = cursor.getString(2);
			byte[] image = cursor.getBlob(3);
			list.add(new Image(id, name, date, image));
		}

		adapter.notifyDataSetChanged();

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// TODO Auto-generated method stub
				Intent i = new Intent(CategoriesActivity.this, ImagesActivity.class);
				i.putExtra("name", list.get(position).getName());
				startActivity(i);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 888 && resultCode == RESULT_OK && data != null) {
			Uri uri = data.getData();
			try {
				InputStream inputStream = getContentResolver().openInputStream(uri);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				imageView.setImageBitmap(bitmap);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
