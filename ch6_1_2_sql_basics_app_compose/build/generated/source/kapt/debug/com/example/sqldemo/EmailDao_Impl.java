package com.example.sqldemo;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EmailDao_Impl implements EmailDao {
  private final RoomDatabase __db;

  public EmailDao_Impl(RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public Object getAll(final Continuation<? super List<Email>> continuation) {
    final String _sql = "SELECT * FROM email";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Email>>() {
      @Override
      public List<Email> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfFolder = CursorUtil.getColumnIndexOrThrow(_cursor, "folder");
          final int _cursorIndexOfStarred = CursorUtil.getColumnIndexOrThrow(_cursor, "starred");
          final int _cursorIndexOfRead = CursorUtil.getColumnIndexOrThrow(_cursor, "read");
          final int _cursorIndexOfReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "received");
          final List<Email> _result = new ArrayList<Email>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Email _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpSubject;
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _tmpSubject = null;
            } else {
              _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            }
            final String _tmpSender;
            if (_cursor.isNull(_cursorIndexOfSender)) {
              _tmpSender = null;
            } else {
              _tmpSender = _cursor.getString(_cursorIndexOfSender);
            }
            final String _tmpFolder;
            if (_cursor.isNull(_cursorIndexOfFolder)) {
              _tmpFolder = null;
            } else {
              _tmpFolder = _cursor.getString(_cursorIndexOfFolder);
            }
            final boolean _tmpStarred;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfStarred);
            _tmpStarred = _tmp != 0;
            final boolean _tmpRead;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfRead);
            _tmpRead = _tmp_1 != 0;
            final int _tmpReceived;
            _tmpReceived = _cursor.getInt(_cursorIndexOfReceived);
            _item = new Email(_tmpId,_tmpSubject,_tmpSender,_tmpFolder,_tmpStarred,_tmpRead,_tmpReceived);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
