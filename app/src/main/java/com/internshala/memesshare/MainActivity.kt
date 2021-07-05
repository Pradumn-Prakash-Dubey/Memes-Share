package com.internshala.memesshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.text.RelativeDateTimeFormatter.getInstance
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View


import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.cronet.CronetHttpStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.security.cert.CertificateFactory.getInstance
import java.text.Collator.getInstance
import java.util.Calendar.getInstance


class MainActivity : AppCompatActivity() {
    var memeURL:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()

    }

    private fun loadMeme() {
        // Instantiate the RequestQueue.

      val progressbar:ProgressBar=findViewById(R.id.progressBar)
        progressbar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)


        val url = "https://meme-api.herokuapp.com/gimme"
        var img: ImageView = findViewById(R.id.memeImageView)

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                memeURL=response.getString("url")
                Glide.with(this).load(memeURL).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        p0: GlideException?,
                        p1: Any?,
                        p2: Target<Drawable>?,
                        p3: Boolean
                    ): Boolean {

                        progressbar.visibility=View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        p0: Drawable?,
                        p1: Any?,
                        p2: Target<Drawable>?,
                        p3: DataSource?,
                        p4: Boolean
                    ): Boolean {

                        progressbar.visibility=View.GONE
                        return false

                    }
                }).into(img)


                              },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

            })

// Add the request to the RequestQueue.
       queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,checkout this cool meme I got from Reddit $memeURL")
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)

    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}