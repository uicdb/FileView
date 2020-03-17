package com.uicdb.app.view.listener;
import java.io.*;

public interface FileItemListener
{
	public void onOpenFile(File file);
	public void onOpenFolder(File dir);
	public void onDeleteFile(File file);
	public void onCreateNewFile(File file);
	public void onCopyFiles(File[] file);
	public void onMoveFiles(File[] file);
	public void onGoBack(File dir);
	public void onRename(File file);
}
