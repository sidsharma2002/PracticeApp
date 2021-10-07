package com.siddharth.practiceapp.viewModels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.*
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.siddharth.practiceapp.BitmapModifiers.BitmapModifier
import com.siddharth.practiceapp.worker.MyWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ViewModelA : ViewModel() {
    private val TAG = "viewmodelA : "

    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap: LiveData<Bitmap> = _bitmap

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _currentPage = MutableLiveData<Int>()
    val currentPage : LiveData<Int> = _currentPage

    fun setBitmap(bitmap: Bitmap, shouldProcessBitmap : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            if(shouldProcessBitmap) {
                val bitmapFiltered = processBitmap(bitmap)
                _loading.postValue(false)
                _bitmap.postValue(bitmapFiltered)
            }
            else{
                _loading.postValue(false)
                _bitmap.postValue(bitmap)
            }
        }
    }

    private fun processBitmap(bitmap: Bitmap): Bitmap {
        val newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        return BitmapModifier.applyFilter(newBitmap, BitmapModifier.GREY_FILTER)
    }

    fun setCurrentNumber(currPage : Int) {
            _currentPage.value = currPage
    }
}