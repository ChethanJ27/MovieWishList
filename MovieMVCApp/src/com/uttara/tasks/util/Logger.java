package com.uttara.tasks.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
	private static Logger instance = null;

	public void log(String data) {
		new Thread(new Runnable() {
			public void run() {
				BufferedWriter bw = null;
				try {
					bw = new BufferedWriter(new FileWriter(Constants.LOGPATH, true));
					Date dt = new Date();
					bw.write(dt.toString() + ":" + data);
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (bw != null) {
						try {
							bw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	private Logger() {
		if (instance != null)
			throw new NullPointerException("reflection not allowed");
	}

	public static Logger getInstance() {
		if (instance == null) {
			synchronized (Logger.class) {
				if (instance == null) {
					instance = new Logger();
				}
			}
		}
		return instance;
	}
}
