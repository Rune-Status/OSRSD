/*
 * BCJ filter for little endian ARM instructions
 *
 * Author: Lasse Collin <lasse.collin@tukaani.org>
 *
 * This file has been put into the public domain.
 * You can do whatever you want with this file.
 */

package com.friz.cache.utility.tukaani.simple;

public interface SimpleFilter {
    int code(byte[] buf, int off, int len);
}
