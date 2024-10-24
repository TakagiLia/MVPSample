package biz.takagi.mvpsample.screen.main

import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import biz.takagi.mvpsample.R
import biz.takagi.mvpsample.base_mvp.Contract
import biz.takagi.mvpsample.databinding.FragmentMainBinding
import biz.takagi.mvpsample.screen.sub.SubFragment
import biz.takagi.mvpsample.screen.sub.SubPresenter

class MainFragment: Fragment(), Contract.View {

//    private lateinit var nfcAdapter: NfcAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override lateinit var presenter: Contract.Presenter

    /**Presenterからの実行メソッドを定義（画面にPresenterでの処理結果を返す）**/
    override fun showTextView(text: String) {
        binding.helloWorld.text = text
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.apply {
            textChangeActionButton.visibility = View.GONE
            navigationActionButton.visibility = View.GONE
            editText.visibility = View.GONE
            helloWorld.visibility = View.GONE
        }
    }

    override fun hideLoading() {
        binding.progressBar.visibility =  View.GONE
        binding.apply {
            textChangeActionButton.visibility = View.VISIBLE
            navigationActionButton.visibility = View.VISIBLE
            editText.visibility = View.VISIBLE
            helloWorld.visibility = View.VISIBLE
        }
    }

    override fun navigateToNextScreen() {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val subFragment = SubFragment()
        fragmentTransaction.replace(R.id.fragment_container2, subFragment)
        fragmentTransaction.commit()
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun errorShow() {
        TODO("Not yet implemented")
    }

    private fun initPresenter() {
        if (::presenter.isInitialized) {
            return
        }
        presenter = MainPresenter(this)
    }

    /**Fragmentのライフサイクル**/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("--NFC", "Fragment onCreate")
        /**NFCアダプターの取得: 上記のコードで nfcAdapter を取得**/
        /**Contextの取得: Fragment内でgetSystemService()メソッドを使用するには、requireContext() または requireActivity().getSystemService() を使用してContextを取得**/
//        val nfcManager = requireContext().getSystemService(Context.NFC_SERVICE) as NfcManager
//        nfcAdapter = nfcManager.defaultAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("--NFC", "Fragment onCreateView")
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onResume() {
        super.onResume()
        Log.d("--NFC", "Fragment onResume")
        // NFCのForeground Dispatch Systemを利用する場合、ActivityでenableForegroundDispatch()を呼び出す
//        nfcAdapter.let { adapter ->
//            (requireActivity() as? MainActivity)?.enableForegroundDispatch(this, adapter)
//        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("--NFC", "Fragment onPause")
        // NFCのForeground Dispatch Systemを利用する場合、ActivityでdisableForegroundDispatch()を呼び出す
//        nfcAdapter.let { adapter ->
//            (requireActivity() as? MainActivity)?.disableForegroundDispatch(this, adapter)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("--NFC", "Fragment onCreateView")
        /**起動時にpresenterをインスタンス化**/
        initPresenter()

        binding.apply {
            textChangeActionButton
            .setOnClickListener{
                presenter.onClickTextButton(editText.text.toString())
            }

            navigationActionButton.setOnClickListener {
                presenter.onClickNavButton("")
            }
        }
        /**presenter.startを呼び出す**/
        presenter.start()
    }

    fun onNfcTagDiscovered(tag: Tag) {
        Log.d("--NFC", "Fragment onNfcTagDiscovered")
        Log.d("onNfcTagDiscovered", tag.id.toString())
        val ndef = Ndef.get(tag)
        Log.d("onNfcTagDiscovered", "${ndef}")
        if (ndef != null) {
            try {
                ndef.connect()
                val ndefMessage = ndef.ndefMessage
                val record = ndefMessage.records[0]
                val text = String(record.payload) // タグからテキストデータを読み取る
                Log.d("onNfcTagDiscovered", text)
//                showTextView(tag.id.toString()) // TextViewに表示する
                presenter.onClickTextButton(tag.id.toString())
            } catch (e: Exception) {
                // エラー処理
            } finally {
                try {
                    ndef.close()
                } catch (e: Exception) {
                    // エラー処理
                }
            }
        }
    }

}