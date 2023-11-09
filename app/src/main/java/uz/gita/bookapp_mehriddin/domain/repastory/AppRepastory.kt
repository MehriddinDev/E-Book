package uz.gita.bookapp_mehriddin.domain.repastory

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uz.gita.bookapp_mehriddin.app.App
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.MyPref
import uz.gita.bookapp_mehriddin.data.source.local.room.database.MyDatabase
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.utils.ConnectInternet
import uz.gita.bookapp_mehriddin.utils.myLog
import java.io.File

@SuppressLint("SuspiciousIndentation")
class AppRepastory private constructor() {
    companion object {
        private var repastory: AppRepastory? = null

        fun getInstance(): AppRepastory {
            if (repastory == null) {
                repastory = AppRepastory()
            }
            return repastory!!
        }
    }

    init {
        insertBooks(" H.G.Wells","The Time Machine","the_time_machine","Classic",79,"https://firebasestorage.googleapis.com/v0/b/book-app-be8ac.appspot.com/o/images%2Fthe_time_machine.png?alt=media&token=acd67956-6857-4e95-918c-bd82d5049c94","This eBook is for the use of anyone anywhere at no cost and with\n" +
                "almost no restrictions whatsoever. You may copy it, give it away or\n" +
                "re-use it under the terms of the Project Gutenberg License included\n" +
                "with this eBook or online at www.gutenberg.net\n")

        insertBooks("Zachary Paul Chopchinski","Webley and The World Machine","webley_and_the_world_machine","Classic",315,"https://firebasestorage.googleapis.com/v0/b/book-app-be8ac.appspot.com/o/images%2Fwebley_and_the_world_machine.png?alt=media&token=5f71520c-5219-4d76-96c4-f7268f167481","There are so many things that I owe to so many people for all the\n" +
                "help that they generously gave to help me make this book all that\n" +
                "it became. To my lovely wife, never forget that I see you, and\n" +
                "always know all that you do for me. I will never be able to pay you\n" +
                "back for everything, but if it helps, I will give you all the love, fur babies, and cookie cake that you could ever ask for. As long as I\n" +
                "can have your love and all the tacos")
        insertBooks("","","","",0,"","")
        insertBooks("","","","",0,"","")
        insertBooks("","","","",0,"","")
        insertBooks("","","","",0,"","")
        insertBooks("","","","",0,"","")
        insertBooks("","","","",0,"","")
    }

