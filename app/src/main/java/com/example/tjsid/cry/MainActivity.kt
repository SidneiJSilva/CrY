package com.example.tjsid.cry

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    var lastPricesBit: ArrayList<String> = ArrayList()
    var lastPricesLit: ArrayList<String> = ArrayList()
    var lastPricesBcash: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lastBit = ArrayList<String>()
        val basicUrl = "https://www.mercadobitcoin.net/api/"
        val urlBit = "BTC/ticker/"
        val urlLit = "LTC/ticker/"
        val urlBCash = "BCH/ticker/"

        val listViewBit = findViewById<ListView>(R.id.listaBit)
        val listViewLit = findViewById<ListView>(R.id.listaLit)
        val listViewBCash = findViewById<ListView>(R.id.listaBCash)

        var date = Dates()

        //inicializa os campos quando o APP é aberto
        get(basicUrl + urlBit)
        get(basicUrl + urlLit)
        get(basicUrl + urlBCash)
        down_bar.text = "Última atualização " + date.getAllHour()

        btnUpdate.setOnClickListener {
            vibrator()
            get(basicUrl + urlBit)
            get(basicUrl + urlLit)
            get(basicUrl + urlBCash)

            down_bar.text = "Última atualização " + date.getAllHour()
            //para BTC
            listViewBit.adapter = MyCustomAdapter(this, getLastPrice(urlBit))
            listViewLit.adapter = MyCustomAdapter(this, getLastPrice(urlLit))
            listViewBCash.adapter = MyCustomAdapter(this, getLastPrice(urlBCash))

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

    }

    private fun getLastPrice(str: String): ArrayList<String> {
        if(str == "BTC/ticker/"){
            lastPricesBit.add(btcUltimoPreco.text.toString())
            return lastPricesBit
        } else if(str == "LTC/ticker/"){
            lastPricesLit.add(ltcUltimoPreco.text.toString())
            return lastPricesLit
        } else if(str == "BCH/ticker/"){
            lastPricesBcash.add(bchUltimoPreco.text.toString())
            return lastPricesBcash
        }
        var default: ArrayList<String> = ArrayList()
        default.add("Sem valor")
        return default

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
            var lastPrice = ("%.2f".format(values.getString("last").toFloat())).toString()
            btcUltimoPreco.text = lastPrice

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

    private fun vibrator() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Check whether device/hardware has a vibrator
        val canVibrate: Boolean = vibrator.hasVibrator()

        if (canVibrate) {
            var milliseconds: Long = 100
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // void vibrate (VibrationEffect vibe)
                vibrator.vibrate(
                        VibrationEffect.createOneShot(
                                milliseconds,
                                // The default vibration strength of the device.
                                VibrationEffect.DEFAULT_AMPLITUDE
                        )
                )
            } else {
                // This method was deprecated in API level 26
                vibrator.vibrate(milliseconds)
            }
        }

    }

    class MyCustomAdapter(context: Context, lastPrices: ArrayList<String>) : BaseAdapter() {

        private val mContext: Context
        private val mLastPrices: ArrayList<String>

        init {
            mContext = context
            mLastPrices = lastPrices
        }

        //determina quantas linhas vai ter a lista
        override fun getCount(): Int {
            return mLastPrices.size
        }

        //pode ignorar
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //pode ignorar
        override fun getItem(p0: Int): Any {
            return "ANYTHING"
        }

        //renderiza cada linha da lista
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

            val rowMain: View

            //checa se convertView é null, para não precisr inflar outra linha
            if (convertView == null) {

                val layoutInflater = LayoutInflater.from(mContext)
                rowMain = layoutInflater.inflate(R.layout.big_value, viewGroup, false)
                val value = rowMain.findViewById<TextView>(R.id.txt_big_value)
                val viewHolder = ViewHolder(value)
                rowMain.tag = viewHolder

            } else {
                //well, we have our row as convertView, so just set rowMain as that view
                //temos a linha pronta, apenas copie mais linhas para a view
                rowMain = convertView
            }

            //coloca as informações nas linhas
            val viewHolder = rowMain.tag as ViewHolder
            var index = mLastPrices.size
            viewHolder.value.text = "${mLastPrices[(mLastPrices.size-1)-position]}"

            return rowMain
        }

        private class ViewHolder(val value: TextView)

    }

}
