package com.siddharth.practiceapp.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.siddharth.practiceapp.bitmapModifiers.BitmapModifier
import com.siddharth.practiceapp.repository.HomeFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelA @Inject constructor(private val repository: HomeFeedRepository) : ViewModel() {

    private val TAG = "viewmodelA : "

    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap: LiveData<Bitmap> = _bitmap

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    fun setBitmap(bitmap: Bitmap, shouldProcessBitmap: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _bitmap.postValue(bitmap)
            if (shouldProcessBitmap) {
                _loading.postValue(true)
                val bitmapFiltered = processBitmap(bitmap)
                _loading.postValue(false)
                _bitmap.postValue(bitmapFiltered)
            }
        }
    }

    private fun processBitmap(bitmap: Bitmap): Bitmap {
        val newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        return newBitmap
        //return BitmapModifier.applyFilter(newBitmap, BitmapModifier.GREY_FILTER)
    }

    fun setCurrentNumber(currPage: Int) {
        _currentPage.value = currPage
    }
}