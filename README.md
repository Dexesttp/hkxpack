# HKXPack version 0.1.6-beta

HKXPack is a tool to 'pack' and 'unpack' to and from hkx files, which is the Havok compressed file format.

It was made to be used with the hk\_2014.1.0-r1 file format, commonly used in Fallout 4. Given the opportunity, support for hk\_2010.2.0-r1 or other hkx formats may be added.

The output format is intended to be TagXML, output by [figment's hkxcmd](https://github.com/figment/hkxcmd).

~~Some other output formats may be added in the future (e.g. XAF/XMM or FBX for animations), but these formats aren't the focus of the tool.~~
Note : this behavior now has been moved to [the HKXAnim repository](https://github.com/Dexesttp/hkxanim).

## Download

You can download the latest release [here](https://github.com/Dexesttp/hkxpack/releases).

## How to

To use this tool, you will need a Java Runtime Environement (jre) for Java 8 or higher.

Don't forget to put quotes around your path if you have a path with spaces !

### Unpack the files

Use the command `java -jar hkxpack-cli.jar unpack <filename>.hkx` to extract the file into `<filename>.xml`.

You can also use `java -jar hkxpack-cli.jar unpack <directory>` to extract the directory's HKX files into XML files, under the `out/` folder

### Pack the files

Use the command `java -jar hkxpack-cli.jar pack <filename>.xml` to pack the file into `<filename>.hkx`.

You can also use `java -jar hkxpack-cli.jar pack <directory>` to extract the directory's XML files into HKX files, under the `out/` folder

### Options

Common options are :

- `-q` for quiet output.
- `-v` for verbose output.
- `-o` to select either the output file or the output directory.

Advanced options are :

- `-t` to set the maximum number of threads to use while walking directories. The default is 32.
- `-b` to set the output buffer size on packing.
- `-d` for debug output, mostly prints advanced infos on error.

## Known bugs

- Sometimes the tool won't work on directories if you're giving a full path to a subdirectory of your current directory. Try changing your current location.
- The tool has problems extracting some cloth data nodes, especially in googles physics or similar things - the data will be valid but some of it will be missing.

## Developing

### Compilation

This project can be built using Maven.
Use the command by running the following Maven goals `mvn clean package`.
The compiled jar will then be available under /cli/target/hkxpack-cli.jar

### Eclipse

Import this project as a standard Maven project.

### Contribution rules

See [CONTRIBUTING.md](CONTRIBUTING.md) for a list of contribution rules.

## General information

The tool was written for Java 8 using the Oracle JavaSE JDK (jdk1.8.0_66), and was originally intended to be a proof of concept.

You may find all available information under /doc. It contains mostly analysis and comparison of the hkx files as well as some software architecture concepts of the hkxpack software.

You might find out that some of the data required to run the tool is missing. That's because the 'classXML' files are obtained from the Fallout 4 game. You'll need the game to retrieve the files and, to prevent possible legal issues, they're not included in the sources. The way to obtain these files is currently built specifically for one machine, and its distribution isn't a priority.

## License

MIT, See the LICENSE file.

## Contributions

Thanks to Aerisam for his help understanding the animation data.  
Thanks to CPU for his overall help with making this project.  
Thanks to Figment for his help to obtain information about the hkx classes. Without him, none of this would be possible.  
Thanks to Fore for his FNIS tool that allowed slight modifications of old files, and for sharing his knowledge of behaviours with me.  
Thanks to Neomonkeus for making the Maven integration.  
Thanks to Orvid for his advice about how to make both working and clean code.  
Thanks to Philjord for adapting data handling from files to buffers.  
Thanks to the people from the NifTools IRC for helping with making this project and their overall support.
