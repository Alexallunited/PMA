package rs.edu.educons.pma

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import rs.edu.educons.pma.Models.ChuckNorrisJoke
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import rs.edu.educons.pma.Helper.Helper

class MainActivity : AppCompatActivity() {

    //private var mediaPlayer: MediaPlayer? = null
    var sp : SoundPool? = null //istraziti kako se koristi

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) //mora da stoji @TargetApi inace koristi API level 1 iz nekog razloga (Moze da stoji i @RequireApi)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        //mediaPlayer (zavrsiti kasnije)
        mediaPlayer = MediaPlayer.create(this, R.raw.Chuck_Norris)
        mediaPlayer?.setOnPreparedListener{
        }
        */
        val editTextIme = findViewById<EditText>(R.id.editTextIme)
        val editTextPrezime = findViewById<EditText>(R.id.editTextPrezime)


        btnJoke.setOnClickListener{
            val mediaPlayer = MediaPlayer.create(this, R.raw.chuck_norris)
            mediaPlayer.start()
            val asyncTask = @TargetApi(Build.VERSION_CODES.LOLLIPOP) //mora da stoji @TargetApi inace koristi API level 1 iz nekog razloga
            object:AsyncTask<String, Void, String>() {
                var mDialog = ProgressDialog(this@MainActivity)
                override fun onPreExecute() {
                    //super.onPreExecute()
                    mDialog.setTitle("Sačekajte trenutak ili dva")
                    mDialog.show()
                }


                override fun doInBackground(vararg params: String?): String {
                    val helper = Helper()
                    //https://api.icndb.com/jokes/random?firstName=Aleksandar&lastName=Trajkovic
                    val url1: String = "https://api.icndb.com/jokes/random?firstName="
                    val first: String = editTextIme.text.toString()
                    val url2: String ="&lastName="
                    val second: String = editTextPrezime.text.toString()
                    val konacniString: String = url1 + first + url2 + second

                        return helper.getHTTPData(konacniString)
                }

                override fun onPostExecute(result: String?) {
                    mDialog.dismiss()
                    val chuckNorrisJoke = Gson().fromJson(result, ChuckNorrisJoke::class.java)
                    txtJoke.text = chuckNorrisJoke.value.joke
                    if (txtJoke.visibility == INVISIBLE)
                        txtJoke.visibility = VISIBLE

                }
            }
            asyncTask.execute()

        }

    }
}
