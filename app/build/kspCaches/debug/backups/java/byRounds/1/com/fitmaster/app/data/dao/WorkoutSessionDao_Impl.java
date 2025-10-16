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
import com.fitmaster.app.data.converter.Converters;
import com.fitmaster.app.data.entity.WorkoutSession;
import com.fitmaster.app.data.entity.WorkoutSessionExercise;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
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
public final class WorkoutSessionDao_Impl implements WorkoutSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkoutSession> __insertionAdapterOfWorkoutSession;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<WorkoutSessionExercise> __insertionAdapterOfWorkoutSessionExercise;

  private final EntityDeletionOrUpdateAdapter<WorkoutSession> __deletionAdapterOfWorkoutSession;

  private final EntityDeletionOrUpdateAdapter<WorkoutSessionExercise> __deletionAdapterOfWorkoutSessionExercise;

  private final EntityDeletionOrUpdateAdapter<WorkoutSession> __updateAdapterOfWorkoutSession;

  private final EntityDeletionOrUpdateAdapter<WorkoutSessionExercise> __updateAdapterOfWorkoutSessionExercise;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSessionExercises;

  public WorkoutSessionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkoutSession = new EntityInsertionAdapter<WorkoutSession>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `workout_sessions` (`id`,`planId`,`planDayId`,`startTime`,`endTime`,`durationSeconds`,`isCompleted`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutSession value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getPlanId());
        stmt.bindLong(3, value.getPlanDayId());
        final Long _tmp = __converters.dateToTimestamp(value.getStartTime());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(value.getEndTime());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
        stmt.bindLong(6, value.getDurationSeconds());
        final int _tmp_2 = value.isCompleted() ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
      }
    };
    this.__insertionAdapterOfWorkoutSessionExercise = new EntityInsertionAdapter<WorkoutSessionExercise>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `workout_session_exercises` (`id`,`sessionId`,`exerciseId`,`plannedSets`,`completedSets`,`plannedReps`,`actualReps`,`restSeconds`,`completedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutSessionExercise value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        stmt.bindLong(3, value.getExerciseId());
        stmt.bindLong(4, value.getPlannedSets());
        stmt.bindLong(5, value.getCompletedSets());
        stmt.bindLong(6, value.getPlannedReps());
        if (value.getActualReps() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getActualReps());
        }
        stmt.bindLong(8, value.getRestSeconds());
        final Long _tmp = __converters.dateToTimestamp(value.getCompletedAt());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp);
        }
      }
    };
    this.__deletionAdapterOfWorkoutSession = new EntityDeletionOrUpdateAdapter<WorkoutSession>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `workout_sessions` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutSession value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__deletionAdapterOfWorkoutSessionExercise = new EntityDeletionOrUpdateAdapter<WorkoutSessionExercise>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `workout_session_exercises` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutSessionExercise value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfWorkoutSession = new EntityDeletionOrUpdateAdapter<WorkoutSession>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `workout_sessions` SET `id` = ?,`planId` = ?,`planDayId` = ?,`startTime` = ?,`endTime` = ?,`durationSeconds` = ?,`isCompleted` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutSession value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getPlanId());
        stmt.bindLong(3, value.getPlanDayId());
        final Long _tmp = __converters.dateToTimestamp(value.getStartTime());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(value.getEndTime());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
        stmt.bindLong(6, value.getDurationSeconds());
        final int _tmp_2 = value.isCompleted() ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
        stmt.bindLong(8, value.getId());
      }
    };
    this.__updateAdapterOfWorkoutSessionExercise = new EntityDeletionOrUpdateAdapter<WorkoutSessionExercise>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `workout_session_exercises` SET `id` = ?,`sessionId` = ?,`exerciseId` = ?,`plannedSets` = ?,`completedSets` = ?,`plannedReps` = ?,`actualReps` = ?,`restSeconds` = ?,`completedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutSessionExercise value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSessionId());
        stmt.bindLong(3, value.getExerciseId());
        stmt.bindLong(4, value.getPlannedSets());
        stmt.bindLong(5, value.getCompletedSets());
        stmt.bindLong(6, value.getPlannedReps());
        if (value.getActualReps() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getActualReps());
        }
        stmt.bindLong(8, value.getRestSeconds());
        final Long _tmp = __converters.dateToTimestamp(value.getCompletedAt());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp);
        }
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteSessionExercises = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM workout_session_exercises WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSession(final WorkoutSession session,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfWorkoutSession.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertSessionExercise(final WorkoutSessionExercise exercise,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfWorkoutSessionExercise.insertAndReturnId(exercise);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteSession(final WorkoutSession session,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutSession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteSessionExercise(final WorkoutSessionExercise exercise,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutSessionExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateSession(final WorkoutSession session,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutSession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateSessionExercise(final WorkoutSessionExercise exercise,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutSessionExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteSessionExercises(final long sessionId,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSessionExercises.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSessionExercises.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<WorkoutSession>> getAllSessions() {
    final String _sql = "SELECT * FROM workout_sessions ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_sessions"}, new Callable<List<WorkoutSession>>() {
      @Override
      public List<WorkoutSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfPlanDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "planDayId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<WorkoutSession> _result = new ArrayList<WorkoutSession>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final long _tmpPlanDayId;
            _tmpPlanDayId = _cursor.getLong(_cursorIndexOfPlanDayId);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpStartTime = _tmp_1;
            }
            final Date _tmpEndTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_2);
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            _item = new WorkoutSession(_tmpId,_tmpPlanId,_tmpPlanDayId,_tmpStartTime,_tmpEndTime,_tmpDurationSeconds,_tmpIsCompleted);
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
  public Flow<WorkoutSession> getSessionById(final long id) {
    final String _sql = "SELECT * FROM workout_sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_sessions"}, new Callable<WorkoutSession>() {
      @Override
      public WorkoutSession call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfPlanDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "planDayId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final WorkoutSession _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final long _tmpPlanDayId;
            _tmpPlanDayId = _cursor.getLong(_cursorIndexOfPlanDayId);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpStartTime = _tmp_1;
            }
            final Date _tmpEndTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_2);
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            _result = new WorkoutSession(_tmpId,_tmpPlanId,_tmpPlanDayId,_tmpStartTime,_tmpEndTime,_tmpDurationSeconds,_tmpIsCompleted);
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
  public Flow<WorkoutSession> getActiveSession() {
    final String _sql = "SELECT * FROM workout_sessions WHERE isCompleted = 0 ORDER BY startTime DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_sessions"}, new Callable<WorkoutSession>() {
      @Override
      public WorkoutSession call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfPlanDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "planDayId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final WorkoutSession _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final long _tmpPlanDayId;
            _tmpPlanDayId = _cursor.getLong(_cursorIndexOfPlanDayId);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpStartTime = _tmp_1;
            }
            final Date _tmpEndTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_2);
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            _result = new WorkoutSession(_tmpId,_tmpPlanId,_tmpPlanDayId,_tmpStartTime,_tmpEndTime,_tmpDurationSeconds,_tmpIsCompleted);
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
  public Flow<List<WorkoutSession>> getCompletedSessions() {
    final String _sql = "SELECT * FROM workout_sessions WHERE isCompleted = 1 ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_sessions"}, new Callable<List<WorkoutSession>>() {
      @Override
      public List<WorkoutSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfPlanDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "planDayId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<WorkoutSession> _result = new ArrayList<WorkoutSession>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final long _tmpPlanDayId;
            _tmpPlanDayId = _cursor.getLong(_cursorIndexOfPlanDayId);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if(_tmp_1 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpStartTime = _tmp_1;
            }
            final Date _tmpEndTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_2);
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            _item = new WorkoutSession(_tmpId,_tmpPlanId,_tmpPlanDayId,_tmpStartTime,_tmpEndTime,_tmpDurationSeconds,_tmpIsCompleted);
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
  public Flow<List<WorkoutSession>> getSessionsByDateRange(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM workout_sessions WHERE startTime >= ? AND startTime <= ? ORDER BY startTime DESC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_sessions"}, new Callable<List<WorkoutSession>>() {
      @Override
      public List<WorkoutSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfPlanDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "planDayId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<WorkoutSession> _result = new ArrayList<WorkoutSession>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final long _tmpPlanDayId;
            _tmpPlanDayId = _cursor.getLong(_cursorIndexOfPlanDayId);
            final Date _tmpStartTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if(_tmp_3 == null) {
              throw new IllegalStateException("Expected non-null java.util.Date, but it was null.");
            } else {
              _tmpStartTime = _tmp_3;
            }
            final Date _tmpEndTime;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_4);
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final boolean _tmpIsCompleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_5 != 0;
            _item = new WorkoutSession(_tmpId,_tmpPlanId,_tmpPlanDayId,_tmpStartTime,_tmpEndTime,_tmpDurationSeconds,_tmpIsCompleted);
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
  public Flow<List<WorkoutSessionExercise>> getSessionExercises(final long sessionId) {
    final String _sql = "SELECT * FROM workout_session_exercises WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_session_exercises"}, new Callable<List<WorkoutSessionExercise>>() {
      @Override
      public List<WorkoutSessionExercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseId");
          final int _cursorIndexOfPlannedSets = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedSets");
          final int _cursorIndexOfCompletedSets = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSets");
          final int _cursorIndexOfPlannedReps = CursorUtil.getColumnIndexOrThrow(_cursor, "plannedReps");
          final int _cursorIndexOfActualReps = CursorUtil.getColumnIndexOrThrow(_cursor, "actualReps");
          final int _cursorIndexOfRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "restSeconds");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<WorkoutSessionExercise> _result = new ArrayList<WorkoutSessionExercise>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutSessionExercise _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final long _tmpExerciseId;
            _tmpExerciseId = _cursor.getLong(_cursorIndexOfExerciseId);
            final int _tmpPlannedSets;
            _tmpPlannedSets = _cursor.getInt(_cursorIndexOfPlannedSets);
            final int _tmpCompletedSets;
            _tmpCompletedSets = _cursor.getInt(_cursorIndexOfCompletedSets);
            final int _tmpPlannedReps;
            _tmpPlannedReps = _cursor.getInt(_cursorIndexOfPlannedReps);
            final Integer _tmpActualReps;
            if (_cursor.isNull(_cursorIndexOfActualReps)) {
              _tmpActualReps = null;
            } else {
              _tmpActualReps = _cursor.getInt(_cursorIndexOfActualReps);
            }
            final int _tmpRestSeconds;
            _tmpRestSeconds = _cursor.getInt(_cursorIndexOfRestSeconds);
            final Date _tmpCompletedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = __converters.fromTimestamp(_tmp);
            _item = new WorkoutSessionExercise(_tmpId,_tmpSessionId,_tmpExerciseId,_tmpPlannedSets,_tmpCompletedSets,_tmpPlannedReps,_tmpActualReps,_tmpRestSeconds,_tmpCompletedAt);
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
