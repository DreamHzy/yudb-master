package com.ynzyq.yudbadmin.util.jiliang;

import javax.activation.MimetypesFileTypeMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileItem {

	private String fileName;
	private String mimeType;
	private byte[] content;

	public FileItem() {
	}

	public FileItem(String fileName, byte[] content) {
		this.fileName = fileName;
		this.content = content;
	}

	public FileItem(String fileName, byte[] content, String mimeType) {
		this.fileName = fileName;
		this.content = content;
		this.mimeType = mimeType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		if (mimeType == null) {
			mimeType = getContentType(fileName);
		}
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	private String getContentType(String fileName) {
		String defaultType = "application/octet-stream";
		if (fileName == null || fileName.isEmpty()) {
			return defaultType;
		}
		String contentType = null;
		try {
			Path path = Paths.get(fileName);
			contentType = Files.probeContentType(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (contentType == null || contentType.isEmpty()) {
			contentType = new MimetypesFileTypeMap().getContentType(fileName);
		}
		return contentType;
	}
}