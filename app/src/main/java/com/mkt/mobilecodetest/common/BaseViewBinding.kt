package com.mkt.mobilecodetest.common

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mkt.mobilecodetest.R
import com.mkt.mobilecodetest.databinding.ToolbarBinding

abstract class BaseViewBinding: AppCompatActivity() {
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())
        dialog = Dialog(this)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.setCancelable(false)
        setUpContents(savedInstanceState)

    }

    protected abstract fun getView(): View

    protected fun setupToolbar(isChild: Boolean) {
        val binding:ToolbarBinding = ToolbarBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)

        if (isChild) {
            if (supportActionBar != null) {

                /*final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                upArrow.setColorFilter(getResources().getColor(R.color.colorTextColorPrimary), PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                binding.toolbar.setNavigationIcon(R.drawable.left_arrow)
            }
        }
    }
    protected abstract fun setUpContents(savedInstanceState: Bundle?)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun showDialog(){
        dialog.show()
    }
    fun hideDialog(){
        dialog.dismiss()
    }

}