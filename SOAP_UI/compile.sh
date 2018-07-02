#!/bin/bash
file_name="QAMinds"
script_path="/Users/jesuseduardomoraperez/Documents/QAMinds/SOAP_UI"
script_name="${file_name}.groovy"
jar_name="${file_name}.jar"
soap_ext_dir="/Applications/SoapUI-5.4.0.app/Contents/java/app/bin/ext"

echo "Moving to script path: $script_path"
cd ${script_path}

echo "GROOVY SCRIPT NAME: $script_name"
echo "Cleaning bin directory"
rm -r ./bin

echo "Cleaning classes directory"
rm -r ./classes

echo "Removing previous library from SOAP UI (Avoid circular dependencies)"
rm $soap_ext_dir/$jar_name

echo "Creating classes directory"
mkdir ./classes

echo "Creating bin directory"
mkdir ./bin

echo "Setting CLASSPATH: Use soap ui classes and current extensions"
CLASSPATH="/Applications/SoapUI-5.4.0.app/Contents/java/app/bin"

echo "Compiling $script_name, class files will be stored at .\classes"
groovyc -d classes $script_name

echo "Creating jar file $jar_name at .\bin"
jar -cvf ./bin/$jar_name -C classes .

echo "Copying jar to $soap_ext_dir"
copy /Y /V ./bin/$jar_name $soap_ext_dir
