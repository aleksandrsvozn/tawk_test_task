package com.alexvoz.tawk_test_task.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.alexvoz.tawk_test_task.R
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.data.wrappers.Resource
import com.alexvoz.tawk_test_task.presentation.main.MainShareViewModel
import com.alexvoz.tawk_test_task.utils.changeVisibility
import com.alexvoz.tawk_test_task.utils.hideKeyBoard
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

const val USER_ID = "user_id"
const val USER_LOGIN = "user_login"
const val USER_AVATAR_URL = "user_avatar_url"

/**
 *  Profile Fragment
 *
 *  Functionality:
 *  1) Display user data;
 *  2) Local database note savings;
 */
@InternalCoroutinesApi
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private val sharedViewModel: MainShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        profileAvatarImg.apply {

            transitionName = arguments?.getString(USER_AVATAR_URL)

            Glide.with(this).load(arguments?.getString(USER_AVATAR_URL)).into(this)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.initialization(arguments?.getLong(USER_ID), arguments?.getString(USER_LOGIN))

        initObserverListeners()

        initViewsListeners()
    }

    private fun initObserverListeners() {

        sharedViewModel.internetConnectionListener.observe(viewLifecycleOwner) {
            val isInternetConnectionReturned =
                it && it != sharedViewModel.internetConnectionListener.previousInternetConnectionState

            if (isInternetConnectionReturned) {
                viewModel.initialization(
                    arguments?.getLong(USER_ID),
                    arguments?.getString(USER_LOGIN)
                )
            }

            profileNoWifiImg.changeVisibility(!it)
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            when (it) {

                is Resource.Loading -> {
                    it.getData?.profile?.let { _ ->
                        setupUserUiData(it.getData!!)
                    }
                }

                is Resource.Success,
                is Resource.Error -> {
                    it.getData?.let { user -> setupUserUiData(user) }
                }
            }
        })
    }

    private fun initViewsListeners() {

        profileButtonSave.setOnClickListener {

            this.hideKeyBoard()
            viewModel.saveNote(profileNotesInput.text.toString())
            Snackbar.make(it, getString(R.string.note_saved), Snackbar.LENGTH_LONG).show()
        }

        profileToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    fun setupUserUiData(user: User) {

        profileToolbar.title = user.login.capitalize(Locale.getDefault())

        profileFollowers.text = "${getString(R.string.followers)}: ${user.profile?.followers ?: 0}"
        profileFollowing.text = "${getString(R.string.following)}: ${user.profile?.following ?: 0}"

        profileUserName.text =
            "${getString(R.string.name)}: ${user.profile?.name ?: getString(R.string.no_name)}"

        profileUserCompany.text =
            "${getString(R.string.company)}: ${user.profile?.company ?: getString(R.string.no_company)}"

        profileUserBlog.text =
            "${getString(R.string.blog)}: ${user.profile?.blog ?: getString(R.string.no_blog)}"

        profileNotesInput.setText(user.notes)
    }
}