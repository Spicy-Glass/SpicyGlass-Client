package spicyglass.client.integration.external

import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * The class that talks to the Spicy API.
 * These functions take a callbackFunc parameter that will be called once the request to the API has
 *  been made and a response received. These will be called on a different thread than the initial
 *  function was called on.
 * Don't expect your callback function to be called instantly. Depending on internet connection, it
 *  may take time to get called. After one of these functions is called, some kind of loading
 *  indicator should be used so the user knows the app is working.
 */
object SpicyApiTalker {
    private const val apiUrl = "https://deployment-test-5tfsskgkda-uc.a.run.app/"
    //GET request
    @Deprecated("DEBUGGING USE ONLY")
    private const val GET_FULL_DB = "get_full_database"
    //POST requests
    //Takes token
    private const val LOGOUT = "revoke_token"
    //Takes token
    private const val CHECK_TOKEN = "verify_token"
    //Takes username and password
    private const val ATTEMPT_LOGIN = "attempt_login"
    //Takes token
    private const val GET_VEHICLE_ID = "get_vehicle_id"
    //Takes token, vehicle_id
    private const val GET_VEHICLE_DATA = "get_vehicle_data"
    //Takes token, vehicle_id, key, new_val, subkey (optional)
    private const val SET_VALUE = "set_val"

    /**
     * ALWAYS CALL THIS ON A DIFFERENT THREAD FROM MAIN. Doing this on main will result in App Not Responding because it takes too long to sit and wait for a response.
     */
    private fun makeRequest(endpoint: String, vararg keyValuePostArgs: Pair<String, String>): APIResponse<JSONObject?> {
        var ret: JSONObject? = null
        var success = false
        var errorMessage: String? = null
        val con = URL(apiUrl + endpoint).openConnection() as HttpURLConnection
        //GET_FULL_DB is the only one that uses GET, and it will be removed by the end of this
        con.requestMethod = if(endpoint == GET_FULL_DB) "GET" else "POST"
        con.setRequestProperty("Content-Type", "application/json; utf-8")
        con.setRequestProperty("Accept", "application/json")
        //Only write parameters for POST, or else we get response code 405 (Method Not Allowed)
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
                success = true
            } catch(e: JSONException) {
                errorMessage = content.toString()
            }
        }
        return APIResponse(ret, responseCode, success, errorMessage)
    }

    @JvmStatic
    @Deprecated("DEBUGGING USE ONLY")
    fun getFullDB(callbackFunc: (response: APIResponse<JSONObject?>) -> Unit) {
        Thread(Runnable{
            callbackFunc.invoke(makeRequest(GET_FULL_DB))
        }).start()
    }

    @JvmStatic
    fun attemptLogin(username: String, password: String, callbackFunc: (response: APIResponse<String?>) -> Unit) {
        Thread(Runnable {
            val idsResponse = makeRequest(GET_VEHICLE_ID, Pair("username", username), Pair("password", password))
            var token: String? = null
            val respJson = idsResponse.response
            if(respJson != null)
                token = respJson["token"] as String
            callbackFunc.invoke(APIResponse(token, idsResponse.httpCode, idsResponse.success, idsResponse.errorMessage))
        }).start()
    }

    @JvmStatic
    fun getVehicleIds(callbackFunc: (response: APIResponse<List<String>>) -> Unit) {
        Thread(Runnable {
            val idsResponse = makeRequest(GET_VEHICLE_ID, Pair("token", token))
            val ids = ArrayList<String>()
            val respJson = idsResponse.response
            if(respJson != null)
                for(v: String in respJson.keys())
                    ids.add(respJson[v] as String)
            callbackFunc.invoke(APIResponse(ids, idsResponse.httpCode, idsResponse.success, idsResponse.errorMessage))
        }).start()
    }
}