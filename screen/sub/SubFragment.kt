package biz.takagi.mvpsample.screen.sub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import biz.takagi.mvpsample.base_mvp.Contract
import biz.takagi.mvpsample.databinding.FragmentSubBinding
import biz.takagi.mvpsample.screen.main.MainPresenter

class SubFragment() : Fragment(), Contract.View {

    private var _binding: FragmentSubBinding? = null
    private val binding get() = _binding!!
    override lateinit var presenter: Contract.Presenter

    /**Presenterからの実行メソッドを定義（画面にPresenterでの処理結果を返す）**/
    override fun showTextView(text: String) {
        binding.apply {
            nameTextView.text = "名前："
            ageTextView.text = "年齢："
            birthdayTextView.text ="誕生日："
        }
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun navigateToNextScreen() {
        TODO("Not yet implemented")
    }

    override fun errorShow() {
        TODO("Not yet implemented")
    }

    private fun initPresenter() {
        if (::presenter.isInitialized) {
            return
        }
        presenter = SubPresenter(this)
    }

    /**Fragmentのライフサイクル**/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("--NFC", "SubFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("--NFC", "Fragment onCreateView")
        _binding = FragmentSubBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        binding.apply {
            textEditButton.setOnClickListener {
                presenter.onClickTextButton("")
            }
        }
    }
}