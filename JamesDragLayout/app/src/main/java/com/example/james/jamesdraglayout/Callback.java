package com.example.james.jamesdraglayout;

/**
 * Created by James.Shi on 9/26/14.
 */
public interface Callback {
    void onBefore();

    boolean onRun();

    void onAfter(boolean b);
}
