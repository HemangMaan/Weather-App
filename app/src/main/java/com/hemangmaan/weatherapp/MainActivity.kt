package com.hemangmaan.weatherapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hemangmaan.weatherapp.databinding.ActivityMainBinding
import org.json.JSONObject
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        window.statusBarColor=Color.parseColor("#1383C3")
        getJSONData(lat,long)

    }

    private fun getJSONData(lat:String?,long:String?){
        val queue = Volley.newRequestQueue(this)
        val API_KEY = ""
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"

        // Request a json response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                setValues(response)
//                Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
            },
            { Toast.makeText(this, "Error", Toast.LENGTH_LONG).show() })
        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun setValues(response: JSONObject?) {
        binding.city.text = response?.getString("name")
        val lat = response?.getJSONObject("coord")?.getString("lat")
        val long = response?.getJSONObject("coord")?.getString("lon")
        binding.coordinates.text = "$lat , $long"
        binding.weather.text = response?.getJSONArray("weather")?.getJSONObject(0)?.getString("main")
        var tempr = response?.getJSONObject("main")?.getString("temp")
        tempr=((((tempr)?.toFloat()?.minus(273.15))?.toInt()).toString())
        binding.temp.text= "$tempr ℃"
        var mintemp = response?.getJSONObject("main")?.getString("temp_min")
        mintemp=((((mintemp)?.toFloat()?.minus(273.15))?.toInt()).toString())
        binding.minTemp.text= "$mintemp ℃"
        var maxtemp = response?.getJSONObject("main")?.getString("temp_max")
        maxtemp=((ceil((maxtemp)!!.toFloat().minus(273.15)).toInt()).toString())
        binding.maxTemp.text= "$maxtemp ℃"
        binding.pressure.text = response?.getJSONObject("main")?.getString("pressure")
        binding.humidity.text = response?.getJSONObject("main")?.getString("humidity")
        binding.windspeed.text = response?.getJSONObject("wind")?.getString("speed")+"%"
        binding.degree.text = "Degree : " + response?.getJSONObject("wind")?.getString("deg")+"°"
        binding.gust.text = "Gust : "+response?.getJSONObject("wind")?.getString("gust")
    }
}