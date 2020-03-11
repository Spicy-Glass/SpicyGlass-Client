package spicyglass.client.integration.system

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HelloWorld {
    private const val apiUrl = "https://deployment-test-5tfsskgkda-uc.a.run.app/"
    private const val fullDbEndpoint = "get_full_database"
    @JvmStatic
    fun helloWorld() {
        Thread(Runnable() {
            val con = URL(apiUrl + fullDbEndpoint).openConnection() as HttpURLConnection
            con.setRequestProperty("Content-Type", "application/json")
            con.requestMethod = "GET"
            val response = con.responseCode
            if (response == 200) {
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
                val jsonObject = JSONObject(content.toString())
                SGLogger.info("We got data from the api. Vehicles:\n%s", jsonObject.getJSONObject("vehicles").toString(3))
            } else
                SGLogger.info("Response was %s", response)
        }).start()
    }
}