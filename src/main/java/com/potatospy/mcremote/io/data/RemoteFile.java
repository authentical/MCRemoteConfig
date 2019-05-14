package com.potatospy.mcremote.io.data;

import java.time.LocalDateTime;


public class RemoteFile {


    private String fileName;
    private String filePath;
    private LocalDateTime modifiedDateTime;
    private String content;
    private boolean isDirectory;


    public RemoteFile(String fileName, String filePath, LocalDateTime modifiedDateTime, String content, boolean isDirectory) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.modifiedDateTime = modifiedDateTime;
        this.content = content;
        this.isDirectory = isDirectory;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
