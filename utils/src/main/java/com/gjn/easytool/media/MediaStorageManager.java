package com.gjn.easytool.media;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.gjn.easytool.utils.ReflexUtils;
import com.gjn.easytool.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaStorageManager {
    private static final String TAG = "MediaStorageManager";

    private static final Uri PHOTO_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final Uri VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    private static final String SORT_ORDER_STR = "_id DESC LIMIT 1";
    private static final String MEDIA_EXTERNAL = "content://media/external";
    private static final int NOTIFY_SIZE = 10;

    private Context mContext;
    private MediaScannerConnection mConnection;
    private SortMergedTask task;
    private HashMap<String, ArrayList<MediaInfo>> mediaMaps;
    private ArrayList<MediaInfo> medias;
    private ArrayList<MediaInfo> dirs;
    private ArrayList<String> dirStrs;
    private String photoSortOrder;
    private String videoSortOrder;
    private String allKey;
    private Class other;

    private boolean isFindVideo;
    private boolean isFindPhoto;

    private boolean isNotify;

    private OnMediaChangeListener onMediaChangeListener;
    private OnMediaNotifyListener onMediaNotifyListener;
    private OnFilterListener onFilterListener;

    public MediaStorageManager(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        task = new SortMergedTask();
        isFindVideo = true;
        isFindPhoto = true;
        mediaMaps = new HashMap<>();
        medias = new ArrayList<>();
        dirs = new ArrayList<>();
        dirStrs = new ArrayList<>();
        photoSortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
        videoSortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";
        allKey = "all";
    }

    private Cursor sqlQuery(Uri uri, String sortOrder) {
        return mContext.getContentResolver().query(uri, null,
                null, null, sortOrder);
    }

    private void addToMap(MediaInfo info) {
        String key = info.getParent();
        if (!dirStrs.contains(key)) {
            dirStrs.add(key);
            dirs.add(info);
        }
        ArrayList<MediaInfo> infos = new ArrayList<>();
        if (mediaMaps.containsKey(key)) {
            infos = mediaMaps.get(key);
        }
        infos.add(info);
        mediaMaps.put(key, infos);
    }

    public void startFindMedia() {
        if (!isFindPhoto && !isFindVideo) {
            Log.w(TAG, "No search set (isFindPhoto or isFindVideo).");
            return;
        }
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void cancelFindMedia() {
        if (task != null) {
            task.cancel(false);
        }
    }

    public void scanFile() {
        scanFile(new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Intent mediaScanIntent;
                mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(uri);
                mContext.sendBroadcast(mediaScanIntent);
            }
        });
    }

    public void scanFile(MediaScannerConnection.OnScanCompletedListener callback) {
        scanFile(new String[]{Environment.getExternalStorageDirectory().toString()}, null, callback);
    }

    public void scanFile(String[] paths, String[] mimeTypes, MediaScannerConnection.OnScanCompletedListener callback) {
        ClientProxy client = new ClientProxy(paths, mimeTypes, callback);
        mConnection = new MediaScannerConnection(mContext, client);
        client.mConnection = mConnection;
        mConnection.connect();
    }

    public void disconnect() {
        if (mConnection != null) {
            mConnection.disconnect();
        }
    }

    public void start() {
        startFindMedia();
        scanFile();
        registerMediaObserver();
    }

    public void destroy() {
        cancelFindMedia();
        disconnect();
    }

    public void registerVideoObserver() {
        MediaObserver videoObserver = new MediaObserver(VIDEO_URI, true);
        mContext.getContentResolver().registerContentObserver(VIDEO_URI,
                true, videoObserver);
    }

    public void registerPhotoObserver() {
        MediaObserver photoObserver = new MediaObserver(PHOTO_URI, false);
        mContext.getContentResolver().registerContentObserver(PHOTO_URI,
                true, photoObserver);
    }

    public void registerMediaObserver() {
        registerVideoObserver();
        registerPhotoObserver();
    }

    public void setOnMediaChangeListener(OnMediaChangeListener onMediaChangeListener) {
        this.onMediaChangeListener = onMediaChangeListener;
    }

    public void setOnMediaNotifyListener(OnMediaNotifyListener onMediaNotifyListener) {
        this.onMediaNotifyListener = onMediaNotifyListener;
    }

    public MediaStorageManager setFindVideo(boolean is) {
        isFindVideo = is;
        return this;
    }

    public MediaStorageManager setFindPhoto(boolean is) {
        isFindPhoto = is;
        return this;
    }

    public MediaStorageManager setAllName(String name) {
        allKey = name;
        return this;
    }

    public String getAllName() {
        return allKey;
    }

    public MediaStorageManager setVideoSortOrder(String sort) {
        videoSortOrder = sort;
        return this;
    }

    public MediaStorageManager setPhotoSortOrder(String sort) {
        photoSortOrder = sort;
        return this;
    }

    public MediaStorageManager setOtherInfo(Class clz) {
        other = clz;
        return this;
    }

    public HashMap<String, ArrayList<MediaInfo>> getMediaMaps() {
        return mediaMaps;
    }

    public ArrayList<MediaInfo> getMedias() {
        return medias;
    }

    public ArrayList<MediaInfo> getDirMedias(String dir) {
        if (dir.equals(allKey)) {
            return medias;
        }
        return mediaMaps.get(dir);
    }

    public ArrayList<MediaInfo> getDirs() {
        return dirs;
    }

    static class ClientProxy implements MediaScannerConnection.MediaScannerConnectionClient {
        final String[] mPaths;
        final String[] mMimeTypes;
        final MediaScannerConnection.OnScanCompletedListener mClient;
        MediaScannerConnection mConnection;
        int mNextPath;

        ClientProxy(String[] paths, String[] mimeTypes, MediaScannerConnection.OnScanCompletedListener client) {
            mPaths = paths;
            mMimeTypes = mimeTypes;
            mClient = client;
        }

        public void onMediaScannerConnected() {
            scanNextPath();
        }

        public void onScanCompleted(String path, Uri uri) {
            if (mClient != null) {
                mClient.onScanCompleted(path, uri);
            }
            scanNextPath();
        }

        void scanNextPath() {
            if (mNextPath >= mPaths.length) {
                mConnection.disconnect();
                mConnection = null;
                return;
            }
            String mimeType = mMimeTypes != null ? mMimeTypes[mNextPath] : null;
            mConnection.scanFile(mPaths[mNextPath], mimeType);
            mNextPath++;
        }

    }

    @SuppressLint("StaticFieldLeak")
    class SortMergedTask extends AsyncTask<Void, ArrayList<MediaInfo>, Void> {

        @Override
        protected void onPreExecute() {
            if (onMediaNotifyListener != null) {
                onMediaNotifyListener.onPreLoad(allKey);
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            for (String key : mediaMaps.keySet()) {
                int size = mediaMaps.get(key).size();
                for (MediaInfo dir : dirs) {
                    if (dir.getParent().equals(key)) {
                        dir.setDirSize(size);
                    }
                }
            }
            //添加全部列表的数据
            MediaInfo info = new MediaInfo();
            info.setParent(allKey);
            info.setDirSize(medias.size());
            info.setPath(medias.get(0).getPath());
            info.setVideo(false);
            dirs.add(0, info);
            if (onMediaNotifyListener != null) {
                onMediaNotifyListener.onCompletion(getDirs());
            }
        }

        @Override
        protected void onProgressUpdate(ArrayList<MediaInfo>... values) {
            if (values[0] != null) {
                medias.addAll(values[0]);
            }
            if (onMediaNotifyListener != null) {
                onMediaNotifyListener.onUpdataFile(medias);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MediaInfo videoInfo;
            MediaInfo photoInfo;
            ArrayList<MediaInfo> cachedList = new ArrayList<>();
            Cursor cursor;
            if (isFindVideo) {
                cursor = sqlQuery(VIDEO_URI, videoSortOrder);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        videoInfo = generateVideoInfo(cursor, mContext.getContentResolver());
                        if (videoInfo != null && videoInfo.getParent() != null) {
                            addToMap(videoInfo);
                            cachedList.add(videoInfo);
                            if (cachedList.size() >= NOTIFY_SIZE) {
                                isNotify = true;
                            }
                        }
                        if (isNotify) {
                            isNotify = false;
                            publishProgress(cachedList);
                            cachedList = new ArrayList<>();
                        }
                    }
                    cursor.close();
                }
            }
            if (isFindPhoto) {
                cursor = sqlQuery(PHOTO_URI, photoSortOrder);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        photoInfo = generatePhotoInfo(cursor);
                        if (photoInfo != null && photoInfo.getParent() != null) {
                            addToMap(photoInfo);
                            cachedList.add(photoInfo);
                            if (cachedList.size() >= NOTIFY_SIZE) {
                                isNotify = true;
                            }
                        }
                        if (isNotify) {
                            isNotify = false;
                            publishProgress(cachedList);
                            cachedList = new ArrayList<>();
                        }
                    }
                    cursor.close();
                }
            }
            publishProgress(cachedList);
            return null;
        }
    }

    class MediaObserver extends ContentObserver {

        Uri mMediaUri;
        boolean isVideo;

        MediaObserver(Uri uri, boolean isVideo) {
            super(null);
            mMediaUri = uri;
            this.isVideo = isVideo;
        }

        @Override
        public void onChange(boolean selfChange) {
        }

        @Override
        public void onChange(final boolean selfChange, final Uri uri) {
            super.onChange(selfChange, uri);
            if (isVideo) {
                Log.i(TAG, "onChange Video uri -> " + uri);
            } else {
                Log.i(TAG, "onChange Photo uri -> " + uri);
            }
            final boolean isMediaExternal = uri.toString().equals(MEDIA_EXTERNAL);
            if (onMediaChangeListener != null) {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onMediaChangeListener.onChange(selfChange, uri, isMediaExternal);
                    }
                });
            }
            Cursor cursor = sqlQuery(mMediaUri, SORT_ORDER_STR);
            final MediaInfo info;
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    if (isVideo) {
                        info = generateVideoInfo(cursor, mContext.getContentResolver());
                    } else {
                        info = generatePhotoInfo(cursor);
                    }
                    if (onMediaChangeListener != null && info != null) {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onMediaChangeListener.onChangeCursor(info, allKey);
                            }
                        });
                    }
                }
                cursor.close();
            }
        }

    }

    public static void scanFile(Context context, String[] paths, String[] mimeTypes,
                                MediaScannerConnection.OnScanCompletedListener callback) {
        ClientProxy client = new ClientProxy(paths, mimeTypes, callback);
        MediaScannerConnection connection = new MediaScannerConnection(context, client);
        client.mConnection = connection;
        connection.connect();
    }

    private MediaInfo generateVideoInfo(Cursor cursor, ContentResolver resolver) {
        MediaInfo info;
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        String parent = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
        int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
        int time = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
        int width = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH));
        int height = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT));
        String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));

        int rotation;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            rotation = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));
        }catch (Exception e){
            rotation = 0;
        }

        String name;
        String parentPath;
        try {
            name = Uri.parse(path).getLastPathSegment();
            if (name == null) {
                name = path.substring(path.lastIndexOf("/") + 1);
            }
            parentPath = path.replace(name, "");
        } catch (Exception e) {
            return null;
        }

        if (onFilterListener != null) {
            if (!onFilterListener.onFilterVideo(name)) {
                return null;
            }
        }

        Cursor thumbCursor = resolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID},
                MediaStore.Video.Thumbnails.VIDEO_ID + "=?",
                new String[]{String.valueOf(id)}, null);
        String thumbnailPath = null;
        if (thumbCursor != null) {
            if (thumbCursor.moveToFirst()) {
                thumbnailPath = thumbCursor.getString(thumbCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
            }
            thumbCursor.close();
        }
        info = new MediaInfo(path, name, parent, parentPath, size);
        info.setThumbnailPath(thumbnailPath);
//        info.setThumbnail(getVideoThumbnail(path));
        info.setTime(time);
        info.setMimeType(mimeType);
        info.setVideo(true);
        info.setWidth(width);
        info.setHeight(height);
        info.setResolution(resolution);
        info.setRotation(rotation);
        if (other != null) {
            info.setOtherObj(ReflexUtils.createObj(other));
        }
        return info;
    }

    private MediaInfo generatePhotoInfo(Cursor cursor) {
        MediaInfo info;
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        String parent = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
        int width = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH));
        int height = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT));
        int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
        int orientation = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION));
        String name;
        String parentPath;
        try {
            name = Uri.parse(path).getLastPathSegment();
            if (name == null) {
                name = path.substring(path.lastIndexOf("/") + 1);
            }
            parentPath = path.replace(name, "");
        } catch (Exception e) {
            return null;
        }

        if (onFilterListener != null) {
            if (!onFilterListener.onFilterPhoto(name)) {
                return null;
            }
        }

        info = new MediaInfo(path, name, parent, parentPath, size);
        info.setMimeType(mimeType);
        info.setWidth(width);
        info.setHeight(height);
        info.setOrientation(orientation);
        if (other != null) {
            info.setOtherObj(ReflexUtils.createObj(other));
        }
        return info;
    }

    public static Bitmap getVideoThumbnail(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        return retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
//        return ThumbnailUtils.createVideoThumbnail(path, 0);
    }

    public static String parseTime(int seconds) {
        String time = "未知";
        if (seconds < 0) {
            return time;
        }
        int min = seconds / 60;
        int sec = seconds % 60;
        time = String.format("%02d:%02d", min, sec);
        return time;
    }

    public interface OnMediaChangeListener {
        void onChange(boolean selfChange, Uri uri, boolean isMediaExternal);

        void onChangeCursor(MediaInfo info, String allName);
    }

    public interface OnMediaNotifyListener {
        void onPreLoad(String allName);

        void onUpdataFile(ArrayList<MediaInfo> infos);

        void onCompletion(ArrayList<MediaInfo> dirs);
    }

    public interface OnFilterListener{
        boolean onFilterVideo(String fileName);

        boolean onFilterPhoto(String fileName);
    }
}
