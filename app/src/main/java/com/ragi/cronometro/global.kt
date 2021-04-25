package com.ragi.cronometro
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class global {

    companion object {
        @JvmStatic
        fun a() : Int = 1;
        @JvmStatic
        var floatbutton: FloatingActionButton? = null;
        @JvmStatic
        var mainactivity: AppCompatActivity? = null;

        @JvmStatic
        fun salvarpref(key: String, valor: String){
            val pref = mainactivity?.getSharedPreferences(key,  android.content.Context.MODE_PRIVATE)
            pref?.edit()?.putString("valor", valor)!!.commit();
        }

        @JvmStatic
        fun lerpref(key: String): String{
            val pref = mainactivity?.getSharedPreferences(key,  android.content.Context.MODE_PRIVATE)
            return pref?.getString("valor","")!!
        }

    }





    constructor(){

    }



}