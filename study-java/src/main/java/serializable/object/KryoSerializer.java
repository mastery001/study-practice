/*******************************************************************************
 * Copyright (c) 1998, 2012 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *      Oracle - initial impl
 ******************************************************************************/
package serializable.object;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Uses Kyro to serialize the object.
 * @author James Sutherland
 */
public class KryoSerializer implements Serializer {
    private final Kryo kryo;

    private final Class<?> clazz;
    
    public KryoSerializer(Class<?> clazz) {
        this(new Kryo() , clazz);
    }

    public KryoSerializer(Kryo kryo , Class<?> clazz) {
        this.kryo = kryo;
        this.clazz = clazz;
    }

    public Kryo getKryo() {
        return kryo;
    }

    public byte[] serialize(Object object) {
        Output output = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            output = new Output(stream);
            this.kryo.writeObject(output, object);
            //stream.flush();
            output.close();
            return stream.toByteArray();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }finally {
            if(output != null) {
                output.close();
            }
        }
    }
    
    public Object deserialize(byte[] bytes) {
        Input input = null;
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            input = new Input(stream);
            Object result = this.kryo.readObject(input, clazz);
            input.close();
            return result;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }finally {
            if(input != null) {
                input.close();
            }
        }
    }
    
    public String toString() {
        return getClass().getSimpleName();
    }
}
