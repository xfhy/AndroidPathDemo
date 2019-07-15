package com.xfhy.processdemo.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.xfhy.processdemo.contentprovider.db.DbOpenHelper;

/**
 * Created by xfhy on 2019/4/14 16:53
 * Description :
 * <p>
 * 我们借助SQLiteOpenHelper来管理数据库的
 * 创建、升级和降级。下面我们就要通过BookProvider向外界提供上述数据库中的信息了。
 * 我们知道，ContentProvider通过Uri来区分外界要访问的的数据集合，在本例中支持外界对
 * BookProvider中的book表和user表进行访问，为了知道外界要访问的是哪个表，我们需要
 * 为它们定义单独的Uri和Uri_Code，并将Uri和对应的Uri_Code相关联，我们可以使用
 * UriMatcher的addURI方法将Uri和Uri_Code关联到一起。这样，当外界请求访问
 * BookProvider时，我们就可以根据请求的Uri来得到Uri_Code，有了Uri_Code我们就可以知
 * 道外界想要访问哪个表，然后就可以进行相应的数据操作了
 */
public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.xfhy.processdemo.contentprovider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String TAG = "BookProvider";

    static {
        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private DbOpenHelper mDbOpenHelper;
    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        //只有onCreate()方法在主线程,其他那几个方法都在Binder线程池中运行的
        Log.e(TAG, "onCreate,current thread:" + Thread.currentThread().getName());
        mContext = getContext();
        //初始化数据库 这里只是demo 其实这里不推荐在主线程朝操作数据库
        initProviderData();
        return false;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4,'Ios');");
        mDb.execSQL("insert into book values(5,'Html5');");
        mDb.execSQL("insert into user values(1,'jake',1);");
        mDb.execSQL("insert into user values(2,'jasmine',0);");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query,current thread:" + Thread.currentThread().
                getName());

        String table = checkParameter(uri);
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(TAG, "insert");

        String table = checkParameter(uri);
        mDb.insert(table, null, contentValues);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete");

        String table = checkParameter(uri);
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "update");

        String table = checkParameter(uri);
        int row = mDb.update(table, contentValues, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return 0;
    }

    private String checkParameter(Uri uri) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return table;
    }

    /**
     * 通过uri来获知需要搞哪个表
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (URI_MATCHER.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }

}
