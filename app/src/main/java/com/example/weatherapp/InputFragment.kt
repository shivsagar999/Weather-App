package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weatherapp.databinding.FragmentInputBinding
import retrofit2.Call
import java.util.zip.Inflater
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InputFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InputFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    interface OnItemClickListener{
        fun onButtonClicked(text: String){

        }
    }

    lateinit var mSubmitClick : OnItemClickListener


    private lateinit var _binding: FragmentInputBinding

    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mSubmitClick = context as OnItemClickListener
        }catch (e: ClassCastException){
            Log.d("Wagle", "Exception")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInputBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{
            getWeather()
            // if the pin is incorrect then display it in text box of same activity
            // else initiate the intent


        }
    }


    override fun onStart() {
        super.onStart()

        // write the code for permissions

    }

    private fun getWeather() {
        var finalText: String? = null
        val pinCode = binding.inputText.text.toString()
        val pinCountry = "$pinCode,in"
        Log.d("Wagle", " your pincode is $pinCountry")
        val weather = WeatherService.weatherInstance.getWeather(code_country = pinCountry)
        weather.enqueue(object: Callback<WeatherInfo>{
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                val result = response.body()
                Log.d("Wagle", " you are inside response")
                if(result != null){
                    val temp = result.main.temp.toFloat() - 273
                    finalText = "Temp = " + temp.toString() + " Place = " + result.name.toString()
                    Log.d("Wagle", "Retrofit response is succesfully fetched")
                    Log.d("Wagle", "your  response text $finalText")
                    mSubmitClick.onButtonClicked(finalText.toString())

                }

                else{
                    Log.d("Wagle", "Error in pincode")
                    finalText = "Incorrect Pin"
                    //mSubmitClick.onButtonClicked(finalText.toString())
                    binding.errorText.text = "Please type correct pincode"
                }
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                Log.d("Wagle", "Could not fetch the retrofit response $t.message() ")
                Toast.makeText(context, "Could not fetch the Weather there is some issues", Toast.LENGTH_SHORT).show()
            }

        })


        Log.d("Wagle", "your text $finalText")
    }


    /*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InputFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InputFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

     */
}