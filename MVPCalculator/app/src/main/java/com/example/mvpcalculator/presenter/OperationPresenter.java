package com.example.mvpcalculator.presenter;

import com.example.mvpcalculator.interfaces.OperationContract;
import com.example.mvpcalculator.model.OperationModel;
import com.example.mvpcalculator.OperationActivityView;

public class OperationPresenter implements OperationContract.OperationPresenter {

    private OperationContract.OperationView view;
    private OperationContract.OperationModel model;

    public OperationPresenter(OperationActivityView view) {
        this.view = view;
        this.model = new OperationModel(this);
    }

    @Override
    public void showResult(String result) {
        if (view != null) {
            view.showResult(result);
        }
    }

    @Override
    public void invalidOperation() {
        if (view != null) {
            view.invalidOperation();
        }
    }

    @Override
    public void add(int num1, int num2) {
        if (model != null) {
            model.add(num1, num2);
        }
    }

    @Override
    public void subtract(int num1, int num2) {
        if (model != null) {
            model.subtract(num1, num2);
        }
    }

    @Override
    public void multiply(int num1, int num2) {
        if (model != null) {
            model.multiply(num1, num2);
        }
    }

    @Override
    public void divide(int num1, int num2) {
        if (model != null) {
            model.divide(num1, num2);
        }
    }
}
