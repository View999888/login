package com.wkx.dragondemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.wkx.base.BaseMvpActivity;
import com.wkx.model.ComnomHomeMdoel;

public class HomeActivity extends BaseMvpActivity<ComnomHomeMdoel> {

    public NavController navController;

    @Override
    protected ComnomHomeMdoel setModel() {
        return null;
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupView() {
        navController = Navigation.findNavController(this, R.id.project_fragment_control);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] objects) {

    }
}