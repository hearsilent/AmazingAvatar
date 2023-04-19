package hearsilent.amazingavatar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hearsilent.amazingavatar.R
import hearsilent.amazingavatar.databinding.ItemDemoBinding
import java.util.*

class DemoAdapter : RecyclerView.Adapter<DemoAdapter.DemoViewHolder>() {

    class DemoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDemoBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        return DemoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_demo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.binding.textView.text =
            String.format(Locale.getDefault(), "HearSilent %d", position)
    }

    override fun getItemCount(): Int {
        return 100
    }
}