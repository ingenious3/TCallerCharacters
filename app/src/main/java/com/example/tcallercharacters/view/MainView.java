package com.example.tcallercharacters.view;

public interface MainView {
    void showResult(String val10thChar, String valEvery10thChar, String wordCounts);
    void showProgress();
    void hideProgress();
    void showError();
}
