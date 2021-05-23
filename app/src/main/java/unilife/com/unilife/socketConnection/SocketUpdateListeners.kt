package bubblebud.com.bubblebud.view.socketConnection

interface SocketUpdateListeners {

    fun onMessageReceived(receivedMessage: String)
    fun onStatusChanged(status:String)

}