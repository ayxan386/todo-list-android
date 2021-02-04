package com.jsimplec.todolist.callback;

import android.os.Parcelable;

public interface StartActivityCallBack<T extends Parcelable> {
    void startActivity(Class<?> activity, T data);
}
