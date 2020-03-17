package com.uicdb.app.view;
import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.uicdb.app.view.adapters.*;
import com.uicdb.app.view.holders.*;
import com.uicdb.app.view.listener.*;
import java.io.*;
import java.util.*;
import com.uicdb.app.view.util.*;

public class SimpleFileView extends ListView
{
	SimpleFileListAdapter adapter;
	boolean showPicture=false;
	public SimpleFileView(Context context) {
		super(context);
	}
	
    public SimpleFileView(Context context,AttributeSet attrs) {
		super(context,attrs);
	}

    public SimpleFileView(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
	}

	public void setShowPicture(boolean showPicture)
	{
		if(adapter!=null){
		this.showPicture = showPicture;
		adapter.setShowPicture(showPicture);
		}
	}

	public boolean isShowPicture()
	{
		return showPicture;
	}
	public void initView(Context context,ArrayList<SrcModel> models,File defaultFile,FileItemListener listener){
		setAdapter(adapter=new SimpleFileListAdapter(context,models,defaultFile));
		initListener(listener);
	}
	private void initListener(final FileItemListener listener){
		adapter.setSimpleFileViewAndListener(this,listener);
		setItemsCanFocus(true);
		setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> list, View v, int p, long id)
				{
					SimpleFileListAdapter adapter=(SimpleFileListAdapter) list.getAdapter();
					File file_clicked=(File) adapter.getItem(p);
					if(file_clicked!=null){
						if(file_clicked.isDirectory()){
							listener.onOpenFolder(file_clicked);
							adapter.openNewDir(file_clicked,true);
							if(getTag()!=null)((TextView)((FileView)getTag()).findViewById(R.id.filelayoutPathText)).setText(file_clicked.getAbsolutePath());
						}else if(file_clicked.isFile()){
							listener.onOpenFile(file_clicked);
						}
					}
				}
			});
	}
	public boolean canGoBack(){
		return adapter.canGoBack();
	}
	public void selectAllCheckBox(boolean bool){
		adapter.selectAllCheckBox(bool);
	}
}
