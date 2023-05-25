/**
 * <p>
 * The <a href="https://www.stsci.edu/software/hcompress.html">HCompress</a> algorithm, and its options (<i>primarily
 * for internal use</i>). HCompress is a lossless compression algorithms was developed by Richard L. White at the Space
 * Telescope Science Institute, and is used for the Hubble archive and the STScI Digital Sky Survey. It's weird that
 * it's a package by itself, but that's life.
 * </p>
 * <p>
 * The only classes in here that users would typically interact with are
 * {@link nom.tam.fits.compression.algorithm.hcompress.HCompressorOption} and potentially
 * {@link nom.tam.fits.compression.algorithm.hcompress.HCompressorQuantizeOption}, e.g. via
 * {@link nom.tam.image.compression.hdu.CompressedImageHDU#getCompressOption(Class)} to set options after Rice
 * compression was selected for compressing an image HDU.
 * </p>
 */

package nom.tam.fits.compression.algorithm.hcompress;

/*-
 * #%L
 * nom.tam FITS library
 * %%
 * Copyright (C) 1996 - 2023 nom-tam-fits
 * %%
 * This is free and unencumbered software released into the public domain.
 * 
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * #L%
 */
