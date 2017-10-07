package com.egps.ict.assetbrowser;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by HP 15-P038 on Feb 19 17.
 */

public class DBAdapter extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "assetsdb.db";
    private static final int DATABASE_VERSION = 1;
    public static final String ID = "assetId", SHORT_DESC = "assetShortDesc", LONG_DESC = "assetLongDesc", PARENT_ID = "parentId";
    public static final String LOCATION = "assetLocn", CODE = "assetCode", GROUP_CODE = "assetGrpCode", SEQ_ID = "seqId", WORK_AREA = "workArea";
    private Cursor c;
    final private SQLiteDatabase db;

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getReadableDatabase();
        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

    }

    public boolean hasChildren(String id) {
        return getAssets(id, false, ID).getCount() > 0;
    }

    public Cursor getAsset(String id) {
        if (c != null && !c.isClosed()) {
            c.close();
        }
        String sql = "SELECT * FROM assets WHERE assetId = '" + id + "'";
        c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getAssets(String parentId, boolean allColumns, String... columns) {
        if (parentId == null) {
            parentId = "EGPS PLANT";
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }

        String sql;

        sql = "SELECT ";
        if (allColumns) {
            if (columns.length == 1) {
                sql += columns[0] + "(*) ";
            } else {
                sql += "* ";
            }
        } else {
            for (int i = 0; i < columns.length; i++) {
                sql += columns[i] + ", ";
            }
            sql = sql.substring(0, sql.lastIndexOf(',')) + ' ';
        }
        sql += "FROM assets WHERE parentId = '" + parentId + "' ORDER BY " + SHORT_DESC + ", " + ID;

        return db.rawQuery(sql, null);
    }


    public Cursor search(CharSequence s) {
        String sql = "SELECT assetId, assetShortDesc FROM assets WHERE UPPER(assetShortDesc) LIKE '%" + s +"%' OR UPPER(assetId) LIKE '%" + s + "%' ORDER BY assetId";
        return db.rawQuery(sql, null);
    }
}