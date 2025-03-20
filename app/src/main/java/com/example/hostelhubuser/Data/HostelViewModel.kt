package com.example.hostelhubuser.Data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hostelhubuser.Complain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HostelViewModel : ViewModel() {

    private  val auth :FirebaseAuth= FirebaseAuth.getInstance()

    private  val _authState= MutableLiveData<AuthState>()

    val authState : LiveData<AuthState> = _authState

    var userid:String=""

    init {
        CheckAuthStatus()
        userid= getId().toString()

    }

    fun CheckAuthStatus(){
        if(auth.currentUser==null){
            _authState.value=AuthState.UnAuthenticated
        }else{
            _authState.value=AuthState.Authenticated
        }
    }

    fun login(email:String,password:String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value=AuthState.Error("Enter email and password")
            return
        }
        _authState.value=AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    _authState.value=AuthState.Authenticated
                    userid= getId().toString()
                }else{
                    _authState.value=AuthState.Error(task.exception?.message?:"Something went Wrong")
                }

            }
    }

    fun SignUP(email:String,password:String,name: String,number: String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value=AuthState.Error("Enter email and password")
            return
        }
        _authState.value=AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val user = result.user
                if (user != null) {
                    _authState.value = AuthState.Authenticated

                    // Creating user in database
                    val id = user.uid
                    val student = Student(
                        id = id,
                        email = email,
                        name = name,
                        number = number
                    )
                    addStudent(student)
                    userid= getId().toString()
                }
                else{
                    _authState.value=AuthState.Error("Something went Wrong")
                }
            }
            .addOnFailureListener { exception ->
                _authState.value =
                    AuthState.Error(exception.message ?: "Something went wrong")
            }
    }

    fun SignOut(){
        auth.signOut()
        _authState.value= AuthState.UnAuthenticated
    }


    fun getId(): String? {
        val user = auth.currentUser?.uid
       Log.d("UID",user.toString())

        return user
    }

    // Database
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Student")


    fun addStudent(student: Student) {
        student.id?.let {
            myRef.child(it).setValue(student)
                .addOnSuccessListener {
                    // Student added successfully
                }
                .addOnFailureListener { exception ->
                    _authState.value = AuthState.Error("Database Error: ${exception.message}")
                }
        }
    }


    //User Data
    private val _studentData = MutableLiveData<Student?>()
    val studentData: LiveData<Student?> = _studentData

    fun getUserData(uid: String) {
        myRef.child(uid).get()
            .addOnSuccessListener { snapshot ->
                _studentData.value = snapshot.getValue(Student::class.java)
            }
            .addOnFailureListener {
                _studentData.value = null
            }
    }

    //UpdateUserData
    fun updateStudentData(student: Student) {

        student.id?.let {
            myRef.child(it).setValue(student)
                .addOnSuccessListener {
                    // Handle success, maybe show a Toast or navigate back
                }
                .addOnFailureListener { exception ->
                    // Handle failure, maybe show an error message
                }
        }
    }

    // Room Allocation
    val RoomRef = database.getReference("Hostel")

    fun giveRoom(hostel: Hostel) {
        RoomRef.child(hostel.Name).setValue(hostel)
    }


    val complainRef = database.getReference(Complain)

    fun addComplain(name: String, number: String, topic: String, description: String) {
        var id = userid
        if (id != null) {


            val complain = Complain(
                id,
                name,
                number,
                topic,
                description
            )


            complainRef.child(id).push().setValue(complain)

        }
    }

    // new code
    private val _complaints = MutableLiveData<List<Complain>>()
    val complaints: LiveData<List<Complain>> get() = _complaints


    fun getComplain() {
        complainRef.child(userid) // Access the correct user node
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val complaintsList = mutableListOf<Complain>()
                    for (complainSnapshot in snapshot.children) {
                        try {
                            val complainMap = complainSnapshot.value as? Map<String, Any>
                            if (complainMap != null) {
                                val complain = Complain(
                                    sid = complainMap["sid"] as? String ?: "",
                                    name = complainMap["name"] as? String ?: "",
                                    number = complainMap["number"] as? String ?: "",
                                    topic = complainMap["topic"] as? String ?: "",
                                    desc = complainMap["desc"] as? String ?: "",
                                    resolved = complainMap["resolved"] as? Boolean?:false
                                )
                                complaintsList.add(complain)
                            }
                        } catch (e: Exception) {
                            Log.e("ComplainViewModel", "Data mapping error: ${e.message}")
                        }
                    }
                    _complaints.postValue(complaintsList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ComplainViewModel", "Error fetching complaints: ${error.message}")
                }
            })
    }




}

sealed class AuthState {
    object Authenticated : AuthState()
    object UnAuthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

