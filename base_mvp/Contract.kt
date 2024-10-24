package biz.takagi.mvpsample.base_mvp

interface Contract {
    interface View : BaseView<Presenter> {
        /**textViewをテキストを表示するメソッド**/
        fun showTextView (text: String)
        /**ローディングを表示するメソッド**/
        fun showLoading()
        /**ローディングを消すメソッド**/
        fun hideLoading()
        /**遷移をして現在の画面を消すメソッド**/
        fun navigateToNextScreen()
        /**ボタン実行時にエラーだったら画面にその内容を表示させるメソッド**/
        fun errorShow()
    }

    interface Presenter: BasePresenter {

        /**TODO Buttonは複数あるのでボタンID渡すまたはボタン増えたらインタフェースメソッド増やす**/

        /**Actionを行うButtonをクリックされた際に使用するメソッド**/
        fun onClickTextButton(text: String)
        /**遷移するButtonをクリックされた際に使用するメソッド**/
        fun onClickNavButton(text: String)
    }
}