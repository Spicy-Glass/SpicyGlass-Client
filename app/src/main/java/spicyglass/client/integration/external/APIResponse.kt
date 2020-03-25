package spicyglass.client.integration.external

class APIResponse<T>(var response: T, var httpCode: Int, var success: Boolean = true, var errorMessage: String? = null)