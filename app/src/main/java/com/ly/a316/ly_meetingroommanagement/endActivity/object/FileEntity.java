package com.ly.a316.ly_meetingroommanagement.endActivity.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * 作者：余智强
 * 2019/2/14
 * 这个类保存的是文件信息
 */
public class FileEntity implements Parcelable {
    private int id;
    private String fileName;
    private String filePath;
    private String mimeType;//文件的MIME类型
    private String size;
    private String date;
    private FileType fileType;
    private boolean isSelected;
    /*File 用于保存全部文件*/
    private File mFile;

    public FileEntity(int id, String name, String path) {
        this.id = id;
        this.fileName = name;
        this.filePath = path;
    }

    public FileEntity(String path, File file, boolean isSelected) {
        this.filePath = path;
        mFile = file;
        this.isSelected = isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileEntity)) return false;

        FileEntity entity = (FileEntity) o;

        return filePath.equals(entity.filePath);
    }

    protected FileEntity(Parcel in) {
        id = in.readInt();
        fileName = in.readString();
        filePath = in.readString();
        mimeType = in.readString();
        size = in.readString();
        date = in.readString();
    }

    public static final Creator<FileEntity> CREATOR = new Creator<FileEntity>() {
        @Override
        public FileEntity createFromParcel(Parcel in) {
            return new FileEntity(in);
        }

        @Override
        public FileEntity[] newArray(int size) {
            return new FileEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeString(mimeType);
        dest.writeString(size);
        dest.writeString(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public File getmFile() {
        return mFile;
    }

    public void setmFile(File mFile) {
        this.mFile = mFile;
    }
}
