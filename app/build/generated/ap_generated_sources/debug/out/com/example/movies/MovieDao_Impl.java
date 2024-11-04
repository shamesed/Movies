package com.example.movies;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import io.reactivex.rxjava3.core.Completable;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Void;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MovieDao_Impl implements MovieDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Movie> __insertionAdapterOfMovie;

  private final SharedSQLiteStatement __preparedStmtOfRemoveMovie;

  public MovieDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovie = new EntityInsertionAdapter<Movie>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `favourite_movies` (`id`,`name`,`description`,`year`,`url`,`kp`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Movie entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        statement.bindLong(4, entity.getYear());
        final Poster _tmpPoster = entity.getPoster();
        if (_tmpPoster != null) {
          if (_tmpPoster.getUrl() == null) {
            statement.bindNull(5);
          } else {
            statement.bindString(5, _tmpPoster.getUrl());
          }
        } else {
          statement.bindNull(5);
        }
        final Rating _tmpRating = entity.getRating();
        if (_tmpRating != null) {
          statement.bindDouble(6, _tmpRating.getKp());
        } else {
          statement.bindNull(6);
        }
      }
    };
    this.__preparedStmtOfRemoveMovie = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favourite_movies WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Completable insertMovie(final Movie movie) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      @Nullable
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMovie.insert(movie);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public Completable removeMovie(final int movieId) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      @Nullable
      public Void call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveMovie.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, movieId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return null;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfRemoveMovie.release(_stmt);
        }
      }
    });
  }

  @Override
  public LiveData<List<Movie>> getAllFavouriteMovies() {
    final String _sql = "SELECT * FROM favourite_movies";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"favourite_movies"}, false, new Callable<List<Movie>>() {
      @Override
      @Nullable
      public List<Movie> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfKp = CursorUtil.getColumnIndexOrThrow(_cursor, "kp");
          final List<Movie> _result = new ArrayList<Movie>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Movie _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
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
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final Poster _tmpPoster;
            if (!(_cursor.isNull(_cursorIndexOfUrl))) {
              final String _tmpUrl;
              if (_cursor.isNull(_cursorIndexOfUrl)) {
                _tmpUrl = null;
              } else {
                _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
              }
              _tmpPoster = new Poster(_tmpUrl);
            } else {
              _tmpPoster = null;
            }
            final Rating _tmpRating;
            if (!(_cursor.isNull(_cursorIndexOfKp))) {
              final double _tmpKp;
              _tmpKp = _cursor.getDouble(_cursorIndexOfKp);
              _tmpRating = new Rating(_tmpKp);
            } else {
              _tmpRating = null;
            }
            _item = new Movie(_tmpId,_tmpName,_tmpDescription,_tmpYear,_tmpPoster,_tmpRating);
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
  public LiveData<Movie> getFavouriteMovie(final int movieId) {
    final String _sql = "SELECT * FROM favourite_movies WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, movieId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"favourite_movies"}, false, new Callable<Movie>() {
      @Override
      @Nullable
      public Movie call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfKp = CursorUtil.getColumnIndexOrThrow(_cursor, "kp");
          final Movie _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
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
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final Poster _tmpPoster;
            if (!(_cursor.isNull(_cursorIndexOfUrl))) {
              final String _tmpUrl;
              if (_cursor.isNull(_cursorIndexOfUrl)) {
                _tmpUrl = null;
              } else {
                _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
              }
              _tmpPoster = new Poster(_tmpUrl);
            } else {
              _tmpPoster = null;
            }
            final Rating _tmpRating;
            if (!(_cursor.isNull(_cursorIndexOfKp))) {
              final double _tmpKp;
              _tmpKp = _cursor.getDouble(_cursorIndexOfKp);
              _tmpRating = new Rating(_tmpKp);
            } else {
              _tmpRating = null;
            }
            _result = new Movie(_tmpId,_tmpName,_tmpDescription,_tmpYear,_tmpPoster,_tmpRating);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
