package com.example.hostelhubuser.Data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hostelhubuser.Complain
import com.example.hostelhubuser.hostel
import com.example.hostelhubuser.notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate


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
            .addOnFailureListener { exception->
                Log.e("Login", "Failed to get data for $uid", exception)
//                Log.d("Login","failed to get data " + uid)
                _studentData.value = null
            }
    }

    //UpdateUserData
    fun updateStudentData(student: Student) {

        student.id?.let {
            myRef.child(it).setValue(student)
                .addOnSuccessListener {
                    // Handle success, maybe show a Toast or navigate back
                    Log.d("Update","Updated Successfully")
                }
                .addOnFailureListener { exception ->
                    // Handle failure, maybe show an error message
                    Log.d("Update","Not Updated Successfully")
                }
        }
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


    // Room Allocation
    val RoomRef = database.getReference(hostel)



    fun createHostels() {
        // Create rooms for one floor
        fun createFloor(floorNum: Int): floor {
            val rooms = mutableListOf<Room>()
            for (i in 1..32) {
                rooms.add(
                    Room(
                        sid = "S${floorNum}${String.format("%02d", i)}",
                        roomNo = ((floorNum) * 100) + i,
                        occupied = false
                    )
                )
            }
            return floor(rooms)
        }

        // Create floors
        val floors = mutableListOf<floor>()
        for (i in 0..5) {
            floors.add(createFloor(i))
        }

        // Create hostels
        val boysHostel = Hostel("Boys Hostel", floors)
        val girlsHostel = Hostel("Girls Hostel", floors)

        // Save to Firebase
        RoomRef.child("Boys Hostel").setValue(boysHostel)
        RoomRef.child("Girls Hostel").setValue(girlsHostel)
    }




    // Hostel Allocation part

    fun allocateRoom(hostelName: String, floor: Int, onResult: (Boolean, String) -> Unit) {
        val floorRef = RoomRef.child(hostelName).child("floors").child(floor.toString()).child("room")
        val studentId = userid
        floorRef.get().addOnSuccessListener { snapshot ->
            for (roomSnapshot in snapshot.children) {
                val occupied = roomSnapshot.child("occupied").getValue(Boolean::class.java) ?: true
                if (!occupied) {
                    val roomNo = roomSnapshot.child("roomNo").getValue(Int::class.java) ?: -1
                    val updates = mapOf(
                        "sid" to studentId,
                        "occupied" to true
                    )

                    roomSnapshot.ref.updateChildren(updates).addOnSuccessListener {

                        val studentUpdates = mapOf(
                            "hostelName" to hostelName,
                            "roomNo" to roomNo.toString()
                        )

                        myRef.child(userid).updateChildren(studentUpdates).addOnSuccessListener {
                            onResult(true, "Room $roomNo allocated successfully in $hostelName!")
                            getUserData(userid)
                        }.addOnFailureListener {
                            onResult(false, "Room allocated but failed to update Student table.")
                        }


//
//                        onResult(true, "Room $roomNo allocated successfully!")
                    }.addOnFailureListener {
                        onResult(false, "Failed to allocate room.")
                    }
                    return@addOnSuccessListener
                }
            }
            onResult(false, "No available rooms on floor $floor.")
        }.addOnFailureListener {
            onResult(false, "Failed to fetch rooms.")
        }
    }


//    Notification

    val notref=database.getReference(notification)


    fun addNotification(message: String, hostels: String) {
        val notificationId = notref.push().key // Generate unique ID
        if (notificationId != null) {
            val notification = Notification(
                date = LocalDate.now().toString(), // Set current date
                id = notificationId,
                message = message,
                hostels = hostels
            )

            notref.child(notificationId).setValue(notification)
                .addOnSuccessListener {
                    Log.d("Firebase", "Notification added successfully!")
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Failed to add notification", e)
                }
        }
    }

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> get() = _notifications



    fun getNotifications() {
        notref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notificationList = mutableListOf<Notification>()
                for (notificationSnapshot in snapshot.children) {
                    try {
                        val notificationMap = notificationSnapshot.value as? Map<String, Any>
                        if (notificationMap != null) {
                            val notification = Notification(
                                id = notificationMap["id"] as? String ?: "",
                                date = notificationMap["date"] as? String ?: LocalDate.now().toString(),
                                message = notificationMap["message"] as? String ?: "",
                                hostels = notificationMap["hostels"] as? String ?: ""
                            )
                            notificationList.add(notification)
                        }
                    } catch (e: Exception) {
                        Log.e("NotificationViewModel", "Data mapping error: ${e.message}")
                    }
                }
                _notifications.postValue(notificationList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("NotificationViewModel", "Error fetching notifications: ${error.message}")
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

