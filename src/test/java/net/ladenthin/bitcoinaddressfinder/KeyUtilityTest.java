// @formatter:off
/**
 * Copyright 2020 Bernard Ladenthin bernard.ladenthin@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
// @formatter:on
package net.ladenthin.bitcoinaddressfinder;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import net.ladenthin.bitcoinaddressfinder.staticaddresses.StaticKey;
import org.bitcoinj.base.Network;
import org.bitcoinj.crypto.ECKey;
import org.junit.Before;
import org.junit.Test;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class KeyUtilityTest {

    private final StaticKey staticKey = new StaticKey();
    private final Network network = new NetworkParameterFactory().getNetwork();
    
    @Before
    public void init() throws IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="createECKey">
    @Test
    public void createECKey_TestUncompressed() throws IOException {
        // arrange
        BigInteger bigIntegerFromHex = new BigInteger(staticKey.privateKeyHex, 16);

        // act
        ECKey key = new KeyUtility(network, new ByteBufferUtility(false)).createECKey(bigIntegerFromHex, false);

        // assert
        byte[] hash160 = key.getPubKeyHash();
        ByteBuffer hash160AsByteBuffer = new ByteBufferUtility(false).byteArrayToByteBuffer(hash160);
        assertThat(key.isCompressed(), is(equalTo(Boolean.FALSE)));
        assertThat(hash160AsByteBuffer, is(equalTo(staticKey.byteBufferPublicKeyUncompressed)));
    }

    @Test
    public void createECKey_TestCompressed() throws IOException {
        // arrange
        BigInteger bigIntegerFromHex = new BigInteger(staticKey.privateKeyHex, 16);

        // act
        ECKey key = new KeyUtility(network, new ByteBufferUtility(false)).createECKey(bigIntegerFromHex, true);

        // assert
        byte[] hash160 = key.getPubKeyHash();
        ByteBuffer hash160AsByteBuffer = new ByteBufferUtility(false).byteArrayToByteBuffer(hash160);
        assertThat(key.isCompressed(), is(equalTo(Boolean.TRUE)));
        assertThat(hash160AsByteBuffer, is(equalTo(staticKey.byteBufferPublicKeyCompressed)));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getHash160ByteBufferFromBase58String">
    @Test
    public void getHash160ByteBufferFromBase58String_TestUncompressed() throws IOException {
        // act
        ByteBuffer byteBufferPublicKeyUncompressed = new KeyUtility(network, new ByteBufferUtility(false)).getHash160ByteBufferFromBase58String(staticKey.publicKeyUncompressed);

        // assert
        assertThat(byteBufferPublicKeyUncompressed, is(equalTo(staticKey.byteBufferPublicKeyUncompressed)));
    }

    @Test
    public void getHash160ByteBufferFromBase58String_TestCompressed() throws IOException {
        // act
        ByteBuffer byteBufferPublicKeyCompressed = new KeyUtility(network, new ByteBufferUtility(false)).getHash160ByteBufferFromBase58String(staticKey.publicKeyCompressed);

        // assert
        assertThat(byteBufferPublicKeyCompressed, is(equalTo(staticKey.byteBufferPublicKeyCompressed)));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getHexFromByteBuffer">
    @Test
    public void getHexFromByteBuffer_TestUncompressed() throws IOException {
        // act
        String hexPublicKeyUncompressed = new ByteBufferUtility(false).getHexFromByteBuffer(staticKey.byteBufferPublicKeyUncompressed);

        // assert
        assertThat(hexPublicKeyUncompressed, is(equalTo(staticKey.publicKeyUncompressedHash160Hex)));
    }

    @Test
    public void getHexFromByteBuffer_TestCompressed() throws IOException {
        // act
        String hexPublicKeyCompressed = new ByteBufferUtility(false).getHexFromByteBuffer(staticKey.byteBufferPublicKeyCompressed);

        // assert
        assertThat(hexPublicKeyCompressed, is(equalTo(staticKey.publicKeyCompressedHash160Hex)));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getByteBufferFromHex">
    @Test
    public void getByteBufferFromHex_TestUncompressed() throws IOException {
        // act
        ByteBuffer byteBufferPublicKeyUncompressed = new ByteBufferUtility(false).getByteBufferFromHex(staticKey.publicKeyUncompressedHash160Hex);

        // assert
        assertThat(byteBufferPublicKeyUncompressed, is(equalTo(staticKey.byteBufferPublicKeyUncompressed)));
    }

    @Test
    public void getByteBufferFromHex_TestCompressed() throws IOException {
        // act
        ByteBuffer byteBufferPublicKeyCompressed = new ByteBufferUtility(false).getByteBufferFromHex(staticKey.publicKeyCompressedHash160Hex);

        // assert
        assertThat(byteBufferPublicKeyCompressed, is(equalTo(staticKey.byteBufferPublicKeyCompressed)));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createSecret">
    @Test
    public void createSecret() throws IOException {
        // act
        BigInteger secret = new KeyUtility(network, new ByteBufferUtility(false)).createSecret(PublicKeyBytes.PRIVATE_KEY_MAX_NUM_BITS, new Random(42));

        // assert
        assertThat(secret.toString(), is(not(equalTo(""))));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createKeyDetails">
    @Test
    public void createKeyDetails_Uncompressed() throws IOException, MnemonicException.MnemonicLengthException {
        // arrange
        ByteBufferUtility byteBufferUtility = new ByteBufferUtility(false);
        KeyUtility keyUtility = new KeyUtility(network, byteBufferUtility);

        BigInteger secret = new BigInteger(staticKey.privateKeyHex, 16);
        ECKey ecKey = keyUtility.createECKey(secret, false);

        // act
        String keyDetails = keyUtility.createKeyDetails(ecKey);

        // assert
        assertThat(keyDetails, is(equalTo("privateKeyBigInteger: [" + staticKey.privateKeyBigInteger + "] privateKeyBytes: [" + Arrays.toString(staticKey.privateKeyBytes) + "] privateKeyHex: [" + staticKey.privateKeyHex + "] WiF: [" + staticKey.privateKeyWiFUncompressed + "] publicKeyAsHex: [" + staticKey.publicKeyUncompressedHex + "] publicKeyHash160Hex: [" + staticKey.publicKeyUncompressedHash160Hex + "] publicKeyHash160Base58: [" + staticKey.publicKeyUncompressed + "] Compressed: [false] Mnemonic: " + MnemonicCode.INSTANCE.toMnemonic(ecKey.getPrivKeyBytes()))));
    }

    @Test
    public void createKeyDetails_Compressed() throws IOException, MnemonicException.MnemonicLengthException {
        // arrange
        ByteBufferUtility byteBufferUtility = new ByteBufferUtility(false);
        KeyUtility keyUtility = new KeyUtility(network, byteBufferUtility);

        BigInteger secret = new BigInteger(staticKey.privateKeyHex, 16);
        ECKey ecKey = keyUtility.createECKey(secret, true);

        // act
        String keyDetails = keyUtility.createKeyDetails(ecKey);

        // assert
        assertThat(keyDetails, is(equalTo("privateKeyBigInteger: [" + staticKey.privateKeyBigInteger + "] privateKeyBytes: [" + Arrays.toString(staticKey.privateKeyBytes) + "] privateKeyHex: [" + staticKey.privateKeyHex + "] WiF: [" + staticKey.privateKeyWiFCompressed + "] publicKeyAsHex: [" + staticKey.publicKeyCompressedHex + "] publicKeyHash160Hex: [" + staticKey.publicKeyCompressedHash160Hex + "] publicKeyHash160Base58: [" + staticKey.publicKeyCompressed + "] Compressed: [true] Mnemonic: " + MnemonicCode.INSTANCE.toMnemonic(ecKey.getPrivKeyBytes()))));
    }
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="bigIntegerToBytes">
    @Test
    public void bigIntegerToBytes_maxPrivateKeyGiven_returnWithoutLeadingZeros() throws IOException {
        // arrange
        BigInteger key = PublicKeyBytes.MAX_TECHNICALLY_PRIVATE_KEY;
        byte[] maxPrivateKey = key.toByteArray();
        assertThat(maxPrivateKey.length, is(equalTo(33)));

        // act
        byte[] keyWithoutLeadingZeros = KeyUtility.bigIntegerToBytes(key);

        // assert
        assertThat(keyWithoutLeadingZeros.length, is(equalTo(32)));
        
        // copy back
        byte[] arrayWithLeadingZero = new byte[33];
        System.arraycopy(keyWithoutLeadingZeros, 0, arrayWithLeadingZero, 1, 32);
        
        // assert content equals
        assertThat(arrayWithLeadingZero, is(equalTo(maxPrivateKey)));
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="killBits">
    @Test
    public void killBits_valueWithAllBitsSetGiven_bitsKilled() throws IOException {
        // act
        BigInteger secret = new KeyUtility(network, new ByteBufferUtility(false)).killBits(BigInteger.valueOf(63L), BigInteger.valueOf(5L));

        // assert
        assertThat(secret, is(equalTo(BigInteger.valueOf(58))));
    }
    
    @Test
    public void killBits_valueWithNotAllBitsSetGiven_bitsKilled() throws IOException {
        // act
        BigInteger secret = new KeyUtility(network, new ByteBufferUtility(false)).killBits(BigInteger.valueOf(62L), BigInteger.valueOf(5L));

        // assert
        assertThat(secret, is(equalTo(BigInteger.valueOf(58))));
    }
    // </editor-fold>
}
