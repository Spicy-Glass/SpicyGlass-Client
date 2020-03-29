package spicyglass.client.integration.external

/**
 * Class to test our connection to the Spicy API
 */
object HelloWorld {
    @JvmStatic
    fun helloWorld() {
        SpicyApiTalker.getFullDB(::printResponseDetails)
        SpicyApiTalker.getVehicleIds(::printResponseDetails)
    }

    private fun <T> printResponseDetails(response: APIResponse<T>) {
        if(response.success) {
            println(response.response?.toString())
        } else {
            println("Response http status code was ${response.httpCode}")
            println("Response error message: ${response.errorMessage}")
        }
    }
}