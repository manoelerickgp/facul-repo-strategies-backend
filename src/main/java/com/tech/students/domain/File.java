package com.tech.students.domain;

public class File {

    private String fileName;
    private String linkDownload;
    private String FileExtension;

    private Long size;

    public File() {
    }

    public File(String fileName, String linkDownload, String fileExtension, Long size) {
        this.fileName = fileName;
        this.linkDownload = linkDownload;
        FileExtension = fileExtension;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String fileExtension) {
        FileExtension = fileExtension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", linkDownload='" + linkDownload + '\'' +
                ", FileExtension='" + FileExtension + '\'' +
                ", size=" + size +
                '}';
    }
}
