package com.potatospy.mcremote;

import java.net.URL;
import java.time.LocalDateTime;

public class RemoteFile {


    private String fileName;
    private String fileloc;
    private LocalDateTime modifiedDateTime;
    private String content;
    private boolean isDirectory;


    public RemoteFile(String fileName, String fileloc, LocalDateTime modifiedDateTime, String content, boolean isDirectory) {
        this.fileName = fileName;
        this.fileloc = fileloc;
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

    public String getFileloc() {
        return fileloc;
    }

    public void setFileloc(String fileloc) {
        this.fileloc = fileloc;
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
