package spicyglass.client.model

/**
 * Class to track the vehicle state and call functions when needed to update the app's display.
 */
object VehicleState {
    private val vehicle_id: String? = null
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
}