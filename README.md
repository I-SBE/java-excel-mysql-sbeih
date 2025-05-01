# Java Excel to MySQL Data Transfer

## Description

This project is a Java desktop application that transfers data from Excel files into a MySQL database, using an intermediate XML file format. It includes a graphical user interface (GUI) that allows users to:

- Create a new MySQL database
- Enter and store database connection credentials
- Convert Excel data to XML
- Import XML data into MySQL
- View imported data

## Features

- 📊 Excel to XML conversion
- 🛢️ XML to MySQL import
- 🖥️ Swing-based GUI
- 🔐 Secure credential input
- 💾 Local configuration support

## Requirements

- Java JDK 8 or above
- Apache POI (for Excel reading)
- MySQL Connector/J (for MySQL connection)
- JAXB (for XML processing)
- A running MySQL server

## Setup Instructions

1. Clone this repository:

```bash
git clone https://github.com/I-SBE/java-excel-mysql-sbeih.git
cd java-excel-mysql-sbeih
```

2. Import the project into your IDE (e.g., IntelliJ IDEA, Eclipse).

3. Add the required libraries to the classpath:
   - `poi-ooxml.jar`
   - `mysql-connector-java.jar`
   - `jaxb-api.jar`, `jaxb-runtime.jar`

4. Compile and run the project.

## Manual Compilation & Execution (for advanced users)

If you prefer using the command line (CMD, Terminal, or PowerShell), follow these steps:

1. Compile the project:

```bash
javac -d bin -cp "lib/mysql-connector-j-9.2.0.jar" src/app/MainAPP.java src/controllers/*.java src/models/*.java src/xml/*.java src/view/*.java
```

2. Create the executable JAR:

```bash
jar cfe DB_verwaltung.jar app.MainAPP -C bin .
```

3. Run the JAR file:

```bash
java -cp "DB_verwaltung.jar;lib/mysql-connector-j-9.2.0.jar" app.MainAPP
```

## Usage

1. Launch the GUI application.
2. Enter MySQL connection details (host, port, user, password).
3. Select the Excel file to convert.
4. The app will create an XML file and import it into the database.
5. You can verify the data from within the app or via your MySQL client.

## Author

I.Sbeih (GitHub: [I-SBE](https://github.com/I-SBE))

## License

This project is licensed under the MIT License - see the LICENSE file for details.
