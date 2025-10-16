package com.fitmaster.app.data.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.fitmaster.app.data.converter.Converters;
import com.fitmaster.app.data.entity.BodyMeasurement;
import com.fitmaster.app.data.entity.UserProfile;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfile> __insertionAdapterOfUserProfile;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<BodyMeasurement> __insertionAdapterOfBodyMeasurement;

  private final EntityDeletionOrUpdateAdapter<BodyMeasurement> __deletionAdapterOfBodyMeasurement;

  private final EntityDeletionOrUpdateAdapter<UserProfile> __updateAdapterOfUserProfile;

  private final EntityDeletionOrUpdateAdapter<BodyMeasurement> __updateAdapterOfBodyMeasurement;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfile = new EntityInsertionAdapter<UserProfile>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `user_profile` (`id`,`name`,`username`,`age`,`sex`,`goal`,`targetWeight`,`targetBodyFatPercentage`,`profileImageUrl`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserProfile value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUsername());
        }
        stmt.bindLong(4, value.getAge());
        if (value.getSex() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSex());
        }
        if (value.getGoal() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getGoal());
        }
        if (value.getTargetWeight() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindDouble(7, value.getTargetWeight());
        }
        if (value.getTargetBodyFatPercentage() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindDouble(8, value.getTargetBodyFatPercentage());
        }
        if (value.getProfileImageUrl() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getProfileImageUrl());
        }
        final Long _tmp = __converters.dateToTimestamp(value.getCreatedAt());
        if (_tmp == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(value.getUpdatedAt());
        if (_tmp_1 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_1);
        }
      }
    };
    this.__insertionAdapterOfBodyMeasurement = new EntityInsertionAdapter<BodyMeasurement>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `body_measurements` (`id`,`date`,`weight`,`bodyFatPercentage`,`leanMass`,`fatMass`,`height`,`chest`,`waist`,`biceps`,`thighs`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BodyMeasurement value) {
        stmt.bindLong(1, value.getId());
        final Long _tmp = __converters.dateToTimestamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        stmt.bindDouble(3, value.getWeight());
        if (value.getBodyFatPercentage() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.getBodyFatPercentage());
        }
        if (value.getLeanMass() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindDouble(5, value.getLeanMass());
        }
        if (value.getFatMass() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindDouble(6, value.getFatMass());
        }
        if (value.getHeight() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindDouble(7, value.getHeight());
        }
        if (value.getChest() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindDouble(8, value.getChest());
        }
        if (value.getWaist() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindDouble(9, value.getWaist());
        }
        if (value.getBiceps() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindDouble(10, value.getBiceps());
        }
        if (value.getThighs() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindDouble(11, value.getThighs());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotes());
        }
      }
    };
    this.__deletionAdapterOfBodyMeasurement = new EntityDeletionOrUpdateAdapter<BodyMeasurement>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `body_measurements` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BodyMeasurement value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUserProfile = new EntityDeletionOrUpdateAdapter<UserProfile>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `user_profile` SET `id` = ?,`name` = ?,`username` = ?,`age` = ?,`sex` = ?,`goal` = ?,`targetWeight` = ?,`targetBodyFatPercentage` = ?,`profileImageUrl` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserProfile value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUsername());
        }
        stmt.bindLong(4, value.getAge());
        if (value.getSex() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSex());
        }
        if (value.getGoal() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getGoal());
        }
        if (value.getTargetWeight() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindDouble(7, value.getTargetWeight());
        }
        if (value.getTargetBodyFatPercentage() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindDouble(8, value.getTargetBodyFatPercentage());
        }
        if (value.getProfileImageUrl() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getProfileImageUrl());
        }
        final Long _tmp = __converters.dateToTimestamp(value.getCreatedAt());
        if (_tmp == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(value.getUpdatedAt());
        if (_tmp_1 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, _tmp_1);
        }
        stmt.bindLong(12, value.getId());
      }
    };
    this.__updateAdapterOfBodyMeasurement = new EntityDeletionOrUpdateAdapter<BodyMeasurement>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `body_measurements` SET `id` = ?,`date` = ?,`weight` = ?,`bodyFatPercentage` = ?,`leanMass` = ?,`fatMass` = ?,`height` = ?,`chest` = ?,`waist` = ?,`biceps` = ?,`thighs` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BodyMeasurement value) {
        stmt.bindLong(1, value.getId());
        final Long _tmp = __converters.dateToTimestamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        stmt.bindDouble(3, value.getWeight());
        if (value.getBodyFatPercentage() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.getBodyFatPercentage());
        }
        if (value.getLeanMass() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindDouble(5, value.getLeanMass());
        }
        if (value.getFatMass() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindDouble(6, value.getFatMass());
        }
        if (value.getHeight() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindDouble(7, value.getHeight());
        }
        if (value.getChest() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindDouble(8, value.getChest());
        }
        if (value.getWaist() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindDouble(9, value.getWaist());
        }
        if (value.getBiceps() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindDouble(10, value.getBiceps());
        }
        if (value.getThighs() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindDouble(11, value.getThighs());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getNotes());
        }
        stmt.bindLong(13, value.getId());
      }
    };
  }

  @Override
  public Object insertUserProfile(final UserProfile profile,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProfile.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertMeasurement(final BodyMeasurement measurement,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfBodyMeasurement.insertAndReturnId(measurement);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteMeasurement(final BodyMeasurement measurement,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBodyMeasurement.handle(measurement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateUserProfile(final UserProfile profile,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserProfile.handle(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateMeasurement(final BodyMeasurement measurement,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBodyMeasurement.handle(measurement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<UserProfile> getUserProfile() {
    final String _sql = "SELECT * FROM user_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"user_profile"}, new Callable<UserProfile>() {
      @Override
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
          final int _cursorIndexOfGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "goal");
          final int _cursorIndexOfTargetWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "targetWeight");
          final int _cursorIndexOfTargetBodyFatPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "targetBodyFatPercentage");
          final int _cursorIndexOfProfileImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final UserProfile _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpSex;
            if (_cursor.isNull(_cursorIndexOfSex)) {
              _tmpSex = null;
            } else {
              _tmpSex = _cursor.getString(_cursorIndexOfSex);
            }
            final String _tmpGoal;
            if (_cursor.isNull(_cursorIndexOfGoal)) {
              _tmpGoal = null;
            } else {
              _tmpGoal = _cursor.getString(_cursorIndexOfGoal);
            }
            final Float _tmpTargetWeight;
            if (_cursor.isNull(_cursorIndexOfTargetWeight)) {
              _tmpTargetWeight = null;
            } else {
              _tmpTargetWeight = _cursor.getFloat(_cursorIndexOfTargetWeight);
            }
            final Float _tmpTargetBodyFatPercentage;
            if (_cursor.isNull(_cursorIndexOfTargetBodyFatPercentage)) {
              _tmpTargetBodyFatPercentage = null;
            } else {
              _tmpTargetBodyFatPercentage = _cursor.getFloat(_cursorIndexOfTargetBodyFatPercentage);
            }
            final String _tmpProfileImageUrl;
            if (_cursor.isNull(_cursorIndexOfProfileImageUrl)) {
              _tmpProfileImageUrl = null;
            } else {
              _tmpProfileImageUrl = _cursor.getString(_cursorIndexOfProfileImageUrl);
            }
            final Date _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpCreatedAt = _tmp_1;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if(_tmp_3 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpUpdatedAt = _tmp_3;
            }
            _result = new UserProfile(_tmpId,_tmpName,_tmpUsername,_tmpAge,_tmpSex,_tmpGoal,_tmpTargetWeight,_tmpTargetBodyFatPercentage,_tmpProfileImageUrl,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BodyMeasurement>> getAllMeasurements() {
    final String _sql = "SELECT * FROM body_measurements ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"body_measurements"}, new Callable<List<BodyMeasurement>>() {
      @Override
      public List<BodyMeasurement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfBodyFatPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyFatPercentage");
          final int _cursorIndexOfLeanMass = CursorUtil.getColumnIndexOrThrow(_cursor, "leanMass");
          final int _cursorIndexOfFatMass = CursorUtil.getColumnIndexOrThrow(_cursor, "fatMass");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfChest = CursorUtil.getColumnIndexOrThrow(_cursor, "chest");
          final int _cursorIndexOfWaist = CursorUtil.getColumnIndexOrThrow(_cursor, "waist");
          final int _cursorIndexOfBiceps = CursorUtil.getColumnIndexOrThrow(_cursor, "biceps");
          final int _cursorIndexOfThighs = CursorUtil.getColumnIndexOrThrow(_cursor, "thighs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<BodyMeasurement> _result = new ArrayList<BodyMeasurement>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final BodyMeasurement _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpDate = _tmp_1;
            }
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final Float _tmpBodyFatPercentage;
            if (_cursor.isNull(_cursorIndexOfBodyFatPercentage)) {
              _tmpBodyFatPercentage = null;
            } else {
              _tmpBodyFatPercentage = _cursor.getFloat(_cursorIndexOfBodyFatPercentage);
            }
            final Float _tmpLeanMass;
            if (_cursor.isNull(_cursorIndexOfLeanMass)) {
              _tmpLeanMass = null;
            } else {
              _tmpLeanMass = _cursor.getFloat(_cursorIndexOfLeanMass);
            }
            final Float _tmpFatMass;
            if (_cursor.isNull(_cursorIndexOfFatMass)) {
              _tmpFatMass = null;
            } else {
              _tmpFatMass = _cursor.getFloat(_cursorIndexOfFatMass);
            }
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpChest;
            if (_cursor.isNull(_cursorIndexOfChest)) {
              _tmpChest = null;
            } else {
              _tmpChest = _cursor.getFloat(_cursorIndexOfChest);
            }
            final Float _tmpWaist;
            if (_cursor.isNull(_cursorIndexOfWaist)) {
              _tmpWaist = null;
            } else {
              _tmpWaist = _cursor.getFloat(_cursorIndexOfWaist);
            }
            final Float _tmpBiceps;
            if (_cursor.isNull(_cursorIndexOfBiceps)) {
              _tmpBiceps = null;
            } else {
              _tmpBiceps = _cursor.getFloat(_cursorIndexOfBiceps);
            }
            final Float _tmpThighs;
            if (_cursor.isNull(_cursorIndexOfThighs)) {
              _tmpThighs = null;
            } else {
              _tmpThighs = _cursor.getFloat(_cursorIndexOfThighs);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new BodyMeasurement(_tmpId,_tmpDate,_tmpWeight,_tmpBodyFatPercentage,_tmpLeanMass,_tmpFatMass,_tmpHeight,_tmpChest,_tmpWaist,_tmpBiceps,_tmpThighs,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<BodyMeasurement> getMeasurementById(final long id) {
    final String _sql = "SELECT * FROM body_measurements WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"body_measurements"}, new Callable<BodyMeasurement>() {
      @Override
      public BodyMeasurement call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfBodyFatPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyFatPercentage");
          final int _cursorIndexOfLeanMass = CursorUtil.getColumnIndexOrThrow(_cursor, "leanMass");
          final int _cursorIndexOfFatMass = CursorUtil.getColumnIndexOrThrow(_cursor, "fatMass");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfChest = CursorUtil.getColumnIndexOrThrow(_cursor, "chest");
          final int _cursorIndexOfWaist = CursorUtil.getColumnIndexOrThrow(_cursor, "waist");
          final int _cursorIndexOfBiceps = CursorUtil.getColumnIndexOrThrow(_cursor, "biceps");
          final int _cursorIndexOfThighs = CursorUtil.getColumnIndexOrThrow(_cursor, "thighs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final BodyMeasurement _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpDate = _tmp_1;
            }
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final Float _tmpBodyFatPercentage;
            if (_cursor.isNull(_cursorIndexOfBodyFatPercentage)) {
              _tmpBodyFatPercentage = null;
            } else {
              _tmpBodyFatPercentage = _cursor.getFloat(_cursorIndexOfBodyFatPercentage);
            }
            final Float _tmpLeanMass;
            if (_cursor.isNull(_cursorIndexOfLeanMass)) {
              _tmpLeanMass = null;
            } else {
              _tmpLeanMass = _cursor.getFloat(_cursorIndexOfLeanMass);
            }
            final Float _tmpFatMass;
            if (_cursor.isNull(_cursorIndexOfFatMass)) {
              _tmpFatMass = null;
            } else {
              _tmpFatMass = _cursor.getFloat(_cursorIndexOfFatMass);
            }
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpChest;
            if (_cursor.isNull(_cursorIndexOfChest)) {
              _tmpChest = null;
            } else {
              _tmpChest = _cursor.getFloat(_cursorIndexOfChest);
            }
            final Float _tmpWaist;
            if (_cursor.isNull(_cursorIndexOfWaist)) {
              _tmpWaist = null;
            } else {
              _tmpWaist = _cursor.getFloat(_cursorIndexOfWaist);
            }
            final Float _tmpBiceps;
            if (_cursor.isNull(_cursorIndexOfBiceps)) {
              _tmpBiceps = null;
            } else {
              _tmpBiceps = _cursor.getFloat(_cursorIndexOfBiceps);
            }
            final Float _tmpThighs;
            if (_cursor.isNull(_cursorIndexOfThighs)) {
              _tmpThighs = null;
            } else {
              _tmpThighs = _cursor.getFloat(_cursorIndexOfThighs);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _result = new BodyMeasurement(_tmpId,_tmpDate,_tmpWeight,_tmpBodyFatPercentage,_tmpLeanMass,_tmpFatMass,_tmpHeight,_tmpChest,_tmpWaist,_tmpBiceps,_tmpThighs,_tmpNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<BodyMeasurement> getLatestMeasurement() {
    final String _sql = "SELECT * FROM body_measurements ORDER BY date DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"body_measurements"}, new Callable<BodyMeasurement>() {
      @Override
      public BodyMeasurement call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfBodyFatPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyFatPercentage");
          final int _cursorIndexOfLeanMass = CursorUtil.getColumnIndexOrThrow(_cursor, "leanMass");
          final int _cursorIndexOfFatMass = CursorUtil.getColumnIndexOrThrow(_cursor, "fatMass");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfChest = CursorUtil.getColumnIndexOrThrow(_cursor, "chest");
          final int _cursorIndexOfWaist = CursorUtil.getColumnIndexOrThrow(_cursor, "waist");
          final int _cursorIndexOfBiceps = CursorUtil.getColumnIndexOrThrow(_cursor, "biceps");
          final int _cursorIndexOfThighs = CursorUtil.getColumnIndexOrThrow(_cursor, "thighs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final BodyMeasurement _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpDate = _tmp_1;
            }
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final Float _tmpBodyFatPercentage;
            if (_cursor.isNull(_cursorIndexOfBodyFatPercentage)) {
              _tmpBodyFatPercentage = null;
            } else {
              _tmpBodyFatPercentage = _cursor.getFloat(_cursorIndexOfBodyFatPercentage);
            }
            final Float _tmpLeanMass;
            if (_cursor.isNull(_cursorIndexOfLeanMass)) {
              _tmpLeanMass = null;
            } else {
              _tmpLeanMass = _cursor.getFloat(_cursorIndexOfLeanMass);
            }
            final Float _tmpFatMass;
            if (_cursor.isNull(_cursorIndexOfFatMass)) {
              _tmpFatMass = null;
            } else {
              _tmpFatMass = _cursor.getFloat(_cursorIndexOfFatMass);
            }
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpChest;
            if (_cursor.isNull(_cursorIndexOfChest)) {
              _tmpChest = null;
            } else {
              _tmpChest = _cursor.getFloat(_cursorIndexOfChest);
            }
            final Float _tmpWaist;
            if (_cursor.isNull(_cursorIndexOfWaist)) {
              _tmpWaist = null;
            } else {
              _tmpWaist = _cursor.getFloat(_cursorIndexOfWaist);
            }
            final Float _tmpBiceps;
            if (_cursor.isNull(_cursorIndexOfBiceps)) {
              _tmpBiceps = null;
            } else {
              _tmpBiceps = _cursor.getFloat(_cursorIndexOfBiceps);
            }
            final Float _tmpThighs;
            if (_cursor.isNull(_cursorIndexOfThighs)) {
              _tmpThighs = null;
            } else {
              _tmpThighs = _cursor.getFloat(_cursorIndexOfThighs);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _result = new BodyMeasurement(_tmpId,_tmpDate,_tmpWeight,_tmpBodyFatPercentage,_tmpLeanMass,_tmpFatMass,_tmpHeight,_tmpChest,_tmpWaist,_tmpBiceps,_tmpThighs,_tmpNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BodyMeasurement>> getMeasurementsByDateRange(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM body_measurements WHERE date >= ? AND date <= ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"body_measurements"}, new Callable<List<BodyMeasurement>>() {
      @Override
      public List<BodyMeasurement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfBodyFatPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyFatPercentage");
          final int _cursorIndexOfLeanMass = CursorUtil.getColumnIndexOrThrow(_cursor, "leanMass");
          final int _cursorIndexOfFatMass = CursorUtil.getColumnIndexOrThrow(_cursor, "fatMass");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfChest = CursorUtil.getColumnIndexOrThrow(_cursor, "chest");
          final int _cursorIndexOfWaist = CursorUtil.getColumnIndexOrThrow(_cursor, "waist");
          final int _cursorIndexOfBiceps = CursorUtil.getColumnIndexOrThrow(_cursor, "biceps");
          final int _cursorIndexOfThighs = CursorUtil.getColumnIndexOrThrow(_cursor, "thighs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<BodyMeasurement> _result = new ArrayList<BodyMeasurement>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final BodyMeasurement _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if(_tmp_3 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpDate = _tmp_3;
            }
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final Float _tmpBodyFatPercentage;
            if (_cursor.isNull(_cursorIndexOfBodyFatPercentage)) {
              _tmpBodyFatPercentage = null;
            } else {
              _tmpBodyFatPercentage = _cursor.getFloat(_cursorIndexOfBodyFatPercentage);
            }
            final Float _tmpLeanMass;
            if (_cursor.isNull(_cursorIndexOfLeanMass)) {
              _tmpLeanMass = null;
            } else {
              _tmpLeanMass = _cursor.getFloat(_cursorIndexOfLeanMass);
            }
            final Float _tmpFatMass;
            if (_cursor.isNull(_cursorIndexOfFatMass)) {
              _tmpFatMass = null;
            } else {
              _tmpFatMass = _cursor.getFloat(_cursorIndexOfFatMass);
            }
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpChest;
            if (_cursor.isNull(_cursorIndexOfChest)) {
              _tmpChest = null;
            } else {
              _tmpChest = _cursor.getFloat(_cursorIndexOfChest);
            }
            final Float _tmpWaist;
            if (_cursor.isNull(_cursorIndexOfWaist)) {
              _tmpWaist = null;
            } else {
              _tmpWaist = _cursor.getFloat(_cursorIndexOfWaist);
            }
            final Float _tmpBiceps;
            if (_cursor.isNull(_cursorIndexOfBiceps)) {
              _tmpBiceps = null;
            } else {
              _tmpBiceps = _cursor.getFloat(_cursorIndexOfBiceps);
            }
            final Float _tmpThighs;
            if (_cursor.isNull(_cursorIndexOfThighs)) {
              _tmpThighs = null;
            } else {
              _tmpThighs = _cursor.getFloat(_cursorIndexOfThighs);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new BodyMeasurement(_tmpId,_tmpDate,_tmpWeight,_tmpBodyFatPercentage,_tmpLeanMass,_tmpFatMass,_tmpHeight,_tmpChest,_tmpWaist,_tmpBiceps,_tmpThighs,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BodyMeasurement>> getAllMeasurementsAscending() {
    final String _sql = "SELECT * FROM body_measurements ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"body_measurements"}, new Callable<List<BodyMeasurement>>() {
      @Override
      public List<BodyMeasurement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfBodyFatPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyFatPercentage");
          final int _cursorIndexOfLeanMass = CursorUtil.getColumnIndexOrThrow(_cursor, "leanMass");
          final int _cursorIndexOfFatMass = CursorUtil.getColumnIndexOrThrow(_cursor, "fatMass");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfChest = CursorUtil.getColumnIndexOrThrow(_cursor, "chest");
          final int _cursorIndexOfWaist = CursorUtil.getColumnIndexOrThrow(_cursor, "waist");
          final int _cursorIndexOfBiceps = CursorUtil.getColumnIndexOrThrow(_cursor, "biceps");
          final int _cursorIndexOfThighs = CursorUtil.getColumnIndexOrThrow(_cursor, "thighs");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<BodyMeasurement> _result = new ArrayList<BodyMeasurement>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final BodyMeasurement _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpDate = _tmp_1;
            }
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final Float _tmpBodyFatPercentage;
            if (_cursor.isNull(_cursorIndexOfBodyFatPercentage)) {
              _tmpBodyFatPercentage = null;
            } else {
              _tmpBodyFatPercentage = _cursor.getFloat(_cursorIndexOfBodyFatPercentage);
            }
            final Float _tmpLeanMass;
            if (_cursor.isNull(_cursorIndexOfLeanMass)) {
              _tmpLeanMass = null;
            } else {
              _tmpLeanMass = _cursor.getFloat(_cursorIndexOfLeanMass);
            }
            final Float _tmpFatMass;
            if (_cursor.isNull(_cursorIndexOfFatMass)) {
              _tmpFatMass = null;
            } else {
              _tmpFatMass = _cursor.getFloat(_cursorIndexOfFatMass);
            }
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpChest;
            if (_cursor.isNull(_cursorIndexOfChest)) {
              _tmpChest = null;
            } else {
              _tmpChest = _cursor.getFloat(_cursorIndexOfChest);
            }
            final Float _tmpWaist;
            if (_cursor.isNull(_cursorIndexOfWaist)) {
              _tmpWaist = null;
            } else {
              _tmpWaist = _cursor.getFloat(_cursorIndexOfWaist);
            }
            final Float _tmpBiceps;
            if (_cursor.isNull(_cursorIndexOfBiceps)) {
              _tmpBiceps = null;
            } else {
              _tmpBiceps = _cursor.getFloat(_cursorIndexOfBiceps);
            }
            final Float _tmpThighs;
            if (_cursor.isNull(_cursorIndexOfThighs)) {
              _tmpThighs = null;
            } else {
              _tmpThighs = _cursor.getFloat(_cursorIndexOfThighs);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new BodyMeasurement(_tmpId,_tmpDate,_tmpWeight,_tmpBodyFatPercentage,_tmpLeanMass,_tmpFatMass,_tmpHeight,_tmpChest,_tmpWaist,_tmpBiceps,_tmpThighs,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
