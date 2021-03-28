package com.ragi.cronometro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView




class cAdpTempos( val items: MutableList<horario>,
                  val _context: Context?
) : RecyclerView.Adapter<cAdpTempos.ViewHolder>() {

    val mHorarios: MutableList<horario>? = items;

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        //val tvhorario = view.tv_horario;

        val tvTitulo = view.findViewById<TextView>(R.id.tv_titulo).text;
        val tvHorario = view.findViewById<TextView>(R.id.tv_horario).text;
        val tvCheckPoint = view.findViewById<TextView>(R.id.tv_chkpnt).text;


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cAdpTempos.ViewHolder {

        val view = LayoutInflater.from(_context).inflate(R.layout.tempo_item, parent, false)
        return cAdpTempos.ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mHorarios?.size!!;
    }

    override fun onBindViewHolder(holder: cAdpTempos.ViewHolder, position: Int) {


        var vhorario = mHorarios?.get(position);
        var txtTitulo = holder.itemView.findViewById<TextView>(R.id.tv_titulo);
        var txtHorario = holder.itemView.findViewById<TextView>(R.id.tv_horario);
        var tvCheckPoint = holder.itemView.findViewById<TextView>(R.id.tv_chkpnt)
        txtTitulo.setText(vhorario?.Titulo);
        txtHorario.setText(vhorario?.Horario);
        tvCheckPoint.setText(vhorario?.checkpoint)
        //holder?.tempo_item_title?.text = vhorario.Titulo;


    }

    public fun clearAll() {
        //this.items.clear();
        mHorarios?.clear();
        notifyDataSetChanged()
    }


    public fun insertItem(item: horario) {
        mHorarios?.add(item);
        notifyItemInserted(itemCount)
    }

    public fun updateList(item: horario?) {
        if (item != null) {
            insertItem(item)
        }
    }
}

public class horario(val checkpoint:String , val Titulo:String, val Horario:String )