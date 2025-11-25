.PHONY: package run clean

package:
	./mvnw package

run:
	./mvnw exec:java -Dexec.mainClass="com.g04.app.EsportsManagementApp"

clean:
	./mvnw clean

test:
	./mvnw test
