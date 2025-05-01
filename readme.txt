
in CMD, Terminal or PowerShell:

1- Compile the files using the following command:

	javac -d bin -cp "lib/mysql-connector-j-9.2.0.jar" src/app/MainAPP.java src/controllers/*.java src/models/*.java src/xml/*.java src/view/*.java



2- Create a JAR file using the following command:

	jar cfe DB_verwaltung.jar app.MainAPP -C bin .


3- Run or execute the newly created JAR file (DB_verwaltung.jar) using the following command:

	java -cp "DB_verwaltung.jar;lib/mysql-connector-j-9.2.0.jar" app.MainAPP
