package com.uicdb.app.view.holders;
import java.io.*;
import java.util.*;

public class FileSorter implements Comparator<File>
{

	@Override
	public int compare(File a, File b)
	{
		return a.getName().compareToIgnoreCase(b.getName());
	}

	@Override
	public boolean equals(Object obj)
	{
		
		return false;
	}
	public static File[] sort(File currentFile){
		FileSorter sorter=new FileSorter();
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
		Arrays.sort(fdir,sorter);
		Arrays.sort(ffile,sorter);
		final File[] mFiles=new File[fdir.length+ffile.length];
		System.arraycopy(fdir,0,mFiles,0,fdir.length);
		System.arraycopy(ffile,0,mFiles,fdir.length,ffile.length);
		return mFiles;
	}
}
