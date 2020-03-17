package com.uicdb.app.view.holders;

import android.view.*;
import android.widget.*;
import com.uicdb.app.view.*;

public class FileHolder 
{
	public ImageView img,rename,delete;
	public TextView big,small;
	public View view;
	public CheckBox box;
	public FileHolder(View view){
		this.view=view;
		img=view.findViewById(R.id.fileitemImg);
		big=view.findViewById(R.id.fileitemTextViewBig);
		small=view.findViewById(R.id.fileitemTextViewSmall);
		rename=view.findViewById(R.id.fileitemRename);
		delete=view.findViewById(R.id.fileitemDelete);
		box=view.findViewById(R.id.fileitemCheckBox);
	}
}
