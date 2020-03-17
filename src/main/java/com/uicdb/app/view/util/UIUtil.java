package com.uicdb.app.view.util;

import java.io.*;
import android.content.*;
import android.app.*;
import org.apache.commons.io.*;
import com.uicdb.app.view.adapters.*;
import android.widget.*;
import android.text.*;
import com.uicdb.app.view.listener.*;

public class UIUtil
{
	public static ProgressDialog deleteDialog;
	public static void showDeleteFileDialog(final Context context,final File file,final SimpleFileListAdapter adapter,final FileItemListener l){
		new AlertDialog.Builder(context)
		.setTitle("确定删除该文件/文件夹吗?")
		.setMessage(file.getAbsolutePath())
			.setNegativeButton("确定", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					l.onDeleteFile(file);
					if(file.isFile()){
						if(deleteDialog==null||!deleteDialog.getContext().equals(context)){
							deleteDialog=ProgressDialog.show(context,"正在删除","请稍等");
						}else{
							deleteDialog.show();
						}
						try{
							new Thread(new Runnable(){

									@Override
									public void run()
									{
										file.delete();
										adapter.reloadDir();
									}
								}).start();
						}finally{
							deleteDialog.hide();
							System.gc();
						}
					}else if(file.isDirectory()){
						try
						{
							ProgressDialog pd=new ProgressDialog(context);
							pd.setMessage("正在删除....");
							try{
								pd.show();
								FileUtils.deleteDirectory(file);
								adapter.reloadDir();
							}finally{
								pd.dismiss();
								System.gc();
							}
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}).setPositiveButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					p1.dismiss();
				}
			}).create().show();
	}
	public static void showRenameConfirm(final Context context,final File file,final SimpleFileListAdapter adapter,final FileItemListener listener){
		final EditText input=new EditText(context);
		input.setHint("请输入新的名字");
		new AlertDialog.Builder(context).setTitle("重命名").setView(input)
			.setNegativeButton("确定", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					String text=input.getText().toString();
					if(TextUtils.isEmpty(text)){
						Toast.makeText(context,"不能为空,重命名失败",Toast.LENGTH_SHORT).show();
					}else{
						listener.onRename(file);
						file.renameTo(new File(file.getParent(),text));
						adapter.reloadDir();
					}
					p1.dismiss();
				}
			}).setPositiveButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					p1.dismiss();
				}
			}).create().show();
	}
}
