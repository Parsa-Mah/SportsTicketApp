package com.hamed.sportsticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamed.sportsticket.adapter.RecyclerViewAdapter
import com.hamed.sportsticket.sqlite.SportsTicketSQLiteOH
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewManager = LinearLayoutManager(this)
        var gameslist: ArrayList<Game>
        val sportsTicketSQLiteOH = SportsTicketSQLiteOH(this)
        gameslist = sportsTicketSQLiteOH.fillRecyclerView(sportsTicketSQLiteOH.readableDatabase)
        viewAdapter = RecyclerViewAdapter(gameslist)

        recyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager  = viewManager
            adapter = viewAdapter
        }
        sportsTicketSQLiteOH.close()

    }

}
