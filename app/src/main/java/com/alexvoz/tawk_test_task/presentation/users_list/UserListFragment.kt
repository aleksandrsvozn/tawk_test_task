package com.alexvoz.tawk_test_task.presentation.users_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexvoz.tawk_test_task.R
import com.alexvoz.tawk_test_task.data.user.User
import com.alexvoz.tawk_test_task.data.wrappers.Resource
import com.alexvoz.tawk_test_task.presentation.main.MainShareViewModel
import com.alexvoz.tawk_test_task.presentation.profile.USER_AVATAR_URL
import com.alexvoz.tawk_test_task.presentation.profile.USER_ID
import com.alexvoz.tawk_test_task.presentation.profile.USER_LOGIN
import com.alexvoz.tawk_test_task.repository.network.isInternetConnected
import com.alexvoz.tawk_test_task.utils.PaginationScrollListener
import com.alexvoz.tawk_test_task.utils.changeVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.coroutines.InternalCoroutinesApi


/**
 *  User List Fragment
 *
 *  Functionality:
 *  1) Display list of github users;
 *  2) Local database search;
 *  3) Swipe up refresh users list;
 *  4) Pagination scroll loading;
 */
@InternalCoroutinesApi
class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by activityViewModels()
    private val sharedViewModel: MainShareViewModel by activityViewModels()

    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        initUsersList()

        initObserverListeners()

        initViewsListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUsers()
    }

    private fun initUsersList() {
        userListRecycleView.apply {
            layoutManager = LinearLayoutManager(context)

            userListAdapter = UserListAdapter(
                onClick = { user, avatarImg -> openProfileFragment(user, avatarImg) }
            )

            adapter = userListAdapter
        }
    }

    private fun openProfileFragment(user: User, avatarImg: ImageView) {
        viewModel.cancelAnyUserLoadingJobJob()

        val bundle = bundleOf(
            USER_ID to user.id,
            USER_LOGIN to user.login,
            USER_AVATAR_URL to user.avatarUrl
        )

        val extras = FragmentNavigatorExtras(avatarImg to user.avatarUrl)

        findNavController().navigate(
            R.id.action_userListFragment_to_profileFragment,
            bundle, null, extras
        )
    }

    private fun initObserverListeners() {

        sharedViewModel.internetConnectionListener.observe(viewLifecycleOwner) {
            val isInternetConnectionReturned =
                it && it != sharedViewModel.internetConnectionListener.previousInternetConnectionState

            if (isInternetConnectionReturned) {
                viewModel.getUsers()
            }

            userListNoWifiImg.changeVisibility(!it)
        }

        viewModel.users.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            var showLoading = false

            when (it) {

                is Resource.Loading -> {

                    userListSearchInput.text?.clear()

                    if (!userListAdapter.isLastLoadingItemIsShown) {
                        showLoading = true
                    }
                }

                is Resource.Success -> {
                    userListAdapter.showLastLoadingItem(false)
                    viewModel.isUsersLoading = false
                }

                is Resource.Error -> {
                    userListAdapter.showLastLoadingItem(false)

                    Snackbar.make(userListToolbar, getString(R.string.error), Snackbar.LENGTH_LONG)
                        .show()
                }
            }

            it.getData?.let { users -> userListAdapter.submitList(users) }

            userListSwipeRefreshLayout.isRefreshing = showLoading
        })
    }

    private fun initViewsListeners() {

        userListSwipeRefreshLayout.setOnRefreshListener { viewModel.getUsers() }

        userListRecycleView.addOnScrollListener(object :
            PaginationScrollListener(userListRecycleView.layoutManager as LinearLayoutManager) {

            override fun loadMoreItems() {
                viewModel.isUsersLoading = true
                userListAdapter.showLastLoadingItem(true)
                viewModel.getUsers(viewModel.users.value?.getData ?: listOf())
            }

            override val isPaginationAvailable: Boolean
                get() = userListSearchInput.text?.isEmpty() ?: false &&
                        !viewModel.isUsersLoading &&
                        this@UserListFragment.requireContext().isInternetConnected
        })

        userListSearchInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchUsers(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                //Do nothing
            }
        })
    }
}