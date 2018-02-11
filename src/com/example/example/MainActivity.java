package com.example.example;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText nameEdit;
	ImageView imgView;
	Button choBtn, addBtn, lstBtn;

	public static Database database;
	private static int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayUseLogoEnabled(true);

		init();

		database = new Database(this);
		database.queryData(
				"CREATE TABLE IF NOT EXISTS IMAGES (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, image BLOB)");

		choBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});

		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
				Drawable img = getResources().getDrawable(R.drawable.ic_launcher);
				Bitmap bitmap2 = ((BitmapDrawable) img).getBitmap();

				if (nameEdit.getText().length() > 0 && bitmap != bitmap2) {
					try {
						database.insertData(nameEdit.getText().toString().trim(),
								new SimpleDateFormat("dd MMM").format(new Date()).trim(), imageViewToByte(imgView));
						Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
						nameEdit.setText("");
						imgView.setImageResource(R.drawable.gray_plus);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Fill all the blanks!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		lstBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
				startActivity(intent);
			}
		});
	}

	public static byte[] imageViewToByte(ImageView image) {
		Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			imgView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}

	}

	private void init() {

		nameEdit = (EditText) findViewById(R.id.nameEdt);
		imgView = (ImageView) findViewById(R.id.imageView);
		choBtn = (Button) findViewById(R.id.chooseButton);
		addBtn = (Button) findViewById(R.id.addButton);
		lstBtn = (Button) findViewById(R.id.listButton);
	}
}
