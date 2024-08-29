package me.shetj.activityresult

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.VideoOnly
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.shetj.activity.CropImage
import me.shetj.activity.FileQUtils
import me.shetj.activity.createFile
import me.shetj.activity.createSimDialog
import me.shetj.activity.cropImage
import me.shetj.activity.hideSoftKeyboard
import me.shetj.activity.immerse
import me.shetj.activity.isVisibleKeyBoard
import me.shetj.activity.lifeScope
import me.shetj.activity.pickContact
import me.shetj.activity.pickMultipleVisualMedia
import me.shetj.activity.pickVisualMedia
import me.shetj.activity.selectFile
import me.shetj.activity.selectMultipleFile
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
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val dataStoreKit by lazy { dataStoreKit() }
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        immerse(Type.statusBars(), statusIsBlackText = true, navigationIsBlackLine = true, color =  Color.RED)
        launch {
            dataStoreKit.get(key = "int", -1)
                .onEach {
                    it.toString().logI("dataStoreKit:get:$it")
                }
                .collect()

        }

        launch {
            dataStoreKit.get(key = "int2", -1)
                .onEach {
                    it.toString().logI("dataStoreKit2:get:$it")
                }
                .collect()
        }

        launch {
            dataStoreKit.getFirst(key = "int", -1).toString().logI("dataStoreKit:getFirst")
        }

        mainBinding.pickContact.setOnClickListener {
            pickContact {
                Timber.i(it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }

        mainBinding.selectFile.setOnClickListener {
            selectMultipleFile {
                Timber.i(it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.creatFile.setOnClickListener {
            createFile("test.png") {
                Timber.i(it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.takeVideo.setOnClickListener {
            takeVideo {
                Timber.i(it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.perimission.setOnClickListener {
            startRequestPermission(permission = READ_EXTERNAL_STORAGE) {
                Timber.i(it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.selectPic.setOnClickListener {
            selectFile {
                Timber.i(it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.takePic.setOnClickListener {
            takePicture {
                Timber.i(it.toString())
                if (it != null) {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                    cropImage(CropImage(it, 16, 9)) { uri ->
                        if (uri != null) {
                            Timber.tag(TAG).i(uri.toString())
                            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        mainBinding.dialog.setOnClickListener {
            createSimDialog<LayoutDialogTestBinding>(onBeforeShow = { _, _ ->

            }, onViewCreated = { _, _ ->

            }, setWindowSizeChange = { dialog, _ ->
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


        mainBinding.pickVisualMedia.setOnClickListener {
            if (!isPhotoPickerAvailable()) return@setOnClickListener
            pickVisualMedia(PickVisualMediaRequest(VideoOnly)) { uri ->
                if (uri != null) {
                    Timber.i(uri.toString())
                    Timber.i(FileQUtils.getFileAbsolutePath(this, uri))
                    Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.pickMultiVisualMedia.setOnClickListener {
            if (!isPhotoPickerAvailable()) return@setOnClickListener
            pickMultipleVisualMedia(PickVisualMediaRequest(ImageOnly)) { uri ->
                if (uri != null) {
                    Timber.i(uri.toString())
                    Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.DataStore.setOnClickListener {
            if (i == 2) {
                dataStoreKit.getFirstBlock("int", -1).toString().logI("dataStoreKit:getFirstBlock")
                i++
                return@setOnClickListener
            }
            if (i == 4) {
                dataStoreKit.saveBlock("int", i++)
                "$i".logI("dataStoreKit:saveBlock")
                i = 0
                return@setOnClickListener
            }
            launch {
                if (i > 3) {
                    dataStoreKit.remove<Int>(key = "int")
                    i++
                } else {
                    dataStoreKit.save(key = "int", i++)
                }
            }
        }

        mainBinding.immerse1.setOnClickListener {
            immerse(type = Type.statusBars(), statusIsBlackText = true, color = Color.BLUE)
        }
        mainBinding.immerse2.setOnClickListener {
            immerse(type = Type.navigationBars(), navigationIsBlackLine = true, color = Color.BLUE)
        }
        mainBinding.immerse3.setOnClickListener {
            immerse(type = Type.systemBars(), navigationIsBlackLine = true, color = Color.BLUE)
        }


    }

    fun isPhotoPickerAvailable(): Boolean {
        return VERSION.SDK_INT >= VERSION_CODES.TIRAMISU
    }
}