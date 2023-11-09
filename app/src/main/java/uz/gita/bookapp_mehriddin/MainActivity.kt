package uz.gita.bookapp_mehriddin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        setupWithNavController(bottomNav, navController)

    }
}
//        */
//
/*   binding.cancell.setOnClickListener {
       myLog("clicked")
       checkPermissions(arrayListOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
           repastory.downloadFile()
               .onEach {
                   it.onSuccess {
                       myLog("Download")
                   }
                   it.onFailure {
                       myLog(it.message.toString())
                   }
               }
               .launchIn(scope)
       }
   }

   binding.img.setOnClickListener {
       checkPermissions(listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
           openGallarey()
       }
   }

   launcher =
       registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { callback ->
           callback?.data?.data?.let {
               myLog(it.toString())
               repastory.uploadImage(it)
                   .onEach {
                       it.onSuccess {uploadData->
                       when(uploadData){
                           UploadData.Pause->{"pause"}
                           UploadData.Complete->{
                               myLog("complete")
                           }
                           UploadData.Success->{                                    myLog("Succes") }
                           UploadData.Cancel->{
                               myLog("cancel")
                           }
                           is UploadData.Progress->{
                               myLog("progress : ${uploadData.progress}")
                           }
                       }

                       }
                       it.onFailure { myLog("xato ${it.message}") }
                   }
                   .launchIn(scope)
           }
       }
*/


/*  private fun openGallarey() {
      val intent = Intent(Intent.ACTION_GET_CONTENT)
      intent.type = "image/*"
      launcher.launch(intent)
  }*/
}

/*
      binding.img.setOnClickListener {
          myLog("click")
          repastory.getImage()
            .onEach {
                it.onSuccess {bitmap->
                  myLog("kirdi")
                  binding.img.setImageBitmap(bitmap)
                }
                it.onFailure {
                myLog(it.message.toString())
              }
            }
            .launchIn(scope)
      }
      */
/*  binding.img.setOnClickListener {
    repastory.getAllImage()
        .onEach {
            it.onSuccess {bitmaps->
                binding.img.setImageBitmap(bitmaps[0])
                binding.img2.setImageBitmap(bitmaps[1])
            }
            it.onFailure {
                myLog(it.message.toString())
            }
        }.launchIn(scope)
}*/

/*
*   Glide
              .with(this)
              .load("https://firebasestorage.googleapis.com/v0/b/book-app-be8ac.appspot.com/o/sample%2F20230214_200326.jpg?alt=media&token=d4026f66-d667-4dfd-bf6e-67e599047ad8")
              .centerCrop()
              .into(binding.img)*/