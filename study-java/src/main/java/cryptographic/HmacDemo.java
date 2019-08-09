package cryptographic;

import org.apache.commons.net.util.Base64;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by zouziwen on 2019/3/1.
 */
public class HmacDemo {

    //签名算法HmacSha256
    public static final String HMAC_SHA256 = "HmacSHA256";

    private static final String ENCODING = "UTF-8";

    private static final Digest digest = new SHA256Digest();

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeyException {

        String message = "HelloWorld";
        String secret = "DemoSecret1";


//        Mac hmacSha256 = Mac.getInstance(HMAC_SHA256);
//        byte[] keyBytes = secret.getBytes(ENCODING);
//        hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, HMAC_SHA256));
//        String ret = new String(Base64.encodeBase64(hmacSha256.doFinal(message.getBytes(ENCODING))), ENCODING);

        String ret = new String(Base64.encodeBase64(implHmac(secret , 64 , message , new SHA256Digest())) , ENCODING);

        System.out.println(ret);

    }


    private static byte[] implHmac(String key , int blockSize , String message , Digest digest) throws UnsupportedEncodingException {

        int keyLength = key.length();
        byte[] keyBytes = key.getBytes();

        byte[] inputPad = new byte[blockSize];
        byte[] outputPad = new byte[blockSize];

        if(keyLength > blockSize) {
            // hash operation
            digest.update(keyBytes , 0, keyLength);
            digest.doFinal(inputPad, 0);

        }else {
            System.arraycopy(keyBytes , 0 , inputPad , 0 , keyLength);

            // 当 < blockSize时，后续的数要补0
            for(int i = keyLength ; i < inputPad.length ; i++) {
                inputPad[i] = 0;
            }
        }

        System.arraycopy(inputPad, 0, outputPad, 0, blockSize);

        // xor inputPad , use 0x5c
        xorPad(inputPad , blockSize , 0x5c);
        // xor outputPad , use 0x36
        xorPad(outputPad , blockSize , 0x36);

        // H(inputPad + message)
        // compose inputPad and message

        // H(outputPad + H(inputPad + message))
        return composeAndHash(outputPad , composeAndHash(inputPad , message.getBytes(ENCODING) , digest) , digest);


    }

    private static byte[] composeAndHash(byte[] inputPad, byte[] otherPad , Digest digest) {
        byte[] composeInputPadAndMessage = new byte[inputPad.length + otherPad.length];
        System.arraycopy(inputPad , 0 , composeInputPadAndMessage , 0 , inputPad.length);
        System.arraycopy(otherPad , 0 , composeInputPadAndMessage , inputPad.length , otherPad.length);

        digest.update(composeInputPadAndMessage , 0 , composeInputPadAndMessage.length);
        digest.doFinal(composeInputPadAndMessage , 0);

        return composeInputPadAndMessage;
    }

    private static void xorPad(byte[] arrs, int blockSize, int v) {
        for(int i = 0 ; i < blockSize ; i++ ) {
            arrs[i] ^= v;
        }
    }


}
