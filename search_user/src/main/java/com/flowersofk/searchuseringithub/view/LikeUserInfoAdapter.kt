package com.flowersofk.searchuseringithub.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flowersofk.searchuseringithub.R
import com.flowersofk.searchuseringithub.model.UserInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_list_row_user.view.*

/**
 * 좋아요 유저 RecyclerView Adapter
 */
class LikeUserInfoAdapter(private val context: Context) :
    ListAdapter<UserInfo, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    fun setOnClickLikeListener(listner: OnClickLikeListener) {
        listener = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LikeUserInfoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null && repoItem.isFavorite) {
            (holder as LikeUserInfoViewHolder).bind(context, repoItem)
        }
    }

    companion object {

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<UserInfo>() {
            override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                // UserInfo 데이터 중 고유한 값으로 비교
                oldItem.node_id == newItem.node_id

            override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem == newItem
        }

        // 좋아요 Callback Listener
        var listener: OnClickLikeListener? = null
        interface OnClickLikeListener { fun onClick(view: View, data: UserInfo) }

        // 좋아요 항목 관리 HashMap
        var selectedList: HashMap<String, UserInfo> = hashMapOf()

    }

    class LikeUserInfoViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(context: Context, user: UserInfo) {

            Glide.with(context).load(user.avatar_url).into(containerView.iv_thumb)   // 썸네일

            containerView.tv_siteName.text = user.login // 사용자 명

            // 좋아요 버튼(애니메이션)
            var scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)
            scaleAnimation.duration = 500
            scaleAnimation.interpolator = BounceInterpolator()

            containerView.tb_favorite.setOnCheckedChangeListener(null)
            containerView.tb_favorite.isChecked = selectedList.containsKey(user.login)
            containerView.tb_favorite.setOnCheckedChangeListener { view, isFavorite ->
                view?.startAnimation(scaleAnimation)

                user.isFavorite = isFavorite

                // ViewModel을 호출하기 위해 Fragment영역에서 Callback처리
                listener?.onClick(view, user)

                // 좋아요 항목들은 별도 Local HashMap으로 관리 (순서가 중요하지 않고, 검색 시 LIST보다 빠르기 때문에 사용)
                if(isFavorite) {
                    selectedList.put(user.login, user)
                } else {
                    selectedList.remove(user.login)

                }

            }

            containerView.setOnClickListener {

                user.html_url.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    containerView.context.startActivity(intent)
                }

            }

        }

        companion object {
            fun create(parent: ViewGroup): LikeUserInfoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_list_row_user, parent, false)
                return LikeUserInfoViewHolder(view)
            }

        }

    }

}