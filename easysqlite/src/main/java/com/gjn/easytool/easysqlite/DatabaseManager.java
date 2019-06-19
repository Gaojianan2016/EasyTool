package com.gjn.easytool.easysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gjn.easytool.logger.EasyLog;
import com.gjn.easytool.utils.ReflexUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2019/6/18 18:13
 */

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";
    private SQLiteOpenHelper mSqLiteOpenHelper;
    private Context context;
    private String dbName;
    private int newVersion = 1;

    public DatabaseManager(Context context, String dbName) {
        this(context, dbName, 1);
    }

    public DatabaseManager(Context context, String dbName, int newVersion) {
        this.context = context;
        this.dbName = dbName;
        this.newVersion = newVersion < 1 ? 1 : newVersion;
        if (mSqLiteOpenHelper == null) {
            init();
        }
    }

    private void init() {
        mSqLiteOpenHelper = new DatabaseHelper(context, dbName + ".db", newVersion);
        EasyLog.i(TAG, "init " + dbName + " oldVersion=" + getWritable().getVersion() + " , newVersion=" + newVersion);
    }

    public void updataDB(int newVersion){
        if (mSqLiteOpenHelper != null && getWritable().getVersion() < newVersion) {
            this.newVersion = newVersion < 1 ? 1 : newVersion;
            init();
        }
    }

    public SQLiteDatabase getWritable() {
        return mSqLiteOpenHelper.getWritableDatabase();
    }

    public SQLiteDatabase getReadable() {
        return mSqLiteOpenHelper.getReadableDatabase();
    }

    public void execSQL(String sql) {
        EasyLog.i(TAG, "execSQL\n" + sql);
        getWritable().execSQL(sql);
        colse();
    }

    public void colse() {
        getWritable().close();
        getReadable().close();
    }

    public void createTable(Class clz){
        createTable(clz.getSimpleName(), clz);
    }

    public void createTable(String sql){
        execSQL(sql);
    }

    public void createTable(String tableName, Class clz){
        execSQL(DatabaseUtils.createTableSql(tableName, clz));
    }

    public void dropTable(Class clz){
        dropTable(clz.getSimpleName());
    }

    public void dropTable(String tableName){
        execSQL(DatabaseUtils.dropTableSql(tableName));
    }

    public void renameTable(String tableName, String tableName2){
        execSQL(DatabaseUtils.renameTableSql(tableName, tableName2));
    }

    public void copyTable(Class clz1, Class clz2){
        copyTable(clz1.getSimpleName(), clz1, clz2);
    }

    //使用clz2新建一个表 并且将clz1的数据写入到新建的表内
    public void copyTable(String tableName, Class clz1, Class clz2){
        //删除旧_表
        dropTable("_" + tableName);
        //表重命名为_表
        renameTable(tableName, "_" + tableName);
        //由clz2创建新表
        createTable(tableName, clz2);
        //将clz2有clz1的字段导入新表
        execSQL(DatabaseUtils.copyTableInsertSql(tableName, "_"+tableName, clz1, clz2));
        //删除_表
        dropTable("_" + tableName);
    }

    public long insert(Object obj){
        return insert(obj.getClass().getSimpleName(), obj);
    }

    public long insert(String tableName, Object obj){
        long result = getWritable().insert(tableName, null, DatabaseUtils.getContentValues(obj));
        colse();
        return result;
    }

    public int delete(Object obj){
        int id = (int) ReflexUtils.doDeclaredMethod(obj, "get_ID");
        return delete(obj.getClass(), id);
    }

    public int delete(String tableName, Object obj){
        int id = (int) ReflexUtils.doDeclaredMethod(obj, "get_ID");
        return delete(tableName, id);
    }

    public int delete(Class clz, int id){
        String whereClause = "_ID=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return delete(clz.getSimpleName(), whereClause, whereArgs);
    }

    public int delete(String tableName, int id){
        String whereClause = "_ID=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return delete(tableName, whereClause, whereArgs);
    }

    public int delete(Class clz, String whereClause, String[] whereArgs){
        return delete(clz.getSimpleName(), whereClause, whereArgs);
    }

    public int delete(String tableName, String whereClause, String[] whereArgs){
        int result = getWritable().delete(tableName, whereClause, whereArgs);
        colse();
        return result;
    }

    public int updata(Object obj) {
        int id = (int) ReflexUtils.doDeclaredMethod(obj, "get_ID");
        String whereClause = "_ID=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return updata(obj, whereClause, whereArgs);
    }

    public int updata(String tableName, Object obj) {
        int id = (int) ReflexUtils.doDeclaredMethod(obj, "get_ID");
        String whereClause = "_ID=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return updata(tableName, obj, whereClause, whereArgs);
    }

    public int updata(Object obj, String whereClause, String[] whereArgs) {
        return updata(obj.getClass().getSimpleName(), DatabaseUtils.getContentValues(obj),
                whereClause, whereArgs);
    }

    public int updata(String tableName, Object obj, String whereClause, String[] whereArgs) {
        return updata(tableName, DatabaseUtils.getContentValues(obj),
                whereClause, whereArgs);
    }

    public int updata(String tableName, ContentValues values, String whereClause, String[] whereArgs){
        int result = getWritable().update(tableName, values, whereClause, whereArgs);
        colse();
        return result;
    }

    public <T> List<T> queryAll(String tableName, Class<T> clz) {
        return query(tableName, clz, null, null, null);
    }

    public <T> List<T> queryAll(Class<T> clz) {
        return query(clz.getSimpleName(), clz, null, null, null);
    }

    public Cursor queryAll(String tableName) {
        return query(tableName, null, null, null, null,
                null, null, null);
    }

    public <T> List<T> query(Class<T> clz, String selection, String[] selectionArgs) {
        return query(clz.getSimpleName(), clz, selection, selectionArgs);
    }

    public <T> List<T> query(Class<T> clz, String selection, String[] selectionArgs, String orderBy) {
        return query(clz.getSimpleName(), clz, selection, selectionArgs, orderBy);
    }

    public <T> List<T> query(String tableName, Class<T> clz, String selection,
                             String[] selectionArgs) {
        return query(tableName, clz, selection, selectionArgs, null);
    }

    public <T> List<T> query(String tableName, Class<T> clz, String selection,
                             String[] selectionArgs, String orderBy) {
        return query(tableName, clz, null, selection, selectionArgs, null,
                null, orderBy, null);
    }

    public <T> List<T> query(String tableName, Class<T> clz, String[] columns, String selection,
                             String[] selectionArgs, String groupBy, String having, String orderBy,
                             String limit){
        List<T> result = new ArrayList<>();
        Cursor cursor = query(tableName, columns, selection, selectionArgs, groupBy,
                having, orderBy, limit);
        try {
            while (cursor.moveToNext()) {
                result.add(DatabaseUtils.getNewInstance(clz, cursor));
            }
        }catch (Exception e){
            EasyLog.w(TAG, "query cursor error: " + e.getMessage());
        }finally {
            cursor.close();
        }
        colse();
        return result;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy, String limit) {
        return getWritable().query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }
}