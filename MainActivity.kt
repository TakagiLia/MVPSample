package biz.takagi.mvpsample

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcF
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import biz.takagi.mvpsample.screen.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var nfcAdapterA: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
//      nfcAdapterA = nfcManager.defaultAdapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mainFragment = MainFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container2, mainFragment)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        Log.d("--NFC", "Activity onResume")
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
//        nfcAdapterA.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        Log.d("--NFC", "Activity onPause")
//        nfcAdapterA.disableForegroundDispatch(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("--NFC", "Activity　onNewIntent")
//        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
//            Log.d("--NFC", "ACTION_NDEF_DISCOVERED")
////            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
//            val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
//            val fragment = supportFragmentManager.findFragmentById(R.id.main) as? MainFragment
//            fragment?.onNfcTagDiscovered(tag!!) // Fragmentにタグ情報を渡す
//        }else{
//            Log.d("--NFC", "Not ACTION_NDEF_DISCOVERED")
//        }
    }

    fun enableForegroundDispatch(fragment: MainFragment, adapter: NfcAdapter) {
        /**NFCイベントが発生した際に起動するアクティビティを指定するためのPendingIntentを作成**/
        /**
         * 第1引数 this:Context を渡す。ここでは Activity 自身
         * 第2引数 0:PendingIntent を識別するためのリクエストコードで。同じPendingIntentを複数作成する場合に、異なる値を指定。
         * 第3引数 Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) は、PendingIntent が起動するアクティビティを指定する Intent
         * 第4引数 PendingIntent.FLAG_MUTABLE:Android 12 (API レベル 31) 以降で必要になったフラグ。PendingIntent の変更可能性を指定**/
        /**Intent.FLAG_ACTIVITY_SINGLE_TOP を指定するのは、NFCタグを読み取った際に、既にアクティビティが起動している場合は新しいインスタンスを作成せず、既存のインスタンスを再利用するため**/
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE  //API レベル31以降ではFLAG_MUTABLE を指定
        )
        /**NDEF形式のデータを含むNFCタグを検出した際にイベントを受け取るようにIntentFilterを作成**/
        val intentFilters = arrayOf(IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED))
        /**NFC-F (FeliCa) タグを検出した際にイベントを受け取るように TechList を作成**/
        /**TechListは、NFCタグの技術方式を指定**/
        val techLists = arrayOf(arrayOf(NfcF::class.java.name))
        /**ForegroundDispatchを有効**/
        /**アプリがフォアグラウンドにある時にNFCタグの検出イベントを優先的に受け取ることが可能。これにより、他のNFC対応アプリが起動してしまうことを防ぐ**/
        /** 第1引数 this：Foreground Dispatchを有効にするアクティビティ
            第2引数 pendingIntent：NFCイベントが発生した際に起動するアクティビティを指定するPendingIntent
            第3引数 intentFilters：受け取るNFCイベントの種類を指定するIntentFilterの配列
            第4引数 techLists：NFCタグの技術方式を指定するTechListの配列**/
        adapter.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists)
    }
    fun disableForegroundDispatch(fragment: MainFragment, adapter: NfcAdapter) {
        adapter.disableForegroundDispatch(this)
    }



}