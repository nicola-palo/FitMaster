package com.fitmaster.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
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
import com.fitmaster.app.data.entity.WorkoutPlan;
import com.fitmaster.app.data.entity.WorkoutPlanDay;
import com.fitmaster.app.data.entity.WorkoutPlanExercise;
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
public final class WorkoutPlanDao_Impl implements WorkoutPlanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkoutPlan> __insertionAdapterOfWorkoutPlan;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<WorkoutPlanDay> __insertionAdapterOfWorkoutPlanDay;

  private final EntityInsertionAdapter<WorkoutPlanExercise> __insertionAdapterOfWorkoutPlanExercise;

  private final EntityDeletionOrUpdateAdapter<WorkoutPlan> __deletionAdapterOfWorkoutPlan;

  private final EntityDeletionOrUpdateAdapter<WorkoutPlanDay> __deletionAdapterOfWorkoutPlanDay;

  private final EntityDeletionOrUpdateAdapter<WorkoutPlanExercise> __deletionAdapterOfWorkoutPlanExercise;

  private final EntityDeletionOrUpdateAdapter<WorkoutPlan> __updateAdapterOfWorkoutPlan;

  private final EntityDeletionOrUpdateAdapter<WorkoutPlanExercise> __updateAdapterOfWorkoutPlanExercise;

  private final SharedSQLiteStatement __preparedStmtOfDeactivateAllPlans;

  private final SharedSQLiteStatement __preparedStmtOfSetActivePlan;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavoriteStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlanDays;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlanDayExercises;

  public WorkoutPlanDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkoutPlan = new EntityInsertionAdapter<WorkoutPlan>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `workout_plans` (`id`,`name`,`createdAt`,`isActive`,`isFavorite`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlan value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final Long _tmp = __converters.dateToTimestamp(value.getCreatedAt());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final int _tmp_1 = value.isActive() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        final int _tmp_2 = value.isFavorite() ? 1 : 0;
        stmt.bindLong(5, _tmp_2);
      }
    };
    this.__insertionAdapterOfWorkoutPlanDay = new EntityInsertionAdapter<WorkoutPlanDay>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `workout_plan_days` (`id`,`planId`,`dayOfWeek`,`dayName`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlanDay value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getPlanId());
        stmt.bindLong(3, value.getDayOfWeek());
        if (value.getDayName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDayName());
        }
      }
    };
    this.__insertionAdapterOfWorkoutPlanExercise = new EntityInsertionAdapter<WorkoutPlanExercise>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `workout_plan_exercises` (`id`,`planDayId`,`exerciseId`,`sets`,`reps`,`restSeconds`,`orderIndex`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlanExercise value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getPlanDayId());
        stmt.bindLong(3, value.getExerciseId());
        stmt.bindLong(4, value.getSets());
        stmt.bindLong(5, value.getReps());
        stmt.bindLong(6, value.getRestSeconds());
        stmt.bindLong(7, value.getOrderIndex());
      }
    };
    this.__deletionAdapterOfWorkoutPlan = new EntityDeletionOrUpdateAdapter<WorkoutPlan>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `workout_plans` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlan value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__deletionAdapterOfWorkoutPlanDay = new EntityDeletionOrUpdateAdapter<WorkoutPlanDay>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `workout_plan_days` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlanDay value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__deletionAdapterOfWorkoutPlanExercise = new EntityDeletionOrUpdateAdapter<WorkoutPlanExercise>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `workout_plan_exercises` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlanExercise value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfWorkoutPlan = new EntityDeletionOrUpdateAdapter<WorkoutPlan>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `workout_plans` SET `id` = ?,`name` = ?,`createdAt` = ?,`isActive` = ?,`isFavorite` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlan value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final Long _tmp = __converters.dateToTimestamp(value.getCreatedAt());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        final int _tmp_1 = value.isActive() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        final int _tmp_2 = value.isFavorite() ? 1 : 0;
        stmt.bindLong(5, _tmp_2);
        stmt.bindLong(6, value.getId());
      }
    };
    this.__updateAdapterOfWorkoutPlanExercise = new EntityDeletionOrUpdateAdapter<WorkoutPlanExercise>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `workout_plan_exercises` SET `id` = ?,`planDayId` = ?,`exerciseId` = ?,`sets` = ?,`reps` = ?,`restSeconds` = ?,`orderIndex` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutPlanExercise value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getPlanDayId());
        stmt.bindLong(3, value.getExerciseId());
        stmt.bindLong(4, value.getSets());
        stmt.bindLong(5, value.getReps());
        stmt.bindLong(6, value.getRestSeconds());
        stmt.bindLong(7, value.getOrderIndex());
        stmt.bindLong(8, value.getId());
      }
    };
    this.__preparedStmtOfDeactivateAllPlans = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE workout_plans SET isActive = 0";
        return _query;
      }
    };
    this.__preparedStmtOfSetActivePlan = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE workout_plans SET isActive = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFavoriteStatus = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE workout_plans SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePlanDays = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM workout_plan_days WHERE planId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePlanDayExercises = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM workout_plan_exercises WHERE planDayId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWorkoutPlan(final WorkoutPlan plan,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfWorkoutPlan.insertAndReturnId(plan);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertPlanDay(final WorkoutPlanDay day,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfWorkoutPlanDay.insertAndReturnId(day);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertPlanExercise(final WorkoutPlanExercise exercise,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfWorkoutPlanExercise.insertAndReturnId(exercise);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteWorkoutPlan(final WorkoutPlan plan,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutPlan.handle(plan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deletePlanDay(final WorkoutPlanDay day,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutPlanDay.handle(day);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deletePlanExercise(final WorkoutPlanExercise exercise,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutPlanExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateWorkoutPlan(final WorkoutPlan plan,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutPlan.handle(plan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updatePlanExercise(final WorkoutPlanExercise exercise,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutPlanExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deactivateAllPlans(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivateAllPlans.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeactivateAllPlans.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object setActivePlan(final long planId, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetActivePlan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, planId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfSetActivePlan.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object updateFavoriteStatus(final long planId, final boolean isFavorite,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavoriteStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, planId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfUpdateFavoriteStatus.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deletePlanDays(final long planId, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlanDays.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, planId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeletePlanDays.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deletePlanDayExercises(final long dayId,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlanDayExercises.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, dayId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeletePlanDayExercises.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<WorkoutPlan>> getAllWorkoutPlans() {
    final String _sql = "SELECT * FROM workout_plans ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_plans"}, new Callable<List<WorkoutPlan>>() {
      @Override
      public List<WorkoutPlan> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<WorkoutPlan> _result = new ArrayList<WorkoutPlan>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutPlan _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
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
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            _item = new WorkoutPlan(_tmpId,_tmpName,_tmpCreatedAt,_tmpIsActive,_tmpIsFavorite);
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
  public Flow<WorkoutPlan> getWorkoutPlanById(final long id) {
    final String _sql = "SELECT * FROM workout_plans WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_plans"}, new Callable<WorkoutPlan>() {
      @Override
      public WorkoutPlan call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final WorkoutPlan _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
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
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            _result = new WorkoutPlan(_tmpId,_tmpName,_tmpCreatedAt,_tmpIsActive,_tmpIsFavorite);
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
  public Flow<WorkoutPlan> getActiveWorkoutPlan() {
    final String _sql = "SELECT * FROM workout_plans WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_plans"}, new Callable<WorkoutPlan>() {
      @Override
      public WorkoutPlan call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final WorkoutPlan _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
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
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            _result = new WorkoutPlan(_tmpId,_tmpName,_tmpCreatedAt,_tmpIsActive,_tmpIsFavorite);
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
  public Flow<List<WorkoutPlan>> getFavoritePlans() {
    final String _sql = "SELECT * FROM workout_plans WHERE isFavorite = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_plans"}, new Callable<List<WorkoutPlan>>() {
      @Override
      public List<WorkoutPlan> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<WorkoutPlan> _result = new ArrayList<WorkoutPlan>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutPlan _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
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
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            _item = new WorkoutPlan(_tmpId,_tmpName,_tmpCreatedAt,_tmpIsActive,_tmpIsFavorite);
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
  public Object getFavoriteCount(final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(*) FROM workout_plans WHERE isFavorite = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<WorkoutPlanDay>> getPlanDays(final long planId) {
    final String _sql = "SELECT * FROM workout_plan_days WHERE planId = ? ORDER BY dayOfWeek ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, planId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_plan_days"}, new Callable<List<WorkoutPlanDay>>() {
      @Override
      public List<WorkoutPlanDay> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfDayName = CursorUtil.getColumnIndexOrThrow(_cursor, "dayName");
          final List<WorkoutPlanDay> _result = new ArrayList<WorkoutPlanDay>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutPlanDay _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpDayName;
            if (_cursor.isNull(_cursorIndexOfDayName)) {
              _tmpDayName = null;
            } else {
              _tmpDayName = _cursor.getString(_cursorIndexOfDayName);
            }
            _item = new WorkoutPlanDay(_tmpId,_tmpPlanId,_tmpDayOfWeek,_tmpDayName);
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
  public Flow<WorkoutPlanDay> getPlanDayById(final long dayId) {
    final String _sql = "SELECT * FROM workout_plan_days WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, dayId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_plan_days"}, new Callable<WorkoutPlanDay>() {
      @Override
      public WorkoutPlanDay call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanId = CursorUtil.getColumnIndexOrThrow(_cursor, "planId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfDayName = CursorUtil.getColumnIndexOrThrow(_cursor, "dayName");
          final WorkoutPlanDay _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanId;
            _tmpPlanId = _cursor.getLong(_cursorIndexOfPlanId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpDayName;
            if (_cursor.isNull(_cursorIndexOfDayName)) {
              _tmpDayName = null;
            } else {
              _tmpDayName = _cursor.getString(_cursorIndexOfDayName);
            }
            _result = new WorkoutPlanDay(_tmpId,_tmpPlanId,_tmpDayOfWeek,_tmpDayName);
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
  public Flow<List<WorkoutPlanExercise>> getPlanDayExercises(final long dayId) {
    final String _sql = "SELECT * FROM workout_plan_exercises WHERE planDayId = ? ORDER BY orderIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, dayId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"workout_plan_exercises"}, new Callable<List<WorkoutPlanExercise>>() {
      @Override
      public List<WorkoutPlanExercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlanDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "planDayId");
          final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseId");
          final int _cursorIndexOfSets = CursorUtil.getColumnIndexOrThrow(_cursor, "sets");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfRestSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "restSeconds");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final List<WorkoutPlanExercise> _result = new ArrayList<WorkoutPlanExercise>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WorkoutPlanExercise _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlanDayId;
            _tmpPlanDayId = _cursor.getLong(_cursorIndexOfPlanDayId);
            final long _tmpExerciseId;
            _tmpExerciseId = _cursor.getLong(_cursorIndexOfExerciseId);
            final int _tmpSets;
            _tmpSets = _cursor.getInt(_cursorIndexOfSets);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final int _tmpRestSeconds;
            _tmpRestSeconds = _cursor.getInt(_cursorIndexOfRestSeconds);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            _item = new WorkoutPlanExercise(_tmpId,_tmpPlanDayId,_tmpExerciseId,_tmpSets,_tmpReps,_tmpRestSeconds,_tmpOrderIndex);
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
