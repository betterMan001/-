package com.ly.a316.ly_meetingroommanagement.endActivity.util;

import java.io.File;
import java.io.FileFilter;

/**
 * 作者：余智强
 * 2019/2/15
 */
public class FileSelectFilter implements FileFilter {
    private String[] mTypes;
    public FileSelectFilter(String[] types) {
        this.mTypes = types;
    }
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (mTypes != null && mTypes.length > 0) {
            for (int i = 0; i < mTypes.length; i++) {
                if (file.getName().endsWith(mTypes[i].toLowerCase()) || file.getName().endsWith(mTypes[i].toUpperCase())) {
                    return true;
                }
            }
        }else {
            return true;
        }
        return false;
    }
}
