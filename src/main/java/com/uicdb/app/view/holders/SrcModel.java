package com.uicdb.app.view.holders;
import android.graphics.drawable.*;
import java.util.*;
import android.content.*;

public class SrcModel
{
	String end;
	Drawable src;

	private SrcModel(String end, Drawable src)
	{
		this.end = end;
		this.src = src;
	}

	public void setEnd(String end)
	{
		this.end = end;
	}

	public String getEnd()
	{
		return end;
	}

	public void setSrc(Drawable src)
	{
		this.src = src;
	}

	public Drawable getSrc()
	{
		return src;
	}
	public static final class SrcModelBuilder{
		Context context;
		public SrcModelBuilder(Context context){
			this.context=context;
		}
		private final ArrayList<SrcModel> list=new ArrayList<>();
		public SrcModelBuilder add(String end,Drawable src){
			list.add(new SrcModel(end,src));
			return this;
		}
		public SrcModelBuilder add(String end,int resId){
			list.add(new SrcModel(end,context.getResources().getDrawable(resId)));
			return this;
		}
		public ArrayList<SrcModel> apply(){
			return list;
		}
	}
}
