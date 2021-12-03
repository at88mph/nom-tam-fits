package nom.tam.util;

/*
 * #%L
 * nom.tam FITS library
 * %%
 * Copyright (C) 1996 - 2021 nom-tam-fits
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.junit.Test;

public class FitsStreamTest {

    @Test
    public void testReadWriteBooleanObjects() throws Exception {
        ByteArrayOutputStream bo = new ByteArrayOutputStream(100);
        
        Boolean[] b = new Boolean[] { Boolean.TRUE, Boolean.FALSE, null };
        
        FitsOutputStream o = new FitsOutputStream(bo);
        o.write(b);
        o.writeBoolean(b[0]);
        o.flush();
        
        FitsInputStream i = new FitsInputStream(new ByteArrayInputStream(bo.toByteArray()));
        
        Boolean[] b2 = new Boolean[b.length];
        i.read(b2);
        
        for(int k=0; k<b.length; k++) {
            assertEquals("[" + k + "]", b[k], b2[k]);
        }
        
        assertEquals("standalone", b[0].booleanValue(), i.readBoolean());
    }
    
    @Test
    public void testTrickleInputStream() throws Exception {
        Random random = new Random();
        
        InputStream reader = new InputStream() {

            @Override
            public int read() throws IOException {
                return 1;
            }

            @Override
            public int read(byte[] b, int from, int length) throws IOException {
                return random.nextBoolean() ? 1 : 0;
            }            
        };
        
        try(FitsInputStream in = new FitsInputStream(reader)) {
            in.setAllowBlocking(true);
            assertTrue(in.isAllowBlocking());
            for(int i=0; i<100; i++) {
                // read() should never return -1 (EOF), but may block a while...
                assertNotEquals(-1, in.read());
            }
        }  
    }
    
    @Test
    public void testTrickleInputStreamEOF() throws Exception {
        InputStream reader = new InputStream() {

            @Override
            public int read() throws IOException {
                return 1;
            }

            @Override
            public int read(byte[] b, int from, int length) throws IOException {
                return 0;
            }            
        };
        
        try(FitsInputStream in = new FitsInputStream(reader)) {
            in.setAllowBlocking(false);
            assertFalse(in.isAllowBlocking());
            
            // We expect an exception from the underlying would block read
            assertEquals(-1, in.read());
        }  
    }
    
    @Test
    public void testTrickleReadFully() throws Exception {
        Random random = new Random();
        
        InputStream reader = new InputStream() {

            @Override
            public int read() throws IOException {
                return 1;
            }

            @Override
            public int read(byte[] b, int from, int length) throws IOException {
                return random.nextBoolean() ? 1 : 0;
            }            
        };
        
        try(FitsInputStream in = new FitsInputStream(reader)) {
            in.setAllowBlocking(true);
            assertTrue(in.isAllowBlocking());
            // Despite the trickle, this should not block...
            in.readFully(new byte[100]);
        }  
    }
    
    
    @Test(expected = EOFException.class)
    public void testTrickleReadFullyEOF() throws Exception {
        Random random = new Random();
        
        InputStream reader = new InputStream() {

            @Override
            public int read() throws IOException {
                return 1;
            }

            @Override
            public int read(byte[] b, int from, int length) throws IOException {
                return random.nextBoolean() ? 1 : 0;
            }            
        };
        
        try(FitsInputStream in = new FitsInputStream(reader)) {
            in.setAllowBlocking(false);
            assertFalse(in.isAllowBlocking());
            // This should throw an EOFException if the stream is blocked.
            in.readFully(new byte[100]);
        }  
    }
   
    @Test
    public void testTrickleDecode() throws Exception {
        Random random = new Random();
        
        InputStream reader = new InputStream() {

            @Override
            public int read() throws IOException {
                return 1;
            }

            @Override
            public int read(byte[] b, int from, int length) throws IOException {
                return random.nextBoolean() ? 1 : 0;
            }            
        };
        
        try(FitsInputStream in = new FitsInputStream(reader)) {
            in.setAllowBlocking(true);
            assertTrue(in.isAllowBlocking());
            // Despite the trickle, this should not block...
            in.readArrayFully(new double[100]);
        }  
    }
    
    @Test(expected = EOFException.class)
    public void testTrickleDecodeEOF() throws Exception {
        Random random = new Random();
        
        InputStream reader = new InputStream() {

            @Override
            public int read() throws IOException {
                return 1;
            }

            @Override
            public int read(byte[] b, int from, int length) throws IOException {
                return random.nextBoolean() ? 1 : 0;
            }            
        };
        
        try(FitsInputStream in = new FitsInputStream(reader)) {
            in.setAllowBlocking(false);
            assertFalse(in.isAllowBlocking());
            // This should throw an EOFException if the stream is blocked.
            in.readArrayFully(new double[100]);
        }  
    }
    
}
