package org.guiVista.core

// A UUID, or Universally unique identifier, is intended to uniquely identify information in a distributed environment.
// For the definition of UUID, see RFC 4122. The creation of UUIDs does not require a centralized authority. UUIDs are
// of relatively small size (128 bits, or 16 bytes). The common string representation
// (ex: 1d6c0810-2bd6-45f3-9890-0268422a6f14) needs 37 bytes.
//
// The UUID specification defines 5 versions, and calling the randomUuidString function will generate a unique (or
// rather random) UUID of the most common version, version 4.

/**
 * Parses the string str and verify if it is a UUID. This function accepts the following syntax:
 * simple forms (e.g. **f81d4fae-7dec-11d0-a765-00a0c91e6bf6**)
 *
 * Note that hyphens are required within the UUID string itself, as per the aforementioned RFC.
 * @param str A string representing a UUID.
 * @return A value of *true* if [str] is a valid UUID.
 */
public expect fun uuidStringIsValid(str: String): Boolean

/**
 * Generates a random UUID (RFC 4122 version 4) as a string. It has the same randomness guarantees as `GRand`, so must
 * **NOT** be used for cryptographic purposes such as key generation, nonces, salts or one-time pads.
 * @return A String that should be freed with `g_free()`.
 */
public expect fun randomUuidString(): String
