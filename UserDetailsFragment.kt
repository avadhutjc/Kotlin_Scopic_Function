package com.ajc.kotlinscopicfunction

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private lateinit var responseDTO: ResponseDTO

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_details, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //below img will place before clicking on api call btn
        //  imgView.setImageResource(R.drawable.a)

        apiBtn.setOnClickListener {
            Toast.makeText(activity?.applicationContext, "API Button Click", Toast.LENGTH_LONG)
                .show()

            /*
             activity?.runOnUiThread {
                Toast.makeText(context, "API Button Click", Toast.LENGTH_SHORT).show()
            }

             */
            //we are calling api on click of button
            callAPI()
            //  imgView.setImageResource(R.drawable.a)

        }
    }


    private fun callAPI() {
        val apiClient = Network.getInstance().create(ApiClient::class.java)

        apiClient.getUserDetails(2).enqueue(object : Callback<ResponseDTO> {
            override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {

                response.body()?.run {
                    responseDTO = this
                    tvFirstName.text = responseDTO.data?.firstName
                    tvLastName.text = responseDTO.data?.lastName
                    //       imgView.setImageResource(R.drawable.a)

                    //https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library
                    Glide.with(imgView)
                        .load(responseDTO.data?.avatar)
                        .placeholder(R.drawable.a)
                        // .error(R.drawable.imagenotfound)
                        .into(imgView)

//                    responseDTO.data?.apply {
//                        tvFirstName.text = firstName
//                        tvLastName.text = lastName
//                    }
                }
            }

            override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                //Toast.makeText(this@MainActivity, "Its toast!", Toast.LENGTH_SHORT).show()
                Toast.makeText(activity?.applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }
        })
    }
}
/*
Try this

In Activity
Toast.makeText(applicationContext, "Test", Toast.LENGTH_LONG).show()
or
Toast.makeText(this@MainActiivty, "Test", Toast.LENGTH_LONG).show()


In Fragment
Toast.makeText(activity, "Test", Toast.LENGTH_LONG).show()
or
Toast.makeText(activity?.applicationContext, "Test", Toast.LENGTH_LONG).show()
 */