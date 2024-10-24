package biz.takagi.mvpsample.screen.sub

import biz.takagi.mvpsample.base_mvp.Contract

class SubPresenter(private val view: Contract.View) : Contract.Presenter  {

    /**Viewからの実行メソッド（リスナーからPresenterに対して処理を要求）**/
    override fun onClickTextButton(text: String) {
        /**TODO　本来ならここにロジックを書く**/
        view.showTextView("")
    }

    override fun onClickNavButton(text: String) {
        /**TODO　本来ならここにロジックを書く**/
    }

    override fun start() {
        /**TODO　本来ならここにロジックを書く**/
    }
}