    fun insertBooks(
        author: String,
        title: String,
        reference: String,
        genre: String,
        pages:Long,
        coverUrl: String,
        desc:String
    ): Flow<Result<Unit>> {
        return callbackFlow {
            val myMap = hashMapOf<String, Any>(
                "name" to reference,
                "author" to author,
                "imgUrl" to coverUrl,
                "genre" to genre,
                "title" to title,
                "pages" to pages,
                "desc" to desc
            )

            db.collection("books")
                .add(myMap)
                .addOnSuccessListener {
                    trySend(Result.success(Unit))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
            awaitClose()
        }
    }

    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private val pref = MyPref.getInstance()
    private val dao = MyDatabase.getInstance().getBookDao()

   /* fun savePageCount(page:Int){
        pref.savePageCount(page)
    }

    fun getPagecount():Int{
        return pref.getPageCount()
    }*/

    // clean arxitektura


init {

    if(pref.getSaveFirst() && ConnectInternet(App.context)){
        myLog("first","ASD")
      val result = GetAllBooksFirstTime()
        result.onSuccess {
            pref.saveFirst(false)
        }
    }
}
// clean arxitekturada emas
    fun GetAllBooksFirstTime():Result<Int>{
    var succes = 0
        db.collection("books")
            .get()
            .addOnSuccessListener { allDocuments ->
                val list = ArrayList<BookEntity>()
                allDocuments.forEach {
                    val bookData = BookEntity(
                        it.get("name") as String,
                        it.get("author") as String,
                        it.get("title") as String,
                        it.get("imgUrl") as String,
                        it.get("desc") as String,
                        false,
                        it.get("pages") as Long,
                        0,
                        it.get("genre") as String
                    )
                    list.add(bookData)
                    dao.addAll(list)
                    succes = allDocuments.size()
                }

            }
            .addOnFailureListener { succes = -1 }
        return if (succes != -1) Result.success(succes) else Result.failure(Exception("xato"))
    }
//done
    fun getBooksByCategory(categoryName: String): Flow<Result<List<BookData>>> = callbackFlow {
        db.collection("books")
            .whereEqualTo("genre", categoryName).get()
            .addOnSuccessListener { allDocuments ->
                val list = ArrayList<BookData>()
                allDocuments.forEach {
                    val bookData = BookData(
                        it.get("name") as String,
                        it.get("author") as String,
                        it.get("title") as String,
                        it.get("imgUrl") as String,
                        it.get("desc") as String,
                        false,
                        it.get("pages") as Long,
                        0,
                        it.get("genre") as String
                    )
                    list.add(bookData)
                }
                trySend(Result.success(list))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }
//done
    suspend fun getAllBook():Result<List<BookData>>
            = withContext(Dispatchers.IO) {
        try {
            val result = db.collection("books")
                .get()
                .await()
            val list = ArrayList<BookData>()
            result.documents.forEach {
                val bookData = BookData(
                    it.get("name") as String,
                    it.get("author") as String,
                    it.get("title") as String,
                    it.get("imgUrl") as String,
                    it.get("desc") as String,
                    false,
                    it.get("pages") as Long,
                    0,
                    it.get("genre") as String
                )
                Log.d("NNN", bookData.name)
                list.add(bookData)
            }
            return@withContext Result.success(list)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }

    }
//done
   suspend fun downloadfayl1(context: Context, book: BookData):Result<String> = withContext(Dispatchers.IO){
        val file = File(context.filesDir,book.name)
    myLog(file.exists().toString()+"repa","GGG")
        if(file.exists()){
            return@withContext Result.success(book.name)
        }else{
            try {
                val result = storage.reference.child("books/${book.name}.pdf")
                    .getFile(file).await()
                if(result.task.isSuccessful) {
                   // saveSavedBook(book)
                    return@withContext Result.success(book.name)
                } else{
                    return@withContext result.task.exception?.let { Result.failure(it) } ?: Result.failure(Exception("Couldn't download"))
                }
            }catch (e:Exception){
                Result.failure(e)
            }

        }
    }

    fun downloadFile(context: Context, book: BookData): Flow<Result<File>> = callbackFlow {
        // val temp = File.createTempFile("books","book")
        val file = File(context.filesDir,book.name)
        dao.getAllBookDownloaded().forEach {
            if (it.name == book.name && book.download){
                Result.success(file)
                return@callbackFlow
            }
        }
            val task = storage.reference.child("books/${book.name}.pdf")
                .getFile(file)
                .addOnSuccessListener {
                    trySend(Result.success(file))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
                .addOnProgressListener {
                    val progress = it.bytesTransferred * 100 / it.totalByteCount
                    myLog("progress = $progress")
                }

        awaitClose()

    }

    fun downloadFile2(file:File,book:String): Flow<Result<File>> = callbackFlow {

        val task = storage.reference.child("books/${book}.pdf")
            .getFile(file)
            .addOnSuccessListener {
                trySend(Result.success(file))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
            .addOnProgressListener {
                val progress = it.bytesTransferred * 100 / it.totalByteCount
                myLog("progress = $progress")
            }

        awaitClose()

    }
//done
   /* private fun saveSavedBook(book: BookData) {
        val gson = Gson()
        val json = gson.toJson(book)
        pref.saveBook(json)
    }*/
//done
 /*   fun getSavedBooks(): String?{

       *//* myLog("all book string = $books")

        val liveDataBooks = MutableLiveData<List<BookData>>()
        liveDataBooks.value = allBooks*//*

        return pref.getBooks()
    }*/



}


/*
 fun insertBooks(
        author: String,
        coverUrl: String,
        genre: String,
        reference: String,
        title: String
    ): Flow<Result<Unit>> {
        return callbackFlow {
            val myMap = hashMapOf<String, String>(
                "name" to reference,
                "author" to author,
                "imgUrl" to coverUrl,
                "genre" to genre,
                "title" to title,
            )

            db.collection("books")
                .add(myMap)
                .addOnSuccessListener {
                    trySend(Result.success(Unit))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
            awaitClose()
        }
    }

* private var uploadTask: StorageTask<UploadTask.TaskSnapshot>? = null
*  val list = listOf<BookData>(
            BookData(
                "1994",
                "George Orwell",
                "1994",
                "https://firebasestorage.googleapis.com/v0/b/book-app-be8ac.appspot.com/o/images%2F1984.png?alt=media&token=81d76d58-2fca-437a-b6dc-459b9e6245d0"
            ),
            BookData(
                "caught_in_the_net",
                "Emile Gaboriau",
                "Caught in the net",
                "https://firebasestorage.googleapis.com/v0/b/book-app-be8ac.appspot.com/o/images%2Fcaught_in_the_net.png?alt=media&token=a814d4cf-4e62-4ac6-8843-3aae1e61d4af"
            ),
            BookData(
                "dream_psychology",
                "DR. SIGMUND FREUD",
                "Dream Psychology",
                "https://firebasestorage.googleapis.com/v0/b/book-app-be8ac.appspot.com/o/images%2Fdream_psychology.png?alt=media&token=b60d0a4f-c2de-4d3b-95a3-8ddfa96d69b9"
            )
        )
        repastory.insertBooksData(list)
* */

/*
* fun downloadFile(context:Context,name: String): Flow<Result<File>> = callbackFlow {
        val temp = File.createTempFile("books","book")

        storage.reference.child("books/80 ta imtihon savollari.pdf")
            .getFile(temp)
            .addOnSuccessListener {
                trySend(Result.success(temp))
            }
            .addOnFailureListener {

                myLog(it.message.toString())
            }
            .addOnProgressListener {
                val progress = it.bytesTransferred * 100 / it.totalByteCount
                myLog("progress = $progress")
            }
        awaitClose()

    }*/

/*
  fun uploadImage(img: Uri): Flow<Result<UploadData>> = callbackFlow {
        uploadTask = storage.reference.child("bootcamp/image/my_image.jpeg")
            .putFile(img)
            .addOnCanceledListener {
                uploadTask = null
                trySend(Result.success(UploadData.Cancel)) }
            .addOnCompleteListener { trySend(Result.success(UploadData.Complete)) }
            .addOnProgressListener {
                val progress = (it.bytesTransferred * 100 / it.totalByteCount).toInt()
                trySend(Result.success(UploadData.Progress(progress)))
            }
            .addOnPausedListener { trySend(Result.success(UploadData.Pause)) }
            .addOnSuccessListener { trySend(Result.success(UploadData.Success)) }
            .addOnFailureListener { trySend(Result.failure(it)) }
        awaitClose()
    }

    fun cancelUpload() {
        uploadTask?.let { it.cancel() }
    }

    fun pause() {
        uploadTask?.pause()
    }


    fun getImage(): Flow<Result<Bitmap>> = callbackFlow {
        val ref = storage.reference.child("sample/IMG_20230308_221048.jpg")
        ref.getBytes(1024 * 1024)
            .addOnSuccessListener { byteArray ->
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                trySend(Result.success(bitmap))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    fun delete(name: String): Flow<Result<Unit>> = callbackFlow {
        storage.reference.child("sample/$name")
            .delete()
            .addOnSuccessListener {

                trySend(Result.success(Unit))
            }
            .addOnFailureListener { trySend(Result.failure(it)) }
        awaitClose()
    }

    fun getAllImage(): Flow<Result<List<Bitmap>>> = callbackFlow {
        val images = listOf("IMG_20230308_221048.jpg", "photo_2023-03-08_20-47-02.jpg")
        val result = ArrayList<Bitmap>()
        images.forEach {
            getImageByName(it)
                .onSuccess {
                    result.add(it)
                }
                .onFailure {
                    trySend(Result.failure(it))
                }
        }
        trySend(Result.success(result))
        awaitClose()
    }

    private suspend fun getImageByName(name: String): Result<Bitmap> {
        val deferred = CompletableDeferred<Result<Bitmap>>()
        storage.reference.child("sample/$name")
            .getBytes(10 * 1024 * 1024)
            .addOnSuccessListener { byteArray ->
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                deferred.complete(Result.success(bitmap))
            }
            .addOnFailureListener {
                deferred.complete(Result.failure(it))
            }
        return deferred.await()
    }
* */