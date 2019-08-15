package com.hamed.sportsticket.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hamed.sportsticket.Game
import com.hamed.sportsticket.R

class SportsTicketSQLiteOH(context: Context) : SQLiteOpenHelper(context, "SportsticketDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE SportList (_id INTEGER PRIMARY KEY AUTOINCREMENT, logo1 INTEGER, logo2 INTEGER, team1 TEXT, team2 TEXT)")
        insertIntoDB(db, R.drawable.esteghlal, R.drawable.perspolis, "استقلال", "پرسپولیس")
        insertIntoDB(db, R.drawable.gostaresh_foolad, R.drawable.sepahan, "گسترش فولاد", "سپاهان")
        insertIntoDB(db, R.drawable.perspolis, R.drawable.teractor, "پرسپولیس", "تراکتور")
        insertIntoDB(db, R.drawable.esteghlal, R.drawable.zobahan, "استقلال", "ذوب آهن")
        insertIntoDB(db, R.drawable.teractor, R.drawable.esteghlal, "تراکتور", "استقلال")

        db?.execSQL("CREATE TABLE credit (_id INTEGER PRIMARY KEY AUTOINCREMENT, credit INTEGER)")
        val contentValues = ContentValues()
        contentValues.put("credit", 300000)
        db?.insert("credit", null, contentValues)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertIntoDB(db: SQLiteDatabase?, logo1: Int, logo2: Int, team1: String, team2: String){
        val contentValues = ContentValues()
        contentValues.put("logo1", logo1)
        contentValues.put("logo2", logo2)
        contentValues.put("team1", team1)
        contentValues.put("team2", team2)
        db?.insert("SportList", null, contentValues)
    }

    fun changeCredit(db: SQLiteDatabase?, credit: Int){
        db?.execSQL("UPDATE credit SET credit=$credit WHERE _id=1")
    }

    fun fillRecyclerView(db: SQLiteDatabase?): ArrayList<Game>{
        var cursor: Cursor? =
            db?.query("SportList", arrayOf( "logo1", "logo2", "team1", "team2"), null, null, null, null, null)
        var gamelist = arrayListOf<Game>()
        while (cursor!!.moveToNext()){
            var game = Game(0, cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3))
            gamelist.add(game)
        }
        cursor.close()
        return gamelist
    }
    fun readFromDB(db: SQLiteDatabase?, position: Int): Game{
        var game: Game = Game(0, 0, 0, "", "")
        var cursor: Cursor? = db?.query("SportList", arrayOf("logo1", "logo2", "team1", "team2"), "_id=?", arrayOf(position.toString()), null, null, null)
        while (cursor!!.moveToNext()) {
            game= Game(0, cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3))
        }
        return game
    }
}