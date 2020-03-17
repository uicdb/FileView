package com.uicdb.app.view;

import android.*;
import android.app.*;
import android.os.*;
import com.uicdb.app.view.holders.*;
import java.io.*;
import com.uicdb.app.view.listener.*;
import android.widget.*;

public class MainActivity extends Activity 
{
	FileView fileview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0x5a);
		try{
			
			}finally{
				fileview = FileView.get(this, new SrcModel.SrcModelBuilder(this).add(".txt", R.drawable.ic_launcher).apply(), Environment.getExternalStorageDirectory(), new FileItemListener(){

						@Override
						public void onGoBack(File dir)
						{
							toast("你点了返回，原文件夹是"+dir.getAbsolutePath());
						}
						

						@Override
						public void onRename(File file)
						{
							toast("重命名之前的文件名是"+file.getName());
						}
						

						@Override
						public void onOpenFile(File file)
						{
							toast("你打开了"+file.getName());
						}

						@Override
						public void onOpenFolder(File dir)
						{
							toast("你打开了"+dir.getName()+"文件夹");
						}

						@Override
						public void onDeleteFile(File file)
						{
							toast("你删除了"+file.getName());
						}

						@Override
						public void onCreateNewFile(File file)
						{
							toast("你新建了"+file.getName());
						}

						@Override
						public void onCopyFiles(File[] file)
						{
							// TODO: Implement this method
						}

						@Override
						public void onMoveFiles(File[] file)
						{
							// TODO: Implement this method
						}
					});
				setContentView(fileview);
			}
			fileview.setShowPicture(true);
    }

	public void toast(String str){
		Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onBackPressed()
	{
		if(fileview.canGoBack()){
		fileview.goBack();
		}else{
			super.onBackPressed();
		}
	}

	@Override
	public void finish()
	{
		System.exit(0);
	}
	
}

