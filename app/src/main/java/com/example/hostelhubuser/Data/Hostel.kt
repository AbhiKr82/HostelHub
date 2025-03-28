package com.example.hostelhubuser.Data

data class Hostel(
    var Name:String="",
    var floors : List<floor>
)

data class floor(
    var room:List<Room> = listOf()
)
data class Room(
    var sid :String ="",
    var roomNo:Int=0,
    var occupied:Boolean=false

)


/*
hostels
  ├── Boys Hostel
  │   ├── Name: "Boys Hostel"
  │   └── floors
  │       ├── 0
  │       │   └── room
  │       │       ├── 0
  │       │       │   ├── sid: "S101"
  │       │       │   ├── roomNo: 101
  │       │       │   └── occupied: false
  │       │       ├── 1
  │       │       │   ├── sid: "S102"
  │       │       │   ├── roomNo: 102
  │       │       │   └── occupied: false
  │       │       ... (up to 31)
  │       ├── 1
  │       ... (up to 4)
  └── Girls Hostel
      ... (same structure)
 */