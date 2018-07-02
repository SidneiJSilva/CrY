package com.example.tjsid.cry

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.example.tjsid.cry.Constants.ValuesConstants
import com.example.tjsid.cry.Date.Dates
import com.example.tjsid.cry.Repository.ValueRepository
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val basicUrl = "https://www.mercadobitcoin.net/api/"
        val urlBit = "BTC/ticker/"
        val urlLit = "LTC/ticker/"
        val urlBCash = "BCH/ticker/"

        val listViewBit = findViewById<ListView>(R.id.listaBit)
        val listViewLit = findViewById<ListView>(R.id.listaLit)
        val listViewBCash = findViewById<ListView>(R.id.listaBCash)
        val listViewDates = findViewById<ListView>(R.id.listaDate)

        val date: Dates = Dates.getInstance(this)

        //inicializa os campos quando o APP é aberto
        get(basicUrl + urlBit)
        get(basicUrl + urlLit)
        get(basicUrl + urlBCash)
        down_bar.text = "Última atualização " + date.getAllHour()
        listViewBit.adapter = MyCustomAdapter(this, getLastPrice(urlBit, this), "bitcoin")
        listViewLit.adapter = MyCustomAdapter(this, getLastPrice(urlLit, this), "litcoin")
        listViewBCash.adapter = MyCustomAdapter(this, getLastPrice(urlBCash, this), "bcash")
        listViewDates.adapter = MyCustomAdapter(this, getLastDate(this), "date")

        btnUpdate.setOnClickListener {
            vibrator()
            get(basicUrl + urlBit)
            get(basicUrl + urlLit)
            get(basicUrl + urlBCash)
            down_bar.text = "Última atualização " + date.getAllHour()
            listViewBit.adapter = MyCustomAdapter(this, getLastPrice(urlBit, this), "bitcoin")
            listViewLit.adapter = MyCustomAdapter(this, getLastPrice(urlLit, this), "litcoin")
            listViewBCash.adapter = MyCustomAdapter(this, getLastPrice(urlBCash, this), "bcash")
            listViewDates.adapter = MyCustomAdapter(this, getLastDate(this), "date")
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

    private fun getLastPrice(str: String, context: Context): ArrayList<String> {

        val dados: ValueRepository = ValueRepository.getInstance(context)

        if (str == "BTC/ticker/") {
            return dados.getValues(30, ValuesConstants.TYPE.BTC)
        } else if (str == "LTC/ticker/") {
            return dados.getValues(30, ValuesConstants.TYPE.LTC)
        } else if (str == "BCH/ticker/") {
            return dados.getValues(30, ValuesConstants.TYPE.BCH)
        } else {
            var default: ArrayList<String> = ArrayList()
            default.add("Sem valor")
            return default
        }

    }

    private fun getLastDate(context: Context): ArrayList<String> {
        val dados: ValueRepository = ValueRepository.getInstance(context)
        return dados.getDates(30)
    }

    private fun deserializacao(str: String, typeUrl: String) {
        putInFields(str, typeUrl, this)
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

    private fun putInFields(str: String, typeUrl: String, context: Context) {

        val date: Dates = Dates.getInstance(context)
        val dados: ValueRepository = ValueRepository.getInstance(context)

        if (typeUrl == "https://www.mercadobitcoin.net/api/BTC/ticker/") {

            var values = JSONObject(getRightJason(str))
            btcMaiorValor.text = ("%.2f".format(values.getString("high").toFloat())).toString()
            btcMenorValor.text = ("%.2f".format(values.getString("low").toFloat())).toString()
            btcVCompraValor.text = ("%.2f".format(values.getString("buy").toFloat())).toString()
            btcValorVenda.text = ("%.2f".format(values.getString("sell").toFloat())).toString()
            btcVolume.text = ("%.2f".format(values.getString("vol").toFloat())).toString()
            var lastPrice = ("%.2f".format(values.getString("last").toFloat())).toString()
            btcUltimoPreco.text = lastPrice

            dados.insert(lastPrice, date.getAllHour(), date.getDate(), ValuesConstants.TYPE.BTC)

        }
        if (typeUrl == "https://www.mercadobitcoin.net/api/LTC/ticker/") {

            var values = JSONObject(getRightJason(str))
            ltcMaiorValor.text = ("%.2f".format(values.getString("high").toFloat())).toString()
            ltcMenorValor.text = ("%.2f".format(values.getString("low").toFloat())).toString()
            ltcVCompraValor.text = ("%.2f".format(values.getString("buy").toFloat())).toString()
            ltcValorVenda.text = ("%.2f".format(values.getString("sell").toFloat())).toString()
            ltcVolume.text = ("%.2f".format(values.getString("vol").toFloat())).toString()
            var lastPrice = ("%.2f".format(values.getString("last").toFloat())).toString()
            ltcUltimoPreco.text = lastPrice

            dados.insert(lastPrice, date.getAllHour(), date.getDate(), ValuesConstants.TYPE.LTC)

        }
        if (typeUrl == "https://www.mercadobitcoin.net/api/BCH/ticker/") {

            var values = JSONObject(getRightJason(str))
            bchMaiorValor.text = ("%.2f".format(values.getString("high").toFloat())).toString()
            bchMenorValor.text = ("%.2f".format(values.getString("low").toFloat())).toString()
            bchVCompraValor.text = ("%.2f".format(values.getString("buy").toFloat())).toString()
            bchValorVenda.text = ("%.2f".format(values.getString("sell").toFloat())).toString()
            bchVolume.text = ("%.2f".format(values.getString("vol").toFloat())).toString()
            var lastPrice = ("%.2f".format(values.getString("last").toFloat())).toString()
            bchUltimoPreco.text = lastPrice

            dados.insert(lastPrice, date.getAllHour(), date.getDate(), ValuesConstants.TYPE.BCH)

        }
    }

    private fun vibrator() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Check whether device/hardware has a vibrator
        val canVibrate: Boolean = vibrator.hasVibrator()

        if (canVibrate) {
            var milliseconds: Long = 50
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

    class MyCustomAdapter(context: Context, lastPrices: ArrayList<String>, type: String) : BaseAdapter() {

        private val mContext: Context
        private val mLastPrices: ArrayList<String>
        private val mType: String

        init {
            mContext = context
            mLastPrices = lastPrices
            mType = type
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
            viewHolder.value.text = "${mLastPrices[position]}"

            if(mType == "date"){

            }else{
                if (position == mLastPrices.size - 1) {
                    viewHolder.value.setTextColor(Color.YELLOW)
                } else {
                    when {
                        mLastPrices[position] > mLastPrices[position + 1] -> viewHolder.value.setTextColor(Color.GREEN)
                        mLastPrices[position] < mLastPrices[position + 1] -> viewHolder.value.setTextColor(Color.RED)
                        mLastPrices[position] == mLastPrices[position + 1] -> viewHolder.value.setTextColor(Color.YELLOW)
                    }

                }
            }

            return rowMain
        }

        private class ViewHolder(val value: TextView)

    }

}
