package spicyglass.client.integration.system

import org.json.JSONException
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
        //GET_FULL_DB is the only one that uses GET at the moment
        con.requestMethod = if(endpoint == GET_FULL_DB) "GET" else "POST"
        con.setRequestProperty("Content-Type", "application/json; utf-8")
        con.setRequestProperty("Accept", "application/json")
        //Only write parameters for POST, we get response code 405 (Method Not Allowed)
        if(con.requestMethod == "POST" && keyValuePostArgs.isNotEmpty()) {
            con.doOutput = true
            var jsonInputString = "{"
            //Add all the key value pairs
            for (pair: Pair<String, String> in keyValuePostArgs)
                jsonInputString += "\"" + pair.first + "\":\"" + pair.second + "\","
            //Trim off the final comma and add the close of the json object
            jsonInputString = jsonInputString.substring(0, jsonInputString.length - 1)+"}"
            //Send the parameters to the server
            con.outputStream.use { os ->
                val input: ByteArray = jsonInputString.byteInputStream(Charsets.UTF_8).readBytes()
                os.write(input, 0, input.size)
            }
        }

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
            try {
                ret = JSONObject(content.toString())
            } catch(e: JSONException) {
                SGLogger.warn("Response could not be formatted to JSON: %s", content.toString())
            }
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
    fun getVehicleIds(username: String, password: String): List<String> {
        val idsObj = makeRequest(GET_VEHICLE_ID, Pair("username", username), Pair("password", password))
        val ids = ArrayList<String>()
        if(idsObj != null)
            for(v: String in idsObj.keys())
                ids.add(idsObj[v] as String)
        return ids
    }
}