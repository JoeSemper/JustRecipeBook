package com.joesemper.justrecipebook.ui.fragments.dialog.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.ui.util.image.IImageLoader
import kotlinx.android.synthetic.main.fragment_dialog_ingredient.*
import javax.inject.Inject

class IngredientDialogFragment: DialogFragment() {

    companion object {
        private const val IMG_ARG = "IMG"
        private const val TITLE_ARG = "TITLE"

        fun newInstance(imgUrl: String, description: String?) = IngredientDialogFragment().apply {
            arguments = Bundle().apply {
                putString(IMG_ARG, imgUrl)
                putString(TITLE_ARG, description)
            }
            App.instance.appComponent.inject(this)
        }
    }

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_dialog_ingredient, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(IMG_ARG)?.let { imgUrl ->
            imageLoader.loadInto(imgUrl, iv_fragment_ingredient)
        }
        arguments?.getString(TITLE_ARG)?.let { title ->
            tv_fragment_ingredient_title.text = title
        }
    }
}