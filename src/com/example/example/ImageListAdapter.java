package com.example.example;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListAdapter extends BaseAdapter {

	private ArrayList<Image> imageList;
	private int layout;
	private Context context;

	public ImageListAdapter(Context context, int layout, ArrayList<Image> imageList) {

		this.imageList = imageList;
		this.layout = layout;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private class ViewHolder {

		TextView txtName;
		TextView txtDate;
		ImageView imageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		ViewHolder holder = new ViewHolder();

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layout, null);

			holder.txtName = (TextView) row.findViewById(R.id.nameTextView);
			holder.txtDate = (TextView) row.findViewById(R.id.dateTextView);
			holder.imageView = (ImageView) row.findViewById(R.id.imageView);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		Image image = imageList.get(position);

		holder.txtName.setText(image.getName());
		holder.txtDate.setText(image.getDate());

		byte[] anImage = image.getImage();
		Bitmap bitmap = BitmapFactory.decodeByteArray(anImage, 0, anImage.length);
		holder.imageView.setImageBitmap(bitmap);

		return row;
	}

}
