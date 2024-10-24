package biz.takagi.mvpsample.screen.main

import android.os.Handler
import android.os.Looper
import android.util.Log
import biz.takagi.mvpsample.base_mvp.Contract
import java.util.Timer

class MainPresenter(private val view: Contract.View): Contract.Presenter {

    /**Viewからの実行メソッド（リスナーからPresenterに対して処理を要求）**/
    override fun start() {
        Log.d("MainPresenter", "start()")
        view.showLoading()
        val thisPresenter = this
        Handler(Looper.getMainLooper()).postDelayed({
            // 5秒後に実行したい処理
            view.presenter = thisPresenter
            view.hideLoading()
        }, 5000)
    }

    override fun onClickTextButton(text: String) {
        /**TODO　本来ならここにロジックを書く**/
        view.showTextView(text)
    }

    override fun onClickNavButton(text: String) {
        /**TODO　本来ならここにロジックを書く**/
        view.navigateToNextScreen()
    }
}