package org.example.project.data.remote.exception

/**
 * Sealed class hierarchy representing all possible remote operation exceptions.
 *
 * This class encapsulates different types of errors that can occur during
 * remote API calls, providing a type-safe way to handle various failure scenarios.
 * Each exception type represents a specific category of error with appropriate
 * context for proper error handling and user feedback.
 *
 * Being a sealed class ensures exhaustive when-expression handling and prevents
 * external inheritance, maintaining a controlled set of possible error states.
 *
 * @param message The error message associated with this exception
 */
sealed class RemoteException(message: String) : Exception(message, null) {

    /**
     * Represents server-side errors (HTTP 500 status codes).
     *
     * This exception is thrown when the server encounters an internal error
     * or is temporarily unavailable. Typically indicates issues on the
     * server side that are outside of client control.
     */
    data object ServerError: RemoteException("")

    /**
     * Represents network connectivity issues.
     *
     * This exception is thrown when the client cannot establish a connection
     * to the server due to network problems, timeout issues, or server
     * unavailability. Indicates infrastructure-level connectivity problems.
     */
    data object ConnectionError: RemoteException("")

    /**
     * Represents JSON parsing or data serialization errors.
     *
     * This exception is thrown when the server response cannot be parsed
     * into the expected data structure. Usually indicates API contract
     * changes, malformed responses, or serialization configuration issues.
     */
    data object ParseError: RemoteException("")

    /**
     * Represents business logic errors returned by the API.
     *
     * This exception is thrown when the server successfully processes the request
     * but returns an error status indicating a business rule violation or
     * application-specific error condition.
     *
     * @param resultMessage The specific error message returned by the API,
     *                      providing context about what went wrong
     */
    data class ResultError(val resultMessage: String?): RemoteException(resultMessage ?: "")
}