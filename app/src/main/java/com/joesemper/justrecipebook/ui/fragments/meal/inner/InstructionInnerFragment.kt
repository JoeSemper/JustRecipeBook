package com.joesemper.justrecipebook.ui.fragments.meal.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.MealPresenter
import kotlinx.android.synthetic.main.fragment_instruction_inner.*
import kotlinx.android.synthetic.main.fragment_meal.*

class InstructionInnerFragment(val presenter: MealPresenter): Fragment(), InstructionInnerView {

    companion object {
        fun newInstance(presenter: MealPresenter) = InstructionInnerFragment(presenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_instruction_inner, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onInstructionReady()
    }

    override fun init() {

    }

    override fun setInstruction(text: String) {
        tv_meal_instruction.text = text
    }

}