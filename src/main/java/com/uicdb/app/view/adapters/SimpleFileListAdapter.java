package com.uicdb.app.view.adapters;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.uicdb.app.view.*;
import com.uicdb.app.view.holders.*;
import java.io.*;
import java.util.*;
import android.view.View.*;
import com.uicdb.app.view.util.*;
import com.uicdb.app.view.listener.*;
import android.app.*;

public class SimpleFileListAdapter extends BaseAdapter
{
	boolean showPicture=false;
    private SimpleFileView simpleFileView;
	private FileItemListener listener;
	HashMap<Integer,Boolean> checkBoxStates=new HashMap<>();

	ProgressDialog progress;
	public void selectAllCheckBox(final boolean state){
	  if(progress==null||!progress.getContext().equals(context)){ progress=progress.show(context,"正在选择","请等待....");}
		else{progress.show();}
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					if(state){
					for(int i=0;i<currentFile.list().length;i++){
						checkBoxStates.put(i,state);
					}
						openNewDir(currentFile,false);
					}else{
						reloadDir();
					}
						((Activity)context).runOnUiThread(new Runnable(){

								@Override
								public void run()
								{
									progress.hide();
								}
							});
						}
			}).start();
		
	}
	public boolean canGoBack()
	{
		return currentFile.getParentFile().list()!=null;
	}
	public void setSimpleFileViewAndListener(SimpleFileView simpleFileView,FileItemListener listener)
	{
		this.simpleFileView = simpleFileView;
		this.listener=listener;
	}
	public SimpleFileView getSimpleFileView()
	{
		return simpleFileView;
	}
	public void setShowPicture(boolean showPicture)
	{
		this.showPicture = showPicture;
	}

	public boolean isShowPicture()
	{
		return showPicture;
	}

	public File[] openNewDir(File newOne,boolean closeCheckBoxes){
		currentFile=newOne;
		if(closeCheckBoxes){
			checkBoxStates.clear();
		}
		File[] fj= getFiles();
		((Activity)context).runOnUiThread(new Runnable(){

				@Override
				public void run()
				{
					notifyDataSetChanged();
				}
			});
		
		return fj;
	}
	public void goDefaultDir(){
	  openNewDir(defaultFile,true);
	}
	public void reloadDir(){
		openNewDir(currentFile,true);
	}
	public void goBack(){
		listener.onGoBack(currentFile);
		if(simpleFileView!=null){
			Object tag=simpleFileView.getTag();
			if(tag!=null){
				if(!currentFile.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())){
					((TextView)((FileView)tag).findViewById(R.id.filelayoutPathText)).setText(currentFile.getParent());
				}
			}
		}
		File file=currentFile.getParentFile();
		openNewDir(file,true);
	}
	@Override
	public int getCount()
	{
		return getItemCount();
	}

	@Override
	public Object getItem(int p)
	{
		return p>thisFiles.length-1?null:thisFiles[p];
	}

	@Override
	public long getItemId(int p1)
	{
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		FileHolder holder;
		if(view==null){
			holder=onCreateViewHolder(parent,position);
			view=holder.view;
			view.setTag(holder);
		}else{
			holder=(FileHolder) view.getTag();
		}
		onBindViewHolder(holder,position);
		return view;
	}
	
	ArrayList<SrcModel> models;
	File defaultFile;
	File currentFile;
	Context context;
    File[] thisFiles;
	FileSorter sorter;
	Drawable fileDrwable,dirDrawable,picDrawable;
	private File[] getFiles(){
		if(currentFile==null){
			currentFile=defaultFile;
		}
		final File[] fdir=currentFile.listFiles(new FilenameFilter(){

				@Override
				public boolean accept(File parent, String name)
				{
					return new File(parent,name).isDirectory();
				}
			});
		final File[] ffile=currentFile.listFiles(new FilenameFilter(){

				@Override
				public boolean accept(File parent, String name)
				{
					return new File(parent,name).isFile();
				}
			});
			if(fdir!=null&&ffile!=null){
		Arrays.sort(fdir,sorter);
		Arrays.sort(ffile,sorter);
		final File[] mFiles=new File[fdir.length+ffile.length];
		System.arraycopy(fdir,0,mFiles,0,fdir.length);
		System.arraycopy(ffile,0,mFiles,fdir.length,ffile.length);
		return thisFiles=mFiles;
		}
		return currentFile.listFiles();
	}
	public SimpleFileListAdapter(Context context,ArrayList<SrcModel> models,File defaultFile){
		super();
		this.models=models;
		this.defaultFile=defaultFile;
		this.context=context;
		sorter=new FileSorter();
	}
	public SimpleFileListAdapter(Context context,ArrayList<SrcModel> models,File[] mfiles){
		super();
		this.models=models;
		if(mfiles.length>0){
			defaultFile=mfiles[0].getParentFile();
		}else{
			defaultFile=Environment.getExternalStorageDirectory();
		}
		this.context=context;
		sorter=new FileSorter();
	}
	public FileHolder onCreateViewHolder(ViewGroup group, int index)
	{
		return new FileHolder(LayoutInflater.from(context).inflate(R.layout.fileitem,group,false));
	}

	public void onBindViewHolder(FileHolder holder, final int index)
	{
		if(index<thisFiles.length){
			Boolean open=checkBoxStates.get(index);
			holder.box.setChecked(open==null?false:open);
			final File file=thisFiles[index];
			String name=file.getName();
			holder.delete.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View view)
					{
						UIUtil.showDeleteFileDialog(view.getContext(),file,SimpleFileListAdapter.this,listener);
					}
				});
			holder.rename.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View view)
					{
						UIUtil.showRenameConfirm(view.getContext(),file,SimpleFileListAdapter.this,listener);
					}
				});
			holder.box.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton button, boolean state)
					{
						checkBoxStates.put(index,state);
					}
				});
			if(file.isFile()){
				holder.img.setImageDrawable(fileDrwable==null?fileDrwable=context.getResources().getDrawable(R.drawable.file_file):fileDrwable);
				if(name.endsWith(".png")||name.endsWith(".jpg")){
					if(showPicture){
					holder.img.setImageDrawable(Drawable.createFromPath(file.getAbsolutePath()));
					}else{
						holder.img.setImageDrawable(picDrawable==null?picDrawable=context.getResources().getDrawable(R.drawable.file_img):picDrawable);
					}
				}
				for(SrcModel model:models){
					if(name.endsWith(model.getEnd())){
						holder.img.setImageDrawable(model.getSrc());
						break;
					}
				}
				holder.small.setText(""+file.length());
			}else{
				holder.img.setImageDrawable(dirDrawable==null?dirDrawable=context.getResources().getDrawable(R.drawable.file_dir):dirDrawable);
				holder.small.setText("");
			}
			holder.big.setText(name);
		}
	}

	public int getItemCount()
	{
		return getFiles().length;
	}

}
