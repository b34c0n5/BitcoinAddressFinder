# BitcoinAddressFinder
<!-- =========================== Build & Environment =========================== -->
[![OpenJDK](https://img.shields.io/badge/OpenJDK-21-blue)]()
[![JUnit](https://img.shields.io/badge/tested%20with-JUnit4-yellow)]()
[![Assembly](https://github.com/bernardladenthin/BitcoinAddressFinder/actions/workflows/assembly.yml/badge.svg)](https://github.com/bernardladenthin/BitcoinAddressFinder/actions/workflows/assembly.yml)
[![Coverage](https://github.com/bernardladenthin/BitcoinAddressFinder/actions/workflows/coverage.yml/badge.svg)](https://github.com/bernardladenthin/BitcoinAddressFinder/actions/workflows/coverage.yml)
[![Matrix CI](https://github.com/bernardladenthin/BitcoinAddressFinder/actions/workflows/matrixci.yml/badge.svg)](https://github.com/bernardladenthin/BitcoinAddressFinder/actions/workflows/matrixci.yml)
<!-- =========================== Test Coverage =========================== -->
[![Coverage Status](https://coveralls.io/repos/github/bernardladenthin/BitcoinAddressFinder/badge.svg?branch=main)](https://coveralls.io/github/bernardladenthin/BitcoinAddressFinder?branch=main)
[![codecov](https://codecov.io/gh/bernardladenthin/BitcoinAddressFinder/graph/badge.svg?token=RRCR4ZC28T)](https://codecov.io/gh/bernardladenthin/BitcoinAddressFinder)
<!-- =========================== Package & Release =========================== -->
[![Maven Central](https://img.shields.io/maven-central/v/net.ladenthin/bitcoinaddressfinder.svg)](https://search.maven.org/artifact/net.ladenthin/bitcoinaddressfinder)
[![Release Date](https://img.shields.io/github/release-date/bernardladenthin/BitcoinAddressFinder)]()
[![Last Commit](https://img.shields.io/github/last-commit/bernardladenthin/BitcoinAddressFinder)]()
<!-- =========================== Quality & Analysis =========================== -->
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=bernardladenthin_BitcoinAddressFinder&metric=alert_status)](https://sonarcloud.io/dashboard?id=bernardladenthin_BitcoinAddressFinder)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=bernardladenthin_BitcoinAddressFinder&metric=code_smells)]()
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=bernardladenthin_BitcoinAddressFinder&metric=security_rating)]()
<!-- =========================== Security & Compliance =========================== -->
[![Known Vulnerabilities](https://snyk.io/test/github/bernardladenthin/BitcoinAddressFinder/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/bernardladenthin/BitcoinAddressFinder?targetFile=targetFile=pom.xml)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fbernardladenthin%2FBitcoinAddressFinder.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fbernardladenthin%2FBitcoinAddressFinder?ref=badge_shield)
[![Dependencies](https://img.shields.io/librariesio/github/bernardladenthin/BitcoinAddressFinder)](https://libraries.io/github/bernardladenthin/BitcoinAddressFinder)
<!-- =========================== License & Contribution =========================== -->
[![Apache 2.0 License](https://img.shields.io/badge/License-Apache%202.0-orange)](./LICENSE)
[![Contribute with Gitpod](https://img.shields.io/badge/Contribute%20with-Gitpod-908a85?logo=gitpod)](https://gitpod.io/#https://github.com/bernardladenthin/BitcoinAddressFinder)
<!-- =========================== Sustainability =========================== -->
[![Treeware](https://img.shields.io/badge/dynamic/json?color=brightgreen&label=Treeware&query=%24.total&url=https%3A%2F%2Fpublic.offset.earth%2Fusers%2Ftreeware%2Ftrees)](https://treeware.earth)
<!--
TODO:
[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/1234/badge)](https://bestpractices.coreinfrastructure.org/projects/1234)
-->

Free high performance tool for fast scanning random Bitcoin, Bitcoin Cash, Bitcoin SV, Litecoin, Dogecoin, Dash, Zcash (and many more) private keys and finding addresses with balance.
The main goal is to generate as fast as possible (Bitcoin/Altcoin) addresses using the JVM combined with OpenCL and check if the address (RIPEMD160 hash) was used/not used before. This includes possible hash collisions.

Copyright (c) 2017-2024 Bernard Ladenthin.

## Requirments
* Java 21 or newer. Java 8, 11, 17 is not supported anymore.

## Quickstart
1. Download the binary (jar) from https://github.com/bernardladenthin/BitcoinAddressFinder/releases
2. Download and extract the light database from https://github.com/bernardladenthin/BitcoinAddressFinder#use-my-prepared-database
3. Download a configuration set like
  1. https://github.com/bernardladenthin/BitcoinAddressFinder/blob/main/examples/logbackConfiguration.xml
  2. https://github.com/bernardladenthin/BitcoinAddressFinder/blob/main/examples/config_Find_1OpenCLDevice.js
  3. https://github.com/bernardladenthin/BitcoinAddressFinder/blob/main/examples/run_Find_1OpenCLDevice.bat
4. Put all in one directory like the following structure
  * Downloads
    * lmdb
      * data.mdb
      * lock.mdb
    * bitcoinaddressfinder-1.3.0-SNAPSHOT-jar-with-dependencies.jar
    * logbackConfiguration.xml
    * config_Find_1OpenCLDevice.js
    * run_Find_1OpenCLDevice.bat
5. Run the file run_Find_1OpenCLDevice.bat

## Features
* Support blockchain addresses which are based on [secp256k1](https://en.bitcoin.it/wiki/Secp256k1).
* Unit tested (trusted) open source which can be compiled easily from yourself.
* Vanitygen of bitcoin addresses using regex pattern.
* Runs completely offline. No internet required or used. You can run it in a bunker with an electric generator somewhere in nowhere and nobody knows it.
* No synchronisation necessary to run multiple instances. Random numbers are used and a search organization is not necessary. Just start on multiple computers.
* Check with a high performance database containing addresses if generated address are already in use.
* Portable, plattform independend, runs on JVM.
* Generate uncompressed and compressed keys at once.
* EC-Key generation via
  * Multiple CPU Threads
  * Multiple OpenCL devices (optional)

## Address database
The addresses will be inserted in a high performance database [LMDB](https://github.com/LMDB).
The database can be used to check if a generated addresses is ever used.

### Import
The importer read multiple txt/text files containing the following addresses in arbitrary order. Each line can contain a different format.
* P2PKH
  * bitcoin
  * bitcoin cash
  * bitcoin gold
  * blackcoin
  * dash
  * digibyte
  * dogecoin
  * feathercoin
  * litecoin
  * litecoin cash
  * namecoin
  * novacoin
  * reddcoin
  * vertcoin
  * ZCash
* P2WPKH
  * bitcoin Bech32

### Create the database by yourself
Useful txt/text file provider:
* http://blockdata.loyce.club/alladdresses/
* https://blockchair.com/dumps

### Export
The exporter writes all addresses in different formats:
* HexHash: The hash160 will be written encoded in hex without the amount. Optimal viewing with a viewer with a fixed width (e.g. HxD).
* FixedWidthBase58BitcoinAddress: The addresses will be written with a fixed width and without the amount. Optimal viewing with a viewer with a fixed width (e.g. HxD).
* DynamicWidthBase58BitcoinAddressWithAmount: The addresses will be written with amount.

### Use my prepared database
I am in the process of preparing databases filled with numerous Bitcoin and altcoin addresses (refer to the Import Support section for more information).
The sources of this information are confidential; however, you have the permission to extract any and all addresses.
Should there be any information you find lacking or have questions about, do not hesitate to ask.

#### Light database
* Light (5.12 GiB), Last update: April 5, 2025
  * Contains Bitcoin addresses whith amount and many altcoin addresses with amount.
  * Static amount of 0 is used to allow best compression.
  * Unique entries: 124362254
  * Mapsize: 5248 MiB
  * Time to create the database: ~9 hours
  * Link (3.47 GiB zip archive): http://ladenthin.net/lmdb_light.zip
  * Link extracted addresses as txt (2.51 GiB zip archive); open with HxD, set 42 bytes each line: http://ladenthin.net/LMDBToAddressFile_Light_HexHash.zip

<details>
<summary>Checksums lmdb_light.zip</summary>

* CRC32: 713075CD
* MD5: 171D41E23E8EC59980BA28465A8764BC
* RipeMD160: AE052E3000C3B9557A97FFAA2A9515FDE9F2B9D1
* SHA-1: D9C5EA07178A4094CFB823CDE537332548B5DCA2
* SHA-256: A35FCEDD4F2C2B1F08B27FC72E40217D8375E7B32810678D1D11598BE70D56B2
* SHA-512: F63C1A430BB572F48AE34D15B80CC61F146A9D1F7BEA88E2BE23A30BDC12CC621CCC952CAB83F951CFD430909DF6D7B12BF5FAA1ED325493C296892A37A4E695
* SHA3-224: 6E7F35BD938A668660AB5E8133FB46F22DBC5C97C90ADC324E6D8DD7
* SHA3-256: ACEFFF02E78C2AD12482A621F367FB3C9FC6EAE5F833947CBA67F386CC83105B
* SHA3-384: 5E8636133192C6151E405F0B43CEE41E4A8CC823982DABC58290083FC2917C822DADC75CF2442A24666B516CE19FED9C
* SHA3-512: B69BE8B2C6C37C5443B0760FC293A96DAB087F856DB6CE865A1855F324121A86F404231732FDEFC94EEAE600B64221D3D7541D362312F2674B932AFCB39A12A3

</details>

<details>
<summary>Checksums LMDBToAddressFile_Light_HexHash.zip</summary>

* CRC32: A463351D
* MD5: C5472DFCAB0C0AAA737B50E21B16FFD3
* RipeMD160: A02DF582D37F9060C008F4D30DA89FF2C92E9736
* SHA-1: 80BA38C2891BD96F6E8FA6831C39A53D8EACA693
* SHA-256: BC60657ED5D362ABD4B827E30901B5C09217CF747FCE237ACAF74A23DF75756E
* SHA-512: 34E763A56C8A4469C7FCB91288E428C13794B24C190D01B48C95EACB3DC0921CB9CAB7677D5AC29A6593CC8C7D688B5D56643E0F247F1E6B77F0D897FEA3A7FC
* SHA3-224: 7F0B47FDD1E3B8BF31143F10FE325034A4B81F5FFF646E37E18566AB
* SHA3-256: B4C1D2DDB554E6DE2705973D7CF8E828EE6C6D0FE156F67B85F4D81CF6A00023
* SHA3-384: A5D1DF7B613706C6424A85EE4344B30D51DF3CABFEDD539746440054E7E50329C6B3EB845A0A7F56E4412ABC978AFAA8
* SHA3-512: 72807FB367EC0B330C79A01AABFA732F5AA4C5E83949C1B195D9286F0E5B2222D2E9439FAA2E0B86879490C0DA872E7E34D0E625BFDB53C55D56B3CD1BC81ECA

</details>


#### Full database
* Full (57.0 GiB), Last update: April 5, 2025
  * Contains all Bitcoin addresses which are ever used and many altcoin addresses with and without amount.
  * Static amount of 0 is used to allow best compression.
  * Unique entries: 1349299900
  * Mapsize: 58368 MiB
  * Time to create the database: ~54 hours
  * Link (33.8 GiB zip archive): http://ladenthin.net/lmdb_full.zip
  * Link extracted addresses as txt (26.7 GiB zip archive); open with HxD, set 42 bytes each line: http://ladenthin.net/LMDBToAddressFile_Full_HexHash.zip

<details>
<summary>Checksums lmdb_full.zip</summary>

* CRC32: A7D4658A
* MD5: B17BA5DACB27FE5FE71373210FEA2D41
* RipeMD160: 186DE9B56E15214D8CFAD38BECC0A4A4E83671A1
* SHA-1: 0E9E8DE60714CDA4E4FDA6E7A6F295792180DDB2
* SHA-256: FDA76402364E4C4E6BDCD644C674EB76DD898C0B6337F6B8DB267B421E5BE0BC
* SHA-512: 06A30D28AD5C40035FFC7BE72B4FE41400A599AB15FA073D3642B8595AC292AD8FAACB1242DDC6DBD4070AB3CC842EE94F291E0A5E59AEE24EA41636622A9EFF
* SHA3-224: C2592F5588F4C33259632E9757845C683EAE2091CEA579E257218240
* SHA3-256: B02169C2F1AD7ECF21C0A4F5512AD824AC488E69F01878A386F55E82DD8BA5D3
* SHA3-384: 299FEF1C4CAC02BE19A66F5B32501042167D88F7A5BB6BB04A15D9F8B9D47476BF8127ABE05302A93A80244EC5852E57
* SHA3-512: 8DF569677C493978E2B44DEF6A37A91E1427FF368AE781CAA7E34D90D2ABF5B882B40026AC49B99267109E612EC5056B9F96EB5E4461A091A1A972F59D226877

</details>

<details>
<summary>Checksums LMDBToAddressFile_Full_HexHash.zip</summary>

* CRC32: C9B08F08
* MD5: B0823B1B02663356DE3B2EC5C800DE7A
* RipeMD160: 56FE29F2799F867969F7EF66A6577448A865C880
* SHA-1: 06E6C0177C2315528C589EE8B15CFEE81BEA5F4F
* SHA-256: BE58229A7523A6248ED013E1AE0CBD65925A3F02DBF5E416B5DC3F9E2A6446F9
* SHA-512: 381C035C84AC015C748A38FD78D61A9EDEB18F5B3269EB726C118D5F2C3F5767E2FE0C1934CAF649949F688806A3CF705F3A4C47FD70B7EAD5EFB9F70AE695CB
* SHA3-224: 5FFF77FA7F26AFCABF407337A87398A2222560D36CCE27E646BDF98C
* SHA3-256: 6BB7EB61CD90285633C1C9794E71A9D24D30DE6EEAEADB18E5D2E01AA0DAE69C
* SHA3-384: C834F256438F6E9C58D01B0A106A14D6EBFD9D97D0135B7CD609FFC938AE4AB32C0728998CE79EA4006BC5FB674FF99C
* SHA3-512: 5F0F9B8AA071F7875D68C206D7A908DCD79E193E549C84BE329BCEBF017A3EF444C4FD28A1F6DF30D500DFDD009D2C791B2DF97CD9BB56758557BAD292B08668

</details>

## Pages and projects to get lists (dumps) of PubkeyHash addresses
* https://github.com/Pymmdrza/Rich-Address-Wallet
* https://github.com/mycroft/chainstate
* https://github.com/graymauser/btcposbal2csv
* https://blockchair.com/dumps
* https://balances.crypto-nerdz.org/

## Find addresses
**Attention**: Do not use this software in a productive, non safe environment. A safe environment might be a dedicated computer with an air gap / disconnected network. A side-channel attack is possible and the software is optimized for performance and not constant-time. You may use a [paper wallet](https://en.bitcoin.it/wiki/Paper_wallet) for created vanity keys.

### Mixed modes
Find personal vanity addresses and check if addresses already exists in the lmdb can be used together.

### Key range
A key range can be defined (e.g. 64-bit) whereas the first (e.g. 192-bit (256-bit - 64-bit)) are zeroed. This can be used to creaty keys in a specific range to find keys in a known range (e.g. [Bitcoin Puzzle Transaction](https://privatekeys.pw/puzzles/bitcoin-puzzle-tx)).
This can be also used to proof that the software works.

### OpenCL
To increase the performance of the EC-key generation OpenCL can be used.
A common secret is transfered to the OpenCL device with a fixed grid size. Each OpenCL thread creates a different EC-Key because it add its thread-id to the secret. Therefore a range of EC-keys for a fixed grid size is created at once and will be transfered back to the main memory.
The CPU is now able to hash the x,y coordinate of the EC-key to create (Bitcoin/Altcoin) addresses.
The CPU doesn't spend most of its time for EC-key generation and can be used more efficient for hashing and database lookups.

The OpenCL mode has a Built-in self-test (BIST) to compare the OpenCL results with CPU based EC-Key generation. This allows an end user to verify it's OpenCL device is working properly.

#### Performance
The effective keys / s using uncompressed and compressed keys. OpenCL creates uncompressed keys only. A compressed key can be deduced easily from the uncompressed key.

GPU | privateKeyMaxNumBits | gridNumBits | effective keys / s
------------ | ------------- | ------------- | -------------
Nvidia RTX 2060 | 256 | 18 | 2160 k keys / s
Nvidia Quadro P2000 | 256 | 18 | 505 k keys /s
Nvidia Quadro P2000 | 64 | 18 | more than 1000 k keys /s (CPU was at its limit)
Nvidia Quadro M2000M | 256 | 16 | 205 k keys /s
Nvidia GTX 1050 Ti Mobile | 64 | 16 | more than 1000 k keys /s (CPU was at its limit)
Nvidia GTX 1050 Ti Mobile | 256 | 16 | 550 k keys /s

## Collision probability and security concerns
It's impossible to find collisions, isn't it? 
Please find the answear for vulnerability questions somewhere else:
* https://crypto.stackexchange.com/questions/33821/how-to-deal-with-collisions-in-bitcoin-addresses
* https://crypto.stackexchange.com/questions/47809/why-havent-any-sha-256-collisions-been-found-yet
* https://github.com/treyyoder/bitcoin-wallet-finder#results
* https://github.com/Frankenmint/PKGenerator_Checker#instructions
* https://github.com/Xefrok/BitBruteForce-Wallet#requeriments

## Similar projects
* The [LBC](https://lbc.cryptoguru.org/) is optimized to find keys for the [Bitcoin Puzzle Transaction](https://privatekeys.pw/puzzles/bitcoin-puzzle-tx). It require communication to a server, doesn't support altcoin and pattern matching.
* https://privatekeys.pw/scanner/bitcoin
* https://allprivatekeys.com/get-lucky
* https://allprivatekeys.com/vanity-address
* https://github.com/treyyoder/bitcoin-wallet-finder
* https://github.com/albertobsd/keyhunt
* https://github.com/mvrc42/bitp0wn
* https://github.com/JeanLucPons/BTCCollider
* https://github.com/JeanLucPons/VanitySearch
* https://github.com/JamieAcharya/Bitcoin-Private-Key-Finder
* https://github.com/mingfunwong/all-bitcoin-private-key
* https://github.com/Frankenmint/PKGenerator_Checker
* https://github.com/Henshall/BitcoinPrivateKeyHunter
* https://github.com/Xefrok/BitBruteForce-Wallet
* https://github.com/Isaacdelly/Plutus
* https://github.com/Noname400/Hunt-to-Mnemonic
* https://github.com/Py-Project/Bitcoin-wallet-cracker
* https://github.com/johncantrell97/bip39-solver-gpu

### Deep learning private key prediction
An export of the full database can be used to predict private keys with deep learning. A funny idea: https://github.com/DRSZL/BitcoinTensorFlowPrivateKeyPrediction

## Known issues
If you have a laptop like HP ZBook G3/G4/G5 "hybrid graphics" mode is very slow because of the shared memory. Please select in the BIOS "discrete graphics".

## Future improvements
- Refactor the entire key generation infrastructure to support a key provider. This provider should be configurable to supply private keys from various sources, such as Random, Secrets File, Key Range, and others. All consumers should retrieve keys from this provider.

### KeyProvider
- Key generation within a specific key range. See #27
Wished from themaster:
```
"privateKeyStartHex" : "0000000000000000000000000000000000000000000000037e26d5b1f3afe216"
"privateKeyEndHex" : "0000000000000000000000000000000000000000000000037e26d5b1ffffffff"
```
Wished from Ulugbek:
```
// Search started from given address. Would be nice if it can save last position...
"sequentalSearch" : true,
"startAddress" : xxxxxxxx,

// Random search with batches, here 100000. I,e. some random number is found and after 100000 sequental addresses should be checked.
"searchAsBatches" : true,
"searchBatchQuantity" : 100000,


// Random search within Address Space, with batches, here 100000.
"searchAsBatches" : true,
"searchAddressStart" : xxxxxxx,
"searchAddressEnd" : xxxxxxxy,
"searchBatchQuantity" : 100000
```

- Incomplete Seed-Phrase as Private KeyProvider. Wished from @mirasu See #38
- Socket KeyProvider for independend KeyProvider via byte protocol
  - Ideas might be a screen recorder and use the visible screen downscaled as 256 bit input
- KeyProvider must get the grid size to increment properly on incremental based Producer
- ExecutableKeyProvider gets data from stdout

-----
## Legal
This software should not be configured and used to find (Bitcoin/Altcoin) address hash (RIPEMD-160) collisions and use (steal) credit from third-party (Bitcoin/Altcoin) addresses.
This mode might be allowed to recover lost private keys of your own public addresses only.

Another mostly legal use case is a check if the (Bitcoin/Altcoin) addresses hash (RIPEMD-160) is already in use to prevent yourself from a known hash (RIPEMD-160) collision and double use.

Some configurations are not allowed in some countries (definitely not complete):
* Germany: § 202c Vorbereiten des Ausspähens und Abfangens von Daten
* United States of America (USA): Computer Fraud and Abuse Act (CFAA)

## License

It is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for the full license text.
Some subprojects have a different license.

This package is [Treeware](https://treeware.earth). If you use it in production, then we ask that you [**buy the world a tree**](https://plant.treeware.earth/bernardladenthin/BitcoinAddressFinder) to thank us for our work. By contributing to the Treeware forest you’ll be creating employment for local families and restoring wildlife habitats.

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fbernardladenthin%2FBitcoinAddressFinder.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Fbernardladenthin%2FBitcoinAddressFinder?ref=badge_large)
