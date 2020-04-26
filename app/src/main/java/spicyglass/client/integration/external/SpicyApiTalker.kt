package spicyglass.client.integration.external

import org.json.JSONException
import org.json.JSONObject
import spicyglass.client.model.VehicleState
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
    private fun makeRequest(endpoint: String, vararg keyValuePostArgs: Pair<String, Any?>): APIResponse<JSONObject?> {
        var ret: JSONObject? = null
        var success = false
        var errorMessage: String? = null
        val con = URL(apiUrl + endpoint).openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json; utf-8")
        con.setRequestProperty("Accept", "application/json")
        if(keyValuePostArgs.isNotEmpty()) {
            con.doOutput = true
            var jsonInputString = "{"
            //Add all the key value pairs
            for (pair: Pair<String, Any?> in keyValuePostArgs)//Check if it is a String before adding the string quotation marks because we want numbers and booleans to be numbers and booleans when they get sent
                jsonInputString += if(pair.second is String) "\"" + pair.first + "\":\"" + pair.second + "\"," else "\"" + pair.first + "\":" + pair.second + ","
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
    fun attemptLogin(username: String, password: String, callbackFunc: (response: APIResponse<String?>) -> Unit) {
        Thread(Runnable {
            val idsResponse = makeRequest(ATTEMPT_LOGIN, Pair("username", username), Pair("password", password))
            var token: String? = null
            val respJson = idsResponse.response
            if(respJson != null)
                token = respJson["token"] as String
            callbackFunc.invoke(APIResponse(token, idsResponse.httpCode, idsResponse.success, idsResponse.errorMessage))
        }).start()
    }

    @JvmStatic
    fun checkToken(token: String, callbackFunc: (response: APIResponse<Boolean>) -> Unit) {
        Thread(Runnable {
            val idsResponse = makeRequest(CHECK_TOKEN, Pair("token", token))
            var validToken = false
            val respJson = idsResponse.response
            if(respJson != null)
                validToken = true
            callbackFunc.invoke(APIResponse(validToken, idsResponse.httpCode, idsResponse.success, idsResponse.errorMessage))
        }).start()
    }

    @JvmStatic
    fun getVehicleIds(callbackFunc: (response: APIResponse<List<String>>) -> Unit) {
        Thread(Runnable {
            val idsResponse = makeRequest(GET_VEHICLE_ID, Pair("token", VehicleState.token))
            val ids = ArrayList<String>()
            val respJson = idsResponse.response
            if(respJson != null)
                for(v: String in respJson.keys())
                    ids.add(respJson[v] as String)
            callbackFunc.invoke(APIResponse(ids, idsResponse.httpCode, idsResponse.success, idsResponse.errorMessage))
        }).start()
    }

    @JvmStatic
    fun getVehicleState(vehicleId: String, callbackFunc: (response: APIResponse<JSONObject?>) -> Unit) {
        Thread(Runnable {
            callbackFunc.invoke(makeRequest(GET_VEHICLE_DATA, Pair("token", VehicleState.token), Pair("vehicle_id", vehicleId)))
        }).start()
    }

    const val FRONT_LEFT = "fDriver"
    const val FRONT_RIGHT = "fPass"
    const val REAR_LEFT = "bDriver"
    const val REAR_RIGHT = "bPass"

    @JvmStatic
    fun updateLockState(vehicleId: String, slot: String, state: Boolean, callbackFunc: (response: APIResponse<Boolean>) -> Unit) {
        Thread(Runnable {
            val idsResponse = makeRequest(SET_VALUE, Pair("vehicle_id", vehicleId), Pair("key", "carLock"), Pair("subkey", slot), Pair("new_val", state), Pair("token", VehicleState.token), Pair("sender", "app"))
            val respJson = idsResponse.response
            val success = respJson?.get("success") as Boolean?
            callbackFunc.invoke(APIResponse(success ?: false, idsResponse.httpCode, idsResponse.success, idsResponse.errorMessage))
        }).start()
    }

    @JvmStatic
    fun updateSeatHeaterState(vehicleId: String, slot: String, state: Boolean, callbackFunc: (response: APIResponse<Boolean>) -> Unit) {
        Thread(Runnable {
            val idsResponse = makeRequest(SET_VALUE, Pair("vehicle_id", vehicleId), Pair("key", "seatHeater"), Pair("subkey", slot), Pair("new_val", state), Pair("token", VehicleState.token), Pair("sender", "app"))
            val respJson = idsResponse.response
            val success = respJson?.get("success") as Boolean?
            callbackFunc.invoke(APIResponse(success ?: false, idsResponse.httpCode, idsResponse.success, idsResponse.errorMessage))
        }).start()
    }
}