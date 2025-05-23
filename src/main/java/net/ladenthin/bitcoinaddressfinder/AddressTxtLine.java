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

import com.github.kiulian.converter.AddressConverter;
import java.nio.ByteBuffer;
import org.bitcoinj.base.Base58;
import org.bitcoinj.base.Coin;
import org.bitcoinj.base.SegwitAddress;
import org.bitcoinj.base.exceptions.AddressFormatException;
import org.jspecify.annotations.Nullable;


/**
 * Most txt files have a common format which uses Base58 address and separated
 * anmount.
 */
public class AddressTxtLine {

    /**
     * Should not be {@link Coin#ZERO} because it can't be written to LMDB.
     */
    public static final Coin DEFAULT_COIN = Coin.SATOSHI;

    public static final String IGNORE_LINE_PREFIX = "#";
    public static final String ADDRESS_HEADER = "address";
    
    
    private final static int VERSION_BYTES_REGULAR = 1;
    private final static int VERSION_BYTES_ZCASH = 2;

    /**
     * If no coins can be found in the line {@link #DEFAULT_COIN} is used.
     *
     * @param line The line to parse.
     * @param keyUtility The {@link KeyUtility}.
     * @return Returns an {@link AddressToCoin} instance.
     */
    @Nullable
    public AddressToCoin fromLine(String line, KeyUtility keyUtility) {
        String[] lineSplitted = SeparatorFormat.split(line);
        String address = lineSplitted[0];
        Coin amount = getCoinIfPossible(lineSplitted, DEFAULT_COIN);
        address = address.trim();
        if (address.isEmpty() || address.startsWith(IGNORE_LINE_PREFIX) || address.startsWith(ADDRESS_HEADER)) {
            return null;
        }

        if (address.startsWith("q")) {
            // q: bitcoin cash Base58 (P2PKH)
            // convert to legacy address
            address = AddressConverter.toLegacyAddress(address);
        }

        if (address.startsWith("d-") || address.startsWith("m-") || address.startsWith("s-")) {
            // blockchair format for Bitcoin (d-) and Bitcoin Cash (m-) and (s-) (P2MS)
            return null;
        } else if (address.startsWith("bc1")) {
            // bitcoin Bech32 (P2WSH or P2WPKH) or P2TR
            // supported (20 bytes): https://privatekeys.pw/address/bitcoin/bc1qazcm763858nkj2dj986etajv6wquslv8uxwczt
            SegwitAddress segwitAddress = SegwitAddress.fromBech32(address, keyUtility.network);
            byte[] hash = segwitAddress.getHash();
            ByteBuffer hash160 = keyUtility.byteBufferUtility.byteArrayToByteBuffer(hash);
            if (hash160.limit() != PublicKeyBytes.HASH160_SIZE) {
                // unsupported (32 bytes): https://privatekeys.pw/bitcoin/address/bc1qp762gmkychywl4elnuyuwph68hqw0uc2jkzu3ax48zfjkskslpsq8p66gf
                return null;
            }
            return new AddressToCoin(hash160, amount);
        } else if (address.startsWith("fc1")) {
            // feathercoin Bech32 (P2WSH or P2WPKH)
            // https://chainz.cryptoid.info/ftc/address.dws?fc1qvr9zesajsdw8aydcndd70wxj2wdgzu6zzltsph.htm
            return null;
        } else if (address.startsWith("lcc1")) {
            // litecoin cash Bech32 (P2WSH or P2WPKH)
            // https://chainz.cryptoid.info/lcc/address.dws?lcc1qrzlsxpjl0tynu3t2fkrw2ff2dgm0pv53ern0s5.htm
            return null;
        } else if (address.startsWith("ltc1")) {
            // litecoin Bech32 (P2WSH or P2WPKH)
            // https://privatekeys.pw/litecoin/address/ltc1qd5wm03t5kcdupjuyq5jffpuacnaqahvfsdu8smf8z0u0pqdqpatqsdrn8h
            return null;
        } else if (address.startsWith("nc1")) {
            // namecoin Bech32 (P2WSH or P2WPKH)
            // https://chainz.cryptoid.info/nmc/address.dws?nc1q2ml905jv7gx0d8z5f7kl23af0vtrjk4j0llmwr.htm
            return null;
        } else if (address.startsWith("vtc1")) {
            // vertcoin Bech32 (P2WSH or P2WPKH)
            // https://chainz.cryptoid.info/vtc/address.dws?vtc1qa4wejdlw9lmc7ks7l8hplc9fm394u79qjj0792.htm
            return null;
        } else if (address.startsWith("dgb1")) {
            // digibyte Bech32 (P2WPKH or P2SH)
            return null;
        } else if (address.startsWith("p")) {
            // p: bitcoin cash / CashAddr (P2SH), this is a unique format and does not work
            return null;
        } else if (address.startsWith("7") || address.startsWith("A") || address.startsWith("9") || address.startsWith("M") || address.startsWith("3") || address.startsWith("t") || address.startsWith("X") || address.startsWith("D") || address.startsWith("L") || address.startsWith("G") || address.startsWith("B") || address.startsWith("V") || address.startsWith("N") || address.startsWith("4") || address.startsWith("R")) {
            // prefix clashes for signs: 7
            //
            // Base58 P2SH
            // 7: dash
            // A: dogecoin
            // 9: dogecoin multisig
            // M: litecoin
            // 3: litecoin deprecated / bitcoin
            // t: Zcash
            //
            // Base58 P2PKH
            // X: dash
            // D: dogecoin / digibyte
            // L: litecoin
            // G: bitcoin gold
            // B: blackcoin
            // 7: feathercoin
            // V: vertcoin
            // N: namecoin
            // 4: novacoin
            // R: reddcoin
            // t: Zcash

            if (address.startsWith("t")) {
                // ZCash has two version bytes
                ByteBuffer hash160 = getHash160AsByteBufferFromBase58AddressUnchecked(address, keyUtility, VERSION_BYTES_ZCASH);
                return new AddressToCoin(hash160, amount);
            } else {
                ByteBuffer hash160 = getHash160AsByteBufferFromBase58AddressUnchecked(address, keyUtility, VERSION_BYTES_REGULAR);
                return new AddressToCoin(hash160, amount);
            }
        } else {
            // bitcoin Base58 (P2PKH)
            ByteBuffer hash160;
            try {
                hash160 = keyUtility.getHash160ByteBufferFromBase58String(address);
            } catch (AddressFormatException.InvalidChecksum e) {
                hash160 = getHash160AsByteBufferFromBase58AddressUnchecked(address, keyUtility, VERSION_BYTES_REGULAR);
            } catch (AddressFormatException.WrongNetwork e) {
                // bitcoin testnet
                hash160 = getHash160AsByteBufferFromBase58AddressUnchecked(address, keyUtility, VERSION_BYTES_REGULAR);
            } catch (AddressFormatException.InvalidDataLength e) {
                // too short address
                hash160 = getHash160AsByteBufferFromBase58AddressUnchecked(address, keyUtility, VERSION_BYTES_REGULAR);
            }
            return new AddressToCoin(hash160, amount);
        }
    }

    private ByteBuffer getHash160AsByteBufferFromBase58AddressUnchecked(String base58, KeyUtility keyUtility, int srcPos) {
        byte[] hash160 = getHash160fromBase58AddressUnchecked(base58, srcPos);
        ByteBuffer hash160AsByteBuffer = keyUtility.byteBufferUtility.byteArrayToByteBuffer(hash160);
        return hash160AsByteBuffer;
    }

    byte[] getHash160fromBase58AddressUnchecked(String base58, int srcPos) {
        byte[] decoded = Base58.decode(base58);
        byte[] hash160 = new byte[20];
        int toCopy = Math.min(decoded.length - srcPos, hash160.length);
        System.arraycopy(decoded, srcPos, hash160, 0, toCopy);
        return hash160;
    }

    @Nullable
    private Coin getCoinIfPossible(String[] lineSplitted, Coin defaultValue) throws NumberFormatException {
        if (lineSplitted.length > 1) {
            String amountString = lineSplitted[1];
            try {
                return Coin.valueOf(Long.valueOf(amountString));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
}
