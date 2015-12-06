package com.snapdoc;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.snapdoc.constants.SnapDocConstants;

public class SnapDocKeyListener implements NativeKeyListener {
	private int keyCount = 0;
	private StringBuffer snapDocBuffer = new StringBuffer();
	private String fileName = null;
	private SnapDoc snapDoc = SnapDoc.getInstance();
	private SnapDocImageProcessor snapDocImageProcessor = new SnapDocImageProcessor();
	private boolean isNewFile;
	private BufferedReader bufferedReader = null;

	public void nativeKeyPressed(NativeKeyEvent e) {

		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void nativeKeyReleased(NativeKeyEvent e) {

		if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN && fileName != null) {
			Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String format = SnapDocConstants.SNAPDOC_IMAGE_FORMAT;
			String imgFileName = snapDoc.getSnapDocTempPath() + "/snapDoc."
					+ SnapDocConstants.SNAPDOC_IMAGE_FORMAT;

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit()
					.getScreenSize());
			BufferedImage screenFullImage = robot
					.createScreenCapture(screenRect);
			try {
				ImageIO.write(screenFullImage, format, new File(imgFileName));
				try {

					snapDocImageProcessor.savetoDoc(imgFileName,
							snapDoc.getSnapdocPath() + "/" + fileName,
							isNewFile);
					isNewFile = false;

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L
				|| e.getKeyCode() == NativeKeyEvent.VC_V
				|| e.getKeyCode() == NativeKeyEvent.VC_SHIFT_L) {
			keyCount++;
			snapDocBuffer.append(NativeKeyEvent.getKeyText(e.getKeyCode()));
		} else {
			keyCount = 0;
		}

		if (keyCount == 3) {
			String snapDocBufferStr = snapDocBuffer.toString();
			snapDocBufferStr = snapDocBufferStr.replace(" ", "");
			if (Arrays.asList(SnapDocConstants.SNAPDOC_KEY_EVENTS).contains(
					snapDocBufferStr.toString())) {
				try {
					System.out
							.println("Please Enter the File Name you would like to create:");
					bufferedReader = new BufferedReader(new InputStreamReader(
							System.in));
					fileName = bufferedReader.readLine();
					isNewFile = true;
					System.out
							.println("That's it hit the prtscr button to capture screenshot!");
				} catch (IOException ie) {
					ie.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			snapDocBuffer.delete(0, snapDocBuffer.length());
			keyCount = 0;
		}

	}

	public void nativeKeyTyped(NativeKeyEvent e) {

		// Do nothing
	}

	public void processSnapDoc() {
		try {
			LogManager.getLogManager().reset();
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage()
					.getName());
			logger.setLevel(Level.OFF);
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err
					.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		try {
			GlobalScreen.addNativeKeyListener(new SnapDocKeyListener());
		} finally {
			try {
				if (null != bufferedReader)
					bufferedReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
