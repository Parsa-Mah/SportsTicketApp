package com.hamed.sportsticket

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hamed.sportsticket.sqlite.SportsTicketSQLiteOH
import kotlinx.android.synthetic.main.activity_buy_ticket.*

class BuyTicketActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var total = 50000
    override fun onNothingSelected(parent: AdapterView<*>?) {
        textViewTotalPrice.text = """مبلغ کل: 50000 ریال"""
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        total = 50000 * (position + 1)
        textViewTotalPrice.text = """مبلغ کل: $total ریال"""
    }

    var sqLiteDatabase: SQLiteDatabase? = null
    var credit = 0
    val items = arrayOf(1, 2, 3, 4, 5)
    var game = Game(0, 0, 0, "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_ticket)

        spinner!!.onItemSelectedListener = this
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        var id = intent.extras.getInt("id")

        getCreditFromDB(id)
        setTextView()




        buttonBuy.setOnClickListener {
            if(credit >= total) {
                changeCredit(-total)
                Toast.makeText(this, "شما ${total / 50000} بلیط خریداری کردید به مبلغ ${total} ریال", Toast.LENGTH_LONG)
                    .show()
                this.finish()
            }else{
                Toast.makeText(this, "شما اعتبار کافی ندارید!", Toast.LENGTH_LONG).show()
            }
        }
        buttonAddCredit.setOnClickListener {
            changeCredit(100000)
        }
    }

    private fun changeCredit(value: Int) {
        val newcredit = credit + value
        val sportsTicketSQLiteOH = SportsTicketSQLiteOH(this)
        val sqLiteDatabase = sportsTicketSQLiteOH.writableDatabase
        sportsTicketSQLiteOH.changeCredit(sqLiteDatabase, newcredit)
        getCreditFromDB(1)
        setTextView()
        sportsTicketSQLiteOH.close()
        sqLiteDatabase.close()
    }

    private fun setTextView() {
        textViewBuyDetails.text =
            """شما در حال خرید بلیط برای بازی:
                |
                |
                |${game.team1} در مقابل ${game.team2} هستید
                |
                |
                |برای خرید روی خرید بلیط و برای افزایش اعتبار روی افزایش اعتبار کلیک کنید
                |
                |
                |اعتبار شما در حال حاظر: 
                |
                |${credit.toString()} ریال 
            """.trimMargin()
    }

    private fun getCreditFromDB(id: Int){
        val sportsTicketSQLiteOH = SportsTicketSQLiteOH(this)
        sqLiteDatabase = sportsTicketSQLiteOH.readableDatabase
        game = sportsTicketSQLiteOH.readFromDB(sqLiteDatabase, id)
        var cursor: Cursor? = sqLiteDatabase!!.query("credit", arrayOf("credit"), null, null, null, null, null)
        while(cursor!!.moveToNext()) {
            credit = cursor!!.getInt(0)
        }
        sqLiteDatabase?.close()
        sportsTicketSQLiteOH.close()
        cursor.close()
    }
}
