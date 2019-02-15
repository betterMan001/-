package com.ly.a316.ly_meetingroommanagement.endActivity.util;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.ly.a316.ly_meetingroommanagement.endActivity.object.FileEntity;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.FileType;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.Lingshi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static com.ly.a316.ly_meetingroommanagement.endActivity.util.FileUtils.getFileType;

/**
 * 作者：余智强
 * 2019/2/14
 */
public class FileScannerTask extends AsyncTask<Void,Void,List<FileEntity>> {
    public interface FileScannerListener {
        void scannerResult(List<FileEntity> entities);
    }

    private FileScannerListener mFileScannerListener;
    private Context context;
    public FileScannerTask(Context context, FileScannerListener fileScannerListener) {
        this.context = context;
        mFileScannerListener = fileScannerListener;
    }
    private final String[] DOC_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Files.FileColumns.TITLE
    };

    @Override
    protected List<FileEntity> doInBackground(Void... voids) {
        List<FileEntity> fileEntities = new ArrayList<>();
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";
        String []selectionArgs = new String[]{
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("text"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("dotx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("dotx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("potx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppsx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("xltx"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("png"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("svg"),
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("gif")
        };
        final Cursor cursor = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),//数据源
                DOC_PROJECTION,//查询类型
                selection,//查询条件
                selectionArgs,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
        if (cursor != null) {
            fileEntities = getFiles(cursor);
            cursor.close();
        }
        return fileEntities;
    }

    //更新ui界面的时候在这里进行
    // 当doInBackground方法完成后,系统将自动调用此方法,并将doInBackground方法返回的值传入此方法.通过此方法进行UI的更新.
    @Override
    protected void onPostExecute(List<FileEntity> entities) {
        super.onPostExecute(entities);
        if (mFileScannerListener != null) {
            Lingshi.fileEntities.clear();
            Lingshi.fileEntities.addAll(entities);

         //   mFileScannerListener.scannerResult(entities);
            mFileScannerListener.scannerResult(Lingshi.fileEntities);
        }
    }


    private List<FileEntity> getFiles(Cursor cursor) {
        List<FileEntity> fileEntities = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(DATA));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
            if (path != null) {
                FileType fileType = getFileType(PickerManager.getInstance().getFileTypes(), path);
                if (fileType != null && !(new File(path).isDirectory())) {
                    FileEntity entity = new FileEntity(id, title, path);
                    entity.setFileType(fileType);

                    String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE));
                    if (mimeType != null && !TextUtils.isEmpty(mimeType))
                        entity.setMimeType(mimeType);
                    else {
                        entity.setMimeType("");
                    }

                    entity.setSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)));
                    if(PickerManager.getInstance().files.contains(entity)){
                        entity.setSelected(true);
                    }
                    if (!fileEntities.contains(entity))
                        fileEntities.add(entity);
                }
            }
        }
        return fileEntities;
    }
}
