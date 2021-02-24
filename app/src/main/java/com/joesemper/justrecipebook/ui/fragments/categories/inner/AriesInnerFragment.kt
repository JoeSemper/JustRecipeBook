package com.joesemper.justrecipebook.ui.fragments.categories.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.CategoriesPresenter

class AriesInnerFragment(val presenter: CategoriesPresenter):Fragment(), AriesInnerView {

    companion object {
        fun newInstance(presenter: CategoriesPresenter) = AriesInnerFragment(presenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_aries_inner, null)

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun updateList() {
        TODO("Not yet implemented")
    }
}