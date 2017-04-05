# fragments-App-demo

Fragments-App-Demo is a submission to the challenge hosted by the Fragments Bot app company for their internship program.

It is made for demonstrating a simple chat application with the following specific features:

 - POST to an API. Posts to [Fragments Backend](http://fragmentstanvir.azurewebsites.net/messages) (DONE)
 
 - When there is no network connection available, the app saves the message and its time to a database as POST request parameter. For example: text=hhhh&time=1491400605485

 -  When there's a network (Wifi or Mobile), the app receives a broadcast indicating the change in the network. (DONE) 
 
 - On receiving the broadcast, the app checks whether there are messages to be sent in the Database. 
 
 - If there are messages to be sent, then it sends the messages one by one.

# Documentation

### Technologies used

* [Pusher](https://pusher.com/) - Pusher is a simple hosted API for quickly, easily and securely integrating realtime bi-directional functionality via WebSockets to web and mobile apps.

* [Android Asynchronous HTTP Client](https://github.com/AsyncHttpClient/async-http-client) - An asynchronous callback-based Http client for Android built on top of Apache’s HttpClient libraries.

### Functions

#### postMessage()
   
Method to connect device to the Pusher API and then send the message to the server. Runs when the "Send" button is pressed in the application.

Arguments : None.

Returns: None
.
#### isNetworkAvailable()

Method to check whether internet connectivity (Either Wifi or Mobile) is available to the device or not.

Arguments : None.

Returns : Boolean.

#### insertData()

Method to insert the messages which are to be stored in case of network unavailability.  

Arguments : String.

Returns : Boolean

#### getAllData()

Method to get all the entries in the table.
 
Arguments: None.

Returns : Cursor object.

