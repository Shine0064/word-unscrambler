# Word Unscrambler 1.0
Word unscrambling CLI tool written in Java.

Includes [DWYL words_alpha.txt](https://github.com/dwyl/english-words) as the default wordlist.
Can be used with any"\n" delimited wordlist.

## Usage
```shell
java -jar word-unscrambler-1.0-all.jar -h
``` 

| **Short flag** |  **Long flag**   | **Arguments** |                                                                            **Description**                                                                             | **Default Value** |
|:--------------:|:----------------:|:-------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------:|
|       -h       |      --help      |               |                                                                        Displays a help message.                                                                        |        N/A        |
|       -d       |   --dictionary   |   < path >    |                                                           Sets the used word list to relative path < path >                                                            |  words_alpha.txt  |
|       -m       | --minimum-length |  < length >   |                                                   Sets the minimum length a resulting word must have to < length >.                                                    |         2         |
|       -M       | --maximum-length |  < length >   |                                                   Sets the maximum length a resulting word must have to < length >.                                                    |        10         |
|       -w       |      --word      |   < word >    | Sets the word to be unscrambled to < word >.<br>Bypasses waiting for user input and quits after printing the result to stdout.<br>Primarily meant for usage in scripts |        N/A        |

# Building
Simply clone the repository, and run
```shell
./gradlew shadowJar
```
in the root directory. The build artifact will be output to `./build/libs/word-unscrambler-1.0-all.jar`.