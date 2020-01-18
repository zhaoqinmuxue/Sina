package org.aoli.weibo.delegates;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDelegate extends Fragment{
    private Unbinder mUnbinder;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        Object object = setLayout();
        if (object instanceof Integer) {
            rootView = inflater.inflate((int) object, container, false);
        } else if (object instanceof View) {
            rootView = (View) object;
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        Log.d("Aoli",toString() + " onCreateView");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
        Log.d("Aoli",toString() + " onDestroyView");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Aoli",toString() + " onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Aoli",toString() + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Aoli",toString() + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Aoli",toString() + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Aoli",toString() + " onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Aoli",toString() + " onDestroy");
    }
}
