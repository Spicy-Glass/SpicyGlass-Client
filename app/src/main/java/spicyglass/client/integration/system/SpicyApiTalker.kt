package spicyglass.client.integration.system

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object SpicyApiTalker {
    private const val apiUrl = "https://deployment-test-5tfsskgkda-uc.a.run.app/"
    //GET requests
    private const val GET_FULL_DB = "get_full_database"
    //POST requests
    //Takes username and password
    private const val GET_VEHICLE_ID = "get_vehicle_id"
    //Takes vehicle_id
    private const val GET_VEHICLE_DATA = "get_vehicle_data"
    //Takes vehicle_id, key, new_val, subkey (optional)
    private const val SET_VALUE = "set_val"

    @JvmStatic
    private fun makeRequest(endpoint: String, vararg keyValuePostArgs: Pair<String, String>): JSONObject? {
        var ret: JSONObject? = null
        var response: String? = null
        //Start the communication on a new thread, because it should not be done on the main thread
        Thread(Runnable() {
            var attempts = 0
            var lastResponse = ""
            //We're going to make a few attempts to get a valid response if needed, in case a packet was dropped or whatever
            while(ret == null && attempts++ < 3) {
                val con = URL(apiUrl + endpoint).openConnection() as HttpURLConnection
                con.setRequestProperty("Content-Type", "application/json")
                //GET_FULL_DB is the only one that uses GET at the moment
                con.requestMethod = if(endpoint == GET_FULL_DB) "GET" else "POST"
                for(pair: Pair<String, String> in keyValuePostArgs)
                    con.setRequestProperty(pair.first, pair.second)

                val responseCode = con.responseCode
                //HTTP Response code 200 means OK
                if (responseCode == 200) {
                    val br = BufferedReader(InputStreamReader(con.inputStream))
                    var inputLine: String?
                    val content = StringBuffer()
                    while (true) {
                        inputLine = br.readLine()
                        if (inputLine == null)
                            break;
                        content.append(inputLine)
                    }
                    br.close()
                    con.disconnect()
                    ret = JSONObject(content.toString())
                } else {//Other response codes mean something went wrong
                    SGLogger.info("Response was %s", responseCode)
                    lastResponse = responseCode.toString()
                }
            }
            //If we didn't get a valid response, set the response code we were given instead
            if(ret == null)
                response = lastResponse
        }).start()
        //TODO loading wheel or something while waiting on response? App will probably go into App Not Responding if this takes too long, there is probably a better way
        while(ret == null && response == null) {
            Thread.sleep(20)
        }
        //TODO handle response != null, which means we didn't get an expected response from the server.
        return ret
    }

    @JvmStatic
    fun getFullDB(): JSONObject? {
        return makeRequest(GET_FULL_DB)
    }

    @JvmStatic
    fun getVehicleId(username: String, password: String): String? {
        //TODO This jsonobject toString probably isn't exactly what we want, I will test this and find out what needs to be retrieved from the JSON
        return makeRequest(GET_VEHICLE_ID, Pair("username", username), Pair("password", password))?.toString()
    }
}