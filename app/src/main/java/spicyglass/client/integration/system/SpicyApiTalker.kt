package spicyglass.client.integration.system

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Do not call this class's functions from the main thread! Use:
 * Java: new Thread(r -> { CODE }).start();
 * Kotlin: Thread(Runnable { CODE }).start()
 * This does not handle the loading wheel or whatever the UI needs to do to indicate that it is connecting to the internet and retrieving data.
 */
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
            //TODO Potentially return this so whatever screen we are on can handle it?
        }
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