package com.ragi.cronometro

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Fragment_CheckPoint : Fragment() {

    var  _utltimoHorario : Date? = null;
    var tv_horario: TextView?=null;
    val time: Long = 1000000000L
    var timer : Timer = Timer(time)
    var runing = 0;
    var pb_seg: android.widget.ProgressBar? = null;
    var _chkpnt_cnt : Int = -1;

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
            pb_seg!!.progress = tic.toInt()

        }


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //global.instacia =



        return inflater.inflate(R.layout.fragment_checkpoint, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_horario = view.findViewById<TextView>(R.id.tv_horario);

        //android.animation.ValueAnimator.sDurationScale == 0.0f
        pb_seg = view.findViewById<ProgressBar?>(R.id.pb_seg);
        pb_seg!!.max = 100;
        pb_seg!!.progress = 0;

        val progressDrawable: Drawable = pb_seg!!.getProgressDrawable().mutate()
        progressDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        pb_seg!!.setProgressDrawable(progressDrawable)

        view.setVisibility(View.VISIBLE);
        pb_seg!!.setVisibility(View.INVISIBLE);



        var rctempos: RecyclerView = view.findViewById<RecyclerView>(R.id.rv_tempos);
        rctempos.layoutManager = LinearLayoutManager(this.context);
        rctempos.adapter = cAdpTempos(arrayListOf(),this.context);
        rctempos.addItemDecoration(DividerItemDecoration(rctempos.getContext(), DividerItemDecoration.VERTICAL))



        global.floatbutton?.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {

                (rctempos.adapter as cAdpTempos).clearAll();
                if (runing==1)
                    reset()
                else
                    start();

                return true  }
        })


        global.floatbutton?.setOnClickListener { view ->

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
            _chkpnt_cnt++;

            var labelTitulo = "Start"
            if(_chkpnt_cnt>0)
                labelTitulo = "Check Point " + _chkpnt_cnt.toString()
            else {
                str = strDataHora;
                strDataHora="";
            }
            adpt.updateList(horario(labelTitulo  ,str, strDataHora));
        }

    }

    fun start() {
        _utltimoHorario = Calendar.getInstance().time
        timer.start()
        runing=1;
        pb_seg!!.setVisibility(View.VISIBLE);
    }

    fun pause() {
        timer.cancel();
        runing = 0;
        pb_seg!!.setVisibility(View.INVISIBLE);
}

    fun reset() {
        tv_horario?.text = "00:00:00:000"
        _utltimoHorario =null;
        timer.cancel()
        timer = Timer(time)
        runing=0;
        _chkpnt_cnt=-1;
        pb_seg!!.setVisibility(View.INVISIBLE);
    }

}