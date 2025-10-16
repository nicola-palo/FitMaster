package com.fitmaster.app.data.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.fitmaster.app.data.entity.Exercise;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExerciseDao_Impl implements ExerciseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Exercise> __insertionAdapterOfExercise;

  private final EntityDeletionOrUpdateAdapter<Exercise> __deletionAdapterOfExercise;

  private final EntityDeletionOrUpdateAdapter<Exercise> __updateAdapterOfExercise;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public ExerciseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExercise = new EntityInsertionAdapter<Exercise>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `exercises` (`id`,`name`,`description`,`technicalTips`,`category`,`difficulty`,`muscleGroup`,`defaultSets`,`defaultReps`,`defaultRestSeconds`,`imageUrl`,`videoUrl`,`isCustom`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Exercise value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getTechnicalTips() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTechnicalTips());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCategory());
        }
        if (value.getDifficulty() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDifficulty());
        }
        if (value.getMuscleGroup() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMuscleGroup());
        }
        stmt.bindLong(8, value.getDefaultSets());
        stmt.bindLong(9, value.getDefaultReps());
        stmt.bindLong(10, value.getDefaultRestSeconds());
        if (value.getImageUrl() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getImageUrl());
        }
        if (value.getVideoUrl() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getVideoUrl());
        }
        final int _tmp = value.isCustom() ? 1 : 0;
        stmt.bindLong(13, _tmp);
      }
    };
    this.__deletionAdapterOfExercise = new EntityDeletionOrUpdateAdapter<Exercise>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `exercises` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Exercise value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfExercise = new EntityDeletionOrUpdateAdapter<Exercise>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `exercises` SET `id` = ?,`name` = ?,`description` = ?,`technicalTips` = ?,`category` = ?,`difficulty` = ?,`muscleGroup` = ?,`defaultSets` = ?,`defaultReps` = ?,`defaultRestSeconds` = ?,`imageUrl` = ?,`videoUrl` = ?,`isCustom` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Exercise value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getTechnicalTips() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTechnicalTips());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCategory());
        }
        if (value.getDifficulty() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDifficulty());
        }
        if (value.getMuscleGroup() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMuscleGroup());
        }
        stmt.bindLong(8, value.getDefaultSets());
        stmt.bindLong(9, value.getDefaultReps());
        stmt.bindLong(10, value.getDefaultRestSeconds());
        if (value.getImageUrl() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getImageUrl());
        }
        if (value.getVideoUrl() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getVideoUrl());
        }
        final int _tmp = value.isCustom() ? 1 : 0;
        stmt.bindLong(13, _tmp);
        stmt.bindLong(14, value.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM exercises WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Exercise exercise, final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfExercise.insertAndReturnId(exercise);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertAll(final List<Exercise> exercises,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfExercise.insert(exercises);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object delete(final Exercise exercise, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object update(final Exercise exercise, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Exercise>> getAllExercises() {
    final String _sql = "SELECT * FROM exercises ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"exercises"}, new Callable<List<Exercise>>() {
      @Override
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTechnicalTips = CursorUtil.getColumnIndexOrThrow(_cursor, "technicalTips");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfDefaultSets = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultSets");
          final int _cursorIndexOfDefaultReps = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultReps");
          final int _cursorIndexOfDefaultRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRestSeconds");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Exercise _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpTechnicalTips;
            if (_cursor.isNull(_cursorIndexOfTechnicalTips)) {
              _tmpTechnicalTips = null;
            } else {
              _tmpTechnicalTips = _cursor.getString(_cursorIndexOfTechnicalTips);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpMuscleGroup;
            if (_cursor.isNull(_cursorIndexOfMuscleGroup)) {
              _tmpMuscleGroup = null;
            } else {
              _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            }
            final int _tmpDefaultSets;
            _tmpDefaultSets = _cursor.getInt(_cursorIndexOfDefaultSets);
            final int _tmpDefaultReps;
            _tmpDefaultReps = _cursor.getInt(_cursorIndexOfDefaultReps);
            final int _tmpDefaultRestSeconds;
            _tmpDefaultRestSeconds = _cursor.getInt(_cursorIndexOfDefaultRestSeconds);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new Exercise(_tmpId,_tmpName,_tmpDescription,_tmpTechnicalTips,_tmpCategory,_tmpDifficulty,_tmpMuscleGroup,_tmpDefaultSets,_tmpDefaultReps,_tmpDefaultRestSeconds,_tmpImageUrl,_tmpVideoUrl,_tmpIsCustom);
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
  public Flow<Exercise> getExerciseById(final long id) {
    final String _sql = "SELECT * FROM exercises WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"exercises"}, new Callable<Exercise>() {
      @Override
      public Exercise call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTechnicalTips = CursorUtil.getColumnIndexOrThrow(_cursor, "technicalTips");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfDefaultSets = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultSets");
          final int _cursorIndexOfDefaultReps = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultReps");
          final int _cursorIndexOfDefaultRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRestSeconds");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final Exercise _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpTechnicalTips;
            if (_cursor.isNull(_cursorIndexOfTechnicalTips)) {
              _tmpTechnicalTips = null;
            } else {
              _tmpTechnicalTips = _cursor.getString(_cursorIndexOfTechnicalTips);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpMuscleGroup;
            if (_cursor.isNull(_cursorIndexOfMuscleGroup)) {
              _tmpMuscleGroup = null;
            } else {
              _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            }
            final int _tmpDefaultSets;
            _tmpDefaultSets = _cursor.getInt(_cursorIndexOfDefaultSets);
            final int _tmpDefaultReps;
            _tmpDefaultReps = _cursor.getInt(_cursorIndexOfDefaultReps);
            final int _tmpDefaultRestSeconds;
            _tmpDefaultRestSeconds = _cursor.getInt(_cursorIndexOfDefaultRestSeconds);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _result = new Exercise(_tmpId,_tmpName,_tmpDescription,_tmpTechnicalTips,_tmpCategory,_tmpDifficulty,_tmpMuscleGroup,_tmpDefaultSets,_tmpDefaultReps,_tmpDefaultRestSeconds,_tmpImageUrl,_tmpVideoUrl,_tmpIsCustom);
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
  public Flow<List<Exercise>> getExercisesByCategory(final String category) {
    final String _sql = "SELECT * FROM exercises WHERE category = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"exercises"}, new Callable<List<Exercise>>() {
      @Override
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTechnicalTips = CursorUtil.getColumnIndexOrThrow(_cursor, "technicalTips");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfDefaultSets = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultSets");
          final int _cursorIndexOfDefaultReps = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultReps");
          final int _cursorIndexOfDefaultRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRestSeconds");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Exercise _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpTechnicalTips;
            if (_cursor.isNull(_cursorIndexOfTechnicalTips)) {
              _tmpTechnicalTips = null;
            } else {
              _tmpTechnicalTips = _cursor.getString(_cursorIndexOfTechnicalTips);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpMuscleGroup;
            if (_cursor.isNull(_cursorIndexOfMuscleGroup)) {
              _tmpMuscleGroup = null;
            } else {
              _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            }
            final int _tmpDefaultSets;
            _tmpDefaultSets = _cursor.getInt(_cursorIndexOfDefaultSets);
            final int _tmpDefaultReps;
            _tmpDefaultReps = _cursor.getInt(_cursorIndexOfDefaultReps);
            final int _tmpDefaultRestSeconds;
            _tmpDefaultRestSeconds = _cursor.getInt(_cursorIndexOfDefaultRestSeconds);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new Exercise(_tmpId,_tmpName,_tmpDescription,_tmpTechnicalTips,_tmpCategory,_tmpDifficulty,_tmpMuscleGroup,_tmpDefaultSets,_tmpDefaultReps,_tmpDefaultRestSeconds,_tmpImageUrl,_tmpVideoUrl,_tmpIsCustom);
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
  public Flow<List<Exercise>> getExercisesByMuscleGroup(final String muscleGroup) {
    final String _sql = "SELECT * FROM exercises WHERE muscleGroup = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (muscleGroup == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, muscleGroup);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"exercises"}, new Callable<List<Exercise>>() {
      @Override
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTechnicalTips = CursorUtil.getColumnIndexOrThrow(_cursor, "technicalTips");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfDefaultSets = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultSets");
          final int _cursorIndexOfDefaultReps = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultReps");
          final int _cursorIndexOfDefaultRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRestSeconds");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Exercise _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpTechnicalTips;
            if (_cursor.isNull(_cursorIndexOfTechnicalTips)) {
              _tmpTechnicalTips = null;
            } else {
              _tmpTechnicalTips = _cursor.getString(_cursorIndexOfTechnicalTips);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpMuscleGroup;
            if (_cursor.isNull(_cursorIndexOfMuscleGroup)) {
              _tmpMuscleGroup = null;
            } else {
              _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            }
            final int _tmpDefaultSets;
            _tmpDefaultSets = _cursor.getInt(_cursorIndexOfDefaultSets);
            final int _tmpDefaultReps;
            _tmpDefaultReps = _cursor.getInt(_cursorIndexOfDefaultReps);
            final int _tmpDefaultRestSeconds;
            _tmpDefaultRestSeconds = _cursor.getInt(_cursorIndexOfDefaultRestSeconds);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new Exercise(_tmpId,_tmpName,_tmpDescription,_tmpTechnicalTips,_tmpCategory,_tmpDifficulty,_tmpMuscleGroup,_tmpDefaultSets,_tmpDefaultReps,_tmpDefaultRestSeconds,_tmpImageUrl,_tmpVideoUrl,_tmpIsCustom);
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
  public Flow<List<Exercise>> getExercisesByDifficulty(final String difficulty) {
    final String _sql = "SELECT * FROM exercises WHERE difficulty = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (difficulty == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, difficulty);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"exercises"}, new Callable<List<Exercise>>() {
      @Override
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTechnicalTips = CursorUtil.getColumnIndexOrThrow(_cursor, "technicalTips");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfDefaultSets = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultSets");
          final int _cursorIndexOfDefaultReps = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultReps");
          final int _cursorIndexOfDefaultRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRestSeconds");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Exercise _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpTechnicalTips;
            if (_cursor.isNull(_cursorIndexOfTechnicalTips)) {
              _tmpTechnicalTips = null;
            } else {
              _tmpTechnicalTips = _cursor.getString(_cursorIndexOfTechnicalTips);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpMuscleGroup;
            if (_cursor.isNull(_cursorIndexOfMuscleGroup)) {
              _tmpMuscleGroup = null;
            } else {
              _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            }
            final int _tmpDefaultSets;
            _tmpDefaultSets = _cursor.getInt(_cursorIndexOfDefaultSets);
            final int _tmpDefaultReps;
            _tmpDefaultReps = _cursor.getInt(_cursorIndexOfDefaultReps);
            final int _tmpDefaultRestSeconds;
            _tmpDefaultRestSeconds = _cursor.getInt(_cursorIndexOfDefaultRestSeconds);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new Exercise(_tmpId,_tmpName,_tmpDescription,_tmpTechnicalTips,_tmpCategory,_tmpDifficulty,_tmpMuscleGroup,_tmpDefaultSets,_tmpDefaultReps,_tmpDefaultRestSeconds,_tmpImageUrl,_tmpVideoUrl,_tmpIsCustom);
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
  public Flow<List<Exercise>> searchExercises(final String query) {
    final String _sql = "SELECT * FROM exercises WHERE name LIKE '%' || ? || '%' OR muscleGroup LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"exercises"}, new Callable<List<Exercise>>() {
      @Override
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTechnicalTips = CursorUtil.getColumnIndexOrThrow(_cursor, "technicalTips");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfDefaultSets = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultSets");
          final int _cursorIndexOfDefaultReps = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultReps");
          final int _cursorIndexOfDefaultRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultRestSeconds");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfVideoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Exercise _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpTechnicalTips;
            if (_cursor.isNull(_cursorIndexOfTechnicalTips)) {
              _tmpTechnicalTips = null;
            } else {
              _tmpTechnicalTips = _cursor.getString(_cursorIndexOfTechnicalTips);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            final String _tmpMuscleGroup;
            if (_cursor.isNull(_cursorIndexOfMuscleGroup)) {
              _tmpMuscleGroup = null;
            } else {
              _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            }
            final int _tmpDefaultSets;
            _tmpDefaultSets = _cursor.getInt(_cursorIndexOfDefaultSets);
            final int _tmpDefaultReps;
            _tmpDefaultReps = _cursor.getInt(_cursorIndexOfDefaultReps);
            final int _tmpDefaultRestSeconds;
            _tmpDefaultRestSeconds = _cursor.getInt(_cursorIndexOfDefaultRestSeconds);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpVideoUrl;
            if (_cursor.isNull(_cursorIndexOfVideoUrl)) {
              _tmpVideoUrl = null;
            } else {
              _tmpVideoUrl = _cursor.getString(_cursorIndexOfVideoUrl);
            }
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new Exercise(_tmpId,_tmpName,_tmpDescription,_tmpTechnicalTips,_tmpCategory,_tmpDifficulty,_tmpMuscleGroup,_tmpDefaultSets,_tmpDefaultReps,_tmpDefaultRestSeconds,_tmpImageUrl,_tmpVideoUrl,_tmpIsCustom);
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
