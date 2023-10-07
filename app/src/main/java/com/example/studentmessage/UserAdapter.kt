import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmessage.R
import com.example.studentmessage.User
import com.google.firebase.auth.FirebaseAuth

class UserAdapter : ListAdapter<User, UserAdapter.ItemHolder>(ItemComparator()) {

    companion object {
        private const val VIEW_TYPE_CURRENT_USER = 1
        private const val VIEW_TYPE_OTHER_USER = 2
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {

            val message = itemView.findViewById<TextView>(R.id.message)
            val rlTime = itemView.findViewById<TextView>(R.id.rlTime)


            message.text = user.message
            rlTime.text = user.datetime
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView: View

        if (viewType == VIEW_TYPE_CURRENT_USER) {
            itemView = layoutInflater.inflate(R.layout.user_list_item, parent, false)
        } else {
            itemView = layoutInflater.inflate(R.layout.contact_list_item, parent, false)
        }

        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun getItemViewType(position: Int): Int {
        val user = getItem(position)
        return if (userIsCurrentUser(user)) VIEW_TYPE_CURRENT_USER else VIEW_TYPE_OTHER_USER
    }

    private fun userIsCurrentUser(user: User): Boolean {
        // Получите текущего авторизованного пользователя, если такой существует
        val currentUser = FirebaseAuth.getInstance().currentUser

        // Проверьте, авторизован ли пользователь
        if (user.name == currentUser?.displayName) {
            return true
        } else {
            // Если пользователь не авторизован, верните false
            return false
        }
    }
}
