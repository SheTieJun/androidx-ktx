package me.shetj.activityresult

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.WindowCompat
import me.shetj.activity.CropImage
import me.shetj.activity.createFile
import me.shetj.activity.cropImage
import me.shetj.activity.pickContact
import me.shetj.activity.selectFile
import me.shetj.activity.selectMultipleFile
import me.shetj.activity.setAppearance
import me.shetj.activity.startRequestPermission
import me.shetj.activity.takePicture
import me.shetj.activity.takeVideo
import me.shetj.activityresult.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setAppearance(true)

        mainBinding.pickContact.setOnClickListener {
            pickContact{
                Log.i(TAG,it.toString())
                if (it!= null){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                }

            }
        }

        mainBinding.selectFile.setOnClickListener {
            selectMultipleFile {
                Log.i(TAG,it.toString())
                if (it!= null){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.creatFile.setOnClickListener {
            createFile("test.png"){
                Log.i(TAG,it.toString())
                if (it!= null){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.takeVideo.setOnClickListener {
            takeVideo {
                Log.i(TAG,it.toString())
                if (it!= null){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.perimission.setOnClickListener {
            startRequestPermission(permission = READ_EXTERNAL_STORAGE){
                Log.i(TAG,it.toString())
                if (it!= null){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.selectPic.setOnClickListener {
            selectFile {
                Log.i(TAG,it.toString())
                if (it!= null){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

        mainBinding.takePic.setOnClickListener {
            takePicture{
                Log.i(TAG,it.toString())
                if (it!= null){
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                    cropImage(CropImage(it,16,9)){
                        if (it!= null){
                            Log.i(TAG,it.toString())
                            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}