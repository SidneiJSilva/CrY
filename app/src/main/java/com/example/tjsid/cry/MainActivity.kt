package com.example.tjsid.cry

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tjsid.cry.Date.Dates
import java.math.RoundingMode
import java.text.DecimalFormat
import com.google.gson.JsonDeserializer
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private var date = Dates()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val basicUrl = "https://www.mercadobitcoin.net/api/"
        val urlBit = "BTC/ticker/"
        val urlLit = "LTC/ticker/"
        val urlBCash = "BCH/ticker/"

        btnUpdate.setOnClickListener {
            get(basicUrl + urlBit)
            get(basicUrl + urlLit)
            get(basicUrl + urlBCash)
        }
    }

    private fun get(url: String) {

        var que = Volley.newRequestQueue(this)
        var req = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener {

                    response ->
                    deserializacao(response.toString(), url)

                }, Response.ErrorListener {

            Toast.makeText(this, "Something was wrong", Toast.LENGTH_LONG).show()

        })
        que.add(req)
        down_bar.text = "Última atualização " + date.getAllHour()

    }

    private fun deserializacao(str: String, typeUrl: String) {
        putInFields(str, typeUrl)
    }

    private fun getRightJason(str: String): String {
        var rightJson = ""
        var index1 = 0
        var index2 = 0
        for (x in 0..str.length - 1) {

            if (str[x] == '{') {
                index1++
            }
            if (index1 == 2 && index2 == 0) {
                rightJson += str[x]
            }
            if (str[x] == '}') {
                index2 = 1
            }
        }
        return rightJson
    }

    private fun putInFields(str: String, typeUrl: String) {

        if (typeUrl == "https://www.mercadobitcoin.net/api/BTC/ticker/") {

            var values = JSONObject(getRightJason(str))
            btcMaiorValor.text = ("%.2f".format(values.getString("high").toFloat())).toString()
            btcMenorValor.text = ("%.2f".format(values.getString("low").toFloat())).toString()
            btcVCompraValor.text = ("%.2f".format(values.getString("buy").toFloat())).toString()
            btcValorVenda.text = ("%.2f".format(values.getString("sell").toFloat())).toString()
            btcVolume.text = ("%.2f".format(values.getString("vol").toFloat())).toString()
            btcUltimoPreco.text = ("%.2f".format(values.getString("last").toFloat())).toString()

        }
        if (typeUrl == "https://www.mercadobitcoin.net/api/LTC/ticker/") {

            var values = JSONObject(getRightJason(str))
            ltcMaiorValor.text = ("%.2f".format(values.getString("high").toFloat())).toString()
            ltcMenorValor.text = ("%.2f".format(values.getString("low").toFloat())).toString()
            ltcVCompraValor.text = ("%.2f".format(values.getString("buy").toFloat())).toString()
            ltcValorVenda.text = ("%.2f".format(values.getString("sell").toFloat())).toString()
            ltcVolume.text = ("%.2f".format(values.getString("vol").toFloat())).toString()
            ltcUltimoPreco.text = ("%.2f".format(values.getString("last").toFloat())).toString()
        }
        if (typeUrl == "https://www.mercadobitcoin.net/api/BCH/ticker/") {

            var values = JSONObject(getRightJason(str))
            bchMaiorValor.text = ("%.2f".format(values.getString("high").toFloat())).toString()
            bchMenorValor.text = ("%.2f".format(values.getString("low").toFloat())).toString()
            bchVCompraValor.text = ("%.2f".format(values.getString("buy").toFloat())).toString()
            bchValorVenda.text = ("%.2f".format(values.getString("sell").toFloat())).toString()
            bchVolume.text = ("%.2f".format(values.getString("vol").toFloat())).toString()
            bchUltimoPreco.text = ("%.2f".format(values.getString("last").toFloat())).toString()
        }
    }

}
