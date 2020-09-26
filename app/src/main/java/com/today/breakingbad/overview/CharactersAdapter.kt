package com.today.breakingbad.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.today.breakingbad.R
import com.today.network.model.Character
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharactersAdapter() :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {
    var charactersList: List<Character> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {

        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.update(charactersList[position])
    }

    override fun getItemCount(): Int = charactersList.size

    class CharacterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun update(character: Character) {
            view.char_name.text = character.name
            view.char_seasons.text = String.format(
                view.context.getString(R.string.char_item_seasons_label),
                character.appearance?.joinToString { season -> season.toString() }
            )

            Picasso.get().load(character.img).into(view.char_image);
            view.setOnClickListener {
                val navigationDirection =
                    CharactersOverviewFragmentDirections.actionListItemClicked(character)
                Navigation.findNavController(it).navigate(navigationDirection)
            }
        }
    }
}