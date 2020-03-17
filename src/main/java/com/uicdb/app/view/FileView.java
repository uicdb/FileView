package com.uicdb.app.view;
import android.content.*;
import android.util.*;
import android.widget.*;
import com.uicdb.app.view.adapters.*;
import com.uicdb.app.view.holders.*;
import java.io.*;
import java.util.*;
import android.view.*;
import com.uicdb.app.view.listener.*;

public class FileView extends RelativeLayout
{
	SimpleFileView real_FileView;
	boolean showPicture=false;
	public SimpleFileListAdapter real_FileViewAdapter;

	public void setShowPicture(boolean showPicture)
	{
		if(real_FileView!=null){
			this.showPicture = showPicture;
			real_FileView.setShowPicture(showPicture);
		}
	}

	public boolean isShowPicture()
	{
		return showPicture;
	}
	public static FileView get(Context context,ArrayList<SrcModel> models,File defaultFile,FileItemListener listener) {
		final FileView f=(FileView) LayoutInflater.from(context).inflate(R.layout.filelayout,null);
		f.findViewById(R.id.filelayoutHomeView).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v)
				{
					f.goHome();
				}
			});
		f.initView(context,models,defaultFile,listener);
		return f;
	}
    public FileView(Context context,AttributeSet attrs) {
		super(context,attrs);
		init();
	}
	public FileView(Context context,AttributeSet attrs,int ds) {
		super(context,attrs,ds);
		init();
	}
	public void goHome(){
		if(real_FileViewAdapter!=null)real_FileViewAdapter.goDefaultDir();
	}
	public void goBack(){
		if(real_FileViewAdapter!=null)real_FileViewAdapter.goBack();
	}
	public boolean canGoBack(){
		return real_FileView.canGoBack();
	}
	public void selectAllCheckBox(boolean bool){
		real_FileView.selectAllCheckBox(bool);
	}
	private void initView(Context context,ArrayList<SrcModel> models,File defaultFile,FileItemListener l){
		real_FileView=findViewById(R.id.filelayoutSimpleFileView);
		real_FileView.initView(context,models,defaultFile,l);
		real_FileView.setTag(this);
		real_FileViewAdapter=(SimpleFileListAdapter) real_FileView.getAdapter();
		((TextView)findViewById(R.id.filelayoutPathText)).setText(defaultFile.getAbsolutePath());
		((CheckBox)findViewById(R.id.filelayoutCheckBox1))
			.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton checkbox, boolean open)
				{
					FileView.this.selectAllCheckBox(open);
				}
			});
		findViewById(R.id.filelayoutLinearLayoutHeader).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v)
				{
					if(FileView.this.canGoBack())
					FileView.this.goBack();
				}
			});
	}
	
	private void init(){
		}
}
