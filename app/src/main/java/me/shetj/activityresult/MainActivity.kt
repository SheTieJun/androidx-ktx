package me.shetj.activityresult

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.shetj.activity.CropImage
import me.shetj.activity.createFile
import me.shetj.activity.createSimDialog
import me.shetj.activity.cropImage
import me.shetj.activity.hideSoftKeyboard
import me.shetj.activity.isVisibleKeyBoard
import me.shetj.activity.lifeScope
import me.shetj.activity.pickContact
import me.shetj.activity.selectFile
import me.shetj.activity.selectMultipleFile
import me.shetj.activity.setAppearance
import me.shetj.activity.showSoftKeyboard
import me.shetj.activity.startRequestPermission
import me.shetj.activity.takePicture
import me.shetj.activity.takeVideo
import me.shetj.activityresult.databinding.ActivityMainBinding
import me.shetj.activityresult.databinding.LayoutDialogTestBinding
import me.shetj.base.ktx.launch
import me.shetj.base.ktx.logI
import me.shetj.base.ktx.showToast
import me.shetj.datastore.dataStoreKit

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val dataStoreKit by lazy { dataStoreKit() }
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setAppearance(true)

        launch {
            dataStoreKit.get(key = "int",1)
                .onEach {
                    it.toString().logI("dataStoreKit")
                }
                .collect()

        }
        launch {
            dataStoreKit.getFirst(key = "int",1).toString().logI("dataStoreKit:getSync")
        }

        mainBinding.pickContact.setOnClickListener {
            pickContact {
                Log.i(TAG, it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }

        mainBinding.selectFile.setOnClickListener {
            selectMultipleFile {
                Log.i(TAG, it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.creatFile.setOnClickListener {
            createFile("test.png") {
                Log.i(TAG, it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.takeVideo.setOnClickListener {
            takeVideo {
                Log.i(TAG, it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.perimission.setOnClickListener {
            startRequestPermission(permission = READ_EXTERNAL_STORAGE) {
                Log.i(TAG, it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.selectPic.setOnClickListener {
            selectFile {
                Log.i(TAG, it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.takePic.setOnClickListener {
            takePicture {
                Log.i(TAG, it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                    cropImage(CropImage(it, 16, 9)) { uri ->
                        if (uri != null) {
                            Log.i(TAG, uri.toString())
                            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        mainBinding.dialog.setOnClickListener {
            createSimDialog<LayoutDialogTestBinding>(onViewCreated = {

            }, setWindowSizeChange = { dialog, window ->
                dialog.context.lifeScope?.launch {
                    "已通过dialog的context获取到AppCompatActivity的lifeScope".showToast()
                }
            })
        }

        mainBinding.softKeyBord.setOnClickListener {
            if (isVisibleKeyBoard()) {
                hideSoftKeyboard()
            } else {
                showSoftKeyboard()
            }
        }

        mainBinding.DataStore.setOnClickListener {
            launch {
                dataStoreKit.save(key = "int",i++)
            }
        }
    }
}