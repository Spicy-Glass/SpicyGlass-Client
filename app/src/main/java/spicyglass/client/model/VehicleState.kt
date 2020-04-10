package spicyglass.client.model

import org.json.JSONObject
import spicyglass.client.integration.external.APIResponse
import spicyglass.client.integration.external.SpicyApiTalker
import spicyglass.client.integration.system.SGLogger

/**
 * Class to track the vehicle state and call functions when needed to update the app's display.
 */
object VehicleState {
    var token: String = ""
    //TODO Have a way to pick vehicle ID correctly
    private val vehicle_id: String = "V-1"
    private var frontLeftLocked = true
    private var frontRightLocked = true
    private var rearLeftLocked = true
    private var rearRightLocked = true

    private var lockUpdatedFunc: ((Boolean, Boolean, Boolean, Boolean) -> Unit)? = null
    @JvmStatic
    fun updateLocks(frontLeftLocked: Boolean, frontRightLocked: Boolean, rearLeftLocked: Boolean, rearRightLocked: Boolean) {
        var changed = false
        if (VehicleState.frontLeftLocked != frontLeftLocked) {
            VehicleState.frontLeftLocked = frontLeftLocked
            changed = true
        }
        if (VehicleState.frontRightLocked != frontRightLocked) {
            VehicleState.frontRightLocked = frontRightLocked
            changed = true
        }
        if (VehicleState.rearLeftLocked != rearLeftLocked) {
            VehicleState.rearLeftLocked = rearLeftLocked
            changed = true
        }
        if (VehicleState.rearRightLocked != rearRightLocked) {
            VehicleState.rearRightLocked = rearRightLocked
            changed = true
        }
        if (changed) {
            lockUpdatedFunc?.invoke(frontLeftLocked, frontRightLocked, rearLeftLocked, rearRightLocked)
        }
    }

    @JvmStatic
    fun setLockUpdatedFunc(func: ((Boolean, Boolean, Boolean, Boolean) -> Unit)?) {
        lockUpdatedFunc = func
    }

    @JvmStatic
    fun getStates() {
        SpicyApiTalker.getVehicleState(vehicle_id, this::onStatesRetrieved)
    }

    private fun onStatesRetrieved(resp: APIResponse<JSONObject?>) {
        if(resp.success && resp.response != null) {
            val json = resp.response as JSONObject
            SGLogger.info(json.toString())
            //TODO update this with the rest of the data from the json as it gets implemented
            updateLocks(json.getBoolean("carLock"), true, true, true)
        } else {
            SGLogger.error(resp.errorMessage ?: "Error retrieving vehicle state information: ${resp.httpCode}")
        }
    }
}