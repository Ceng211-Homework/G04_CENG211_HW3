.PHONY: package run clean test

package:
	./mvnw package

run:
	./mvnw exec:java -Dexec.mainClass="com.g04.SlidingPuzzle.SlidingPuzzleApp"

clean:
	./mvnw clean

test:
	./mvnw test
