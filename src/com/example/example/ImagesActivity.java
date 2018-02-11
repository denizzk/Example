package com.example.example;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class ImagesActivity extends Activity {

	GridView gridView;
	ArrayList<Image> list;
	ImageListAdapter adapter = null;

	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images);
		setTitle(getIntent().getExtras().getString("name"));

		gridView = (GridView) findViewById(R.id.gridView1);
		list = new ArrayList<Image>();
		adapter = new ImageListAdapter(this, R.layout.custom_layout2, list);
		gridView.setAdapter(adapter);

		// get all data from database
		Cursor cursor = MainActivity.database
				.getData("SELECT * FROM " + getIntent().getExtras().getString("name") + " ORDER BY ID ASC");
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
				Intent i = new Intent(ImagesActivity.this, ImageActivity.class);
				i.putExtra("id", list.get(position).getId());
				i.putExtra("name", list.get(position).getName());
				i.putExtra("date", list.get(position).getDate());
				startActivity(i);

			}
		});
	}

}
