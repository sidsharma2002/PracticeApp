package com.siddharth.practiceapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage
import com.siddharth.practiceapp.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SmartReplyViewModel : ViewModel() {

    private val _smartReply = MutableLiveData<Response<MutableList<String>>>()
    val smartReply: LiveData<Response<MutableList<String>>> = _smartReply

    private val _conversation: MutableList<TextMessage> = mutableListOf()
    private val smartReplyClient = SmartReply.getClient()

    fun getSmartReply(text: String) {
        viewModelScope.launch(Dispatchers.Default) {
//            _conversation.clear()
            _conversation.add(TextMessage.createForLocalUser(text, System.currentTimeMillis()))
            smartReplyClient.suggestReplies(_conversation)
                .addOnSuccessListener { result ->
                    if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        _smartReply.value?.data?.clear()
                        val tempList = mutableListOf<String>()
                        for (texts in result.suggestions) {
                            tempList.add(texts.text)
                        }
                        _smartReply.postValue(Response.Success(tempList))
                    }
                }
                .addOnFailureListener {
                    // TODO Error
                }
        }
    }
}