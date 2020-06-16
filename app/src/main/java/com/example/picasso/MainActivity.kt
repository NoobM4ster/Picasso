    package com.example.picasso


    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import com.squareup.picasso.Picasso
    import kotlinx.android.synthetic.main.activity_main.*
    import okhttp3.*
    import org.json.JSONObject
    import java.io.IOException

    class MainActivity : AppCompatActivity() {
        private val client=OkHttpClient()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            load.setOnClickListener{
                run("https://aws.random.cat/meow")
            }
        }
        private fun run(url: String){
            val request = Request.Builder().url(url).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()?.string()
                    val jsonImage = (JSONObject(json).get("file")).toString()
                    this@MainActivity.runOnUiThread {
                        if (response.isSuccessful) {
                            (
                                    Picasso.get()
                                        .load(jsonImage)
                                        .fit()
                                        .placeholder(R.drawable.ic_replay_black_24dp)
                                        .error(R.drawable.ic_error_black_24dp)
                                        .into(ImageView))
                        }
                    }
                }
            })
    }
    }






