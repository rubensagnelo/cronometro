package com.ragi.cronometro

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.time.DateTimeException

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    var  _utltimoHorario : Date? = null;
    var tv_horario: TextView?=null;
    val time: Long = 1000000000L
    var timer : Timer = Timer(time)

    inner class Timer(miliis:Long) : CountDownTimer(miliis,1){
        var millisUntilFinished:Long = 0
        override fun onFinish() {
        }
        override fun onTick(millisUntilFinished: Long) {
            this.millisUntilFinished = millisUntilFinished
            val passTime = time - millisUntilFinished
            val f = DecimalFormat("00")
            val fm = DecimalFormat("000")
            val hour = passTime / 3600000 % 24
            val min = passTime / 60000 % 60
            val sec = passTime / 1000 % 60
            val mls = passTime-sec*1000-min*60000-hour*3600000;
            tv_horario?.text = f.format(hour) + ":" + f.format(min) + ":" + f.format(sec) + ":" + fm.format(mls)
            val tic: Float = 100f/60f * sec
            //progressBar.progress = tic.toInt()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //global.instacia =



        return inflater.inflate(R.layout.fragment_first, container, false)


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tv_horario = view.findViewById<TextView>(R.id.tv_horario);

/*
        var i=0;
        var lshorario : List<horario>;
        lshorario = arrayListOf();
        while (i<1)
        {
            lshorario.add(horario("Horario"+i.toString(),"00:"+ i.toString().padStart(2,'0')));
            i++;
        }
*/
/*
        var lshorario = arrayListOf(
            horario("horario1","10:00"),
            horario("horario2","11:00"),
            horario("horario3","12:00")
            );
*/


        var rctempos: RecyclerView = view.findViewById<RecyclerView>(R.id.rv_tempos);
        rctempos.layoutManager = LinearLayoutManager(this.context);
        rctempos.adapter = cAdpTempos(arrayListOf(),this.context);


        global.floatbutton?.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {

                (rctempos.adapter as cAdpTempos).clearAll();
                start();

                return true  }
        })


        global.floatbutton?.setOnClickListener { view ->

            //var rctempos: RecyclerView = view.findViewById<RecyclerView>(R.id.rv_tempos);
            var adpt:cAdpTempos =  rctempos.adapter as cAdpTempos;

            val date = Calendar.getInstance().time;

            if (_utltimoHorario == null)
                start();


            var dif = date.time - _utltimoHorario!!.time;
            var difseg = TimeUnit.MILLISECONDS.toSeconds(dif);
            var difmin = TimeUnit.MILLISECONDS.toMinutes(dif);
            var difHor = TimeUnit.MILLISECONDS.toHours(dif);
            var difDia = TimeUnit.MILLISECONDS.toDays(dif);

            if (dif>=1000) dif = dif-(difseg*1000);

            if (difseg>=60) difseg = difseg-(difmin*60);
            if (difmin>=60) difmin = difmin-(difHor*60);
            if (difHor>=24) difHor = difHor-(difDia*24);

            var dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            var strDataHora : String =  dateTimeFormat.format(date)

            val f = DecimalFormat("00")
            val fm = DecimalFormat("000")
            //_utltimoHorario = date;
            var str : String = f.format(difDia) +":" + f.format(difHor) + ":" + f.format(difmin) + ":" + f.format(difseg) + ":" + fm.format(dif);
            adpt.updateList(horario(str, "Checkpoint:" + strDataHora));
        }




        //var adptempos: RecyclerView.A


        //view.findViewById<Button>(R.id.button_first).setOnClickListener {
        //    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}

    }

    fun start() {
        _utltimoHorario = Calendar.getInstance().time
        timer.start()
    }



}