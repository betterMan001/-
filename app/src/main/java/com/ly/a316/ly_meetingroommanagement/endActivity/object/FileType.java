package com.ly.a316.ly_meetingroommanagement.endActivity.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：余智强
 * 2019/2/14
 */
public class FileType implements Parcelable{
   private String title;
   private int iconStyle;//表示的图片
    public String[] fileType;

    public FileType(String title,  String[] fileType,int iconStyle) {
        this.title = title;
        this.iconStyle = iconStyle;
        this.fileType = fileType;
    }
    protected FileType(Parcel in){
        title = in.readString();
        iconStyle = in.readInt();
        fileType = in.createStringArray();
    }
    public static final Creator<FileType> CREATOR = new Creator<FileType>() {
        @Override
        public FileType createFromParcel(Parcel in) {
            return new FileType(in);
        }

        @Override
        public FileType[] newArray(int size) {
            return new FileType[size];
        }
    };
    public String getTitle() {
        return title;
    }

    public int getIconStyle() {
        return iconStyle;
    }

    public String[] getFileType() {
        return fileType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(iconStyle);
        dest.writeStringArray(fileType);
    }
}
