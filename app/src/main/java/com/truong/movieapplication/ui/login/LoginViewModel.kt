package com.truong.movieapplication.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truong.movieapplication.data.models.Message
import com.truong.movieapplication.data.models.User
import com.truong.movieapplication.data.respository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _user = MutableLiveData<Result<User?>>()
    val user: LiveData<Result<User?>> get() = _user

    private val _selectedImage = MutableLiveData<Bitmap>()
    val selectedImage: LiveData<Bitmap> get() = _selectedImage

    private val _timeLeft = MutableLiveData<Long>()
    val timeLeft: LiveData<Long> get() = _timeLeft

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isTimeExceeded = MutableLiveData<Boolean>(false)
    val isTimeExceeded: LiveData<Boolean> get() = _isTimeExceeded

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private var countDownTimer: CountDownTimer? = null

    fun login(email: String, password: String) {
        loginRepository.login(email, password) { success, user, error ->
            if (success) {
                _loginResult.value = true
                _errorMessage.value = "Login successful"
            } else {
                _errorMessage.value = "Login failed: $error"
            }
        }
    }

    fun fetchMessages() {
        viewModelScope.launch {
            val result = loginRepository.getMessage()
            _messages.postValue(result)
        }
    }

    fun register(email : String, displayName: String, password: String, confirmPassword: String) {
        if (!isPasswordValid(password, confirmPassword)) {
            _errorMessage.value = "Password must be at least 6 characters and match"
            return
        }

        loginRepository.register(email, displayName, password) { success, user, error ->
            if (success) {
                _registerResult.value = true
                _errorMessage.value = "Register successful"
            } else {
                _errorMessage.value = "Register failed: $error"
            }
        }
    }

    fun getUserData(email: String) {
        loginRepository.getUserData(email) { success, user, error ->
            if (success) {
                _user.value = Result.success(user)
            } else {
                _errorMessage.value = "Get data failed: $error"
            }
        }
    }

    fun updateUserData(displayName: String, dob: String, gender: Boolean, address: String, phone: String) {
        val newUser = User(
            _user.value?.getOrNull()?.id ?: 0,
            displayName,
            _user.value?.getOrNull()?.email ?: "",
            _user.value?.getOrNull()?.password ?: "",
            _user.value?.getOrNull()?.avatar,
            _user.value?.getOrNull()?.wish_list,
            dob,
            gender,
            address,
            phone
        )

        loginRepository.updateUserData(newUser) { success, error ->
            if (success) {
                _user.value = Result.success(newUser)
                _errorMessage.value = "Update data successful"
            } else {
                _errorMessage.value = "Update data failed: $error"
            }
        }
    }

    fun addToWishList(email: String, newItem: Long) {
        loginRepository.getUserData(email) { success, user, error ->
            if (success && user != null) {
                val currentWishList: MutableList<Long> = user.wish_list?.map { it }?.toMutableList() ?: mutableListOf()
                if (!currentWishList.contains(newItem)) {
                    currentWishList.add(newItem)
                    loginRepository.updateWishList(email, currentWishList) { updateSuccess, updateError ->
                        if (updateSuccess) {
                            _user.value = Result.success(user.apply { wish_list = currentWishList })
                            _errorMessage.value = "Item added to wish list"
                        } else {
                            _errorMessage.value = "Update wish list failed: $updateError"
                        }
                    }
                } else {
                    _errorMessage.value = "Item already exists in the wish list"
                }
            } else {
                _errorMessage.value = "Get user data failed: $error"
            }
        }
    }

    fun startCountdown() {
        val interval = 1000L

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(300000L, interval) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                _timeLeft.value = 0
                _isTimeExceeded.value = false
            }
        }.start()
    }

    fun saveUserEmail(email: String) {
        loginRepository.saveEmail(email)
    }

    fun getUserEmail(): String? {
        return loginRepository.getEmail()
    }

    fun stopCountdown() {
        countDownTimer?.cancel()
        _timeLeft.value = 0
        _isTimeExceeded.value = false
    }

    fun saveCurrentTime() {
        val currentTime = System.currentTimeMillis()
        loginRepository.saveStartTime(currentTime)
        startCountdown()
    }

    fun checkTimeExceeded() {
        val startTime = loginRepository.getStartTime()
        val currentTime = System.currentTimeMillis()
        val timeElapsed = currentTime - startTime

        // Check if time has exceeded 5 minutes
        _isTimeExceeded.value = timeElapsed > 300000
    }

    fun openGallery(context: Context, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (context is Activity) {
            context.startActivityForResult(intent, requestCode)
        }
    }

    fun handleImageResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?, expectedRequestCode: Int) {
        if (requestCode == expectedRequestCode && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let {
                val inputStream = context.contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                _selectedImage.postValue(bitmap)
            }
        }
    }

    fun isPasswordValid(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && password.length >= 6
    }
}