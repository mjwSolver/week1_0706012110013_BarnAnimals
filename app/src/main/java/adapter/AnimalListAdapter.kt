package adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ex.week1_0706012110013_barnanimals.R
import com.ex.week1_0706012110013_barnanimals.databinding.CardAnimalBinding
import com.ex.week1_0706012110013_barnanimals.formActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import database.GlobalVar.Companion.animalList
import model.Animal

class AnimalListAdapter(var theList:List<Animal>):RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val itemview: View = LayoutInflater.from(parent.context).inflate(R.layout.card_animal, parent,false)
        return AnimalViewHolder(itemview)
    }

    override fun onBindViewHolder (holder: AnimalViewHolder, position: Int) {
        val currentItem: Animal = theList[position]

        val allClear = currentItem.uri!=""
        if(allClear){  holder.image.setImageURI(Uri.parse(currentItem.uri)) }
        holder.nickName.text = currentItem.name
        holder.jenisHewan.text = currentItem.type
        var message = "usia: " + currentItem.age.toString() + " tahun"
        holder.umurHewan.text = message
    }

    override fun getItemCount() = theList.size

    inner class AnimalViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
//    inner class AnimalViewHolder(itemView:View, listener:onItemClickListener):RecyclerView.ViewHolder(itemView){

        private var animalCard: CardAnimalBinding = CardAnimalBinding.bind(itemView)

        var image = animalCard.animalImageView
        var nickName: TextView = animalCard.nickNameTextView
        var jenisHewan: TextView = animalCard.jenisHewanTextView
        var umurHewan: TextView = animalCard.umurHewanTextInputlayout

        init {
//            itemView.setOnClickListener { listener.onItemClick(adapterPosition) }
            animalCard.editAnimalButton.setOnClickListener {

                if(adapterPosition>=0){
                    val theIntent = Intent(itemView.context, formActivity::class.java)
                        .putExtra("list_index", adapterPosition)
                        .putExtra("theURI", theList[adapterPosition].uri)
                        .putExtra("theName", theList[adapterPosition].name)
                        .putExtra("theType", theList[adapterPosition].type)
                        .putExtra("theAge", theList[adapterPosition].age)
                    it.context.startActivity(theIntent)
                } else {
                    Toast.makeText(it.context, "It's 0", Toast.LENGTH_SHORT).show()}
            }
//            Remove the thing without
            animalCard.deleteAnimalButton.setOnClickListener {

                val some = MaterialAlertDialogBuilder(it.context)
                    .setTitle("Delete Animal Data")
                    .setMessage("Are you sure you want to remove this data?")
                    .setPositiveButton("Delete") { function, which ->
                        val snacky = Snackbar.make(
                            animalCard.deleteAnimalButton,
                            "Animal Removed",
                            Snackbar.LENGTH_LONG
                        )
                        snacky.setAction("Dismiss") { snacky.dismiss() }
                        snacky.show()

                        animalList.removeAt(adapterPosition)
                        notifyDataSetChanged()
                    }
                    .setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                some.show()
            }

        }

    }
}