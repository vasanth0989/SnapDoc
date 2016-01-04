package com.snapdoc;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.snapdoc.constants.SnapDocConstants;

public class SnapDocImageProcessor {

	private XWPFDocument doc =null;
	private FileOutputStream out=null;
	private SnapDoc snapDoc= SnapDoc.getInstance();

	public  void savetoDoc(String imageFile,String fileToSave,boolean isNewFile,boolean appendFile) throws Exception {

		if(isNewFile)
		{
			doc = new XWPFDocument();	
		}  
		if(appendFile)
		{
			doc = new XWPFDocument(new FileInputStream(fileToSave+SnapDocConstants.SNAPDOC_FILE_EXTENSION));
		}
		
		XWPFParagraph p = doc.createParagraph();
		XWPFRun r = p.createRun();
		String imgFile=imageFile;
		int format=XWPFDocument.PICTURE_TYPE_JPEG;
		if(!isNewFile) r.addBreak();
		r.addPicture(new FileInputStream(imgFile), format, imgFile, Units.toEMU(snapDoc.getSnapDocImageWidth()), Units.toEMU(snapDoc.getSnapDocImageHeight())); // 200x200 pixels
		out = new FileOutputStream(fileToSave+SnapDocConstants.SNAPDOC_FILE_EXTENSION);
		doc.write(out);
		out.flush();
		out.close();
		System.out.println("Done!");
	}




}
