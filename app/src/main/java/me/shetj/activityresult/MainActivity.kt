package me.shetj.activityresult

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import me.shetj.activity.pickContact
import me.shetj.activity.selectFile
import me.shetj.activity.selectMultipleFile
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
                }
            }
        }
    }
}