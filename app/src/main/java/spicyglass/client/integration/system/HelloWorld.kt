package spicyglass.client.integration.system

object HelloWorld {
    @JvmStatic
    fun helloWorld() {
        Thread(Runnable {
            println(SpicyApiTalker.getFullDB()?.toString() ?: "DB was null!")
            println(SpicyApiTalker.getVehicleId("michael.bishop@ttu.edu", "blah") ?: "Vehicle ID was null!")
        }).start()
    }
}