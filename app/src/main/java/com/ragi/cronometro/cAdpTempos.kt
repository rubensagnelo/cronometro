package com.ragi.cronometro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.reflect.TypeToken
import kotlin.reflect.typeOf

//import com.google.code.




class cAdpTempos( val items: MutableList<horario>,
                  val _context: Context?
) : RecyclerView.Adapter<cAdpTempos.ViewHolder>() {

    var mHorarios: MutableList<horario>? = items;

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
        salvar()
    }


    public fun insertItem(item: horario) {
        mHorarios?.add(item);
        notifyItemInserted(itemCount)
        salvar()
    }

    public fun updateList(item: horario?) {
        if (item != null) {
            insertItem(item)
            salvar()
        }

    }

    public fun salvar(){
        val gs = com.google.gson.Gson();
        var strHorarios : String = gs.toJson(mHorarios)
        global.salvarpref("check_point",strHorarios)
    }

    public fun carregar(){
        val gs = com.google.gson.Gson();
        var strHorarios : String = global.lerpref("check_point")
        if (!strHorarios.isNullOrEmpty()) {
            var mhoarios: MutableList<horario> = gs.fromJson<MutableList<horario>>(strHorarios, object : TypeToken<MutableList<horario>>() {}.type)
            mHorarios = mhoarios;
        }
        //var mhoario : List<horario> = gs.fromJson<List<horario>>(strHorarios);
    }

}

public class horario(val checkpoint:String , val Titulo:String, val Horario:String )

public abstract class TLstHorarios : MutableList<horario>