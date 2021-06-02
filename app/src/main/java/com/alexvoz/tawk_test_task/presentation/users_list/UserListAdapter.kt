package com.alexvoz.tawk_test_task.presentation.users_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexvoz.tawk_test_task.R
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.utils.Util
import com.alexvoz.tawk_test_task.utils.changeVisibility
import com.alexvoz.tawk_test_task.utils.getDrawableImage
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*


private const val USER_HOLDER = 1
private const val LAST_LOADING_ITEM_HOLDER = 2

private val LAST_LOADING_ITEM = User(0, "loading_item", "loading_item", "loading_item")

/**
 *  User list adapter;
 *  Created by using DiffUtil
 *
 *  Have two Holders: USER_HOLDER and LAST_LOADING_ITEM_HOLDER (For pagination)
 *
 *  @param onClick ([User], ImageView) -> Unit
 */
class UserListAdapter(private val onClick: (user: User, avatarImg: ImageView) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var isLastLoadingItemIsShown = false

    fun showLastLoadingItem(show: Boolean) {
        if (show != isLastLoadingItemIsShown) isLastLoadingItemIsShown = show
    }

    /**
     *  Updating the list
     *  Add/Remove the last loading Item if its required
     */
    fun submitList(list: List<User>) {
        differ.submitList(
            list.toMutableList().apply {
                if (isLastLoadingItemIsShown && !list.contains(LAST_LOADING_ITEM))
                    add(LAST_LOADING_ITEM)
                else if (!isLastLoadingItemIsShown && list.contains(LAST_LOADING_ITEM))
                    remove(LAST_LOADING_ITEM)
            }
        )
    }

    /**
     *  @return 1 (USER_HOLDER) if is user object; Or 2 (LAST_LOADING_ITEM_HOLDER) if is a last position and isLastLoadingItemIsShown = true
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == differ.currentList.size - 1 && isLastLoadingItemIsShown) LAST_LOADING_ITEM_HOLDER else USER_HOLDER
    }

    /**
     *  @return Instance of [UserHolder] or [LAST_LOADING_ITEM_HOLDER]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            USER_HOLDER -> {
                UserHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_user, parent, false
                    ), onClick
                )
            }

            LAST_LOADING_ITEM_HOLDER -> {
                LastLoadingItemHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_user_last_loading, parent, false
                    )
                )
            }

            else -> {
                LastLoadingItemHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_user_last_loading, parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserHolder -> {
                holder.bind(differ.currentList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /**
     *  User Holder
     *  Every fourth element will be with inverted avatar colors and different background
     */
    class UserHolder constructor(
        itemView: View,
        private val onClick: (user: User, avatarImg: ImageView) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: User, position: Int) = with(itemView) {

            itemUserNameTextView.text = item.login.capitalize(Locale.getDefault())

            itemUserNoteImg.changeVisibility(item.notes.isNotBlank())

            this.itemUserLayout.background = context.getDrawableImage(
                if (position % 4 == 0) {
                    R.drawable.bg_item_user_second_color_ripple
                } else {
                    R.drawable.bg_item_user_ripple
                }
            )

            itemUserAvatarImg.apply {
                transitionName = item.avatarUrl

                colorFilter = if (position % 4 == 0) Util.getInvertedColorFilter() else null

                Glide.with(context)
                    .load(item.avatarUrl)
                    .error(context.getDrawableImage(R.drawable.ic_user))
                    .into(this)
            }

            setOnClickListener {
                onClick(item, itemUserAvatarImg)
            }
        }
    }

    class LastLoadingItemHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}