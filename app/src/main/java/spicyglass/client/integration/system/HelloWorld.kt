package spicyglass.client.integration.system

object HelloWorld {
    @JvmStatic
    fun helloWorld() {
        Thread(Runnable {
            println(SpicyApiTalker.getFullDB()?.toString() ?: "DB was null!")
            println(SpicyApiTalker.getVehicleIds("michael.bishop@ttu.edu", "blah"))
            //Now let's try with an invalid user
            println(SpicyApiTalker.getVehicleIds("michael.bishop@ttu.edu", "NotHisPassword"))
        }).start()
    }
}