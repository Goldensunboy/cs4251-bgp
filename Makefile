all:
	javac src/*.java -s bin
	java -classpath bin BGPSimFrame

clean:
	rm -rf bin/*.class
