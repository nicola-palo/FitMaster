package com.fitmaster.app.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.fitmaster.app.data.dao.ExerciseDao;
import com.fitmaster.app.data.dao.ExerciseDao_Impl;
import com.fitmaster.app.data.dao.UserDao;
import com.fitmaster.app.data.dao.UserDao_Impl;
import com.fitmaster.app.data.dao.WorkoutPlanDao;
import com.fitmaster.app.data.dao.WorkoutPlanDao_Impl;
import com.fitmaster.app.data.dao.WorkoutSessionDao;
import com.fitmaster.app.data.dao.WorkoutSessionDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FitMasterDatabase_Impl extends FitMasterDatabase {
  private volatile ExerciseDao _exerciseDao;

  private volatile WorkoutPlanDao _workoutPlanDao;

  private volatile WorkoutSessionDao _workoutSessionDao;

  private volatile UserDao _userDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `exercises` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `technicalTips` TEXT NOT NULL, `category` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `muscleGroup` TEXT NOT NULL, `defaultSets` INTEGER NOT NULL, `defaultReps` INTEGER NOT NULL, `defaultRestSeconds` INTEGER NOT NULL, `imageUrl` TEXT, `videoUrl` TEXT, `isCustom` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `workout_plans` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `workout_plan_days` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `planId` INTEGER NOT NULL, `dayOfWeek` INTEGER NOT NULL, `dayName` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `workout_plan_exercises` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `planDayId` INTEGER NOT NULL, `exerciseId` INTEGER NOT NULL, `sets` INTEGER NOT NULL, `reps` INTEGER NOT NULL, `restSeconds` INTEGER NOT NULL, `orderIndex` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `workout_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `planId` INTEGER NOT NULL, `planDayId` INTEGER NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `durationSeconds` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `workout_session_exercises` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `exerciseId` INTEGER NOT NULL, `plannedSets` INTEGER NOT NULL, `completedSets` INTEGER NOT NULL, `plannedReps` INTEGER NOT NULL, `actualReps` INTEGER, `restSeconds` INTEGER NOT NULL, `completedAt` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `body_measurements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `weight` REAL NOT NULL, `bodyFatPercentage` REAL, `leanMass` REAL, `fatMass` REAL, `height` REAL, `chest` REAL, `waist` REAL, `biceps` REAL, `thighs` REAL, `notes` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user_profile` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `username` TEXT, `age` INTEGER NOT NULL, `sex` TEXT NOT NULL, `goal` TEXT NOT NULL, `targetWeight` REAL, `targetBodyFatPercentage` REAL, `profileImageUrl` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a0996661085ba83a20a77bf8e35aa2fb')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `exercises`");
        _db.execSQL("DROP TABLE IF EXISTS `workout_plans`");
        _db.execSQL("DROP TABLE IF EXISTS `workout_plan_days`");
        _db.execSQL("DROP TABLE IF EXISTS `workout_plan_exercises`");
        _db.execSQL("DROP TABLE IF EXISTS `workout_sessions`");
        _db.execSQL("DROP TABLE IF EXISTS `workout_session_exercises`");
        _db.execSQL("DROP TABLE IF EXISTS `body_measurements`");
        _db.execSQL("DROP TABLE IF EXISTS `user_profile`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsExercises = new HashMap<String, TableInfo.Column>(13);
        _columnsExercises.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("technicalTips", new TableInfo.Column("technicalTips", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("muscleGroup", new TableInfo.Column("muscleGroup", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("defaultSets", new TableInfo.Column("defaultSets", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("defaultReps", new TableInfo.Column("defaultReps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("defaultRestSeconds", new TableInfo.Column("defaultRestSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("videoUrl", new TableInfo.Column("videoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("isCustom", new TableInfo.Column("isCustom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExercises = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExercises = new TableInfo("exercises", _columnsExercises, _foreignKeysExercises, _indicesExercises);
        final TableInfo _existingExercises = TableInfo.read(_db, "exercises");
        if (! _infoExercises.equals(_existingExercises)) {
          return new RoomOpenHelper.ValidationResult(false, "exercises(com.fitmaster.app.data.entity.Exercise).\n"
                  + " Expected:\n" + _infoExercises + "\n"
                  + " Found:\n" + _existingExercises);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutPlans = new HashMap<String, TableInfo.Column>(5);
        _columnsWorkoutPlans.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlans.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutPlans = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkoutPlans = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkoutPlans = new TableInfo("workout_plans", _columnsWorkoutPlans, _foreignKeysWorkoutPlans, _indicesWorkoutPlans);
        final TableInfo _existingWorkoutPlans = TableInfo.read(_db, "workout_plans");
        if (! _infoWorkoutPlans.equals(_existingWorkoutPlans)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_plans(com.fitmaster.app.data.entity.WorkoutPlan).\n"
                  + " Expected:\n" + _infoWorkoutPlans + "\n"
                  + " Found:\n" + _existingWorkoutPlans);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutPlanDays = new HashMap<String, TableInfo.Column>(4);
        _columnsWorkoutPlanDays.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanDays.put("planId", new TableInfo.Column("planId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanDays.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanDays.put("dayName", new TableInfo.Column("dayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutPlanDays = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkoutPlanDays = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkoutPlanDays = new TableInfo("workout_plan_days", _columnsWorkoutPlanDays, _foreignKeysWorkoutPlanDays, _indicesWorkoutPlanDays);
        final TableInfo _existingWorkoutPlanDays = TableInfo.read(_db, "workout_plan_days");
        if (! _infoWorkoutPlanDays.equals(_existingWorkoutPlanDays)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_plan_days(com.fitmaster.app.data.entity.WorkoutPlanDay).\n"
                  + " Expected:\n" + _infoWorkoutPlanDays + "\n"
                  + " Found:\n" + _existingWorkoutPlanDays);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutPlanExercises = new HashMap<String, TableInfo.Column>(7);
        _columnsWorkoutPlanExercises.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanExercises.put("planDayId", new TableInfo.Column("planDayId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanExercises.put("exerciseId", new TableInfo.Column("exerciseId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanExercises.put("sets", new TableInfo.Column("sets", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanExercises.put("reps", new TableInfo.Column("reps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanExercises.put("restSeconds", new TableInfo.Column("restSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutPlanExercises.put("orderIndex", new TableInfo.Column("orderIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutPlanExercises = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkoutPlanExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkoutPlanExercises = new TableInfo("workout_plan_exercises", _columnsWorkoutPlanExercises, _foreignKeysWorkoutPlanExercises, _indicesWorkoutPlanExercises);
        final TableInfo _existingWorkoutPlanExercises = TableInfo.read(_db, "workout_plan_exercises");
        if (! _infoWorkoutPlanExercises.equals(_existingWorkoutPlanExercises)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_plan_exercises(com.fitmaster.app.data.entity.WorkoutPlanExercise).\n"
                  + " Expected:\n" + _infoWorkoutPlanExercises + "\n"
                  + " Found:\n" + _existingWorkoutPlanExercises);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutSessions = new HashMap<String, TableInfo.Column>(7);
        _columnsWorkoutSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("planId", new TableInfo.Column("planId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("planDayId", new TableInfo.Column("planDayId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("durationSeconds", new TableInfo.Column("durationSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessions.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkoutSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkoutSessions = new TableInfo("workout_sessions", _columnsWorkoutSessions, _foreignKeysWorkoutSessions, _indicesWorkoutSessions);
        final TableInfo _existingWorkoutSessions = TableInfo.read(_db, "workout_sessions");
        if (! _infoWorkoutSessions.equals(_existingWorkoutSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_sessions(com.fitmaster.app.data.entity.WorkoutSession).\n"
                  + " Expected:\n" + _infoWorkoutSessions + "\n"
                  + " Found:\n" + _existingWorkoutSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutSessionExercises = new HashMap<String, TableInfo.Column>(9);
        _columnsWorkoutSessionExercises.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("exerciseId", new TableInfo.Column("exerciseId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("plannedSets", new TableInfo.Column("plannedSets", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("completedSets", new TableInfo.Column("completedSets", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("plannedReps", new TableInfo.Column("plannedReps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("actualReps", new TableInfo.Column("actualReps", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("restSeconds", new TableInfo.Column("restSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSessionExercises.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutSessionExercises = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkoutSessionExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkoutSessionExercises = new TableInfo("workout_session_exercises", _columnsWorkoutSessionExercises, _foreignKeysWorkoutSessionExercises, _indicesWorkoutSessionExercises);
        final TableInfo _existingWorkoutSessionExercises = TableInfo.read(_db, "workout_session_exercises");
        if (! _infoWorkoutSessionExercises.equals(_existingWorkoutSessionExercises)) {
          return new RoomOpenHelper.ValidationResult(false, "workout_session_exercises(com.fitmaster.app.data.entity.WorkoutSessionExercise).\n"
                  + " Expected:\n" + _infoWorkoutSessionExercises + "\n"
                  + " Found:\n" + _existingWorkoutSessionExercises);
        }
        final HashMap<String, TableInfo.Column> _columnsBodyMeasurements = new HashMap<String, TableInfo.Column>(12);
        _columnsBodyMeasurements.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("weight", new TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("bodyFatPercentage", new TableInfo.Column("bodyFatPercentage", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("leanMass", new TableInfo.Column("leanMass", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("fatMass", new TableInfo.Column("fatMass", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("height", new TableInfo.Column("height", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("chest", new TableInfo.Column("chest", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("waist", new TableInfo.Column("waist", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("biceps", new TableInfo.Column("biceps", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("thighs", new TableInfo.Column("thighs", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBodyMeasurements.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBodyMeasurements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBodyMeasurements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBodyMeasurements = new TableInfo("body_measurements", _columnsBodyMeasurements, _foreignKeysBodyMeasurements, _indicesBodyMeasurements);
        final TableInfo _existingBodyMeasurements = TableInfo.read(_db, "body_measurements");
        if (! _infoBodyMeasurements.equals(_existingBodyMeasurements)) {
          return new RoomOpenHelper.ValidationResult(false, "body_measurements(com.fitmaster.app.data.entity.BodyMeasurement).\n"
                  + " Expected:\n" + _infoBodyMeasurements + "\n"
                  + " Found:\n" + _existingBodyMeasurements);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProfile = new HashMap<String, TableInfo.Column>(11);
        _columnsUserProfile.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("sex", new TableInfo.Column("sex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("goal", new TableInfo.Column("goal", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("targetWeight", new TableInfo.Column("targetWeight", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("targetBodyFatPercentage", new TableInfo.Column("targetBodyFatPercentage", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("profileImageUrl", new TableInfo.Column("profileImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfile = new TableInfo("user_profile", _columnsUserProfile, _foreignKeysUserProfile, _indicesUserProfile);
        final TableInfo _existingUserProfile = TableInfo.read(_db, "user_profile");
        if (! _infoUserProfile.equals(_existingUserProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profile(com.fitmaster.app.data.entity.UserProfile).\n"
                  + " Expected:\n" + _infoUserProfile + "\n"
                  + " Found:\n" + _existingUserProfile);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a0996661085ba83a20a77bf8e35aa2fb", "46cb3d52d2aa7c342800fcc0e7876bcc");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "exercises","workout_plans","workout_plan_days","workout_plan_exercises","workout_sessions","workout_session_exercises","body_measurements","user_profile");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `exercises`");
      _db.execSQL("DELETE FROM `workout_plans`");
      _db.execSQL("DELETE FROM `workout_plan_days`");
      _db.execSQL("DELETE FROM `workout_plan_exercises`");
      _db.execSQL("DELETE FROM `workout_sessions`");
      _db.execSQL("DELETE FROM `workout_session_exercises`");
      _db.execSQL("DELETE FROM `body_measurements`");
      _db.execSQL("DELETE FROM `user_profile`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ExerciseDao.class, ExerciseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkoutPlanDao.class, WorkoutPlanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkoutSessionDao.class, WorkoutSessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public ExerciseDao exerciseDao() {
    if (_exerciseDao != null) {
      return _exerciseDao;
    } else {
      synchronized(this) {
        if(_exerciseDao == null) {
          _exerciseDao = new ExerciseDao_Impl(this);
        }
        return _exerciseDao;
      }
    }
  }

  @Override
  public WorkoutPlanDao workoutPlanDao() {
    if (_workoutPlanDao != null) {
      return _workoutPlanDao;
    } else {
      synchronized(this) {
        if(_workoutPlanDao == null) {
          _workoutPlanDao = new WorkoutPlanDao_Impl(this);
        }
        return _workoutPlanDao;
      }
    }
  }

  @Override
  public WorkoutSessionDao workoutSessionDao() {
    if (_workoutSessionDao != null) {
      return _workoutSessionDao;
    } else {
      synchronized(this) {
        if(_workoutSessionDao == null) {
          _workoutSessionDao = new WorkoutSessionDao_Impl(this);
        }
        return _workoutSessionDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
