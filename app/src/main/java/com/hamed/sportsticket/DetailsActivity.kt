package com.hamed.sportsticket


import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hamed.sportsticket.sqlite.SportsTicketSQLiteOH
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.imageViewVS


class DetailsActivity : AppCompatActivity() {

    var sqLiteDatabase: SQLiteDatabase? = null
    var time = ""
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        var id = intent.extras.getInt("id") + 1

        val sportsTicketSQLiteOH = SportsTicketSQLiteOH(this)
        sqLiteDatabase = sportsTicketSQLiteOH.readableDatabase
        var game = sportsTicketSQLiteOH.readFromDB(sqLiteDatabase, id)
        sqLiteDatabase!!.close()
        sportsTicketSQLiteOH.close()

        imageView1.setImageResource(game.logo1)
        imageView2.setImageResource(game.logo2)
        imageViewVS.setImageResource(R.drawable.vs)
        imageViewStadium.setImageResource(when(id){
            1 -> R.drawable.azadi_stadium
            2 -> R.drawable.naghsh_e_jahan_stadium
            3 -> R.drawable.yadegar_e_emam_stadium
            4 -> R.drawable.foolad_shahr_stadium
            5 -> R.drawable.azadi_stadium
            else -> 0
        })
        textViewStadium.text =
            when(id){
                1 -> "استادیوم آزادی"
                2 -> "استادیوم نقش جهان"
                3 -> "استادیوم یادگار امام"
                4 -> "استادیوم فولادشهر"
                5 -> "استادیوم آزادی"
                else -> ""
            }
        when(id){
            1 -> {date = "دوشنبه"; time = "12:30"}
            2 -> {date = "دوشنبه"; time = "13:40"}
            3 -> {date = "شنبه"; time = "16:00"}
            4 -> {date = "چهارشنبه"; time = "19:00"}
            5 -> {date = "سه شنبه"; time = "18:30"}
            else -> {date = "جمعه"; time = "15:30"}
        }
        textView4.text = """بازی ${game.team1} و ${game.team2}
            |
            |در تاریخ $date 
            |
            |ساعت $time
            |
            |در ورزشگاه ${textViewStadium.text}
            |برگزار میشود برای خرید بلیط کلیک کنید
        """.trimMargin()
        buttonBuyTicket.setOnClickListener {
            var intent = Intent(this, BuyTicketActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

}
