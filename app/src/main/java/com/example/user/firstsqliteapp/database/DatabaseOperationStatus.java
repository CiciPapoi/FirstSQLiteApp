package com.example.user.firstsqliteapp.database;

import java.util.ArrayList;

/**
 * Interface used as callback for the worker thread. This interface will notify the caller when the thread that handle
 * database operations completes its job.

 */
public interface DatabaseOperationStatus<T> {
    void onComplete(T result);
    void onComplete(ArrayList<T> result);

    void onError(Throwable error);
}
