package com.snapdoc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.snapdoc.constants.SnapDocConstants;

public class SnapDoc {

	private String snapdocPath;
	private String snapDocFileName;
	private String snapDocTempPath;
	private static SnapDoc snapDoc;
	private int snapDocImageWidth;
	private int snapDocImageHeight;


	private SnapDoc()
	{

	}

	public static SnapDoc getInstance()
	{
		if(snapDoc==null){
			snapDoc =new SnapDoc();
		}

		return snapDoc;
	}

	public static void main(String args[])
	{
		SnapDoc snapDoc = SnapDoc.getInstance();
		snapDoc.loadProperties();
		SnapDocKeyListener snapDocKeyListener = new SnapDocKeyListener();
		snapDocKeyListener.processSnapDoc();
	}

	public void loadProperties()
	{
		Properties property = new Properties();
		try {
			property.load(new FileInputStream("snapdoc.ini"));
			snapdocPath=property.getProperty(SnapDocConstants.SNAPDOC_PATH).trim();
			snapDocTempPath=property.getProperty(SnapDocConstants.SNAPDOC_TEMP_PATH).trim();
			snapDocImageWidth=Integer.parseInt(property.getProperty(SnapDocConstants.SNAPDOC_IMAGE_WIDTH).trim());
			snapDocImageHeight=Integer.parseInt(property.getProperty(SnapDocConstants.SNAPDOC_IMAGE_HEIGHT).trim());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getSnapdocPath() {
		return snapdocPath;
	}

	public void setSnapdocPath(String snapdocPath) {
		this.snapdocPath = snapdocPath;
	}

	public String getSnapDocFileName() {
		return snapDocFileName;
	}

	public void setSnapDocFileName(String snapDocFileName) {
		this.snapDocFileName = snapDocFileName;
	}

	public String getSnapDocTempPath() {
		return snapDocTempPath;
	}

	public void setSnapDocTempPath(String snapDocTempPath) {
		this.snapDocTempPath = snapDocTempPath;
	}

	public int getSnapDocImageWidth() {
		return snapDocImageWidth;
	}

	public void setSnapDocImageWidth(int snapDocImageWidth) {
		this.snapDocImageWidth = snapDocImageWidth;
	}

	public int getSnapDocImageHeight() {
		return snapDocImageHeight;
	}

	public void setSnapDocImageHeight(int snapDocImageHeight) {
		this.snapDocImageHeight = snapDocImageHeight;
	}

}
