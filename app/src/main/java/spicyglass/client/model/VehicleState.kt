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
    val vehicleId: String = "V-1"
    //Track all states of the active vehicle
    var frontLeftLocked = true
    var frontRightLocked = true
    var rearLeftLocked = true
    var rearRightLocked = true
    var frontLeftSeatHeating = false
    var frontRightSeatHeating = false
    var rearLeftSeatHeating = false
    var rearRightSeatHeating = false

    //Function to be called when the vehicle state is updated. There will only be one if we are on the Locker screen, as that's the only time something on the display will need to be updated with the new states
    private var lockUpdatedFunc: ((Boolean, Boolean, Boolean, Boolean) -> Unit)? = null
    private var seatHeatingUpdatedFunc: ((Boolean, Boolean, Boolean, Boolean) -> Unit)? = null

    /**
     * Update the stored lock state of the vehicle.
     */
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
            //If the state is any different and the update function is not null, call the update function to update what's on the screen.
            lockUpdatedFunc?.invoke(frontLeftLocked, frontRightLocked, rearLeftLocked, rearRightLocked)
        }
    }
    
    @JvmStatic
    fun updateSeatHeaters(frontLeftSeatHeating: Boolean, frontRightSeatHeating: Boolean, rearLeftSeatHeating: Boolean, rearRightSeatHeating: Boolean) {
        var changed = false
        if (VehicleState.frontLeftSeatHeating != frontLeftSeatHeating) {
            VehicleState.frontLeftSeatHeating = frontLeftSeatHeating
            changed = true
        }
        if (VehicleState.frontRightSeatHeating != frontRightSeatHeating) {
            VehicleState.frontRightSeatHeating = frontRightSeatHeating
            changed = true
        }
        if (VehicleState.rearLeftSeatHeating != rearLeftSeatHeating) {
            VehicleState.rearLeftSeatHeating = rearLeftSeatHeating
            changed = true
        }
        if (VehicleState.rearRightSeatHeating != rearRightSeatHeating) {
            VehicleState.rearRightSeatHeating = rearRightSeatHeating
            changed = true
        }
        if (changed) {
            //If the state is any different and the update function is not null, call the update function to update what's on the screen.
            seatHeatingUpdatedFunc?.invoke(frontLeftSeatHeating, frontRightSeatHeating, rearLeftSeatHeating, rearRightSeatHeating)
        }
    }

    /**
     * Set the function to be called when the lock states change. Set to null to clear the function.
     */
    @JvmStatic
    fun setLockUpdatedFunc(func: ((Boolean, Boolean, Boolean, Boolean) -> Unit)?) {
        lockUpdatedFunc = func
    }
    
    @JvmStatic
    fun setSeatHeatingUpdatedFunc(func: ((Boolean, Boolean, Boolean, Boolean) -> Unit)?) {
        seatHeatingUpdatedFunc = func
    }

    @JvmStatic
    fun getStates() {
        SpicyApiTalker.getVehicleState(vehicleId, this::onStatesRetrieved)
    }

    private fun onStatesRetrieved(resp: APIResponse<JSONObject?>) {
        if(resp.success && resp.response != null) {
            val json = resp.response!!.getJSONObject("states")
            //SGLogger.info(json.toString())
            //TODO update this with the rest of the data from the json as it gets implemented
            val carLockObj = json.getJSONObject("carLock")
            updateLocks(carLockObj.getBoolean(SpicyApiTalker.FRONT_LEFT), carLockObj.getBoolean(SpicyApiTalker.FRONT_RIGHT), carLockObj.getBoolean(SpicyApiTalker.REAR_LEFT), carLockObj.getBoolean(SpicyApiTalker.REAR_RIGHT))
            val seatHeatObj = json.getJSONObject("seatHeater")
            updateSeatHeaters(seatHeatObj.getBoolean(SpicyApiTalker.FRONT_LEFT), seatHeatObj.getBoolean(SpicyApiTalker.FRONT_RIGHT), seatHeatObj.getBoolean(SpicyApiTalker.REAR_LEFT), seatHeatObj.getBoolean(SpicyApiTalker.REAR_RIGHT))
        } else {
            SGLogger.error(resp.errorMessage ?: "Error retrieving vehicle state information: ${resp.httpCode}")
        }
    }
}